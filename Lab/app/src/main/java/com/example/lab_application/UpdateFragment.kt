package com.example.lab_application

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Update
import com.example.lab_application.adapter.PlaceViewModel
import com.example.lab_application.model.Place
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date


class UpdateFragment : Fragment() {

    private lateinit var placeViewModel: PlaceViewModel
    private val args by navArgs<UpdateFragmentArgs>()
    private var rating: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        view.findViewById<TextInputEditText>(R.id.cityupdate).setText(args.currentPlace.city)
        val spf = SimpleDateFormat("dd/mm/yyyy")
        val date = spf.format(args.currentPlace.date)
        view.findViewById<TextInputEditText>(R.id.dateupdate).setText(date)
        view.findViewById<TextInputEditText>(R.id.aboutupdate).setText(args.currentPlace.about)
        rating = args.currentPlace.rating
        Toast.makeText(requireContext(), "Rating is ${rating}", Toast.LENGTH_LONG)
        if (rating == 1) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 2) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 3) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 4) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 5) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        view.findViewById<MaterialButton>(R.id.add_place_update).setOnClickListener {
            updateItem()
        }
        view.findViewById<MaterialButton>(R.id.cancel_place_update).setOnClickListener {
            onAbortButtonClick()
        }


        return view
    }

    private fun updateItem() {
        if (requireView().findViewById<RadioButton>(R.id.b1u).isChecked) {
            rating = 1
        } else if (requireView().findViewById<RadioButton>(R.id.b2u).isChecked) {
            rating = 2
        } else if (requireView().findViewById<RadioButton>(R.id.b3u).isChecked) {
            rating = 3
        } else if (requireView().findViewById<RadioButton>(R.id.b4u).isChecked) {
            rating = 4
        } else if (requireView().findViewById<RadioButton>(R.id.b5u).isChecked) {
            rating = 5
        }
        val city = requireView().findViewById<EditText>(R.id.cityupdate).text.toString()
        val dateEditable = requireView().findViewById<EditText>(R.id.dateupdate).text.toString()
        val sdf = SimpleDateFormat("dd/mm/yyyy")
        val date = sdf.parse(dateEditable)
        var about = requireView().findViewById<EditText>(R.id.aboutupdate).text.toString()
        if (about.isEmpty()) {
            about = ""
        }
        if (inputCheck(city, date, about, rating)) {
            val place = Place(args.currentPlace.id, city, date, about, rating)
            placeViewModel.updatePlace(place)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all required fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(city: String, date: Date, about: String, rating: Int): Boolean {
        return !(TextUtils.isEmpty(city) && date == null && TextUtils.isEmpty(about) && rating == null)
    }

    private fun onAbortButtonClick() {
        Toast.makeText(requireContext(), "Updating place aborted.", Toast.LENGTH_LONG).show()
        findNavController().navigate((R.id.action_updateFragment_to_listFragment))
    }

}