package edu.udb.desafio2dsm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.udb.desafio2dsm.R
import edu.udb.desafio2dsm.models.Orden
import edu.udb.desafio2dsm.models.Comidas

class OrdenAdapter(private val ordenesList: List<Orden>) : RecyclerView.Adapter<OrdenAdapter.OrdenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_items, parent, false)
        return OrdenViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdenViewHolder, position: Int) {
        val orden = ordenesList[position]
        holder.bind(orden)
    }

    override fun getItemCount(): Int = ordenesList.size

    class OrdenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtTotalItems: TextView = itemView.findViewById(R.id.txtTotalItems)
        private val txtItemsDetails: TextView = itemView.findViewById(R.id.txtItemsDetails)

        fun bind(orden: Orden) {
            val itemsDetails = orden.items.joinToString(separator = "\n") { "${it.nombre} - \$${it.precio}" }
            txtTotalItems.text = "Total Items: ${orden.items.size}"
            txtItemsDetails.text = itemsDetails
        }
    }
}
