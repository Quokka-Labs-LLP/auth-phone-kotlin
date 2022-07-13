package com.android.loginviaphonenumberlibrary.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.android.loginviaphonefirebase.PhoneLogin
import com.android.loginviaphonenumberlibrary.R

class DoubleScreenActivity : AppCompatActivity() {

    lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_screen)

        editText= findViewById(R.id.editTextTextPersonName3)

        findViewById<Button>(R.id.button5).setOnClickListener {
            val number = "+91" + editText.text.toString()
            if(number != "") {
                PhoneLogin.sendVerificationCode(number, this)
                startActivity(Intent(this, SecondActivity::class.java))
            }
        }
    }
}