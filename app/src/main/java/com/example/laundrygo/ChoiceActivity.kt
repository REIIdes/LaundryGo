package com.example.laundrygo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ChoiceActivity : AppCompatActivity() {
    private var fabricName: String? = null
    private var fabricQuantity: Int = 0
    private var selectedSoap: String? = null
    private var soapQuantity: Int = 0
    private var bleachName: String? = null
    private var bleachQuantity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        val totalPrice = intent.getIntExtra("TOTAL_COST", 0) // Default to 0 if missing

        val buttonDelivery = findViewById<Button>(R.id.buttonLogin2)
        val buttonPickup = findViewById<Button>(R.id.buttonLogin)

        buttonPickup.setOnClickListener {
            val intent = Intent(this, pickupActivity::class.java)
            intent.putExtra("TOTAL_COST", totalPrice)
            startActivity(intent)
        }

        buttonDelivery.setOnClickListener {
            val intent = Intent(this, DeliveryActivity::class.java)
            intent.putExtra("TOTAL_COST", totalPrice)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Moved this function outside onCreate to avoid nesting issues
    private fun goToPickupActivity() {
        val intent = Intent(this, SummaryActivity::class.java).apply {
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
            putExtra("fabricName", fabricName)
            putExtra("fabricQuantity", fabricQuantity)
            putExtra("bleachName", bleachName)
            putExtra("bleachQuantity", bleachQuantity)
        }
        startActivity(intent)
    }
}
