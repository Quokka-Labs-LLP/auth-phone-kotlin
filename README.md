# Phone number login via firebase module

Features:
login via phone number

To import this module, First download it. Now open your project in which you want to use this
module.

Goto File -> New -> Import Module Select the source directory of the Module you want to import and
click Finish. Open Project Structure Dialog (You can open the PSD by selecting File > Project
Structure) and from the left panel click on Dependencies. Open the Dependencies tab. Click
on <All Module> now in right side you will see add dependencies tab. Click the (+) icon from
Dependencies section and click Module Dependency. Add Module Dependency dialog will open, Now select
app module and click Ok. Then you will see another Add Module Dependency dialog and it will show you 
loginviaphonefirebase module. Click on CheckBox then click ok. Open your build.gradle file and check
that the module is now listed under dependencies as shown below. implementation project(path: ':
loginviaphonefirebase')

After Successfully importing loginviaphonefirebase module, Now let's implement it.

Then Follow this to make project in firebase console.
https://firebase.google.com/docs/auth/android/phone-auth#kotlin+ktx

Then enable Android Device Verification API. The SafetyNet Attestation API is deprecated and has
been replaced by the Play Integrity API.
(If this is not enabled then it will open chrome to check for reCAPTCHA verification)
https://console.cloud.google.com/apis/library/androidcheck.googleapis.com

Then Goto firebase -> Open your project -> Authentication -> Sign-in method and enable phone Sign-in
providers.

Now Goto project settings and add SHA certificate fingerprints. Download latest google-services.json
and paste it inside your app folder.

Check google services classpath is added build.gradle (project level) file. If not Add this.

    buildscript {
        dependencies {
        classpath 'com.google.gms:google-services:4.3.10'
        }
    }

In build.gradle (module level) file.
see if this plugin is added, If not Add this.

    plugins {
        id 'com.google.gms.google-services'
    }
        
    implementation 'com.google.firebase:firebase-auth-ktx:21.0.6'

Then sync project.

This module can be used in 2 ways.

[1]. If you are using Two screen validation (enter phone number and enter OTP are on different screen) then use this.

In Enter number Screen

Inside onCreate() function.

//Set onClick listener in send OTP button.
      
      findViewById<Button>(R.id.button).setOnClickListener 
      { 
            PhoneLogin.sendVerificationCode("[ENTERED MOBILE NUMBER HERE]",this)
            startActivity(Intent(this,OtpScreen::class.java))
      }


Then in OTP Screen
you have to implement a listener i.e., OnVerificationCodeListener and import all the functions.

      class OtpScreen : AppCompatActivity(), OnVerificationCodeListener

create variable:
      
      var verificationId=""

Inside onCreate() function.

      PhoneLogin.initializePhoneLogin(this)

//we have to set verificationID to verify number and the OTP send to that number.

      override fun onCodeSent(verificationID: String) {
      verificationId= verificationID
      }
[CASE 1] If you want to autofill the otp block, use below code.

    override fun onVerificationCompleted(code: String) {
     edtOTP.setText(code)  // edtOTP.binding.setText(code) 
    }

[CASE 2] or (if you want that if otp is auto filled then automatically verify code. Use below code)
      
      override fun onVerificationCompleted(code: String) {
        editText.setText(code)  // edtOTP.binding.setText(code) 
        val auth = PhoneLogin.verifyCode(verificationId,code)
        System.err.println(">>>>> auth $auth")
        signInWithCredential(auth)
    }

    override fun onVerificationFailed(error: String) {

    }

After OTP is filled, If you want to click listener on verify button. [if you have used CASE 1]
[If you have used CASE 2 then skip this]


      findViewById<Button>(R.id.button2).setOnClickListener {
      val otp = editText.text.toString()
      if(otp != ""){
            val auth = PhoneLogin.verifyCode(verificationId,otp)
            System.err.println(">>>>> auth $auth")
            signInWithCredential(auth)
      }
      else{
            Toast.makeText(this,"Enter a valid otp", Toast.LENGTH_SHORT).show()
      }
      }

      
// Calling signInWithCredential function to Authenticate user.
   
      fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                try {
                    System.err.println(">>>>>Successful login")
                    startActivity(Intent(this,MainActivity::class.java))
                } catch (e: Exception) {
                    System.err.println(">>>>> ${e.localizedMessage}")
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }


[2]. If you are using one screen validation (enter phone number and enter OTP are on same screen) then use this.

First, implement a listener i.e., OnVerificationCodeListener and import all the functions.

      class EnterNumberScreen : AppCompatActivity(), OnVerificationCodeListener

create variable:
      
      var verificationId=""


Inside onCreate() function.

      PhoneLogin.initializePhoneLogin(this)
      
//Set onClick listener in send OTP button.
      
      findViewById<Button>(R.id.button).setOnClickListener
      {
            PhoneLogin.sendVerificationCode("[ENTERED MOBILE NUMBER HERE]",this)
      }

//we have to set verificationID to verify number and the OTP send to that number.

      override fun onCodeSent(verificationID: String) {
      verificationId= verificationID
      }
[CASE 1] If you want to autofill the otp block, use below code.

    override fun onVerificationCompleted(code: String) {
     edtOTP.setText(code)  // edtOTP.binding.setText(code) 
    }

[CASE 2] or (if you want that if otp is auto filled then automatically verify code. Use below code)

      override fun onVerificationCompleted(code: String) {
        editText.setText(code)  // edtOTP.binding.setText(code) 
        val auth = PhoneLogin.verifyCode(verificationId,code)
        System.err.println(">>>>> auth $auth")
        signInWithCredential(auth)
    }

    override fun onVerificationFailed(error: String) {

    }


After OTP is filled, If you want to click listener on verify button. [if you have used CASE 1]
[If you have used CASE 2 then skip this]

      findViewById<Button>(R.id.button2).setOnClickListener {
      val otp = editText.text.toString()
      if(otp != ""){
            val auth = PhoneLogin.verifyCode(verificationId,otp)
            System.err.println(">>>>> auth $auth")
            signInWithCredential(auth)
      }
      else{
            Toast.makeText(this,"Enter a valid otp", Toast.LENGTH_SHORT).show()
      }
      }


// Calling signInWithCredential function to Authenticate user.

      fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                try {
                    System.err.println(">>>>>Successful login")
                    startActivity(Intent(this,MainActivity::class.java))
                } catch (e: Exception) {
                    System.err.println(">>>>> ${e.localizedMessage}")
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
