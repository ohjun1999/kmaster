package com.moilsurok.kmaster.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.databinding.ActivityEmailCheckBinding
import com.moilsurok.kmaster.databinding.ActivityPhoneCheckBinding

class EmailCheckActivity: AppCompatActivity() {

    private lateinit var binding: ActivityEmailCheckBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityEmailCheckBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.backKey.setOnClickListener {
            finish()
        }
        val theUid = MySharedPreferences.getUid(this)

        binding.hideBtn.setOnClickListener {
            db
                .collection("User").document(theUid).update("emailCheck", "O")
            finish()
            Log.d("test4", theUid)
        }











    }
}