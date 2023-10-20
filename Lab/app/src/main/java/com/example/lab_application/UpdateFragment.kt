package com.example.lab_application

import android.app.AlertDialog
import android.app.DatePickerDialog
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
import com.example.lab_application.databinding.FragmentAddBinding
import com.example.lab_application.databinding.FragmentUpdateBinding
import com.example.lab_application.model.Place
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialTextInputPicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class UpdateFragment : Fragment() {

    private lateinit var placeViewModel: PlaceViewModel
    private val args by navArgs<UpdateFragmentArgs>()
    private var rating: Int = 0
    private var imguri: Uri = Uri.parse("")
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Toast.makeText(requireContext(), "Photo selected", Toast.LENGTH_LONG)
            imguri = uri
            requireContext().contentResolver.takePersistableUriPermission(imguri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            binding.imageUpdate.setImageURI(imguri)
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

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        binding.cityupdate.setText(args.currentPlace.city)
        val spf = SimpleDateFormat("dd/mm/yyyy")
        val date = spf.format(args.currentPlace.date)
        binding.dateupdate.setText(date)
        binding.aboutupdate.setText(args.currentPlace.about)
        rating = args.currentPlace.rating
        imguri = args.currentPlace.image
        binding.imageUpdate.setImageURI(imguri)
        binding.dateupdate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val datesel = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                    binding.dateupdate.setText(datesel)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        if (rating == 1) {
            binding.b1u.isChecked = true
        }
        else if (rating == 2) {
            binding.b2u.isChecked = true
        }
        else if (rating == 3) {
            binding.b3u.isChecked = true
        }
        else if (rating == 4) {
            binding.b4u.isChecked = true
        }
        else if (rating == 5) {
            binding.b5u.isChecked = true
        }
        binding.addPlaceUpdate.setOnClickListener {
            updateItem()
        }
        binding.cancelPlaceUpdate.setOnClickListener {
            onAbortButtonClick()
        }
        binding.iconDelete.setOnClickListener {
            deletePlace()
        }
        binding.loadImgButtonUpdate.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }


        return view
    }

    private fun updateItem() {
        if (binding.b1u.isChecked) {
            rating = 1
        } else if (binding.b2u.isChecked) {
            rating = 2
        } else if (binding.b3u.isChecked) {
            rating = 3
        } else if (binding.b4u.isChecked) {
            rating = 4
        } else if (binding.b5u.isChecked) {
            rating = 5
        }
        val city = binding.cityupdate.text.toString()
        val dateEditable = binding.dateupdate.text.toString()
        val sdf = SimpleDateFormat("dd/mm/yyyy")
        val date = sdf.parse(dateEditable)
        var about = binding.aboutupdate.text.toString()
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