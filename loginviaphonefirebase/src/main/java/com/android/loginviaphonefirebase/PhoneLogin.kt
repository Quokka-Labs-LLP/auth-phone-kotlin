package com.android.loginviaphonefirebase

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.android.loginviaphonefirebase.util.OnVerificationCodeListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneLogin {
    companion object {

        val firebaseAuth = FirebaseAuth.getInstance()
        lateinit var codeListener: OnVerificationCodeListener

        fun initializePhoneLogin(verificationCodeListener: OnVerificationCodeListener) {
            codeListener = verificationCodeListener
        }

        fun sendVerificationCode(phone: String, activity: Activity) {

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity) // Activity (for callback binding)
                .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }

        // callback method is called on Phone auth provider.
        private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onCodeSent(
                    s: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken,
                ) {
                    super.onCodeSent(s, forceResendingToken)
                    codeListener.onCodeSent(s)
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    val code = phoneAuthCredential.smsCode
                    if (code != null) {
                        codeListener.onVerificationCompleted(code)
                    }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // displaying error message with firebase exception.
                    codeListener.onVerificationFailed(e.localizedMessage)
                    System.err.println("error ${e.localizedMessage}")
                }
            }

        fun verifyCode(verificationId: String, code: String): PhoneAuthCredential {
            // below line is used for getting
            // credentials from our verification id and code.
            System.err.println(">>>>>verification ID  $verificationId")
            System.err.println(">>>>> OTP $code")
            return PhoneAuthProvider.getCredential(verificationId, code)
//            signInWithCredential(credential)
        }
    }
}