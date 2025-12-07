package com.example.tiptime

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        calculateTip()
    }

    private fun setupListeners() {
        binding.billAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateBillAmount()
                calculateTip()
            }
        })

        binding.tipPercentage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateTipPercentage()
                calculateTip()
            }
        })

        binding.roundUpSwitch.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }

    }

    private fun validateBillAmount() {
        val text = binding.billAmount.text.toString()
        if (text.isNotEmpty() && text.toDoubleOrNull() == null) {
            binding.billAmountLayout.error = "Please enter a valid number"
        } else {
            binding.billAmountLayout.error = null
        }
    }

    private fun validateTipPercentage() {
        val text = binding.tipPercentage.text.toString()
        if (text.isNotEmpty() && text.toDoubleOrNull() == null) {
            binding.tipPercentageLayout.error = "Please enter a valid number"
        } else {
            binding.tipPercentageLayout.error = null
        }
    }

    private fun calculateTip() {
        val billString = binding.billAmount.text.toString()
        val tipPercentString = binding.tipPercentage.text.toString()

        val amount = billString.toDoubleOrNull() ?: 0.0
        val tipPercent = tipPercentString.toDoubleOrNull() ?: 0.0

        var tip = tipPercent / 100 * amount
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = "Tip Amount: $formattedTip"
    }
}