package com.example.lab_application

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData.Item
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.adapter.ItemAdapter
import com.example.lab_application.adapter.PlaceViewModel
import com.example.lab_application.databinding.AffirmationLayoutBinding
import com.example.lab_application.data.DataSource
import com.example.lab_application.databinding.ActivityMainBinding
import com.example.lab_application.databinding.TipLayoutBinding
import com.example.lab_application.model.Affirmation
import com.example.lab_application.model.Place

class AffirmationView : AppCompatActivity() {

    private lateinit var binding : AffirmationLayoutBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var myDataset : List<Place>
    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var itemAdapter : ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AffirmationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize data.
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        placeViewModel.readAllData.observe(this, Observer { place ->
            itemAdapter.setData(place)
        })
        recyclerView.adapter = itemAdapter
        binding.iconArrowBack.setOnClickListener{ goBack() }
        //binding.iconAdd.setOnClickListener({onAddButton()})


    }

    override fun onStart() {
        super.onStart()
        Log.d(ContentValues.TAG, "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(ContentValues.TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(ContentValues.TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(ContentValues.TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(ContentValues.TAG, "onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(ContentValues.TAG, "onRestart Called")
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /*private fun onAddButton() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_update_layout, null)
        val addDialog = AlertDialog.Builder(this)
        val myDataset = DataSource()
        val city = v.findViewById<EditText>(R.id.edit_city)
        val date = v.findViewById<EditText>(R.id.edit_date)
        val about = v.findViewById<EditText>(R.id.edit_about)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") {
            dialog, _->
            val cities = city.text.toString()
            val dates = date.text.toString()
            val abouts = about.text.toString()

            //myDataset.loadAffirmations().add(Affirmation("$cities", "$dates", "$abouts", R.drawable.image1 ))
            itemAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Adding a new place", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") {
            dialog, _->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()

    }
*/


}