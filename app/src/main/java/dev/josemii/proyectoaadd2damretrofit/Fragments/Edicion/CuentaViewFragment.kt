package dev.josemii.proyectoaadd2damretrofit.Fragments.Edicion

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import dev.josemii.proyectoaadd2damretrofit.Clases.Cliente
import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceCliente
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceCuenta
import dev.josemii.proyectoaadd2damretrofit.UI.APIServiceSucursal
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentCuentaViewBinding
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
 * Use the [CuentaViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CuentaViewFragment : Fragment() {

    private lateinit var binding : FragmentCuentaViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuentaViewBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = arguments?.getInt("id_cuenta")
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
                var cuenta = Cuenta(
                    Integer.parseInt(binding.etID.text.toString()),
                    binding.etCCC.text.toString(),
                    binding.etSaldo.text.toString().toFloat(),
                    binding.ckEnAlta.isChecked,
                    getClienteSpinner()
                )
                var id = Integer.parseInt(binding.etID.text.toString())
                editar(id, cuenta)
            } else {
                var cuenta = Cuenta(
                    0,
                    binding.etCCC.text.toString(),
                    binding.etSaldo.text.toString().toFloat(),
                    binding.ckEnAlta.isChecked,
                    getClienteSpinner()
                )
                guardar(cuenta)
            }
        }
        binding.btnEliminar.setOnClickListener {
            if (binding.btnEliminar.text.equals("Eliminar")) {
                var id = Integer.parseInt(binding.etID.text.toString())
                eliminar(id)
            } else {
                findNavController()?.navigate(R.id.nav_cuentas)
            }
        }
    }

    private fun getClienteSpinner(): Cliente {
        val cliente : Cliente = binding.spCliente.selectedItem as Cliente
        return cliente
    }
    private fun CargarSpinner() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Cliente>> =
                getRetrofit().create(APIServiceCliente::class.java).getCliente()
            val cl: List<Cliente>? = call.body()
            var lista = mutableListOf<Cliente>()
            if (cl != null) {
                lista = cl.toMutableList()
            }
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    binding.spCliente.adapter = context?.let {
                        ArrayAdapter<Cliente>(
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
            val call: Response<Cuenta> =
                getRetrofit().create(APIServiceCuenta::class.java).getCuenta(id)
            val cl: Cuenta? = call.body()
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    binding.etID.text = cl?.id.toString()
                    binding.etCCC.text = Editable.Factory.getInstance().newEditable(cl?.ccc)
                    binding.etSaldo.text = Editable.Factory.getInstance().newEditable(cl?.saldo.toString())
                    binding.ckEnAlta.isChecked = cl?.enAlta ?: false
                } else {
                    showMessage("Error al Cargar")
                }
            }
        }
    }

    fun guardar(cuenta: Cuenta) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Cuenta> =
                getRetrofit().create(APIServiceCuenta::class.java).createCuenta(cuenta)
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    showMessage("Cuenta añadida")
                    binding.etID.text = ""
                    binding.etCCC.text.clear()
                    binding.etSaldo.text.clear()
                    binding.ckEnAlta.isChecked = false
                } else {
                    showMessage("Error al Insertar")
                }
            }
        }
    }

    fun editar(id: Int, cuenta: Cuenta) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Cuenta> =
                getRetrofit().create(APIServiceCuenta::class.java).updateCuenta(id, cuenta)
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    showMessage("Cuenta Modificada")
                    binding.etID.text = ""
                    binding.etCCC.text.clear()
                    binding.etSaldo.text.clear()
                    binding.ckEnAlta.isChecked = false
                } else {
                    showMessage("Error al Modificar")
                }
            }
        }
    }

    fun eliminar(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Void> =
                getRetrofit().create(APIServiceCuenta::class.java).deleteCuental(id)
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    showMessage("Cuenta Eliminada")
                    binding.etID.text = ""
                    binding.etCCC.text.clear()
                    binding.etSaldo.text.clear()
                    binding.ckEnAlta.isChecked = false
                } else {
                    showMessage("Error al Eliminar")
                }
            }
        }
    }
}