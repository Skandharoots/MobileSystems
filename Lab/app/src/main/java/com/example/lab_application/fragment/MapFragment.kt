package com.example.lab_application.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab_application.R
import com.example.lab_application.databinding.FragmentListBinding
import com.example.lab_application.databinding.FragmentMapBinding
import com.example.lab_application.view_model.MarkerViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var myMap: GoogleMap? = null

    private final val FIN_PERM_CODE = 101

    private var currentLocation : Location? = null

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    private lateinit var markerViewModel : MarkerViewModel

    private var myDialog : Dialog? = null

    private lateinit var currentLocMarker : com.example.lab_application.model.Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocationUser()
        myDialog = Dialog(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.myToolbar.inflateMenu(R.menu.map_menu)
        binding.myToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.myToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_listFragment)
        }
        binding.myToolbar.setTitle("Map")

    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        markerViewModel = ViewModelProvider(this).get(MarkerViewModel::class.java)
        val latLng = currentLocation?.let { LatLng(it.latitude, it.longitude) }
        var options = latLng?.let { MarkerOptions().position(it).title("Your location") }
        options?.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        latLng?.let { CameraUpdateFactory.newLatLng(it) }?.let { myMap?.animateCamera(it) }
        latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 14f) }?.let { myMap?.animateCamera(it) }
        markerViewModel.readAllData.observe(viewLifecycleOwner, Observer {markerread ->
            for (marker in markerread) {
                val latlng = LatLng(marker.lat, marker.lng)
                val options2 = MarkerOptions().position(latlng).title(marker.title).contentDescription(marker.about)
                myMap?.addMarker(options2)
            }

        })
        myMap?.setOnMapLongClickListener {
            showAddMarkerPopup(requireView(), it.latitude, it.longitude)
        }
        myMap?.setOnMarkerClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCurrentLocationUser() {

        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            val getLocation =
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = location
                        var supportMapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        supportMapFragment.getMapAsync(this)
                    }
                }
        }
        else {
            Toast.makeText(requireContext(), "Location not permitted", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        markerViewModel.getMarker(marker.position.latitude, marker.position.longitude).observe(viewLifecycleOwner
        ) { markerData ->
            showPopup(requireView(), markerData)
        }

        return true
    }

    private fun showPopup(v: View, marker: com.example.lab_application.model.Marker) {
        myDialog?.setContentView(R.layout.marker_popup)
        myDialog?.findViewById<MaterialTextView>(R.id.marker_title)?.text = marker.title
        myDialog?.findViewById<MaterialTextView>(R.id.marker_about)?.text = marker.about
        myDialog?.findViewById<MaterialTextView>(R.id.exit)?.setOnClickListener {
            myDialog?.dismiss()
        }
        myDialog?.findViewById<ConstraintLayout>(R.id.marker_popup_window)?.setOnClickListener {
            val action = MapFragmentDirections.actionMapFragmentToUpdateMarkerFragment(marker)
            findNavController().navigate(action)
            myDialog?.dismiss()
        }
        myDialog?.show()
    }

    private fun showAddMarkerPopup(v: View, lat: Double, lng: Double) {
        myDialog?.setContentView(R.layout.add_marker_popup)
        myDialog?.findViewById<EditText>(R.id.date)?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                    myDialog?.findViewById<EditText>(R.id.date)?.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        myDialog?.findViewById<EditText>(R.id.about)?.movementMethod = ScrollingMovementMethod()
        myDialog?.findViewById<Button>(R.id.add_marker_button)?.setOnClickListener {
            if (myDialog?.findViewById<EditText>(R.id.title)?.length() == 0) {
                myDialog?.findViewById<TextInputLayout>(R.id.title_win)?.error = "Title required"
                //myDialog?.show()
            }
            if (myDialog?.findViewById<EditText>(R.id.date)?.length() == 0) {
                myDialog?.findViewById<TextInputLayout>(R.id.date_win)?.error = "Date required"
                //myDialog?.show()
            } else {
                insertMarkerIntoDatabase(lat, lng)
            }
        }
        myDialog?.findViewById<Button>(R.id.cancel_add_button)?.setOnClickListener {
            onCancelButtonClick()
        }
        myDialog?.findViewById<MaterialTextView>(R.id.exit)?.setOnClickListener {
            myDialog?.dismiss()
        }
        myDialog?.show()

    }

    private fun insertMarkerIntoDatabase(lat: Double, lng: Double) {
        val title = myDialog?.findViewById<EditText>(R.id.title)?.text.toString()
        val dateEditable = myDialog?.findViewById<EditText>(R.id.date)?.text.toString()
        var about = myDialog?.findViewById<EditText>(R.id.about)?.text.toString()
        if (about.isEmpty()) {
            about = ""
        }
        val sdf = SimpleDateFormat("dd/mm/yyyy")
        val date = sdf.parse(dateEditable)
        if (inputCheck(title, dateEditable)) {
            val marker = com.example.lab_application.model.Marker(0, title, date, about, lat, lng)
            markerViewModel.addMarker(marker)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            myDialog?.dismiss()
        } else {
            Toast.makeText(requireContext(), "Please fill out title and date fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, date: String): Boolean {
        return !(title.isEmpty() || date.isEmpty())
    }

    private fun onCancelButtonClick() {
        myDialog?.dismiss()
        Toast.makeText(requireContext(), "Cancelled adding a marker.", Toast.LENGTH_LONG).show()
    }

}