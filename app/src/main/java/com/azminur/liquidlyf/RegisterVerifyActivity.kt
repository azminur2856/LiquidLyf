package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.text.TextWatcher


class RegisterVerifyActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText
    private lateinit var resendCodeLink: TextView
    private lateinit var countdownText: TextView
    private lateinit var verifyButton: Button
    private lateinit var progressBar: ProgressBar

    // In a real application, you would receive the correct OTP from an API
    // For this example, we'll use a hardcoded value.
    private var corrOtp = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_verify)

        backArrow = findViewById(R.id.back_arrow)
        otp1 = findViewById(R.id.otp1)
        otp2 = findViewById(R.id.otp2)
        otp3 = findViewById(R.id.otp3)
        otp4 = findViewById(R.id.otp4)
        otp5 = findViewById(R.id.otp5)
        otp6 = findViewById(R.id.otp6)
        resendCodeLink = findViewById(R.id.resend_code_link)
        countdownText = findViewById(R.id.resend_time_countdown)
        verifyButton = findViewById(R.id.verify_code_button)
        progressBar = findViewById(R.id.progress_bar)

        val otpFields = listOf(otp1, otp2, otp3, otp4, otp5, otp6)

        for (i in otpFields.indices) {
            otpFields[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && i < otpFields.lastIndex) {
                        otpFields[i + 1].requestFocus()
                    } else if (s.isNullOrEmpty() && i > 0) {
                        otpFields[i - 1].requestFocus()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }

        backArrow.setOnClickListener {
            finish()
        }

        resendCodeLink.setOnClickListener {
            resendCode()
        }

        verifyButton.setOnClickListener {
            val enteredOtp = otp1.text.toString() + otp2.text.toString() +
                    otp3.text.toString() + otp4.text.toString() +
                    otp5.text.toString() + otp6.text.toString()
            if(corrOtp == enteredOtp){
                Toast.makeText(this, "Verifying code...", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.VISIBLE

                android.os.Handler().postDelayed({
                    progressBar.visibility = View.GONE
                    // On successful registration, navigate to LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("registration_success", "Registration successful. Please log in.")
                    startActivity(intent)
                    finish() // Close this activity
                }, 1000)
            }
            else{
                Toast.makeText(this, "Input valid code.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resendCode() {
        Toast.makeText(this, "Resending code...", Toast.LENGTH_SHORT).show()

        progressBar.visibility = View.VISIBLE

        android.os.Handler().postDelayed({
            progressBar.visibility = View.GONE
            resendCodeLink.isEnabled = false
            resendCodeLink.setTextColor(ContextCompat.getColor(this, R.color.textColorHint))
            startCountdown(120)
        }, 500)
    }

    private fun startCountdown(seconds: Int) {
        countdownText.text = ""
        object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished / 1000
                val min = sec / 60
                val secPart = sec % 60
                countdownText.text = String.format("%02d:%02d", min, secPart)
            }

            override fun onFinish() {
                countdownText.text = ""
                resendCodeLink.isEnabled = true
                resendCodeLink.setTextColor(ContextCompat.getColor(this@RegisterVerifyActivity, R.color.red_button))
            }
        }.start()
    }
}