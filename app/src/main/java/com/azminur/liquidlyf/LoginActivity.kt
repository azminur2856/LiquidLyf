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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var password: EditText
    private lateinit var forgotPasswordLink: TextView
    private lateinit var loginButton: Button
    private lateinit var rememberCheckbox: CheckBox
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private val PREFS_NAME = "login_prefs"
    private val KEY_CREDENTIAL = "saved_credential"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        emailInput = findViewById(R.id.phone_number_input)
        password = findViewById(R.id.password_input)
        forgotPasswordLink = findViewById(R.id.forgot_password_link)
        loginButton = findViewById(R.id.login_button)
        rememberCheckbox = findViewById(R.id.remember_checkbox)
        progressBar = findViewById(R.id.progress_bar)
        auth = Firebase.auth


        val resetMessage = intent.getStringExtra("reset_message")
        if (!resetMessage.isNullOrEmpty()) {
            Toast.makeText(this, resetMessage, Toast.LENGTH_SHORT).show()
        }

        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedCredential = sharedPref.getString(KEY_CREDENTIAL, "")
        if (!savedCredential.isNullOrEmpty()) {
            emailInput.setText(savedCredential)
            rememberCheckbox.isChecked = true
        }

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val pass = password.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter your email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call the new login method
            loginUser(email, pass)
        }

        forgotPasswordLink.setOnClickListener {
            val intent = Intent(this, ForgotPasswordPhoneActivity::class.java)
            startActivity(intent)
        }

        rememberCheckbox.setOnCheckedChangeListener { _, isChecked ->
            val sharedPref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
            val editor = sharedPref.edit()

            if (isChecked) {
                val credential = emailInput.text.toString()
                editor.putString(KEY_CREDENTIAL, credential)
                editor.apply()
                Toast.makeText(this, "Email/Phone saved!", Toast.LENGTH_SHORT).show()
            } else {
                editor.remove(KEY_CREDENTIAL)
                editor.apply()
                Toast.makeText(this, "Email/Phone removed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    // START: ADDED EMAIL VERIFICATION CHECK
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Save login state
                        val sharedPref = getSharedPreferences("auth_prefs", MODE_PRIVATE)
                        sharedPref.edit()
                            .putBoolean("is_logged_in", true)
                            .apply()

                        val intent = Intent(this, AfterLoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // User is signed in but email is not verified
                        Toast.makeText(this, "Please verify your email address to log in.", Toast.LENGTH_LONG).show()

                        // Optionally, sign out the user after the check and offer to resend the verification email
                        user?.sendEmailVerification()
                        Toast.makeText(this, "Verification email resent.", Toast.LENGTH_SHORT).show()

                        auth.signOut()
                    }
                    // END: ADDED EMAIL VERIFICATION CHECK

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

//    private fun loginUser(email: String, password: String) {
//        progressBar.visibility = View.VISIBLE
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                progressBar.visibility = View.GONE
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
//
//                    // Save login state
//                    val sharedPref = getSharedPreferences("auth_prefs", MODE_PRIVATE)
//                    sharedPref.edit()
//                        .putBoolean("is_logged_in", true)
//                        .apply()
//
//                    val intent = Intent(this, AfterLoginActivity::class.java)
//                    startActivity(intent)
//                    finish() // Prevent going back to LoginActivity
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
//                }
//            }
//    }
}