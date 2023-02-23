package dev.josemii.proyectoaadd2damretrofit.Fragments.Edicion

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import dev.josemii.proyectoaadd2damretrofit.Clases.Cliente
import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceCliente
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceSucursal
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentClienteViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ClienteViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteViewFragment : Fragment() {

    private lateinit var binding : FragmentClienteViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClienteViewBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = arguments?.getInt("id_cliente")
        CargarSpinner()
        id?.let {
            llamarAPI(it)
            binding.btnEliminar.text = "Eliminar"
            binding.btnGuardar.text = "Modificar"
        }?:{
            binding.btnEliminar.text = "Cancelar"
            binding.btnGuardar.text = "Añadir"
        }
        binding.btnGuardar.setOnClickListener {
            if(binding.btnGuardar.text.equals("Modificar")){
                var cliente = Cliente(binding.etID.text,binding.etDni.text,binding.etNombre.text,binding.etApellidos.text,binding.etFechaNacimiento.text)
                var id = Integer.parseInt(binding.etID.text.toString())
                editar(id,sucursal)
            }else{
                var sucursal = Sucursal(0,binding.etPoblacion.text.toString(),binding.etProvincia.text.toString(),binding.etReferencia.text.toString())
                guardar(sucursal)
            }
        }
        binding.btnEliminar.setOnClickListener {
            if(binding.btnEliminar.text.equals("Eliminar")){
                var id = Integer.parseInt(binding.etID.text.toString())
                eliminar(id)
            }else{
                findNavController()?.navigate(R.id.nav_sucursales)
            }
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
    private fun showMessage(texto:String){
        Toast.makeText(context,texto, Toast.LENGTH_LONG).show()
    }
    fun llamarAPI(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Cliente> = getRetrofit().create(APIServiceCliente::class.java).getCliente(id)
            val cl: Cliente?= call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    binding.etID.text = cl?.id.toString()
                    binding.etDni.text = Editable.Factory.getInstance().newEditable(cl?.dni)
                    binding.etNombre.text = Editable.Factory.getInstance().newEditable(cl?.nombre)
                    binding.etApellidos.text = Editable.Factory.getInstance().newEditable(cl?.apellidos)
                    binding.etFechaNacimiento.text = Editable.Factory.getInstance().newEditable(cl?.fechaMacimiento)
                }else{
                    showMessage("Error al Cargar")
                }
            }
        }
    }

    fun guardar(sucursal: Sucursal){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Sucursal> = getRetrofit().create(APIServiceSucursal::class.java).createSucursal(sucursal)
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    showMessage("Sucursal añadida")
                    binding.etID.text = ""
                    binding.etPoblacion.text.clear()
                    binding.etProvincia.text.clear()
                    binding.etReferencia.text.clear()
                }else{
                    showMessage("Error al Insertar")
                }
            }
        }
    }
    fun editar(id:Int,sucursal: Sucursal){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Sucursal> = getRetrofit().create(APIServiceSucursal::class.java).updateSucursal(id,sucursal)
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    showMessage("Sucursal Modificada")
                    binding.etID.text = ""
                    binding.etPoblacion.text.clear()
                    binding.etProvincia.text.clear()
                    binding.etReferencia.text.clear()
                }else{
                    showMessage("Error al Modificar")
                }
            }
        }
    }
    fun eliminar(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Void> = getRetrofit().create(APIServiceSucursal::class.java).deleteSucursal(id)
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    showMessage("Sucursal Eliminada")
                    binding.etID.text = ""
                    binding.etPoblacion.text.clear()
                    binding.etProvincia.text.clear()
                    binding.etReferencia.text.clear()
                }else{
                    showMessage("Error al Eliminar")
                }
            }
        }
    }
}