package dev.josemii.proyectoaadd2damretrofit.Miscelanea

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import dev.josemii.proyectoaadd2damretrofit.R

class SucursalAdapter (val sucursales: List<Sucursal>) : RecyclerView.Adapter<SucursalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SucursalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SucursalViewHolder(layoutInflater.inflate(R.layout.item_sucursal,parent,false))
    }

    override fun onBindViewHolder(holder: SucursalViewHolder, position: Int) {
        val item = sucursales[position]
        holder.render(item)
        /*holder.itemView.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("dni", item.dni)
            bundle.putString("nombre", item.nombre)
            bundle.putString("apellidos", item.apellidos)
            bundle.putString("sexo", item.sexo)
            it?.findNavController()?.navigate(R.id.nav_modificar, bundle)
        }*/
    }

    override fun getItemCount(): Int = sucursales.size
}