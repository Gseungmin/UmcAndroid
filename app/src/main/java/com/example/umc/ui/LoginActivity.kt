package com.example.umc.ui

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.umc.databinding.ActivityLoginBinding
import com.example.umc.repository.LoginRepository
import com.example.umc.viewmodel.LoginViewModel
import com.example.umc.viewmodel.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    private lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //사용자의 이메일 주소도 요청하려면 requestEmail 옵션 추가
        //ID 및 기본 프로파일은 DEFAULT_SIGN_IN에 포함됩니다.
        //추가로 요청해야하는 정보는 requestScopes를 지정하여 요청함. 꼭 필요한 것들만 요청하도록 한다.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        //GoogleSignInClient 객체를 생성
        val mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        //사용자가 로그인하면 활동의 registerForActivityResult를 통해 사용자의 GoogleSignInAccount 객체를 가져올 수 있음
        GoogleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        val loginRepository = LoginRepository()
        val factory = LoginViewModelFactory(loginRepository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        //인텐트를 시작하면 사용자에게 로그인할 Google 계정을 선택하라는 메시지가 표시됨
        //profile, email, openid 이외의 범위를 요청한 경우 사용자에게 요청된 리소스에 대한 액세스 권한을 부여하라는 메시지도 표시됨
        binding.google.setOnClickListener{
            var signIntent: Intent = mGoogleSignInClient.getSignInIntent()
            GoogleSignResultLauncher.launch(signIntent)
        }

        binding.kakao.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.naver.setOnClickListener{
            startActivity(Intent(this, MapActivity::class.java))
            finish()
        }
    }

    //로그인 상태 확인
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            Log.d("INFO", "로그인 안되있음")
        } else {
            Log.d("INFO", "로그인 완료된 상태")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    //GoogleSignInAccount 객체에는 사용자의 이름과 같이 로그인한 사용자에 대한 정보가 포함됨
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            var googletoken = account?.idToken.toString()
            var googletokenAuth = account?.serverAuthCode.toString()

            Log.d("INFO",email)
            Log.d("INFO",googletoken)
            Log.d("INFO", googletokenAuth)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } catch (e: ApiException){
            Log.d("INFO","signInResult:failed Code = " + e.statusCode)
        }
    }
}