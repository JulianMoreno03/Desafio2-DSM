package edu.udb.desafio2dsm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import edu.udb.desafio2dsm.adapters.ComidaAdapter
import edu.udb.desafio2dsm.models.Comidas

class ComidaActivity : AppCompatActivity() {

    private lateinit var rvComidas: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var comidaList: MutableList<Comidas>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comida)

        // Inicializa el RecyclerView y configura su LayoutManager
        rvComidas = findViewById(R.id.rvComidas)
        rvComidas.layoutManager = LinearLayoutManager(this)

        // Inicializa la lista y la referencia de Firebase
        comidaList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("comidas")

        // Escucha cambios en la base de datos
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comidaList.clear()
                for (comidaSnapshot in snapshot.children) {
                    val id = comidaSnapshot.child("id").getValue(Int::class.java)
                    val nombre = comidaSnapshot.child("nombre").getValue(String::class.java)
                    val precio = comidaSnapshot.child("precio").getValue(Double::class.java)

                    if (id != null && nombre != null && precio != null) {
                        val comida = Comidas(id, nombre, precio)
                        comidaList.add(comida)
                        Log.d("ComidaActivity", "Comida añadida: $nombre")
                    }
                }

                Log.d("ComidaActivity", "Total de comidas: ${comidaList.size}")

                // Configura el adaptador con la lista actualizada
                rvComidas.adapter = ComidaAdapter(comidaList)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("ComidaActivity", "Error al leer los datos.", error.toException())
            }
        })
    }
}
