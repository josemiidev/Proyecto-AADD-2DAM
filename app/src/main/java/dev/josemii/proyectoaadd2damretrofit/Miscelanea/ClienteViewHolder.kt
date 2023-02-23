package dev.josemii.proyectoaadd2damretrofit.Miscelanea

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.josemii.proyectoaadd2damretrofit.Clases.Cliente
import dev.josemii.proyectoaadd2damretrofit.databinding.ItemClienteBinding

class ClienteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemClienteBinding.bind(view)

    fun render(cliente: Cliente){
        binding.tvReferencia.text = sucursal.referencia
        binding.tvPoblacion.text = sucursal.poblacion
        binding.tvProvincia.text = sucursal.provincia
    }
}