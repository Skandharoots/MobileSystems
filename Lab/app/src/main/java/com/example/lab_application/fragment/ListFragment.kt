package com.example.lab_application.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.R
import com.example.lab_application.adapter.ItemAdapter
import com.example.lab_application.databinding.FragmentListBinding
import com.example.lab_application.model.Place
import com.example.lab_application.view_model.PlaceViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var myDataset: List<Place>
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        val adapter = mutableListOf<Place>()
        itemAdapter = ItemAdapter(requireContext(), adapter)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        placeViewModel.readAllData.observe(viewLifecycleOwner, Observer {place ->
            itemAdapter.setData(place)
        })
        val btn = view.findViewById<FloatingActionButton>(R.id.add_place_button1)
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        val btn2 = view.findViewById<FloatingActionButton>(R.id.map_button)
        btn2.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_mapFragment)
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            getLocationPermissions()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        binding.myToolbar.inflateMenu(R.menu.recycler_menu)
        binding.myToolbar.setTitle("Places")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Toast.makeText(requireContext(), "Location permission granted", Toast.LENGTH_LONG).show()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Toast.makeText(requireContext(), "Approximate location permission granted", Toast.LENGTH_LONG).show()
                } else -> {
                Toast.makeText(requireContext(), "Location permission rejected", Toast.LENGTH_LONG).show()
            }
            }
        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

}