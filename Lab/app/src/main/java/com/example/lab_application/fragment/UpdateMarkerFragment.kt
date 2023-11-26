package com.example.lab_application.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab_application.R
import com.example.lab_application.databinding.FragmentUpdateMarkerBinding
import com.example.lab_application.model.Marker
import com.example.lab_application.view_model.MarkerViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class UpdateMarkerFragment : Fragment() {

    private lateinit var markerViewModel: MarkerViewModel

    private val args by navArgs<UpdateMarkerFragmentArgs>()

    private var _binding: FragmentUpdateMarkerBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateMarkerBinding.inflate(inflater, container, false)
        val view = binding.root

        markerViewModel = ViewModelProvider(this).get(MarkerViewModel::class.java)
        binding.titleupdate.setText(args.currentMarker.title)
        val spf = SimpleDateFormat("dd/mm/yyyy")
        val date = spf.format(args.currentMarker.date)
        binding.dateupdate.setText(date)
        binding.aboutupdate.setText(args.currentMarker.about)
        binding.latitude.setText("Latitude: " + args.currentMarker.lat.toString())
        binding.longitude.setText("Longitude: " + args.currentMarker.lng.toString())

        binding.dateupdate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, yearr, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val datesel = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + yearr)
                    calendar.set(yearr, monthOfYear, dayOfMonth)
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
        binding.aboutupdate.movementMethod = ScrollingMovementMethod()
        binding.iconDelete.setOnClickListener {
            deleteMarker()
        }
        binding.updateMarkerButton.setOnClickListener {
            if (binding.titleupdate.length() == 0) {
                binding.updatetitleWin.error = "City required"
            }
            else if (binding.dateupdate.toString().isEmpty()) {
                binding.updatedateWin.error = "Date required"
            } else {
                updateMarker()
            }
        }
        binding.cancelButtonUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_updateMarkerFragment_to_mapFragment)
            Toast.makeText(requireContext(), "Cancelled marker update", Toast.LENGTH_LONG).show()
        }
        binding.aboutupdate.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.aboutupdate.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener false
                    }
                }
            }
            false
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.myToolbar.inflateMenu(R.menu.main_menu)
        binding.myToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.myToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_updateMarkerFragment_to_mapFragment)
        }
        binding.myToolbar.setTitle("Update Marker")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateMarker() {
        val city = binding.titleupdate.text.toString()
        val dateEditable = binding.dateupdate.text.toString()
        var about = binding.aboutupdate.text.toString()
        if (about.isEmpty()) {
            about = ""
        }
        if (inputCheck(city, dateEditable)) {
            val sdf = SimpleDateFormat("dd/mm/yyyy")
            val date = sdf.parse(dateEditable)
            val marker = Marker(args.currentMarker.id, city, calendar.timeInMillis, about, args.currentMarker.lat, args.currentMarker.lng)
            markerViewModel.updateMarker(marker)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateMarkerFragment_to_mapFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out city and date fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(city: String, date: String): Boolean {
        return !(city.isEmpty() || date.isEmpty())
    }

    private fun deleteMarker() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") {_, _ ->
            markerViewModel.deleteMarker(args.currentMarker)
            Toast.makeText(requireContext(), "Place deleted", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_updateMarkerFragment_to_mapFragment)
        }
        builder.setNegativeButton("No") {_, _-> }
        builder.setMessage("Do you want to delete ${args.currentMarker.title}?")
        builder.create().show()
    }

}