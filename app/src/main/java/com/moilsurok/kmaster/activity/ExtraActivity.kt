package com.moilsurok.kmaster.activity


import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.dataClass.InquiryDataClass
import com.moilsurok.kmaster.databinding.ActivityExtraBinding

class ExtraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExtraBinding

    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityExtraBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val id = intent.getStringExtra("id")


        binding.changeProfile.setOnClickListener {

            getProfileReq()

        }

        binding.goInquiry.setOnClickListener {
            val intent = Intent(this, InquiryActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("id", id)
            startActivity(intent)


        }


//        db.collection("User")
//            .get().addOnSuccessListener { result ->
//                for (document in result) {
//                    if (document.getString("year") == "교수진" || document.getString("year") == "OLC 사무국"){
//
//                    }else{
//                        db.collection("User").document(document.id).update("yearCheck", "O")
//                    }
//                }
//
//            }

//        val theUid = MySharedPreferences.getUid(this)
//        db
//            .collection("User").whereEqualTo("uid", theUid).limit(1)
//            .addSnapshotListener { result, _ ->
//
//
//                for (document in result!!) {
//                    if (document.getString("phoneNumCheck")=="O"){
//                        binding.phoneHideState.text = "숨김"
//                    }else{
//                        binding.phoneHideState.text = "허용"
//                    }
//                    if (document.getString("emailCheck")=="O"){
//                        binding.emailHideState.text = "숨김"
//                    }else{
//                        binding.emailHideState.text = "허용"
//                    }
//
//                }
//
//
//            }

//        binding.goPhoneCheck.setOnClickListener {
//            if (binding.phoneHideState.text == "숨김"){
//                val intent = Intent(this, PhoneCheckNonActivity::class.java)
//
//                startActivity(intent)
//            }else if (binding.phoneHideState.text == "허용"){
//                val intent = Intent(this, PhoneCheckActivity::class.java)
//
//                startActivity(intent)
//            }
//        }
//        binding.goEmailCheck.setOnClickListener {
//            if (binding.emailHideState.text == "숨김"){
//                val intent = Intent(this, EmailCheckNonActivity::class.java)
//                startActivity(intent)
//
//            }else if (binding.emailHideState.text == "허용"){
//                val intent = Intent(this, EmailCheckActivity::class.java)
//                startActivity(intent)
//            }
//        }


        binding.goAlarmSetting.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {

                }
            }
        }
        binding.authenticationManage.setOnClickListener {
            val intent = Intent(this, PasswordChangeActivity::class.java)
            startActivity(intent)
        }

        binding.backKey.setOnClickListener {
            finish()
        }

//        binding.exYear.text = year
        binding.exName.text = name
    }

    private fun getProfileReq() {

        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val id = intent.getStringExtra("id")
        val field = intent.getStringExtra("field")
        val occupation = intent.getStringExtra("occupation")
        val num = intent.getStringExtra("num")


        val intent = Intent(this, NoteProfileChangeActivity::class.java)
        intent.putExtra("company", company)
        intent.putExtra("name", name)
        intent.putExtra("year", year)
        intent.putExtra("phoneNum", phoneNum)
        intent.putExtra("email", email)
        intent.putExtra("id", id)
        intent.putExtra("num", num)
        intent.putExtra("occupation", occupation)
        intent.putExtra("field", field)
        startActivity(intent)


    }

    private fun getQuestionReq() {
        var answer: String
        var notice: String
        var profile: String
        var question: String
        var reqProfile: String
        var reqQuestion: String
        var reqUser: String
        var schedule: String
        var user: String
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")


        val getReq = db
            .collection("Counter")
        getReq
            .get()
            //IF문 사용해서 빈값을 받아왔을 때 실패 메시지 document를 받아왔을 때 액티비티 이동
            .addOnSuccessListener { documents ->


                for (document in documents) {


                    answer = document.data["answer"].toString()
                    notice = document.data["notice"].toString()
                    profile = document.data["profile"].toString()
                    question = document.data["question"].toString()
                    reqProfile = document.data["reqProfile"].toString()
                    reqQuestion = document.data["reqQuestion"].toString()
                    reqUser = document.data["reqUser"].toString()
                    schedule = document.data["schedule"].toString()
                    user = document.data["user"].toString()

                    val intent = Intent(this, InquiryActivity::class.java)
                    intent.putExtra("answer", answer)
                    intent.putExtra("name", name)
                    intent.putExtra("notice", notice)
                    intent.putExtra("profile", profile)
                    intent.putExtra("question", question)
                    intent.putExtra("reqProfile", reqProfile)
                    intent.putExtra("reqQuestion", reqQuestion)
                    intent.putExtra("reqUser", reqUser)
                    intent.putExtra("schedule", schedule)
                    intent.putExtra("user", user)
                    intent.putExtra("id", id)

                    startActivity(intent)
                }
            }
            //경로가 실패했을 때
            .addOnFailureListener {
            }
    }

}