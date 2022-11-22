package com.moilsurok.kmaster.activity


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.dataClass.AnswerDataClass
import com.moilsurok.kmaster.dataClass.CounterDataClass
import com.moilsurok.kmaster.dataClass.ProfileDataClass
import com.moilsurok.kmaster.databinding.ActivityNoteProfileChangeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteProfileChangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteProfileChangeBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val formatted = current.format(formatter)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNoteProfileChangeBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        firestore = FirebaseFirestore.getInstance()
        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val id = intent.getStringExtra("id")
        val num = intent.getStringExtra("num")
        val occupation = intent.getStringExtra("occupation")
        val field = intent.getStringExtra("field")

//        Log.d("test", sum.toString())
        Log.d("test12", name.toString())

        binding.all.setOnClickListener {
            hideKeyboard()
        }

        binding.info.setOnClickListener {
            hideKeyboard()
        }
        binding.main.setOnClickListener {
            hideKeyboard()
        }
        binding.backKey.setOnClickListener {
            finish()
        }
        var ProfileList = arrayListOf<ProfileDataClass>()
        db.collection("Profile")
            .whereEqualTo("user", id).limit(1).get()
            //IF문 사용해서 빈값을 받아왔을 때 실패 메시지 document를 받아왔을 때 액티비티 이동
            .addOnSuccessListener { documents ->


                for (document in documents) {


                    var theCheck = document.getString("check")
                    Log.d("test123", "${document.id}->${document.data}")

                    if (theCheck == "X") {
                        binding.request.visibility = View.GONE


                    } else {
                        binding.request.visibility = View.VISIBLE
                    }

                }


            }
        db.collection("Profile")
            .whereEqualTo("user", id).limit(1).get()
            .addOnSuccessListener { documents ->

                if (documents!!.isEmpty) {
                    binding.frName.text = name.toString()
                    binding.frPhoneNum.text = phoneNum.toString()
                    binding.frEmail.setText(email)
                    binding.frCompany.setText(company)
                    if(year == "사무국"){
                        binding.frNum.text = null
                    }else{
                        binding.frNum.text = year + "년 " + "제" + num + "호"
                    }

                    binding.frSector1.text = field
                    binding.frSector2.text = occupation

                } else {
                    for (document in documents) {
//                        binding.frYear.text = year.toString()
                        binding.frName.text = name.toString()
                        binding.frNum.text = document.getString("num")
                        binding.frPhoneNum.text =
                            document.getString("phoneNum").toString().replace("[", "")
                                .replace("]", "")
                        binding.frSector1.text = document.getString("field")
                        binding.frSector2.text = document.getString("occupation")
                        binding.frEmail.setText(document.getString("email"))
                        binding.frCompany.setText(document.getString("company"))

                    }
                }


            }
        binding.request.setOnClickListener {
//            binding.request.visibility = View.GONE
//            binding.cancellationRequest.visibility = View.VISIBLE
            val profileDataClass = ProfileDataClass()
            profileDataClass.check = "X"
            profileDataClass.company = binding.frCompany.text.toString()
            profileDataClass.email = binding.frEmail.text.toString()
            profileDataClass.modifiedDate = formatted.toString()
            profileDataClass.pubDate = formatted.toString()
            profileDataClass.user = id.toString()
            profileDataClass.year = year.toString()
            profileDataClass.name = binding.frName.text.toString()
            profileDataClass.phoneNum = binding.frPhoneNum.text.toString()
            profileDataClass.num = binding.frNum.text.toString()
            profileDataClass.field = binding.frSector1.text.toString()
            profileDataClass.occupation = binding.frSector2.text.toString()
            db
                .collection("Counter").document("counter")
                .update("reqProfile", FieldValue.increment(1))
            db.collection("Profile")
                .add(profileDataClass)
                .addOnSuccessListener { documentReference ->

                    val doId = documentReference.id
                    db.collection("Profile")
                        .document(doId).update("uuid", doId)
                    MySharedPreferences.setUserUid(this, doId)
                }
                .addOnFailureListener { e ->

                }


            Toast.makeText(this, "수정요청이 접수 되었습니다", Toast.LENGTH_SHORT).show()
            finish()

        }

//        binding.cancellationRequest.setOnClickListener {
//            binding.request.visibility = View.VISIBLE
//            binding.cancellationRequest.visibility = View.GONE
//
//
//        }


//        binding.frYear.text = year.toString()
//        binding.frName.text = name.toString()
//        binding.frBirthDate.setText(birthdate)
//        binding.frPhoneNum.setText(phoneNum)
//        binding.frEmail.setText(email)
//        binding.frCompany.setText(company)
//        binding.frDepartment.setText(department)
//        binding.frComPosition.setText(comPosition)
//        binding.frComAdr.setText(comAdr)


    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.all.windowToken, 0)
    }

}