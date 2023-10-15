package com.example.lab_application

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.adapter.ItemAdapter
import com.example.lab_application.data.DataSource
import com.example.lab_application.databinding.AddUpdateLayoutBinding
import com.example.lab_application.databinding.AffirmationLayoutBinding
import com.example.lab_application.model.Affirmation

class AddUpdateView : AppCompatActivity(){

    private lateinit var binding : AddUpdateLayoutBinding
    private lateinit var itemAdapter : ItemAdapter

    companion object {
        const val CITY = "city"
        const val DATE = "date"
        const val ABOUT = "about"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddUpdateLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var city = intent?.extras?.getString(CITY).toString()
        var date = intent?.extras?.getString(DATE).toString()
        var about = intent?.extras?.getString(ABOUT).toString()
        binding.editAbout.append(about)
        binding.editCity.append(city)
        binding.editDate.append(date)


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

//    private fun goBack() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//    }
}