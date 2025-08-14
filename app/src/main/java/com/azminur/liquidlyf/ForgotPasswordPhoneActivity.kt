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

class ForgotPasswordPhoneActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var phone: EditText
    private lateinit var sendCodeButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password_phone)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        backArrow = findViewById(R.id.back_arrow)
        phone = findViewById(R.id.phone_number_input)
        sendCodeButton = findViewById(R.id.send_code_button)
        progressBar = findViewById(R.id.progress_bar)

        backArrow.setOnClickListener {
            finish()
        }

        val number:String = "01234567890"

        sendCodeButton.setOnClickListener {
            if(phone.text.isNotEmpty() && phone.text.toString() == number){
                Toast.makeText(this, "Sending verification code...", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.VISIBLE

                android.os.Handler().postDelayed({
                    progressBar.visibility = View.GONE
                val intent = Intent(this, ForgotPasswordVerifyActivity::class.java)
                startActivity(intent)
                }, 1000)
            }
            else{
                Toast.makeText(this, "Input a valid phone number.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}