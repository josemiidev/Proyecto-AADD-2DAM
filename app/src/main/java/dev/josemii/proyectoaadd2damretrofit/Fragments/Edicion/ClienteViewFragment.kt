package dev.josemii.proyectoaadd2damretrofit.Fragments.Edicion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.josemii.proyectoaadd2damretrofit.R

/**
 * A simple [Fragment] subclass.
 * Use the [ClienteViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cliente_view, container, false)
    }

}