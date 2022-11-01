package com.moilsurok.kmaster.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.databinding.ActivityExplainGroupBinding
import com.moilsurok.kmaster.fragment.*
import com.moilsurok.kmaster.setOnSingleClickListener

class ExplainGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExplainGroupBinding
//    private lateinit var noteAdapter: NoteAdapter

    var firestore: FirebaseFirestore? = null
    var isCheck = BooleanArray(50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityExplainGroupBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)


        // bottomSheetDialog.setContentView(R.layout.bottom_sheet) 이렇게 사용 가능
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()


        binding.backKey.setOnClickListener {
            finish()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, GroupOneFragment())
            .commit()
        binding.getDrawer.setOnClickListener {
            binding.layout.openDrawer((GravityCompat.END))

        }
        binding.btn1.setOnClickListener {


            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, GroupOneFragment())
                .commit()
            binding.layout.closeDrawer((GravityCompat.END))

        }

        binding.btn2.setOnClickListener {


            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, GroupTwoFragment())
                .commit()
            binding.layout.closeDrawer((GravityCompat.END))

        }
        binding.btn3.setOnClickListener {


            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, GroupThreeFragment())
                .commit()
            binding.layout.closeDrawer((GravityCompat.END))

        }
        binding.btn4.setOnClickListener {


            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, GroupFourFragment())
                .commit()
            binding.layout.closeDrawer((GravityCompat.END))

        }
        binding.btn5.setOnClickListener {


            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, GroupFiveFragment())
                .commit()
            binding.layout.closeDrawer((GravityCompat.END))

        }



    }




    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.frameLayout.windowToken, 0)
    }
}