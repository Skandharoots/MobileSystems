package com.example.lab_application.fragment

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
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
        binding.myToolbar.inflateMenu(R.menu.main_menu)
        binding.myToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.myToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_listFragment)
        }
        binding.myToolbar.setTitle("Map")

    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        val latLng = currentLocation?.let { LatLng(it.latitude, it.longitude) }
        var options = latLng?.let { MarkerOptions().position(it).title("Your location") }
        options?.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        latLng?.let { CameraUpdateFactory.newLatLng(it) }?.let { myMap?.animateCamera(it) }
        latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 12f) }?.let { myMap?.animateCamera(it) }
        if (options != null) {
            myMap?.addMarker(options)
        }
        markerViewModel = ViewModelProvider(this).get(MarkerViewModel::class.java)
        markerViewModel.readAllData.observe(viewLifecycleOwner, Observer {markerread ->
            for (marker in markerread) {
                val latlng = LatLng(marker.lat, marker.lng)
                val options2 = MarkerOptions().position(latlng).title(marker.title).contentDescription(marker.about)
                myMap?.addMarker(options2)
            }

        })
        myMap?.setOnMapLongClickListener {
            val ltln = LatLng(it.latitude, it.longitude)
            val marker = MarkerOptions().position(ltln).title("New Marker")
            val sdf = SimpleDateFormat("dd/mm/yyyy")
            val date = sdf.parse("23/11/2023")
            var markerfin = com.example.lab_application.model.Marker(0, marker.title.toString(), date, "Test about", marker.position.latitude, marker.position.longitude)
            markerViewModel.addMarker(markerfin)
            myMap?.addMarker(marker)
            Toast.makeText(requireContext(), "Marker added!", Toast.LENGTH_LONG).show()

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
        Toast.makeText(requireContext(), "Hello there", Toast.LENGTH_LONG).show()
        showPopup(requireView(), marker.title.toString(), "Test about.")
        return true
    }

    private fun showPopup(v: View, title: String, about: String) {
        myDialog?.setContentView(R.layout.marker_popup)
        myDialog?.findViewById<MaterialTextView>(R.id.marker_title)?.text = title
        myDialog?.findViewById<MaterialTextView>(R.id.marker_about)?.text = about
        myDialog?.findViewById<MaterialTextView>(R.id.exit)?.setOnClickListener {
            myDialog?.dismiss()
        }
        myDialog?.show()
    }

}