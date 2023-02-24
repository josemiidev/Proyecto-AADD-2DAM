package dev.josemii.proyectoaadd2damretrofit.Fragments.Edicion

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [ClienteViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteViewFragment : Fragment() {

    private lateinit var binding: FragmentClienteViewBinding
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
        } ?: {
            binding.btnEliminar.text = "Cancelar"
            binding.btnGuardar.text = "Añadir"
        }
        binding.btnGuardar.setOnClickListener {
            if (binding.btnGuardar.text.equals("Modificar")) {
                var cliente = Cliente(
                    Integer.parseInt(binding.etID.text.toString()),
                    binding.etDni.text.toString(),
                    binding.etNombre.text.toString(),
                    binding.etApellidos.text.toString(),
                    getFecha(),
                    getSucursalSpinner()
                )
                var id = Integer.parseInt(binding.etID.text.toString())
                editar(id, cliente)
            } else {
                var cliente = Cliente(
                    0,
                    binding.etDni.text.toString(),
                    binding.etNombre.text.toString(),
                    binding.etApellidos.text.toString(),
                    getFecha(),
                    getSucursalSpinner()

                )
                guardar(cliente)
            }
        }
        binding.btnEliminar.setOnClickListener {
            if (binding.btnEliminar.text.equals("Eliminar")) {
                var id = Integer.parseInt(binding.etID.text.toString())
                eliminar(id)
            } else {
                findNavController()?.navigate(R.id.nav_sucursales)
            }
        }
    }

    private fun getFecha():String{
        val date = Date(binding.calendarView.date)
        val format = SimpleDateFormat("YYYY-MM-dd")
        return format.format(date)
    }
    private fun getSucursalSpinner():Sucursal{
        val sucursal : Sucursal = binding.spSucursal.selectedItem as Sucursal
        return sucursal
    }
    private fun CargarSpinner() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Sucursal>> =
                getRetrofit().create(APIServiceSucursal::class.java).getSucursales()
            val cl: List<Sucursal>? = call.body()
            var lista = mutableListOf<Sucursal>()
            if (cl != null) {
                lista = cl.toMutableList()
            }
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    binding.spSucursal.adapter = context?.let {
                        ArrayAdapter<Sucursal>(
                            it,
                            android.R.layout.simple_spinner_dropdown_item,
                            lista
                        )
                    }
                } else {
                    showMessage("Error al Cargar")
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.28.54.69:8000/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    }

    private fun showMessage(texto: String) {
        Toast.makeText(context, texto, Toast.LENGTH_LONG).show()
    }

    fun llamarAPI(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Cliente> =
                getRetrofit().create(APIServiceCliente::class.java).getCliente(id)
            val cl: Cliente? = call.body()
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    binding.etID.text = cl?.id.toString()
                    binding.etDni.text = Editable.Factory.getInstance().newEditable(cl?.dni)
                    binding.etNombre.text = Editable.Factory.getInstance().newEditable(cl?.nombre)
                    binding.etApellidos.text =
                        Editable.Factory.getInstance().newEditable(cl?.apellidos)
                } else {
                    showMessage("Error al Cargar")
                }
            }
        }
    }

    fun guardar(cliente: Cliente) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Cliente> =
                getRetrofit().create(APIServiceCliente::class.java).createCliente(cliente)
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    showMessage("Cliente añadido")
                    binding.etID.text = ""
                    binding.etDni.text.clear()
                    binding.etNombre.text.clear()
                    binding.etApellidos.text.clear()
                } else {
                    println("::LOG::" + call.message())
                    showMessage("Error al Insertar")
                }
            }
        }
    }

    fun editar(id: Int, cliente: Cliente) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Cliente> =
                getRetrofit().create(APIServiceCliente::class.java).updateCliente(id, cliente)
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    showMessage("Cliente Modificado")
                    binding.etID.text = ""
                    binding.etDni.text.clear()
                    binding.etNombre.text.clear()
                    binding.etApellidos.text.clear()
                } else {
                    showMessage("Error al Modificar")
                }
            }
        }
    }

    fun eliminar(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Void> =
                getRetrofit().create(APIServiceCliente::class.java).deleteCliente(id)
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    showMessage("Cliente Eliminado")
                    binding.etID.text = ""
                    binding.etDni.text.clear()
                    binding.etNombre.text.clear()
                    binding.etApellidos.text.clear()
                } else {
                    showMessage("Error al Eliminar")
                }
            }
        }
    }
}