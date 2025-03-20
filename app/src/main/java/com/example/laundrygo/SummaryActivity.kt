package com.example.laundrygo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Find Views
        val selectedSoapTextView = findViewById<TextView>(R.id.selectedSoapTextView)
        val selectedFabricTextView = findViewById<TextView>(R.id.selectedFabricTextView)
        val selectedBleachTextView = findViewById<TextView>(R.id.selectedBleachTextView)
        val totalPriceTextView = findViewById<TextView>(R.id.totalPriceTextView)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val confirmButton = findViewById<Button>(R.id.confirmButton)

        // Retrieve passed data (ensure correct data types)
        val selectedSoap = intent.getStringExtra("selectedSoap") ?: "N/A"
        val soapQuantity = intent.getIntExtra("soapQuantity", 0)
        val fabricName = intent.getStringExtra("fabricName") ?: "N/A"
        val fabricQuantity = intent.getIntExtra("fabricQuantity", 0)
        val bleachName = intent.getStringExtra("bleachName") ?: "N/A"
        val bleachQuantity = intent.getIntExtra("bleachQuantity", 0)

        // Debugging logs (remove after testing)
        println("DEBUG: Selected Soap = $selectedSoap, Quantity = $soapQuantity")
        println("DEBUG: Selected Fabric = $fabricName, Quantity = $fabricQuantity")
        println("DEBUG: Selected Bleach = $bleachName, Quantity = $bleachQuantity")

        // Define detergent prices
        val detergentPrices = mapOf(
            "TIDE" to 15,
            "ARIEL" to 15,
            "BREEZE" to 21,
            "SURF" to 10
        )

        // Define fabric softener prices
        val fabricPrices = mapOf(
            "DOWNY" to 10,
            "CHAMPION" to 12,
            "DEL" to 14,
            "SURF" to 9
        )

        // Define bleach prices
        val bleachPrices = mapOf(
            "COLOR SAFE ZONROX" to 21,
            "ORIGINAL ZONROX" to 15
        )

        // Ensure uppercase key lookup
        val detergentPrice = detergentPrices[selectedSoap.uppercase()] ?: 0
        val fabricPrice = fabricPrices[fabricName.uppercase()] ?: 0
        val bleachPrice = bleachPrices[bleachName.uppercase()] ?: 0

        // Calculate total
        val totalPrice = (detergentPrice * soapQuantity) + (fabricPrice * fabricQuantity) + (bleachPrice * bleachQuantity)

        // Update UI
        selectedSoapTextView.text = "Detergent: $selectedSoap x$soapQuantity (PHP ${detergentPrice * soapQuantity})"
        selectedFabricTextView.text = "Fabric Softener: $fabricName x$fabricQuantity (PHP ${fabricPrice * fabricQuantity})"
        selectedBleachTextView.text = "Bleach: $bleachName x$bleachQuantity (PHP ${bleachPrice * bleachQuantity})"
        totalPriceTextView.text = "Total Price: PHP $totalPrice"

        // Confirm Button -> Navigates to ChoiceActivity
        confirmButton.setOnClickListener {
            val intent = Intent(this, ChoiceActivity::class.java)
            intent.putExtra("TOTAL_COST", totalPrice) // Pass total price to ChoiceActivity
            startActivity(intent)
            finish() // Remove SummaryActivity from back stack
        }

        // Cancel Button -> Shows confirmation dialog
        cancelButton.setOnClickListener {
            showCancelDialog()
        }
    }

    // Function to navigate back to HomeFragment
    private fun navigateTohome() {
        val intent = Intent(this, home::class.java) // Ensure `home.kt` is an Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Close the current activity
    }

    // Function to show cancel confirmation dialog
    private fun showCancelDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure?")
        builder.setMessage("This will clear all selections and return to Home.")

        builder.setPositiveButton("Yes") { _, _ ->
            clearSelections()
            navigateTohome() // Calls the function to go to `home.kt`
        }

        builder.setNegativeButton("Back") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    // Function to clear selections (if needed)
    private fun clearSelections() {
        // Implement logic to reset selections (if needed)
    }
}
