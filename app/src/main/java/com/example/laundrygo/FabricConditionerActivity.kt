package com.example.laundrygo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FabricConditionerActivity : AppCompatActivity() {
    private var selectedSoap: String? = null
    private var soapQuantity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fabric_conditioner)

        // Retrieve selected detergent & quantity as INT
        selectedSoap = intent.getStringExtra("selectedSoap")
        soapQuantity = intent.getIntExtra("soapQuantity", 0)

        val fabricOptions = mapOf(
            R.id.fabric1 to "DOWNY",
            R.id.fabric2 to "CHAMPION",
            R.id.fabric3 to "DEL",
            R.id.fabric4 to "SURF"
        )

        fabricOptions.forEach { (viewId, fabricName) ->
            findViewById<ImageView>(viewId).setOnClickListener {
                showQuantityDialog(fabricName)
            }
        }

        // Handle back button click
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Handle continue button click
        findViewById<TextView>(R.id.buttonContinue).setOnClickListener {
            // Assuming you want to go to the BleachActivity even if no fabric conditioner is selected
            goToBleachActivity()
        }
    }

    private fun showQuantityDialog(fabricName: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_quantity, null)
        val quantityTextView = dialogView.findViewById<TextView>(R.id.quantityTextView)
        var quantity = 1 // Default quantity

        val decrementButton = dialogView.findViewById<Button>(R.id.buttonDecrement)
        val incrementButton = dialogView.findViewById<Button>(R.id.buttonIncrement)
        val confirmButton = dialogView.findViewById<Button>(R.id.buttonConfirm)
        val cancelButton = dialogView.findViewById<Button>(R.id.buttonCancel)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        dialog.show()

        quantityTextView.text = quantity.toString()

        decrementButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityTextView.text = quantity.toString()
            }
        }

        incrementButton.setOnClickListener {
            quantity++
            quantityTextView.text = quantity.toString()
        }

        confirmButton.setOnClickListener {
            goToSummaryActivity(fabricName, quantity)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun goToSummaryActivity(fabricName: String, fabricQuantity: Int) {
        val intent = Intent(this, BleachActivity::class.java).apply {
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
            putExtra("fabricName", fabricName)
            putExtra("fabricQuantity", fabricQuantity)
        }
        startActivity(intent)
    }

    private fun goToBleachActivity() {
        // Navigate directly to BleachActivity if "Continue" is clicked without a fabric conditioner selection
        val intent = Intent(this, BleachActivity::class.java).apply {
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
        }
        startActivity(intent)
    }
}
