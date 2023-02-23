package dev.josemii.proyectoaadd2damretrofit.Miscelanea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import dev.josemii.proyectoaadd2damretrofit.R

class CuentaAdapter(val cuentas: List<Cuenta>) : RecyclerView.Adapter<CuentaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CuentaViewHolder(layoutInflater.inflate(R.layout.item_cuenta, parent, false))
    }

    override fun onBindViewHolder(holder: CuentaViewHolder, position: Int) {
        val item = cuentas[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("id_cuenta", item.id)
            it?.findNavController()?.navigate(R.id.nav_cuenta_view, bundle)
        }
    }

    override fun getItemCount(): Int = cuentas.size
}