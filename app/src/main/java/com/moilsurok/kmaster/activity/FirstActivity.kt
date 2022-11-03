package com.moilsurok.kmaster.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private var mBinding: ActivityFirstBinding? = null

    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        mBinding = ActivityFirstBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val handler = Handler()
        FirebaseMessaging.getInstance().subscribeToTopic("1");
        handler.postDelayed({
            // SharedPreferences 안에 값이 저장되어 있지 않을 때 -> Login
            if (MySharedPreferences.getUserPass(this).isNullOrBlank()) {
                val intent = Intent(this, AuthorityActivity::class.java)
                startActivity(intent)
                finish()
            } else { // SharedPreferences 안에 값이 저장되어 있을 때 -> SimplePassword2Activity로 이동
                val intent = Intent(this, SimplePassword2Activity::class.java)
                startActivity(intent)

                finish()
            }
        }, 2000)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroy()
    }
}