package dev.josemii.proyectoaadd2damretrofit.Miscelanea

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import dev.josemii.proyectoaadd2damretrofit.databinding.ItemCuentaBinding

class CuentaViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemCuentaBinding.bind(view)

    fun render(cuenta: Cuenta){
        binding.tvReferencia.text = sucursal.referencia
        binding.tvPoblacion.text = sucursal.poblacion
        binding.tvProvincia.text = sucursal.provincia
    }
}