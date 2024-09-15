package edu.udb.desafio2dsm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.udb.desafio2dsm.R
import edu.udb.desafio2dsm.models.Comidas
import edu.udb.desafio2dsm.models.Orden

class OrdenAdapter(private val ordenesList: List<Orden>) : RecyclerView.Adapter<OrdenAdapter.OrdenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_ordenes_item, parent, false)
        return OrdenViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdenViewHolder, position: Int) {
        val orden = ordenesList[position]
        holder.bind(orden)
    }

    override fun getItemCount(): Int = ordenesList.size

    class OrdenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Definir TextViews para mostrar nombre, precio y cantidad
        private val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        private val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
        private val txtCantidad: TextView = itemView.findViewById(R.id.txtCantidad)

        // Bind para asignar los valores de la comida
        fun bind(orden: Orden) {
            val firstItem = orden.items[0] // Mostrar el primer elemento (podrías iterar si quieres mostrar todos)
            txtNombre.text = "Nombre: ${firstItem.nombre}"
            txtPrecio.text = "Precio: \$${firstItem.precio}"
            txtCantidad.text = "Cantidad: ${firstItem.cantidad}"
        }
    }
}
