package com.moilsurok.kmaster.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.dataClass.NoticeDataClass
import com.moilsurok.kmaster.databinding.ActivityAlbumDetailBinding
import com.moilsurok.kmaster.databinding.ActivityNoticeDetailBinding

class AlbumDetailActivity : AppCompatActivity() {
    lateinit var albumImg: RecyclerView
    private lateinit var binding: ActivityAlbumDetailBinding
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        binding.backKey.setOnClickListener {
            finish()
        }
        var deNotice: ArrayList<NoticeDataClass> = arrayListOf()
        albumImg = binding.albumImg
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val pubDate = intent.getStringExtra("pubDate")
        val modifiedDate = intent.getStringArrayListExtra("modifiedDate")
        val creator = intent.getStringExtra("creator")
        var albumImgList = arrayListOf<String>()
        db.collection("Gallery").whereEqualTo("content", content).limit(1)
            .get().addOnSuccessListener { result ->

                for (document in result) {
                    if (document.get("files") == null) {

                    } else {
                        albumImgList = document.get("files") as ArrayList<String>
                    }


                }
                val realNoticeAdapter = RealNoticeAdapter(this, albumImgList)
                albumImg.adapter = realNoticeAdapter
                albumImg.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                Log.d("test12", albumImgList.toString())
            }

        val theImg = MySharedPreferences.getSearch(this)




        binding.title.text = title
        binding.modifiedDate.text = pubDate.toString()
        binding.albumText.text = content
    }

    inner class RealNoticeAdapter(
        val context: Context,
        var albumImgList: ArrayList<String>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_album_img, parent, false)
            val olcYear = albumImgList[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as RealNoticeAdapter.ViewHolder).itemView
            val notice = albumImgList[position]


            Glide.with(holder.itemView)
                .load(notice)
                .into(holder.albumRealImg)


        }

        override fun getItemCount(): Int {
            return albumImgList.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val albumRealImg: ImageView = itemView.findViewById(R.id.albumRealImg)


        }
    }
}