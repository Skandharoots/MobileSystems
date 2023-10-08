package com.example.lab_application

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity.Header
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.lab_application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.switchViewButton.setOnClickListener{ switchToCalculator() }
        binding.switchListButton.setOnClickListener{ switchToAffirmations() }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }


    private fun switchToCalculator() {
        val intent = Intent(this, TipCalculatorSite::class.java)
        startActivity(intent)
    }

    private fun switchToAffirmations() {
        val intent = Intent(this, AffirmationView::class.java)
        startActivity(intent)
    }
}