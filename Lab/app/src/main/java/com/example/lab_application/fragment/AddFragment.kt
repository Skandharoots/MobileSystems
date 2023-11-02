package com.example.lab_application.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab_application.R
import com.example.lab_application.view_model.PlaceViewModel
import com.example.lab_application.databinding.FragmentAddBinding
import com.example.lab_application.model.Place
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddFragment : Fragment() {

    private lateinit var placeViewModel: PlaceViewModel

    var checkedRating: Int = 0

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private var imguri: Uri = Uri.parse("")

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Toast.makeText(requireContext(), "Photo selected", Toast.LENGTH_LONG).show()
            imguri = uri
            requireContext().contentResolver.takePersistableUriPermission(imguri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            binding.image.setImageURI(imguri)
        } else {
            Toast.makeText(requireContext(), "No media selected", Toast.LENGTH_LONG).show()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        val addBttn = view.findViewById<Button>(R.id.add_place)
        addBttn.setOnClickListener {
            insertDataToDatabase()
        }
        binding.date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                    binding.date.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        binding.removeImgButton.setOnClickListener {
            onAbortButtonClick()
        }
        binding.loadImgButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
        return view
    }


    private fun insertDataToDatabase() {
        if (requireView().findViewById<RadioButton>(R.id.b1).isChecked) {
            checkedRating = 1
        } else if (requireView().findViewById<RadioButton>(R.id.b2).isChecked) {
            checkedRating = 2
        } else if (requireView().findViewById<RadioButton>(R.id.b3).isChecked) {
            checkedRating = 3
        } else if (requireView().findViewById<RadioButton>(R.id.b4).isChecked) {
            checkedRating = 4
        } else if (requireView().findViewById<RadioButton>(R.id.b5).isChecked) {
            checkedRating = 5
        }
        val city = requireView().findViewById<EditText>(R.id.city).text.toString()
        val dateEditable = requireView().findViewById<EditText>(R.id.date).text.toString()
        var about = requireView().findViewById<EditText>(R.id.about).text.toString()
        if (about.isEmpty()) {
            about = ""
        }
        val rating = checkedRating

        if (inputCheck(city, dateEditable)) {
            val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.UK)
            val date = sdf.parse(dateEditable)
            val place = Place(0, city, date, about, rating, imguri)
            placeViewModel.addPlace(place)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out city and date fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(city: String, date: String): Boolean {
        return !(city.isEmpty() || date.isEmpty())
    }

    private fun onAbortButtonClick() {
        imguri = Uri.parse("")
        binding.image.setImageURI(imguri)
        Toast.makeText(requireContext(), "Image removed", Toast.LENGTH_LONG).show()

    }



}

