package com.android.loginviaphonenumberlibrary.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.loginviaphonefirebase.PhoneLogin
import com.android.loginviaphonefirebase.util.OnVerificationCodeListener
import com.android.loginviaphonenumberlibrary.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential

class MainActivity : AppCompatActivity(), OnVerificationCodeListener {

    var verificationId=""
    lateinit var editText :EditText
    lateinit var editTextNumber :EditText
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth= FirebaseAuth.getInstance()
        PhoneLogin.initializePhoneLogin(this)

        editText= findViewById(R.id.editTextTextPersonName)

        editTextNumber= findViewById(R.id.editTextTextPersonName2)

        findViewById<Button>(R.id.button).setOnClickListener {
            val number = "+91" + editTextNumber.text.toString()
            if(number != "") {
                PhoneLogin.sendVerificationCode(number, this)
//                startActivity(Intent(this, SecondActivity::class.java))
            }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            val otp = editText.text.toString()
            if(otp != ""){
               val auth = PhoneLogin.verifyCode(verificationId,otp)
                System.err.println(">>>>> auth $auth")
                signInWithCredential(auth)
            }
            else{
                Toast.makeText(this,"Enter a valid otp",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
       firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                try {
                    System.err.println(">>>>> ${task.result}")
                    System.err.println(">>>>> ${task.exception}")

                    System.err.println(">>>>>Successful login")
                    startActivity(Intent(this, HomeActivity::class.java))
                } catch (e: Exception) {
                    System.err.println(">>>>> ${e.localizedMessage}")
                }
            }
            .addOnFailureListener {

                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
    override fun onCodeSent(verificationID: String) {
        verificationId = verificationID
        System.err.println(">>>> verification id $verificationID")
    }

    override fun onVerificationCompleted(code: String) {
        editText.setText(code)
        System.err.println(">>>> on verification  $code")
    }

    override fun onVerificationFailed(error: String) {
        System.err.println(">>>> error $error")
    }
}