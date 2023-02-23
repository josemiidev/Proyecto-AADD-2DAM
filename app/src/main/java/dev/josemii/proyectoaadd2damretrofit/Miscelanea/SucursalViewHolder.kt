package dev.josemii.proyectoaadd2damretrofit.Miscelanea

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import dev.josemii.proyectoaadd2damretrofit.databinding.ItemSucursalBinding

class SucursalViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = ItemSucursalBinding.bind(view)

    fun render(sucursal: Sucursal){
        binding.tvReferencia.text = sucursal.referencia
        binding.tvPoblacion.text = sucursal.poblacion
        binding.tvProvincia.text = sucursal.provincia
    }
}