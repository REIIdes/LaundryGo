package com.example.laundrygo


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        val buttonDelivery = findViewById<Button>(R.id.buttonLogin2)
        val buttonPickup = findViewById<Button>(R.id.buttonLogin)

        buttonPickup.setOnClickListener {
            val intent = Intent(this, PickupActivity::class.java) // Fixed
            startActivity(intent)
        }

        buttonDelivery.setOnClickListener {
            val intent = Intent(this, DeliveryActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
