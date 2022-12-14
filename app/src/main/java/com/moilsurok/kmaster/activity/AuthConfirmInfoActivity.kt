package com.moilsurok.kmaster.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.moilsurok.kmaster.databinding.ActivityAuthConfirmInfoBinding

class AuthConfirmInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthConfirmInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAuthConfirmInfoBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val phoneNum = intent.getStringExtra("phoneNum")!!.replace("[","").replace("]","")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val field = intent.getStringExtra("field")
        val occupation = intent.getStringExtra("occupation")
        val num = intent.getStringExtra("num")
        val files = intent.getStringExtra("files")
        val id = intent.getStringExtra("id")

        binding.goPass.setOnClickListener {
            val intent = Intent(this, SimplePasswordActivity::class.java)
            intent.putExtra("content", "원하는 데이터를 보냅니다.")
            intent.putExtra("num", num)
            intent.putExtra("name", name)
            intent.putExtra("year", year)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("email", email)
            intent.putExtra("occupation", occupation)
            intent.putExtra("field", field)
            intent.putExtra("company", company)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        if (files == "null") {
            Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/korea-master-firebase.appspot.com/o/files%2Fuser%2F39b59978-ced5-4dd4-8e89-a7460f7bc2a7_%EB%AA%85%EC%9E%A5%ED%9A%8C%20%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=3f5fee96-bcb9-4dd6-a88a-9b7ce06f99bd")
                .into(binding.conImage)
        } else {
            Glide.with(this)
                .load(files.toString())
                .into(binding.conImage)

        }
        binding.conNum.text = year + "년" + " 제" + num + "호"
        binding.conSector.text = occupation
        binding.conField.text = field
        binding.conName.text = name + "님의 정보를 확인해주세요"
        binding.conPhoneNum.text = phoneNum
        binding.conEmail.text = email
        binding.conCompany.text = company
    }


}