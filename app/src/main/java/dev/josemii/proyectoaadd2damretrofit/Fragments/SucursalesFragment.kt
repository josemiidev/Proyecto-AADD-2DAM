package dev.josemii.proyectoaadd2damretrofit.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.josemii.proyectoaadd2damretrofit.R
import dev.josemii.proyectoaadd2damretrofit.databinding.FragmentSucursalesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SucursalesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SucursalesFragment : Fragment() {

    private lateinit var binding : FragmentSucursalesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSucursalesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }
}