package com.moilsurok.kmaster.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.dataClass.GalleryDataClass
import com.moilsurok.kmaster.databinding.ActivityAlbumBinding

class AlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumBinding
    lateinit var albumRecyclerView: RecyclerView
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        binding.backKey.setOnClickListener {
            finish()
        }
        albumRecyclerView = binding.albumRecyclerView
        var galleryList = arrayListOf<GalleryDataClass>()
        db.collection("Gallery").limit(6)
            .get().addOnSuccessListener { result ->

                if (result.isEmpty) {

                } else {
                    for (document in result) {

                        val item = document.toObject(GalleryDataClass::class.java)

                        galleryList.add(item)
                    }


                    val galleryAdapter =
                        GalleryAdapter(this, galleryList)
                    albumRecyclerView.adapter = galleryAdapter
                    val gridLayoutManager = GridLayoutManager(this, 2)
                    albumRecyclerView.layoutManager =
                        gridLayoutManager


                }
            }
    }

    inner class GalleryAdapter(
        val context: Context,
        var galleryList: ArrayList<GalleryDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
            val olcYear = galleryList[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as GalleryAdapter.ViewHolder).itemView
            val gallery = galleryList[position]

            holder.title.text = gallery.title
            Glide.with(holder.itemView)
                .load(gallery.files!![0].toString())
                .into(holder.firstImg)
            Log.d("test1234", gallery.files[0].toString())

            holder.itemView.setOnClickListener {

                val intent =
                    Intent(holder.itemView.context, AlbumDetailActivity::class.java)
                intent.putExtra("content", gallery.content)
                intent.putExtra("title", gallery.title)
                intent.putExtra("creator", gallery.creator)
                intent.putExtra("modifiedDate", gallery.modifiedDate)
                intent.putExtra("pubDate", gallery.pubDate)
                ContextCompat.startActivity(holder.itemView.context, intent, null)

            }
        }

        override fun getItemCount(): Int {
            return galleryList.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val firstImg: ImageView = itemView.findViewById(R.id.firstImg)

            val title: TextView = itemView.findViewById(R.id.title)
        }
    }


}