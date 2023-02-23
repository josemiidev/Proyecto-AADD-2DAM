package dev.josemii.proyectoaadd2damretrofit.Miscelanea

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.databinding.ItemCuentaBinding

class CuentaViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemCuentaBinding.bind(view)

    fun render(cuenta: Cuenta){
        binding.tvCCC.text = cuenta.ccc
        binding.tvSaldo.text = cuenta.saldo.toString()

        if(cuenta.enAlta){
            binding.ivEstado.setImageResource(R.drawable.alta)
        }else{
            binding.ivEstado.setImageResource(R.drawable.baja)
        }
    }
}