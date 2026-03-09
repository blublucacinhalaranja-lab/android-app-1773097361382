package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var operand1: Double? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNumberButtonListeners()
        setOperatorButtonListeners()
        binding.buttonClear.setOnClickListener { clearCalculator() }
        binding.buttonEquals.setOnClickListener { calculateResult() }
        binding.buttonDecimal.setOnClickListener { appendDecimal() }
    }

    private fun setNumberButtonListeners() {
        val numberButtons = arrayOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                appendNumber(button.text.toString())
            }
        }
    }

    private fun setOperatorButtonListeners() {
        binding.buttonAdd.setOnClickListener { setOperator("+") }
        binding.buttonSubtract.setOnClickListener { setOperator("-") }
        binding.buttonMultiply.setOnClickListener { setOperator("*") }
        binding.buttonDivide.setOnClickListener { setOperator("/") }
    }

    private fun appendNumber(number: String) {
        binding.resultTextView.append(number)
    }

    private fun appendDecimal() {
        if (!binding.resultTextView.text.contains(".")) {
            binding.resultTextView.append(".")
        }
    }

    private fun setOperator(operator: String) {
        operand1 = binding.resultTextView.text.toString().toDoubleOrNull()
        this.operator = operator
        binding.resultTextView.text = ""
    }

    private fun clearCalculator() {
        binding.resultTextView.text = ""
        operand1 = null
        operator = null
    }

    private fun calculateResult() {
        val operand2 = binding.resultTextView.text.toString().toDoubleOrNull()

        if (operand1 != null && operand2 != null && operator != null) {
            val result = when (operator) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "*" -> operand1!! * operand2
                "/" -> if (operand2 != 0.0) operand1!! / operand2 else {
                    binding.resultTextView.text = "Error"
                    return
                }
                else -> return
            }
            binding.resultTextView.text = result.toString()
            operand1 = result
            operator = null
        }
    }
}