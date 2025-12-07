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
                calculateTip()
            }
        })

        binding.tipPercentage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateTip()
            }
        })

        binding.roundUpSwitch.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }

        binding.clearButton.setOnClickListener {
            binding.billAmount.setText("")
            binding.tipPercentage.setText("")
            binding.roundUpSwitch.isChecked = false
            binding.billAmountLayout.error = null
            binding.tipPercentageLayout.error = null
            binding.tipResult.text = "Tip Amount: $0.00"
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