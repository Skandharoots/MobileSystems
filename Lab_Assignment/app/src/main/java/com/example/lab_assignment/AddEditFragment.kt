package com.example.lab_assignment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab_assignment.R
import com.example.lab_assignment.adapter.PlaceViewModel
import com.example.lab_assignment.model.Place
import java.text.SimpleDateFormat
import java.util.Date

class AddEditFragment : Fragment() {

    private lateinit var  placeViewModel: PlaceViewModel
    var checkedRating: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_edit, container, false)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        val addBttn = view.findViewById<Button>(R.id.add_place)
        addBttn.setOnClickListener {
            insertDataToDatabase()
        }


        return view
    }

    private fun insertDataToDatabase() {
        if (requireView().findViewById<RadioButton>(R.id.b1).isChecked) {
            checkedRating = 1
        }
        else if (requireView().findViewById<RadioButton>(R.id.b2).isChecked) {
            checkedRating = 2
        }
        else if (requireView().findViewById<RadioButton>(R.id.b3).isChecked) {
            checkedRating = 3
        }
        else if (requireView().findViewById<RadioButton>(R.id.b4).isChecked) {
            checkedRating = 4
        }
        else if (requireView().findViewById<RadioButton>(R.id.b5).isChecked) {
            checkedRating = 5
        }
        val city = requireView().findViewById<EditText>(R.id.editTextText2).text.toString()
        val dateEditable = requireView().findViewById<EditText>(R.id.editTextText3).text.toString()
        val sdf = SimpleDateFormat("dd/mm/yyyy")
        val date = sdf.parse(dateEditable)
        var about = requireView().findViewById<EditText>(R.id.editTextText4).text.toString()
        if (about.isEmpty()) {
            about = ""
        }
        val rating = checkedRating
        if (inputCheck(city, date, about, rating)) {
            val place = Place(0, city, date, about, rating)
            placeViewModel.addPlace(place)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addEditFragment_to_placesFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(city: String, date: Date, about: String, rating: Int) : Boolean {
        return !(TextUtils.isEmpty(city) && date == null && TextUtils.isEmpty(about) && rating == null)
    }

}