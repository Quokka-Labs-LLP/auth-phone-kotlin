package com.android.loginviaphonefirebase.util

interface OnVerificationCodeListener {
    fun onCodeSent(verificationID:String)
    fun onVerificationCompleted(code:String)
    fun onVerificationFailed(error:String)
}