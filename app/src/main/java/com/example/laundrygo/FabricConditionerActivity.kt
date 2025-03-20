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
    private var selectedType: String? = null  // Declare selectedType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fabric_conditioner)

        // Retrieve selected detergent, quantity, and service type (wash, handwash, fold, etc.)
        selectedSoap = intent.getStringExtra("selectedSoap")
        soapQuantity = intent.getIntExtra("soapQuantity", 0)
        selectedType = intent.getStringExtra("selectedType")  // Retrieve selectedType

        // Define fabric conditioner options with their respective IDs
        val fabricOptions = mapOf(
            R.id.fabric1 to "DOWNY",
            R.id.fabric2 to "CHAMPION",
            R.id.fabric3 to "DEL",
            R.id.fabric4 to "SURF"
        )

        // Set listeners for fabric conditioner options
        fabricOptions.forEach { (viewId, fabricName) ->
            findViewById<ImageView>(viewId).setOnClickListener {
                showQuantityDialog(fabricName)
            }
        }

        // Handle back button click to return to the previous activity
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Handle the continue button click to proceed to BleachActivity
        findViewById<TextView>(R.id.buttonContinue).setOnClickListener {
            // Go to BleachActivity if no fabric conditioner selection is made
            goToBleachActivity()
        }
    }

    // Function to show a dialog for selecting fabric conditioner quantity
    private fun showQuantityDialog(fabricName: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_quantity, null)
        val quantityTextView = dialogView.findViewById<TextView>(R.id.quantityTextView)
        var quantity = 1 // Default quantity

        // Buttons for adjusting the quantity
        val decrementButton = dialogView.findViewById<Button>(R.id.buttonDecrement)
        val incrementButton = dialogView.findViewById<Button>(R.id.buttonIncrement)
        val confirmButton = dialogView.findViewById<Button>(R.id.buttonConfirm)
        val cancelButton = dialogView.findViewById<Button>(R.id.buttonCancel)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        dialog.show()

        // Update the quantity displayed in the dialog
        quantityTextView.text = quantity.toString()

        // Decrement quantity
        decrementButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityTextView.text = quantity.toString()
            }
        }

        // Increment quantity
        incrementButton.setOnClickListener {
            quantity++
            quantityTextView.text = quantity.toString()
        }

        // Confirm the selection and navigate to BleachActivity
        confirmButton.setOnClickListener {
            goToSummaryActivity(fabricName, quantity)
            dialog.dismiss()
        }

        // Close the dialog without selecting
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    // Function to navigate to BleachActivity with the selected fabric details
    private fun goToSummaryActivity(fabricName: String, fabricQuantity: Int) {
        val intent = Intent(this, BleachActivity::class.java).apply {
            // Pass the selected soap, soap quantity, fabric conditioner, and fabric quantity
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
            putExtra("fabricName", fabricName)
            putExtra("fabricQuantity", fabricQuantity)
            putExtra("selectedType", selectedType)  // Pass selectedType to BleachActivity
        }
        startActivity(intent)
    }

    // Function to navigate directly to BleachActivity if continue is clicked without fabric conditioner selection
    private fun goToBleachActivity() {
        // Navigate directly to BleachActivity with soap, soap quantity, and selectedType
        val intent = Intent(this, BleachActivity::class.java).apply {
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
            putExtra("selectedType", selectedType)  // Pass selectedType to BleachActivity
        }
        startActivity(intent)
    }
}
