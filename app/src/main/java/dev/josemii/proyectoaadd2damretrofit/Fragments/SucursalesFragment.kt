package dev.josemii.proyectoaadd2damretrofit.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import dev.josemii.proyectoaadd2damretrofit.Miscelanea.SucursalAdapter
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceSucursal
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentSucursalesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass.
 * Use the [SucursalesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SucursalesFragment : Fragment() {
    private lateinit var adapter: SucursalAdapter
    private val listaSucursales = mutableListOf<Sucursal>()
    private lateinit var binding : FragmentSucursalesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSucursalesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        llamarAPI()
    }
    private fun initRecyclerView(){
        adapter = SucursalAdapter(listaSucursales)
        binding.rvSucursales.layoutManager = LinearLayoutManager(context)
        binding.rvSucursales.adapter = adapter
    }
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://172.28.54.69:8000/")
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            ))
            .build()
    }
    private fun showError(){
        Toast.makeText(context,"Error de carga",Toast.LENGTH_LONG).show()
    }
    fun llamarAPI(){
        CoroutineScope(Dispatchers.IO).launch {
            val call:Response<List<Sucursal>> = getRetrofit().create(APIServiceSucursal::class.java).getSucursales()
            val sucursales: List<Sucursal> ?= call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    val su = sucursales ?: emptyList()
                    listaSucursales.clear()
                    listaSucursales.addAll(su)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }
}