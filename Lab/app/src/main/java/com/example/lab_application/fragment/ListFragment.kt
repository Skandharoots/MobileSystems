package com.example.lab_application.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
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
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(){

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val calendar1 = Calendar.getInstance()
    private val calendar2 = Calendar.getInstance()
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var myDataset: List<Place>
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView
    private var upcomingEvents : Boolean = false
    private var betweenEvents : Boolean = false
    private var myDialog : Dialog? = null


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
        myDialog = Dialog(requireContext())
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
        binding.myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.upcoming -> {
                    if (!upcomingEvents) {
                        val currentDate = Calendar.getInstance()
                        filterByEvent(currentDate.timeInMillis)
                        upcomingEvents = true
                        true
                    } else {
                        getDefaultDatabase()
                        upcomingEvents = false
                        true
                    }
                }
                    R.id.between -> {
                        if (!betweenEvents) {
                            showPopup(requireView())
                            betweenEvents = true
                            true
                        } else {
                            getDefaultDatabase()
                            betweenEvents = false
                            true
                        }
                    }
                    else -> {
                        false
                    }
                }
            }
        }

    private fun showPopup(v: View) {
        myDialog?.setContentView(R.layout.event_between_popup)
        myDialog?.findViewById<EditText>(R.id.datestart)?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, yearr, monthh, dayy ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayy.toString() + "/" + (monthh + 1) + "/" + yearr)
                    calendar1.set(yearr, monthh, dayy)
                    myDialog?.findViewById<EditText>(R.id.datestart)?.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        myDialog?.findViewById<EditText>(R.id.dateend)?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, yearr, monthh, dayy ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayy.toString() + "/" + (monthh + 1) + "/" + yearr)
                    calendar2.set(yearr, monthh, dayy)
                    myDialog?.findViewById<EditText>(R.id.dateend)?.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        myDialog?.findViewById<Button>(R.id.searchevents_button)?.setOnClickListener {
            if (myDialog?.findViewById<EditText>(R.id.datestart)?.length() == 0) {
                myDialog?.findViewById<TextInputLayout>(R.id.datestart_win)?.error = "Title required"
            }
            if (myDialog?.findViewById<EditText>(R.id.dateend)?.length() == 0) {
                myDialog?.findViewById<TextInputLayout>(R.id.dateend_win)?.error = "Date required"
            } else {
                searchEventsBetween(calendar1.timeInMillis, calendar2.timeInMillis)
            }
        }
        myDialog?.show()
    }

    private fun searchEventsBetween(start: Long, end: Long) {
        val start = myDialog?.findViewById<EditText>(R.id.datestart)?.text.toString()
        val end = myDialog?.findViewById<EditText>(R.id.datestart)?.text.toString()
        if (inputCheck(start, end)) {
            Log.e("C1: ", "${calendar1.timeInMillis}")
            Log.e("C2: ", "${calendar2.timeInMillis}")
            placeViewModel.searchDatabaseBetweenEvents(calendar1.timeInMillis, calendar2.timeInMillis)
                .observe(viewLifecycleOwner) { places ->
                    itemAdapter.setData(places)
                }
            myDialog?.dismiss()
        } else {
            Toast.makeText(requireContext(), "Please fill out date fields", Toast.LENGTH_LONG).show()

        }
    }

    private fun inputCheck(start: String, end: String): Boolean {
        return !(start.isEmpty() || end.isEmpty())
    }
    private fun filterByEvent(currentDate: Long) {
        placeViewModel.searchDatabaseByEvent(currentDate).observe(viewLifecycleOwner) { markers ->
            itemAdapter.setData(markers)
        }
    }

    private fun getDefaultDatabase() {
        placeViewModel.readAllData.observe(viewLifecycleOwner) { markers ->
            itemAdapter.setData(markers)
        }
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