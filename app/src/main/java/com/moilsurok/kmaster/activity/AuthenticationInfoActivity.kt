package com.moilsurok.kmaster.activity

import com.moilsurok.kmaster.databinding.ActivityAuthenticationInfoBinding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import java.util.concurrent.TimeUnit

class AuthenticationInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationInfoBinding
    val auth = Firebase.auth
    var verificationId = ""
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAuthenticationInfoBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.authAll.setOnClickListener {
            hideKeyboard()
        }


        binding.requestAuthNum.setOnClickListener {
            hideKeyboard()
            if (binding.inputPhoneNum.text.trim().isEmpty()

            ) {
                Toast.makeText(this, "전화번호를 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else if (binding.inputPhoneNum.length() < 13
            ) {
                Toast.makeText(this, "전화번호를 모두 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                binding.linearAuthNum.visibility = View.VISIBLE
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
                    override fun onVerificationFailed(e: FirebaseException) {
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@AuthenticationInfoActivity.verificationId = verificationId
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(
                        phoneNumber82(
                            binding.inputPhoneNum.text.toString().replace("[^0-9]".toRegex(), "")

                        )
                    )
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
                auth.setLanguageCode("kr")
                binding.reRequestAuthNum.visibility = View.VISIBLE

            }


        }
        binding.requestAuthNum.setOnClickListener {
            hideKeyboard()
            if (binding.inputPhoneNum.text.trim().isEmpty()

            ) {
                Toast.makeText(this, "전화번호를 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else if (binding.inputPhoneNum.length() < 12
            ) {
                Toast.makeText(this, "전화번호를 모두 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                binding.linearAuthNum.visibility = View.VISIBLE
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
                    override fun onVerificationFailed(e: FirebaseException) {
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@AuthenticationInfoActivity.verificationId = verificationId
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(
                        phoneNumber82(
                            binding.inputPhoneNum.text.toString().replace("[^0-9]".toRegex(), "")

                        )
                    )
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
                auth.setLanguageCode("kr")
                binding.reRequestAuthNum.visibility = View.VISIBLE

            }


        }

        binding.confirmAuthNum.setOnClickListener {
            if (binding.inputAuthNum.text.trim().isEmpty()

            ) {
                Toast.makeText(this, "인증번호를 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else if (binding.inputAuthNum.length() < 6) {
                Toast.makeText(this, "인증번호를 모두 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                val credential = PhoneAuthProvider.getCredential(
                    verificationId,
                    binding.inputAuthNum.text.toString()
                )
                signInWithPhoneAuthCredential(credential)
            }
            hideKeyboard()
        }

        binding.goNext.setOnClickListener {

            goPhoneNum()

        }
        binding.inputPhoneNum.addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))


    }

    private fun phoneNumber82(msg: String): String {
        val firstNumber: String = msg.substring(0, 3)
        var phoneEdit = msg.substring(3)

        when (firstNumber) {
            "010" -> phoneEdit = "+8210$phoneEdit"
            "011" -> phoneEdit = "+8211$phoneEdit"
            "016" -> phoneEdit = "+8216$phoneEdit"
            "017" -> phoneEdit = "+8217$phoneEdit"
            "018" -> phoneEdit = "+8218$phoneEdit"
            "019" -> phoneEdit = "+8219$phoneEdit"
            "106" -> phoneEdit = "+82106$phoneEdit"
        }
        return phoneEdit
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //인증성공
                    binding.goNext.isEnabled = true
                    Toast.makeText(this, "인증에 성공했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //인증실패
                    Toast.makeText(this, "인증에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun goPhoneNum() {
        var name: String
        var company: String
        var year: String
        var birthdate: String
        var phoneNum : ArrayList<String>
        var email: String
        var department: String
        var comPosition: String
        var comTel: String
        var comAdr: String
        var faxNum: String
        var id: String
        var userCheck: String
        var bookMark: String
        val logPhoneNum = binding.inputPhoneNum.text.toString()
        val login = db
            .collection("User").whereArrayContains("phoneNum", logPhoneNum)
        login
            .get()
            //IF문 사용해서 빈값을 받아왔을 때 실패 메시지 document를 받아왔을 때 액티비티 이동
            .addOnSuccessListener { documents ->
                if (documents!!.isEmpty) {
                    Toast.makeText(this, "등록되지 않은 번호입니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, NonNumActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    for (document in documents) {
                        id = document.id
                        name = document.getString("name").toString()
                        company = document.getString("company").toString()
                        year = document.get("year").toString()
                        birthdate = document.getString("birthdate").toString()
                        phoneNum = document.get("phoneNum") as ArrayList<String>
                        email = document.getString("email").toString()
                        department = document.getString("department").toString()
                        comPosition = document.getString("comPosition").toString()
                        comTel = document.getString("comTel").toString()
                        comAdr = document.getString("comAdr").toString()
                        faxNum = document.getString("faxNum").toString()
                        bookMark = document.get("bookmark").toString()
                        userCheck = document.getString("userCheck").toString()
                        if (userCheck == "O") {


                        } else {
                            db.collection("teams").document("PA9LiTPH9WiBzSbizAPg")
                                .collection("Counter").document("counter")
                                .update("auth", FieldValue.increment(1))
                            db.collection("teams").document("PA9LiTPH9WiBzSbizAPg")
                                .collection("User").document(id).update("userCheck", "O")
                        }
                        MySharedPreferences.setUserId(this, logPhoneNum.toString())
                        val intent = Intent(this, AuthConfirmInfoActivity::class.java)
                        intent.putExtra("content", "원하는 데이터를 보냅니다.")
                        intent.putExtra("company", company)
                        intent.putExtra("name", name)
                        intent.putExtra("year", year)
                        intent.putExtra("birthdate", birthdate)
                        intent.putExtra("phoneNum", phoneNum.toString())
                        intent.putExtra("email", email)
                        intent.putExtra("department", department)
                        intent.putExtra("comPosition", comPosition)
                        intent.putExtra("comTel", comTel)
                        intent.putExtra("comAdr", comAdr)
                        intent.putExtra("faxNum", faxNum)
                        intent.putExtra("id", id)
                        intent.putExtra("bookMark", bookMark)
                        startActivity(intent)

                        finish()

                    }

                }
            }
            //경로가 실패했을 때
            .addOnFailureListener { exception ->
                Toast.makeText(this, "등록되지 않은 번호입니다.", Toast.LENGTH_SHORT).show()
            }

    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.authAll.windowToken, 0)
    }
}