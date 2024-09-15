package edu.udb.desafio2dsm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import edu.udb.desafio2dsm.adapters.ComidaAdapter
import edu.udb.desafio2dsm.models.Comidas

class ComidaActivity : AppCompatActivity() {

    private lateinit var rvComidas: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var comidaList: MutableList<Comidas>
    private lateinit var storage: FirebaseStorage
    private var cartItems: MutableList<Comidas> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comida)

        rvComidas = findViewById(R.id.rvComidas)
        rvComidas.layoutManager = LinearLayoutManager(this)

        storage = FirebaseStorage.getInstance()
        comidaList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("comidas")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comidaList.clear()
                for (comidaSnapshot in snapshot.children) {
                    val id = comidaSnapshot.child("id").getValue(Int::class.java)
                    val nombre = comidaSnapshot.child("nombre").getValue(String::class.java)
                    val precio = comidaSnapshot.child("precio").getValue(Double::class.java)
                    if (id != null && nombre != null && precio != null ) {
                        val comida = Comidas(id, nombre, precio)
                        comidaList.add(comida)
                        Log.d("ComidaActivity", "Comida añadida: $nombre")
                    }
                }

                Log.d("ComidaActivity", "Total de comidas: ${comidaList.size}")

                rvComidas.adapter = ComidaAdapter(comidaList) { comida ->
                    addToCart(comida)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ComidaActivity", "Error al leer los datos.", error.toException())
            }
        })

        findViewById<Button>(R.id.carrito).setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putParcelableArrayListExtra("cart_items", ArrayList(cartItems))
            startActivity(intent)
        }
    }

    private fun addToCart(item: Comidas) {
        if (!cartItems.contains(item)) {
            cartItems.add(item)

            Toast.makeText(this, "${item.nombre} fue agregado al carrito correctamente", Toast.LENGTH_LONG).show()
        }
        Log.d("ComidaActivity", "Item añadido al carrito: ${item.nombre}")
    }


}
