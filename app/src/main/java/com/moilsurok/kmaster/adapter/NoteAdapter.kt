package com.moilsurok.kmaster.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.activity.NoteProfileDetailActivity
import com.moilsurok.kmaster.dataClass.UserDataClass

class NoteAdapter(
//    @JvmStatic
//    @NoteAdapter("app:imageUrl")
    val iduser: String,
    val context: Context,
    val UserList: ArrayList<UserDataClass>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val storage = Firebase.storage
    private val checkboxStatus = SparseBooleanArray()
    private val deNote: ArrayList<UserDataClass> = arrayListOf()
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    val storageRef = storage.reference
    // Create a reference with an initial file path and name


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val user = UserList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as NoteAdapter.ViewHolder).itemView
        val user: UserDataClass = UserList[position]
//        val checkMark: checkboxData = checkboxList[position]


        holder.name.text = user.name
        holder.year.text = user.year

        if (user.phoneNum == null) {
            holder.phoneNum.text = "미기입"
        } else {
            holder.phoneNum.text = user.phoneNum
        }
        if (user.email == null) {
            holder.mailAdress.text = "미기입"
        } else {
            holder.mailAdress.text = user.email

        }
//        if (user.company == null) {
//            holder.companyName.text = "미기입"
//        } else {
//            holder.companyName.text = user.company
//        }
        if (user.files == null) {
            Glide.with(holder.itemView)
                .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                .into(holder.noteImage)
        } else {
            Glide.with(holder.itemView)
                .load(user.files.toString())
                .into(holder.noteImage)
        }



        holder.itemView.setOnClickListener {

            val intent =
                Intent(holder.itemView.context, NoteProfileDetailActivity::class.java)

            intent.putExtra("content", "원하는 데이터를 보냅니다.")
            intent.putExtra("year", user.year.toString())
            intent.putExtra("name", user.name)
            intent.putExtra("files", user.files)
//            intent.putExtra("filenames", user.filenames)
            intent.putExtra("birthdate", user.birthdate)
            intent.putExtra("phoneNum", user.phoneNum?.replace("/".toRegex(),""))
            intent.putExtra("email", user.email?.replace("/".toRegex(),""))
            intent.putExtra("company", user.company?.replace("/".toRegex(),""))
            intent.putExtra("department", user.department?.replace("/".toRegex(),""))
            intent.putExtra("comPosition", user.comPosition?.replace("/".toRegex(),""))
            intent.putExtra("comTel", user.comTel?.replace("/".toRegex(),""))
            intent.putExtra("comAdr", user.comAdr?.replace("/".toRegex(),""))
            intent.putExtra("faxNum", user.faxNum?.replace("/".toRegex(),""))
            intent.putExtra("sector", user.sector)
            intent.putExtra("uid", user.uid)
            intent.putExtra("iduser", iduser)
            intent.putExtra("sector", user.sector)
            if (user.bookmark!!.contains(iduser)) {
                intent.putExtra("check", "O")
            } else {
                intent.putExtra("check", "X")
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return UserList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.noteName)
        val phoneNum: TextView = itemView.findViewById(R.id.phoneNum)
        val year: TextView = itemView.findViewById(R.id.noteYear)
        val mailAdress: TextView = itemView.findViewById(R.id.mailAdress)
//        val companyName: TextView = itemView.findViewById(R.id.companyName)

//        val img1: ImageButton = itemView.findViewById(R.id.img1)
//        val img2: ImageButton = itemView.findViewById(R.id.img2)

        //        val noteCheck: CheckBox = itemView.findViewById(R.id.noteCheck)
        val noteImage: ImageView = itemView.findViewById(R.id.noteImage)


    }

    fun search(searchWord: String, option: String) {
        firestore
            ?.collection("User")
            ?.addSnapshotListener { querySnapshot, _ ->
                // ArrayList 비워줌
                deNote.clear()

                for (snapshot in querySnapshot!!.documents) {
                    if (snapshot.getString(option)!!.contains(searchWord)) {
                        var item = snapshot.toObject(UserDataClass::class.java)
                        deNote.add(item!!)
                    }
                }
                Log.d("test", "이거 안되=ㅏ ")
                notifyDataSetChanged()
            }
    }
}