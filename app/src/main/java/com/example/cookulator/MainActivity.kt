package com.example.cookulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var statusQuery: String? = null
    private var statusResult: String? = null
    private lateinit var autocompleteTVQuery: AutoCompleteTextView
    private lateinit var autocompleteTVResult: AutoCompleteTextView
    private lateinit var queryEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var resultButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autocompleteTVQuery = findViewById(R.id.acTVQuery)
        autocompleteTVResult = findViewById(R.id.acTVResult)
        queryEditText = findViewById(R.id.textInputEditTextQuery)
        resultTextView = findViewById(R.id.tvResult)
        resultButton = findViewById(R.id.btnCompare)
        resultButton.setOnClickListener { calculateResult() }

        // get reference to the string array that we just created
        val variations = resources.getStringArray(R.array.variations)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_menu, variations)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewVariasi)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
        autocompleteTV.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.getItemAtPosition(position).toString()) {
            "Dry Ingredients" -> {
                autocompleteTVQuery.setText("")
                autocompleteTVResult.setText("")
                val dryET = resources.getStringArray(R.array.dry)
                val dryTV = resources.getStringArray(R.array.dry)
                val arrayAdapterDryET = ArrayAdapter(this, R.layout.dropdown_menu, dryET)
                val arrayAdapterDryTV = ArrayAdapter(this, R.layout.dropdown_menu, dryTV)
                autocompleteTVQuery.setAdapter(arrayAdapterDryET)
                autocompleteTVQuery.onItemClickListener = onItemClickListenerQuery
                autocompleteTVResult.setAdapter(arrayAdapterDryTV)
                autocompleteTVResult.onItemClickListener = onItemClickListenerResult
            }
            "Liquid Ingredients" -> {
                autocompleteTVQuery.setText("")
                autocompleteTVResult.setText("")
                val liqET = resources.getStringArray(R.array.liquid)
                val liqTV = resources.getStringArray(R.array.liquid)
                val arrayAdapterLiqET = ArrayAdapter(this, R.layout.dropdown_menu, liqET)
                val arrayAdapterLiqTV = ArrayAdapter(this, R.layout.dropdown_menu, liqTV)
                autocompleteTVQuery.setAdapter(arrayAdapterLiqET)
                autocompleteTVQuery.onItemClickListener = onItemClickListenerQuery
                autocompleteTVResult.setAdapter(arrayAdapterLiqTV)
                autocompleteTVResult.onItemClickListener = onItemClickListenerResult
            }
            "Temperature" -> {
                autocompleteTVQuery.setText("")
                autocompleteTVResult.setText("")
                val tempET = resources.getStringArray(R.array.temperature)
                val tempTV = resources.getStringArray(R.array.temperature)
                val arrayAdapterTempET = ArrayAdapter(this, R.layout.dropdown_menu, tempET)
                val arrayAdapterTempTV = ArrayAdapter(this, R.layout.dropdown_menu, tempTV)
                autocompleteTVQuery.setAdapter(arrayAdapterTempET)
                autocompleteTVQuery.onItemClickListener = onItemClickListenerQuery
                autocompleteTVResult.setAdapter(arrayAdapterTempTV)
                autocompleteTVResult.onItemClickListener = onItemClickListenerResult
            }
        }
    }

    private var onItemClickListenerQuery: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener { parent, _, position, _ ->
            when (parent!!.getItemAtPosition(position).toString()) {
                "cup" -> getQuerySelected(1)
                "tbsp" -> getQuerySelected(2)
                "tsp" -> getQuerySelected(3)
                "gr" -> getQuerySelected(4)
                "oz" -> getQuerySelected(5)
                "mL" -> getQuerySelected(6)
                "fl.cup" -> getQuerySelected(7)
                "fl.oz" -> getQuerySelected(8)
                "fl.tbsp" -> getQuerySelected(9)
                "fl.tsp" -> getQuerySelected(10)
                "C" -> getQuerySelected(11)
                "F" -> getQuerySelected(12)
            }
        }

    private var onItemClickListenerResult: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener { parent, _, position, _ ->
            when (parent!!.getItemAtPosition(position).toString()) {
                "cup" -> getResultSelected(1)
                "tbsp" -> getResultSelected(2)
                "tsp" -> getResultSelected(3)
                "gr" -> getResultSelected(4)
                "oz" -> getResultSelected(5)
                "mL" -> getResultSelected(6)
                "fl.cup" -> getResultSelected(7)
                "fl.oz" -> getResultSelected(8)
                "fl.tbsp" -> getResultSelected(9)
                "fl.tsp" -> getResultSelected(10)
                "C" -> getResultSelected(11)
                "F" -> getResultSelected(12)
            }
        }

    private fun getQuerySelected(item: Int) {
        when (item) {
            1 -> statusQuery = "cup"
            2 -> statusQuery = "tbsp"
            3 -> statusQuery = "tsp"
            4 -> statusQuery = "gr"
            5 -> statusQuery = "oz"
            6 -> statusQuery = "mL"
            7 -> statusQuery = "fl.cup"
            8 -> statusQuery = "fl.oz"
            9 -> statusQuery = "fl.tbsp"
            10 -> statusQuery = "fl.tsp"
            11 -> statusQuery = "C"
            12 -> statusQuery = "F"
        }
    }

    private fun getResultSelected(item: Int) {
        when (item) {
            1 -> statusResult = "cup"
            2 -> statusResult = "tbsp"
            3 -> statusResult = "tsp"
            4 -> statusResult = "gr"
            5 -> statusResult = "oz"
            6 -> statusResult = "mL"
            7 -> statusResult = "fl.cup"
            8 -> statusResult = "fl.oz"
            9 -> statusResult = "fl.tbsp"
            10 -> statusResult = "fl.tsp"
            11 -> statusResult = "C"
            12 -> statusResult = "F"
        }
    }

    private fun calculateResult() {
        val query = queryEditText.text.toString()
        val stringQuery = query.toDoubleOrNull()
        if (stringQuery == null || stringQuery == 0.0) {
            Toast.makeText(applicationContext, "Input number!", Toast.LENGTH_SHORT).show()
            return
        }
        when (statusQuery) {
            "cup" -> {
                when (statusResult) {
                    "cup" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 16)
                        resultTextView.text = result.toString()
                    }
                    "tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 48)
                        resultTextView.text = result.toString()
                    }
                    "gr" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 128)
                        resultTextView.text = result.toString()
                    }
                    "oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 4.5)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "tbsp" -> {
                when (statusResult) {
                    "tbsp" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "cup" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 16)
                        resultTextView.text = result.toString()
                    }
                    "tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 3)
                        resultTextView.text = result.toString()
                    }
                    "gr" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 14.3)
                        resultTextView.text = result.toString()
                    }
                    "oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 0.50479)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "tsp" -> {
                when (statusResult) {
                    "tsp" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "cup" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 0.02083)
                        resultTextView.text = result.toString()
                    }
                    "tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 3)
                        resultTextView.text = result.toString()
                    }
                    "gr" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 4.766)
                        resultTextView.text = result.toString()
                    }
                    "oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 0.168263)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "gr" -> {
                when (statusResult) {
                    "gr" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "cup" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 128)
                        resultTextView.text = result.toString()
                    }
                    "tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 14.3)
                        resultTextView.text = result.toString()
                    }
                    "tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 4.766)
                        resultTextView.text = result.toString()
                    }
                    "oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 0.0353)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "oz" -> {
                when (statusResult) {
                    "oz" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 0.50479)
                        resultTextView.text = result.toString()
                    }
                    "tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 0.168263)
                        resultTextView.text = result.toString()
                    }
                    "gr" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 0.0353)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "mL" -> {
                when (statusResult) {
                    "mL" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "fl.cup" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 250)
                        resultTextView.text = result.toString()
                    }
                    "fl.oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 29.574)
                        resultTextView.text = result.toString()
                    }
                    "fl.tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 14.787)
                        resultTextView.text = result.toString()
                    }
                    "fl.tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 4.929)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "fl.cup" -> {
                when (statusResult) {
                    "fl.cup" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "mL" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 236.6)
                        resultTextView.text = result.toString()
                    }
                    "fl.oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 8)
                        resultTextView.text = result.toString()
                    }
                    "fl.tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 16)
                        resultTextView.text = result.toString()
                    }
                    "fl.tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 48)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "fl.oz" -> {
                when (statusResult) {
                    "fl.oz" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "mL" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 29.574)
                        resultTextView.text = result.toString()
                    }
                    "fl.cup" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 8)
                        resultTextView.text = result.toString()
                    }
                    "fl.tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1.929)
                        resultTextView.text = result.toString()
                    }
                    "fl.tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 5.765)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "fl.tbsp" -> {
                when (statusResult) {
                    "fl.tbsp" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "mL" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 14.787)
                        resultTextView.text = result.toString()
                    }
                    "fl.cup" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 16)
                        resultTextView.text = result.toString()
                    }
                    "fl.oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 2)
                        resultTextView.text = result.toString()
                    }
                    "fl.tsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 3)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "fl.tsp" -> {
                when (statusResult) {
                    "fl.tsp" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "mL" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 4.929)
                        resultTextView.text = result.toString()
                    }
                    "fl.cup" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 48)
                        resultTextView.text = result.toString()
                    }
                    "fl.oz" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 5.765)
                        resultTextView.text = result.toString()
                    }
                    "fl.tbsp" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 1 / 3)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "C" -> {
                when (statusResult) {
                    "C" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "F" -> {
                        val result = kotlin.math.ceil(stringQuery.toDouble() * 33.8)
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            "F" -> {
                when (statusResult) {
                    "F" -> {
                        resultTextView.text = stringQuery.toDouble().toString()
                    }
                    "C" -> {
                        val result = (kotlin.math.ceil(stringQuery.toDouble() * -17.22))
                        resultTextView.text = result.toString()
                    }
                    else -> Toast.makeText(
                        applicationContext,
                        "Choose the unit!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> Toast.makeText(applicationContext, "Choose the units!", Toast.LENGTH_SHORT)
                .show()
        }
    }
}