package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForgotPasswordNewActivity : AppCompatActivity() {

    private  lateinit var backArrow: ImageView
    private lateinit var newPassword: EditText
    private lateinit var confirmNewPassword: EditText
    private lateinit var resetPasswordButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password_new)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        backArrow = findViewById(R.id.back_arrow)
        newPassword= findViewById(R.id.new_password_input)
        confirmNewPassword = findViewById(R.id.confirm_new_password_input)
        resetPasswordButton = findViewById(R.id.reset_password_button)
        progressBar= findViewById(R.id.progress_bar)

        backArrow.setOnClickListener {
            finish()
        }

        resetPasswordButton.setOnClickListener {
            val newPass = newPassword.text.toString()
            val confirmPass = confirmNewPassword.text.toString()

            if (newPass.isNotEmpty() && confirmPass.isNotEmpty() && newPass == confirmPass) {
                Toast.makeText(this, "Resetting password...", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.VISIBLE

                android.os.Handler().postDelayed({
                    progressBar.visibility = View.GONE
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("reset_message", "Password reset successfully")
                    startActivity(intent)
                    finish()
                }, 2000)
            } else {
                Toast.makeText(this, "Password must be same", Toast.LENGTH_SHORT).show()
            }
        }

    }
}