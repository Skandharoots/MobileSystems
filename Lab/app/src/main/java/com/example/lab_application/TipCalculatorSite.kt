package com.example.lab_application

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_application.databinding.ActivityMainBinding
import com.example.lab_application.databinding.TipLayoutBinding
import java.text.NumberFormat

const val KEY_TIP = "tip_key"

class TipCalculatorSite : AppCompatActivity() {

    private lateinit var binding : TipLayoutBinding

    private var tipFinal = "Tip Amount: $0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TipLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            tipFinal = savedInstanceState.getString(KEY_TIP, "Tip Amount: $0")
        }
        binding.tipResult.text = tipFinal
        binding.calculateButton.setOnClickListener{calculateTip()}
        binding.iconArrowBack.setOnClickListener{goBack()}
        binding.costOfService.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TIP, tipFinal)
        Log.d(TAG, "onSaveInstanceState Called")
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        tipFinal = "Tip Amount: ${formattedTip}"
        Log.d(TAG, tipFinal)
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}