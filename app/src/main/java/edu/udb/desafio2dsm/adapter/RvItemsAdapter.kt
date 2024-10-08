package edu.udb.desafio2dsm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.udb.desafio2dsm.R
import edu.udb.desafio2dsm.models.Comidas

class ComidaAdapter(
    private val comidasList: List<Comidas>,
    private val onAddToCart: ((Comidas) -> Unit)? = null
) : RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_items, parent, false)
        return ComidaViewHolder(view, onAddToCart)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = comidasList[position]
        holder.bind(comida)
    }

    override fun getItemCount(): Int = comidasList.size

    class ComidaViewHolder(
        itemView: View,
        private val onAddToCart: ((Comidas) -> Unit)?
    ) : RecyclerView.ViewHolder(itemView) {
        private val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        private val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
        private val btnAddToCart: Button = itemView.findViewById(R.id.btnAddToCart)


        fun bind(comida: Comidas) {
            txtNombre.text = comida.nombre
            txtPrecio.text = "$ ${comida.precio}"



            btnAddToCart.setOnClickListener {
                onAddToCart?.invoke(comida)
            }
        }
    }
}
