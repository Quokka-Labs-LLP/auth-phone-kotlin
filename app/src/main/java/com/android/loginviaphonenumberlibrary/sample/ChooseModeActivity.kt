package com.android.loginviaphonenumberlibrary.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.loginviaphonenumberlibrary.R

class ChooseModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_mode)

        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this,DoubleScreenActivity::class.java))
        }
    }
}