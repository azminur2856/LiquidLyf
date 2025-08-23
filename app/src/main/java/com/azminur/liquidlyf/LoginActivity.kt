package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var forgotPasswordLink: TextView
    private lateinit var loginButton: Button
    private lateinit var rememberCheckbox: CheckBox
    private lateinit var progressBar: ProgressBar
    private val PREFS_NAME = "login_prefs"
    private val KEY_PHONE = "saved_phone"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        phone = findViewById(R.id.phone_number_input)
        password = findViewById(R.id.password_input)
        forgotPasswordLink = findViewById(R.id.forgot_password_link)
        loginButton = findViewById(R.id.login_button)
        rememberCheckbox = findViewById(R.id.remember_checkbox)
        progressBar = findViewById(R.id.progress_bar)


        val resetMessage = intent.getStringExtra("reset_message")
        if (!resetMessage.isNullOrEmpty()) {
            Toast.makeText(this, resetMessage, Toast.LENGTH_SHORT).show()
        }

        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedPhone = sharedPref.getString(KEY_PHONE, "")
        if (!savedPhone.isNullOrEmpty()) {
            phone.setText(savedPhone)
            rememberCheckbox.isChecked = true
        }

        loginButton.setOnClickListener {
            val phoneN = "01234567890"
            val pass = "1"
            if(phone.text.toString() == phoneN && password.text.toString() == pass) {
                Toast.makeText(this, "Login...", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.VISIBLE

                // Save login state
                val sharedPref = getSharedPreferences("auth_prefs", MODE_PRIVATE)
                sharedPref.edit()
                    .putBoolean("is_logged_in", true)
                    .apply()

                android.os.Handler().postDelayed({
                    progressBar.visibility = View.GONE
                    val intent = Intent(this, AfterLoginActivity::class.java)
                    startActivity(intent)
                    finish() // Prevent going back to LoginActivity
                }, 1000)
            }
            else{
                Toast.makeText(this, "Wrong phone or password", Toast.LENGTH_SHORT).show()
            }
        }

        forgotPasswordLink.setOnClickListener {
            val intent = Intent(this, ForgotPasswordPhoneActivity::class.java)
            startActivity(intent)
        }

        rememberCheckbox.setOnCheckedChangeListener { _, isChecked ->
            val sharedPref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
            val editor = sharedPref.edit()

            if (isChecked) {
                val phoneNumber = phone.text.toString()
                editor.putString(KEY_PHONE, phoneNumber)
                editor.apply()
                Toast.makeText(this, "Phone number saved!", Toast.LENGTH_SHORT).show()
            } else {
                editor.remove(KEY_PHONE)
                editor.apply()
                Toast.makeText(this, "Phone number removed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}