package com.example.laundrygo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SoapActivity : AppCompatActivity() {

    private var selectedType: String? = null  // Store the selected service type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soap)

        // Retrieve the selected type (wash, fold, etc.) from the intent
        selectedType = intent.getStringExtra("selectedType")

        val buttonContinue: TextView = findViewById(R.id.buttonContinue)
        buttonContinue.setOnClickListener {
            // Navigate to FabricConditionerActivity once the continue button is clicked
            navigateToFabricConditionerActivity()
        }

        // Mapping of soap options to their respective names
        val soapOptions = mapOf(
            R.id.backgroundTide to "TIDE",
            R.id.backgroundAriel to "ARIEL",
            R.id.backgroundBreeze to "BREEZE",
            R.id.backgroundSurf to "SURF"
        )

        // Set onClickListeners for each soap option
        soapOptions.forEach { (layoutId, soapName) ->
            findViewById<View>(layoutId).setOnClickListener {
                showQuantityDialog(soapName)
            }
        }

        // Handle back button click
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Function to navigate to the FabricConditionerActivity
    private fun navigateToFabricConditionerActivity() {
        val intent = Intent(this, FabricConditionerActivity::class.java)
        // Pass the selected type to the next activity
        intent.putExtra("selectedType", selectedType)
        startActivity(intent)
    }

    // Function to show the quantity dialog for selecting soap quantity
    private fun showQuantityDialog(selectedSoap: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_quantity, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.show()

        var quantity = 1

        val quantityTextView = dialogView.findViewById<TextView>(R.id.quantityTextView)
        val buttonIncrement = dialogView.findViewById<Button>(R.id.buttonIncrement)
        val buttonDecrement = dialogView.findViewById<Button>(R.id.buttonDecrement)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.buttonConfirm)
        val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)

        quantityTextView.text = quantity.toString()

        // Increment quantity
        buttonIncrement.setOnClickListener {
            quantity++
            quantityTextView.text = quantity.toString()
        }

        // Decrement quantity
        buttonDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityTextView.text = quantity.toString()
            }
        }

        // Confirm and navigate to the next activity with the selected soap and quantity
        buttonConfirm.setOnClickListener {
            navigateToFabricConditioner(selectedSoap, quantity)
            dialog.dismiss()
        }

        // Cancel the dialog
        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    // Function to navigate to FabricConditionerActivity with the selected soap and quantity
    private fun navigateToFabricConditioner(selectedSoap: String, soapQuantity: Int) {
        val intent = Intent(this, FabricConditionerActivity::class.java).apply {
            putExtra("selectedSoap", selectedSoap)  // Pass the selected soap
            putExtra("soapQuantity", soapQuantity)  // Pass the soap quantity
            putExtra("selectedType", selectedType)  // Pass the selected service type
        }
        startActivity(intent)
    }
}
