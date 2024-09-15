package edu.udb.desafio2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.udb.desafio2dsm.adapters.CartAdapter
import edu.udb.desafio2dsm.models.Comidas

class CartActivity : AppCompatActivity() {

    private lateinit var rvCartItems: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private var comidasList: MutableList<Comidas> = mutableListOf()
    private lateinit var btnPedir: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rvCartItems = findViewById(R.id.rvCartItems)
        rvCartItems.layoutManager = LinearLayoutManager(this)
        btnPedir = findViewById(R.id.btnPedir)

        // Obtener los datos del carrito desde el Intent
        val items = intent.getParcelableArrayListExtra<Comidas>("cart_items")
        if (items != null) {
            comidasList.addAll(items)
        }
        // Inicializa Firebase Auth y Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        cartAdapter = CartAdapter(comidasList) { comida ->
            removeFromCart(comida)
        }
        rvCartItems.adapter = cartAdapter

        btnPedir.setOnClickListener {
            placeOrder()
        }
    }

    private fun removeFromCart(comida: Comidas) {
        val item = comidasList.find { it.id == comida.id }
        if (item != null) {
            if (item.cantidad > 1) {
                item.cantidad--
                cartAdapter.notifyDataSetChanged()
            } else {
                comidasList.remove(item)
                cartAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun placeOrder() {
        val userId = auth.currentUser?.uid ?: return
        val orderId = database.child("ordenes").push().key ?: return
        val order = mapOf(
            "userId" to userId,
            "items" to comidasList.map {
                mapOf(
                    "id" to it.id,
                    "nombre" to it.nombre,
                    "precio" to it.precio,
                    "cantidad" to it.cantidad
                )
            }
        )

        database.child("ordenes").child(orderId).setValue(order)
            .addOnSuccessListener {

                Toast.makeText(this, ("Pedido Realizado con exito"), Toast.LENGTH_LONG).show()
                goToMenuActivity();
                comidasList.clear()
                cartAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // Manejar el error
            }


    }

    // Función para ir a la MenuActivity
   private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

}
