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
import dev.josemii.proyectoaadd2damretrofit.Clases.Cliente
import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import dev.josemii.proyectoaadd2damretrofit.Miscelanea.ClienteAdapter
import dev.josemii.proyectoaadd2damretrofit.Miscelanea.CuentaAdapter
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceCliente
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceCuenta
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentClientesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ClientesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientesFragment : Fragment() {
    private lateinit var adapter: ClienteAdapter
    private val listaClientes = mutableListOf<Cliente>()
    private lateinit var binding :FragmentClientesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        llamarAPI()

        binding.btnNuevo.setOnClickListener {
            findNavController().navigate(R.id.nav_cliente_view)
        }
    }
    private fun initRecyclerView(){
        adapter = ClienteAdapter(listaClientes)
        binding.rvClientes.layoutManager = LinearLayoutManager(context)
        binding.rvClientes.adapter = adapter
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
            val call: Response<List<Cliente>> = getRetrofit().create(APIServiceCliente::class.java).getCliente()
            val clientes: List<Cliente> ?= call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    val su = clientes ?: emptyList()
                    listaClientes.clear()
                    listaClientes.addAll(su)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }
}