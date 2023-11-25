package com.example.lab_application.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab_application.R
import com.example.lab_application.view_model.PlaceViewModel
import com.example.lab_application.databinding.FragmentUpdateBinding
import com.example.lab_application.model.Place
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class UpdateFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var placeViewModel: PlaceViewModel
    private val args by navArgs<UpdateFragmentArgs>()
    private var rating: Int = 0
    private var imguri: Uri = Uri.parse("")
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            imguri = uri
            requireContext().contentResolver.takePersistableUriPermission(imguri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            binding.imageUpdate.setImageURI(imguri)
            Toast.makeText(requireContext(), "Photo selected", Toast.LENGTH_LONG).show()
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
        if (imguri.toString().isNotEmpty()) {
            val file = DocumentFile.fromSingleUri(requireContext(), imguri)
            if (file != null) {
                if (file.exists()) {
                    binding.imageUpdate.setImageURI(imguri)
                } else {
                    Toast.makeText(context, "Image not found for " + args.currentPlace.city,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        binding.dateupdate.setOnClickListener {
            DatePickerDialog(requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.aboutupdate.movementMethod = ScrollingMovementMethod()
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
            if (binding.cityupdate.length() == 0) {
                binding.updatecityWin.error = "City required"
            }
            if (binding.dateupdate.toString().isEmpty()) {
                binding.updatedateWin.error = "Date required"
            }
                updateItem()
        }
        binding.removeImageButtonUpdate.setOnClickListener {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.myToolbar.inflateMenu(R.menu.main_menu)
        binding.myToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.myToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        binding.myToolbar.setTitle("Update Place")
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
        var about = binding.aboutupdate.text.toString()
        if (about.isEmpty()) {
            about = ""
        }
        if (inputCheck(city, dateEditable, about, rating)) {
            val place = Place(args.currentPlace.id, city, calendar.timeInMillis, about, rating, imguri)
            placeViewModel.updatePlace(place)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out city and date fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(city: String, date: String, about: String, rating: Int): Boolean {
        return !(city.isEmpty() || date.isEmpty())
    }

    private fun onAbortButtonClick() {
        imguri = Uri.parse("")
        binding.imageUpdate.setImageURI(imguri)
        Toast.makeText(requireContext(), "Image removed.", Toast.LENGTH_LONG).show()
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        Log.e("Calendar: ", "$year - $month - $dayOfMonth")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        binding.dateupdate.setText(formatter.format(calendar.timeInMillis))
    }

}