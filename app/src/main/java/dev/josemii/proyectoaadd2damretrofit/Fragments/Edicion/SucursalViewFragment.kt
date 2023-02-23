package dev.josemii.proyectoaadd2damretrofit.Fragments.Edicion

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.GsonBuilder
import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceSucursal
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentSucursalViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 * Use the [SucursalViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SucursalViewFragment : Fragment() {

    private lateinit var binding: FragmentSucursalViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSucursalViewBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = arguments?.getInt("id_sucursal")

        id?.let {
            llamarAPI(it)
        }
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
    fun llamarAPI(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Sucursal> = getRetrofit().create(APIServiceSucursal::class.java).getSucursal(id)
            val sucursal: Sucursal ?= call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    val su = sucursal
                    binding.etID.text = su?.id.toString()
                    binding.etPoblacion.text = Editable.Factory.getInstance().newEditable(su?.poblacion)
                    binding.etProvincia.text = Editable.Factory.getInstance().newEditable(su?.provincia)
                    binding.etReferencia.text = Editable.Factory.getInstance().newEditable(su?.referencia)
                }else{
                    showError()
                }
            }
        }
    }
}