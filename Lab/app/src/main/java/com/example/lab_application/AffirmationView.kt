package com.example.lab_application

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.adapter.ItemAdapter
import com.example.lab_application.databinding.AffirmationLayoutBinding
import com.example.lab_application.data.DataSource
import com.example.lab_application.databinding.ActivityMainBinding
import com.example.lab_application.databinding.TipLayoutBinding
import com.example.lab_application.model.Affirmation

class AffirmationView : AppCompatActivity() {

    private lateinit var binding : AffirmationLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AffirmationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize data.
        val myDataset = DataSource().loadAffirmations()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true)

        binding.iconArrowBack.setOnClickListener{ goBack() }

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

}