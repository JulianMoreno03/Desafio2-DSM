package edu.udb.desafio2dsm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.udb.desafio2dsm.adapters.OrdenAdapter
import edu.udb.desafio2dsm.models.Comidas
import edu.udb.desafio2dsm.models.Orden

class OrdenesActivity : AppCompatActivity() {

    private lateinit var rvOrdenes: RecyclerView
    private lateinit var ordenAdapter: OrdenAdapter
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var ordenesList: MutableList<Orden> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordenes)

        rvOrdenes = findViewById(R.id.rvOrdenes)
        rvOrdenes.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        loadOrders()
    }

    private fun loadOrders() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.e("OrdenesActivity", "No se pudo obtener el ID del usuario")
            return
        }

        database.child("ordenes")
            .orderByChild("userId")
            .equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ordenesList.clear()
                    if (snapshot.exists()) {
                        for (ordenSnapshot in snapshot.children) {
                            try {
                                val itemsSnapshot = ordenSnapshot.child("items")
                                val itemsList = mutableListOf<Comidas>()

                                // Iterar sobre los items dentro de la orden
                                for (itemSnapshot in itemsSnapshot.children) {
                                    // Deserialización flexible
                                    val id = itemSnapshot.child("id").getValue(Int::class.java) ?: 0
                                    val nombre = itemSnapshot.child("nombre").getValue(String::class.java) ?: ""
                                    val precio = itemSnapshot.child("precio").getValue(Double::class.java) ?: 0.0
                                    val cantidad = itemSnapshot.child("cantidad").getValue(Int::class.java) ?: 1

                                    val comida = Comidas(id, nombre, precio, cantidad)
                                    itemsList.add(comida)
                                }

                                val orden = Orden(
                                    userId = ordenSnapshot.child("userId").getValue(String::class.java) ?: "",
                                    items = itemsList
                                )

                                ordenesList.add(orden)
                            } catch (e: Exception) {
                                Log.e("OrdenesActivity", "Error al procesar la orden: ${e.message}")
                            }
                        }

                        // Verifica si el adaptador está inicializado
                        if (!::ordenAdapter.isInitialized) {
                            ordenAdapter = OrdenAdapter(ordenesList)
                            rvOrdenes.adapter = ordenAdapter
                        } else {
                            // Solo notifica el cambio de datos si el adaptador ya está inicializado
                            ordenAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("OrdenesActivity", "No se encontraron órdenes para este usuario")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("OrdenesActivity", "Error al cargar las órdenes: ${error.message}")
                }
            })
    }
}
