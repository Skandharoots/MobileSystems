package com.example.lab_application.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab_application.R
import com.example.lab_application.databinding.FragmentListBinding
import com.example.lab_application.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var myMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.myToolbar.inflateMenu(R.menu.main_menu)
        binding.myToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.myToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_listFragment)
        }
        binding.myToolbar.setTitle("Map")
    }
}