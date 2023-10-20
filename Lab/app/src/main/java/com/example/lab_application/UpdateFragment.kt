package com.example.lab_application

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
    private var imguri: Uri = Uri.parse("")
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Toast.makeText(requireContext(), "Photo selected", Toast.LENGTH_LONG)
            imguri = uri
            requireContext().contentResolver.takePersistableUriPermission(imguri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            Toast.makeText(requireContext(), "No media selected", Toast.LENGTH_LONG)
        }
    }
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
        imguri = args.currentPlace.image
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
        view.findViewById<ImageView>(R.id.icon_delete).setOnClickListener {
            deletePlace()
        }
        view.findViewById<MaterialButton>(R.id.load_img_button_update).setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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
            val place = Place(args.currentPlace.id, city, date, about, rating, imguri)
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

    private fun deletePlace() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") {_, _ ->
            placeViewModel.deletePlace(args.currentPlace)
            Toast.makeText(requireContext(), "Place deleted", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") {_, _-> }
        builder.setMessage("Do you want to delete ${args.currentPlace.city}?")
        builder.create().show()
    }

}