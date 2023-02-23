package dev.josemii.proyectoaadd2damretrofit.Fragments.Edicion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentCuentaViewBinding

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

}