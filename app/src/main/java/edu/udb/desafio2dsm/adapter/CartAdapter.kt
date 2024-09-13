package edu.udb.desafio2dsm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.udb.desafio2dsm.R
import edu.udb.desafio2dsm.models.Comidas

class CartAdapter(
    private val comidasList: MutableList<Comidas>,
    private val onRemoveItem: (Comidas) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_cart_item, parent, false)
        return CartViewHolder(view, onRemoveItem)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val comida = comidasList[position]
        holder.bind(comida)
    }

    override fun getItemCount(): Int = comidasList.size

    class CartViewHolder(
        itemView: View,
        private val onRemoveItem: (Comidas) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        private val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
        private val btnBorrar: Button = itemView.findViewById(R.id.btnBorrar)

        fun bind(comida: Comidas) {
            txtNombre.text = comida.nombre
            txtPrecio.text = "$ ${comida.precio} x ${comida.cantidad}"
            btnBorrar.setOnClickListener {
                onRemoveItem(comida)
            }
        }
    }
}
