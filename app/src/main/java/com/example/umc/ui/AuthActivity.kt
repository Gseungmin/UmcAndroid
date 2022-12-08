package com.example.umc.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.isVisible
import com.example.umc.databinding.ActivityAuthBinding
import com.example.umc.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var storedVerificationId = ""
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var binding: ActivityAuthBinding

    private val callbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // 번호인증 혹은 기타 다른 인증(구글로그인, 이메일로그인 등) 끝난 상태
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Toast.makeText(baseContext, "인증코드가 전송되었습니다. 90초 이내에 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            //잘못된 전화 번호나 확인 코드를 지정하는 요청과 같은 잘못된 확인 요청에 대한 응답으로 호출
            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
                Toast.makeText(baseContext, "인증실패", Toast.LENGTH_SHORT).show()
            }

            // 전화번호는 확인 되었으나 인증코드를 입력해야 하는 상태
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId // verificationId 와 전화번호인증코드 매칭해서 인증하는데 사용예정
                resendToken = token

                Log.d("TEST", "onCodeSent:$verificationId")
                Log.d("TEST", resendToken.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        binding.button.setOnClickListener {

            /**
             * 사용자의 전화로 인증 코드 보내기
             * 전화번호를 PhoneAuthProvider.verifyPhoneNumber 메서드에 전달하여 Firebase에서 사용자의 전화번호를 확인하도록 요청
             * */
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+821090210816")       // Phone number to verify
                .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            auth.setLanguageCode("kr")

            binding.editText.isVisible = true
            binding.button2.isVisible = true
        }

        binding.button2.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, binding.editText.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("AUTHTEST", "signInWithCredential:success")
                    val user = task.result?.user
                    if (user != null) {
                        Log.d("AUTHTEST", user.phoneNumber.toString())
                        Log.d("AUTHTEST", user.email.toString())
                        Log.d("AUTHTEST", user.displayName.toString())
                        Log.d("AUTHTEST", user.uid.toString())
                        Log.d("AUTHTEST", user.tenantId.toString())
                        Log.d("AUTHTEST", user.metadata.toString())
                    }

                    Toast.makeText(this, "비회원 로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()

                    binding.editText.isVisible = false
                    binding.button2.isVisible = false

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("AUTHTEST", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    Toast.makeText(this, "인증번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show()
                    // Update UI
                }
            }
    }
}