package com.moilsurok.kmaster.adapter


import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.activity.NoteProfileDetailActivity
import com.moilsurok.kmaster.dataClass.UserDataClass
import com.moilsurok.kmaster.dataClass.UserYearDataClass
class NoteYearAdapter(
    val iduser: String,
    val context: Context,
    val UserList: ArrayList<UserYearDataClass>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    private val deNote: ArrayList<UserDataClass> = arrayListOf()
    private var userList = mutableListOf<User>()
    fun setListData(data: MutableList<User>) {
        userList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val user = UserList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as NoteYearAdapter.ViewHolder).itemView
        val user: UserYearDataClass = UserList[position]


        holder.name.text = user.name
        holder.year.text = user.year + "년"
        holder.num.text = "제" + user.num + "호"
        holder.sector.text = user.occupation
        if (user.phoneNum == null) {
            holder.phoneNum.text = "미기입"
        } else {
            holder.phoneNum.text = user.phoneNum.toString().replace("[","").replace("]","")
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
                .load("https://firebasestorage.googleapis.com/v0/b/korea-master-firebase.appspot.com/o/files%2Fuser%2F39b59978-ced5-4dd4-8e89-a7460f7bc2a7_%EB%AA%85%EC%9E%A5%ED%9A%8C%20%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=3f5fee96-bcb9-4dd6-a88a-9b7ce06f99bd")
                .into(holder.noteImage)
        } else {
            Glide.with(holder.itemView)
                .load(user.files.toString())
                .into(holder.noteImage)
        }
//        holder.noteCheck.isChecked = user.bookmark!!.contains(iduser)
//
//        holder.noteCheck.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//            } else {
//                db.collection("teams")
//                    .document("PA9LiTPH9WiBzSbizAPg")
//                    .collection("User").document(user.uid.toString())
//                    .update("bookmark", FieldValue.arrayRemove(iduser))
//
//            }
//        }



        holder.itemView.setOnClickListener {

            val intent =
                Intent(holder.itemView.context, NoteProfileDetailActivity::class.java)
            intent.putExtra("content", "원하는 데이터를 보냅니다.")
            intent.putExtra("year", holder.year.text)
            intent.putExtra("num", holder.num.text)
            intent.putExtra("phoneNum", holder.phoneNum.text)
            intent.putExtra("name", user.name)
            intent.putExtra("files", user.files)
            intent.putExtra("email", user.email?.replace("/".toRegex(),""))
            intent.putExtra("company", user.company?.replace("/".toRegex(),""))
            intent.putExtra("iduser", iduser)
            intent.putExtra("uid", user.uid)
            intent.putExtra("field", user.field)
            intent.putExtra("occupation", user.occupation)
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

    // 파이어스토어에서 데이터를 불러와서 검색어가 있는지 판단
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
                notifyDataSetChanged()
            }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.noteName)
        val phoneNum: TextView = itemView.findViewById(R.id.phoneNum)
        val year: TextView = itemView.findViewById(R.id.noteYear)
        val num: TextView = itemView.findViewById(R.id.noteNum)
        val sector: TextView = itemView.findViewById(R.id.sector)
        val mailAdress: TextView = itemView.findViewById(R.id.mailAdress)
        val noteImage: ImageView = itemView.findViewById(R.id.noteImage)


    }
}