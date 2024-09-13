package edu.udb.desafio2dsm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.udb.desafio2dsm.adapters.CartAdapter
import edu.udb.desafio2dsm.models.Comidas

class CartActivity : AppCompatActivity() {

    private lateinit var rvCartItems: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private var comidasList: MutableList<Comidas> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rvCartItems = findViewById(R.id.rvCartItems)
        rvCartItems.layoutManager = LinearLayoutManager(this)

        // Obtener los datos del carrito desde el Intent
        val items = intent.getParcelableArrayListExtra<Comidas>("cart_items")
        if (items != null) {
            comidasList.addAll(items)
        }

        cartAdapter = CartAdapter(comidasList) { comida ->
            removeFromCart(comida)
        }
        rvCartItems.adapter = cartAdapter
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
}
