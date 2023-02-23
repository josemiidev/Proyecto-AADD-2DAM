package dev.josemii.proyectoaadd2damretrofit.Miscelanea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.josemii.proyectoaadd2damretrofit.Clases.Cliente
import dev.josemii.proyectoaadd2damretrofit.R

class ClienteAdapter (val clientes: List<Cliente>) : RecyclerView.Adapter<ClienteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClienteViewHolder(layoutInflater.inflate(R.layout.item_cliente, parent, false))
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val item = clientes[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("id_cliente", item.id)
            it?.findNavController()?.navigate(R.id.nav_cliente_view, bundle)
        }
    }

    override fun getItemCount(): Int = clientes.size

}