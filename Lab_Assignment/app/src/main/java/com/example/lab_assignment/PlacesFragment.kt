package com.example.lab_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab_assignment.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PlacesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_places, container, false)
        val btn = view.findViewById<FloatingActionButton>(R.id.add_place_button1)
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_placesFragment_to_addEditFragment)
        }

        return view
    }


}