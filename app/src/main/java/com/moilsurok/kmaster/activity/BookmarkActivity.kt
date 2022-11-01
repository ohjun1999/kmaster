package com.moilsurok.kmaster.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.databinding.ActivityBookmarkBinding
import com.moilsurok.kmaster.fragment.BookmarkFragment

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        binding.backKey.setOnClickListener {
            finish()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.noteFrame, BookmarkFragment())
            .commit()

    }
}