package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var fullName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var email: EditText
    private lateinit var bloodGroupSpinner: Spinner
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var createAccountButton: Button
    private lateinit var signInLink: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        backArrow = findViewById(R.id.back_arrow)
        fullName = findViewById(R.id.full_name_input)
        phoneNumber = findViewById(R.id.phone_number_input)
        email = findViewById(R.id.email_input)
        bloodGroupSpinner = findViewById(R.id.blood_group_spinner)
        password = findViewById(R.id.password_input)
        confirmPassword = findViewById(R.id.confirm_password_input)
        createAccountButton = findViewById(R.id.create_account_button)
        signInLink = findViewById(R.id.signin_link)
        progressBar = findViewById(R.id.progress_bar)


        backArrow.setOnClickListener {
            finish()
        }

        val bloodGroups = resources.getStringArray(R.array.blood_groups)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodGroupSpinner.adapter = adapter

        createAccountButton.setOnClickListener {
            Toast.makeText(this, "Creating account...", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.VISIBLE

            android.os.Handler().postDelayed({
                progressBar.visibility = View.GONE
                val intent = Intent(this, RegisterVerifyActivity::class.java)
                startActivity(intent)
            }, 2000)
        }

        signInLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}