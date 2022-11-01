package com.moilsurok.kmaster.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.dataClass.SearchUserDataClass


class SearchAdapter (
//    @JvmStatic
//    @NoteAdapter("app:imageUrl")
    val iduser: String,
    val context: Context,
    val userList: ArrayList<SearchUserDataClass>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val storage = Firebase.storage

    private val deNote: ArrayList<SearchUserDataClass> = arrayListOf()
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    val storageRef = storage.reference
    // Create a reference with an initial file path and name


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val user = userList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as SearchAdapter.ViewHolder).itemView
        val user: SearchUserDataClass = userList[position]
//        val gsReference = storage.getReferenceFromUrl("gs://moilsurok-f60eb.appspot.com")
        val storageReference = Firebase.storage.reference
//        pathReference.downloadUrl.addOnSuccessListener {
//            Glide.with(holder.itemView.context)
//                .load(pathReference)
//                .into(holder.profileImage)
//        }.addOnFailureListener {
//            // Handle any errors
//        }


        holder.name.text = user.name
        holder.phoneNum.text = user.phoneNum
        holder.year.text = user.year.toString() + "기"



        holder.itemView.setOnClickListener {


            val dialog = BottomSheetDialog(holder.itemView.context)
            val view = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.bottom_sheet, null)
            dialog.setContentView(view)
            val deName = view.findViewById<TextView>(R.id.deName)
            val deYear = view.findViewById<TextView>(R.id.deYear)
            val dePhoneNum = view.findViewById<TextView>(R.id.dePhoneNum)
            val deEmail = view.findViewById<TextView>(R.id.deEmail)
            val deCompany = view.findViewById<TextView>(R.id.deCompany)
            val deComNum = view.findViewById<TextView>(R.id.deComNum)
            val deComAdr = view.findViewById<TextView>(R.id.deComAdr)
            val request = view.findViewById<TextView>(R.id.request)
            val img1 = view.findViewById<ImageButton>(R.id.img1)
            val img2 = view.findViewById<ImageButton>(R.id.img2)
            deName.text = user.name
            deYear.text = user.year.toString() + "기"
            if (user.phoneNum == null){
                dePhoneNum.text  = "미기입"
            }else{
                dePhoneNum.text = user.phoneNum
            }
            if (user.email == null){
                deEmail.text = "미기입"
            }else{
                deEmail.text = user.email
            }
            if (user.company == null){
                deCompany.text = "미기입"
            }else{
                deCompany.text = user.company
            }
            if (user.comTel == null){
                deComNum.text = "미기입"
            }else{
                deComNum.text = user.comTel
            }
            if (user.comAdr == null){
                deComAdr.text = "미기입"
            }else{
                deComAdr.text = user.comAdr
            }
            request.setOnClickListener {
                val input = dePhoneNum.text.toString()
                val myUri = Uri.parse("tel:${input}")
                val myIntent = Intent(Intent.ACTION_DIAL, myUri)
                holder.itemView.context.startActivity(myIntent)
            }

            if (user.bookmark!!.contains(iduser)) {
                img2.visibility = View.VISIBLE
            } else {
                img2.visibility = View.GONE
            }
            img1.setOnClickListener {

                db.collection("User").document(user.uid.toString())
                    .update("bookmark", FieldValue.arrayUnion(iduser))
                img2.visibility = View.VISIBLE
                notifyDataSetChanged()

            }
            img2.setOnClickListener {

                db.collection("User").document(user.uid.toString())
                    .update("bookmark", FieldValue.arrayRemove(iduser))
                img2.visibility = View.GONE
                notifyDataSetChanged()

            }
            println(deYear.toString())
            dialog.show()

        }




    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.noteName)
        val phoneNum: TextView = itemView.findViewById(R.id.phoneNum)
        val year: TextView = itemView.findViewById(R.id.noteYear)



    }



}