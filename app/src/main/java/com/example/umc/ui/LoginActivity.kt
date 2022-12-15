package com.example.umc.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coinstudy.dataStore.MyDataStore
import com.example.umc.Constants.GClientId
import com.example.umc.Constants.KAKAO_KEY
import com.example.umc.Constants.idToken
import com.example.umc.databinding.ActivityLoginBinding
import com.example.umc.viewmodel.TokenViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>
    lateinit var viewModel: TokenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(TokenViewModel::class.java)

        binding.auth.setOnClickListener {
//            Log.d("TEST", viewModel.accessToken.value.toString())
//            viewModel.test(viewModel.accessToken.value.toString())
//
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }

        binding.naver.setOnClickListener {
        }

        //사용자의 이메일 주소도 요청하려면 requestEmail 옵션 추가
        //ID 및 기본 프로파일은 DEFAULT_SIGN_IN에 포함됩니다.
        //추가로 요청해야하는 정보는 requestScopes를 지정하여 요청함. 꼭 필요한 것들만 요청하도록 한다.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestIdToken(GClientId)
            .requestServerAuthCode(GClientId)
            .requestProfile()
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

        //인텐트를 시작하면 사용자에게 로그인할 Google 계정을 선택하라는 메시지가 표시됨
        //profile, email, openid 이외의 범위를 요청한 경우 사용자에게 요청된 리소스에 대한 액세스 권한을 부여하라는 메시지도 표시됨
        binding.google.setOnClickListener{
            var signIntent: Intent = mGoogleSignInClient.getSignInIntent()
            GoogleSignResultLauncher.launch(signIntent)
        }

        var keyHash = Utility.getKeyHash(this)

        //Kakao SDK를 사용하기 위해선 Native App Key로 초기화
        KakaoSdk.init(this, KAKAO_KEY)

        UserApiClient.instance.me { user, error ->
            if (user != null) {
                Log.d("KAKAOUSER", user.toString())
                Log.d("KAKAOPROPERTY", user.properties.toString())
                Log.d("KAKAOUSERTOKEN", user.groupUserToken.toString())
                Log.d("KAKAOID", user.id.toString())
                Log.d("KAKAOCONNECTAT", user.connectedAt.toString())
                Log.d("KAKAOKAKAOACCOUNT", user.kakaoAccount.toString())
                Log.d("KAKAOSIGNEDUP", user.hasSignedUp.toString())

//                //kakao 로그인
//                viewModel.kakao()

//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                finish()
            }
        }

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
            }
            else if (tokenInfo != null) {

                Log.d("KAKAOCLIENTAPI", UserApiClient.instance.toString())
                Log.d("KAKAOTOKEN", tokenInfo.toString())
                Log.d("KAKAOTOKENID", tokenInfo.id.toString())
                Log.d("KAKAOTOKENAPPID", tokenInfo.appId.toString())
                Log.d("KAKAOEXPIRES", tokenInfo.expiresIn.toString())
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {

                Log.d("KAKAO_IDTOKEN", token.idToken.toString())
                Log.d("KAKAO_REFRESH", token.refreshToken.toString())
                Log.d("KAKAO_ACCESS", token.accessToken.toString())
                Log.d("KAKAO_SCOPE", token.scopes.toString())

                viewModel.login(token.accessToken.toString(), "kakao")

                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                finish()
            }
        }

        val serviceTerms = listOf("account_email", "phone_number", "birthday", "openid")

        //로그인 버튼 입력
        binding.kakao.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(context = this, serviceTerms = serviceTerms, callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(context = this, serviceTerms = serviceTerms, callback = callback)
            }
        }
    }

    //로그인 상태 확인
    override fun onStart() {
        super.onStart()

        viewModel.checkAccessToken()

        viewModel.accessToken.observe(this, Observer {
            if (it != "") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account == null) {
            Log.d("INFO", "로그인 안되있음")
        } else {
            Log.d("INFO", "로그인 완료된 상태")
            Log.d("INFOGOOGLEIDTOKEN", account.idToken.toString())
            Log.d("INFOGOOGLEIDTOKEN", account.grantedScopes.toString())
            Log.d("INFOGOOGLEIDTOKEN", account.requestedScopes.toString())

//            viewModel.sendToken(account.idToken.toString())

//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }
    }

    //GoogleSignInAccount 객체에는 사용자의 이름과 같이 로그인한 사용자에 대한 정보가 포함됨
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            var googletoken = account?.idToken.toString()
            var googletokenAuth = account?.serverAuthCode

            viewModel.login(googletoken, "google")


//            if (googletokenAuth != null) {
//                val accessToken = viewModel.getAccessToken(googletokenAuth)
//                Log.d("ACCESSToken", accessToken.toString())
//                viewModel.googleLogin(googletokenAuth)
//            }

//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        } catch (e: ApiException){
            Log.d("INFO","signInResult:failed Code = " + e.statusCode)
        }
    }
}