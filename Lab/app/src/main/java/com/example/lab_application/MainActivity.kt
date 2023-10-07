package com.example.lab_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity.Header
import android.view.View
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

    private fun switchToCalculator() {
        val intent = Intent(this, TipCalculatorSite::class.java)
        startActivity(intent)
    }

    private fun switchToAffirmations() {
        val intent = Intent(this, AffirmationView::class.java)
        startActivity(intent)
    }
}