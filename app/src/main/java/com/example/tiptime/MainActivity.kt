package com.example.tiptime

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val costOfService: EditText = findViewById(R.id.cost_of_service_edit_text)

        costOfService.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }

        val calculateButton: Button = findViewById(R.id.calculate_button)
        calculateButton.setOnClickListener {
            displayTip(finalDisplayedCost())
        }
    }

    private fun calculateTip(cost: Double?): Double {

        val tipChoice: RadioGroup = findViewById(R.id.radio_group_service)

        val tipPercentage =
            when (tipChoice.checkedRadioButtonId) {
                R.id.radio_button_amazing -> 0.20
                R.id.radio_button_good -> 0.18
                else -> 0.15
            }

        return if (cost != null) {
            cost * (tipPercentage)
        } else {
            0.00
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun finalDisplayedCost(): Double {

        val costOfService: EditText = findViewById(R.id.cost_of_service_edit_text)
        val cost = costOfService.text.toString().toDoubleOrNull()
        val totalCost = calculateTip(cost)


        val roundUpTip: Switch = findViewById(R.id.round_tip_switch)

        if (roundUpTip.isChecked) {
            return ceil(totalCost)
        }
        return totalCost
    }


    private fun displayTip(tip: Double) {

        val tipAmount: TextView = findViewById(R.id.tip_amount)

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        tipAmount.text = getString(R.string.tip_amount_s, formattedTip)
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
