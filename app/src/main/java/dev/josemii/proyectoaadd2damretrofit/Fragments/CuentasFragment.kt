package dev.josemii.proyectoaadd2damretrofit.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import dev.josemii.proyectoaadd2damretrofit.Miscelanea.CuentaAdapter
import dev.josemii.proyectoaadd2damretrofit.Miscelanea.SucursalAdapter
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceCuenta
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceSucursal
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentCuentasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 * Use the [CuentasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CuentasFragment : Fragment() {
    private lateinit var adapter: CuentaAdapter
    private val listaCuentas = mutableListOf<Cuenta>()
    private lateinit var binding : FragmentCuentasBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuentasBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        llamarAPI()

        binding.btnNuevo.setOnClickListener {
            findNavController().navigate(R.id.nav_cuenta_view)
        }
    }
    private fun initRecyclerView(){
        adapter = CuentaAdapter(listaCuentas)
        binding.rvCuentas.layoutManager = LinearLayoutManager(context)
        binding.rvCuentas.adapter = adapter
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.28.54.69:8000/")
            .addConverterFactory(
                GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            ))
            .build()
    }
    private fun showError(){
        Toast.makeText(context,"Error de carga", Toast.LENGTH_LONG).show()
    }
    fun llamarAPI(){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Cuenta>> = getRetrofit().create(APIServiceCuenta::class.java).getCuentas()
            val cuentas: List<Cuenta> ?= call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    val su = cuentas ?: emptyList()
                    listaCuentas.clear()
                    listaCuentas.addAll(su)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }
}