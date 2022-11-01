package com.moilsurok.kmaster.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.databinding.ActivityAuthCheckBinding


class AuthCheckActivity : AppCompatActivity() {
    private var mBinding: ActivityAuthCheckBinding? = null
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    private val iDuration: Long = 1500

    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        mBinding = ActivityAuthCheckBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val handler = Handler()
//        db.collection("teams").document("PA9LiTPH9WiBzSbizAPg")
//            .collection("Counter").document("counter")
//            .update("auth", FieldValue.increment(1))
        db.collection("Counter").document("counter")
            .get().addOnSuccessListener { document ->
                binding.countAuthText.text = "총 " + document.get("auth").toString() + "명 의 OLC 동창들이 함께하고 있습니다!"
                binding.countAuth.text = document.get("auth").toString()
            }
        val animator: ValueAnimator = ObjectAnimator.ofFloat(binding.mainAuth, View.ALPHA, 0f, 1.0f)
        animator.duration = iDuration
        animator.start()


        handler.postDelayed({
            val intent = Intent(this, SimplePassword2Activity::class.java)
            startActivity(intent)

            finish()
        }, 2500)

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