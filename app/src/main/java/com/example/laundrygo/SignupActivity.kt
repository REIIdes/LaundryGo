package com.example.laundrygo

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class SignupActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonSignup: Button

    private lateinit var textViewLogin: TextView
    private lateinit var progressDialog: ProgressDialog
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize views
        editTextName = findViewById(R.id.editTextName)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonSignup = findViewById(R.id.buttonSignup)

        textViewLogin = findViewById(R.id.signin)

        // Initialize Volley request queue and progress dialog
        requestQueue = Volley.newRequestQueue(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Signing up...")

        // Set click listener for the signup button
        buttonSignup.setOnClickListener { registerUser() }

        // Navigate to MainActivity when login text is clicked
        textViewLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val name = editTextName.text.toString().trim()
        val address = editTextAddress.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()

        // Input validation
        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Show progress dialog while registering
        progressDialog.show()

        // Define the URL for registration
        val url = "http://10.0.2.2/laundrygo/register.php"

        // Create a request body with the form data
        val request = object : StringRequest(Method.POST, url,
            { response ->
                progressDialog.dismiss() // Dismiss progress dialog
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                    if (status == "success") {
                        // Navigate to MainActivity on successful signup
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                progressDialog.dismiss() // Dismiss progress dialog on error
                val errorMessage = error.message ?: "Signup failed"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }) {

            // Set POST parameters to send data
            override fun getParams(): Map<String, String> {
                return hashMapOf(
                    "name" to name,
                    "address" to address,
                    "email" to email,
                    "password" to password
                )
            }

            // Set content type header
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                return headers
            }
        }

        // Add the request to the request queue for execution
        requestQueue.add(request)
    }
}
