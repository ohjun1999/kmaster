package com.moilsurok.kmaster.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.activity.AlbumDetailActivity
import com.moilsurok.kmaster.activity.ExplainGroupActivity
import com.moilsurok.kmaster.dataClass.ExecutiveDataClass
import com.moilsurok.kmaster.dataClass.GalleryDataClass
import com.moilsurok.kmaster.dataClass.UserYearDataClass
import com.moilsurok.kmaster.databinding.FragmentGroupFourBinding
import kotlin.math.exp

class GroupFourFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentGroupFourBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var recyclerView1: RecyclerView
    lateinit var recyclerView2: RecyclerView
    lateinit var recyclerView3: RecyclerView
    lateinit var recyclerView4: RecyclerView
    lateinit var recyclerView5: RecyclerView
    lateinit var recyclerView6: RecyclerView
    lateinit var recyclerView7: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupFourBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth

        var explainList = arrayListOf<ExecutiveDataClass>()
        var explain2List = arrayListOf<ExecutiveDataClass>()
        var explain3List = arrayListOf<ExecutiveDataClass>()
        var explain4List = arrayListOf<ExecutiveDataClass>()
        var explain5List = arrayListOf<ExecutiveDataClass>()
        var explain6List = arrayListOf<ExecutiveDataClass>()
        var explain7List = arrayListOf<ExecutiveDataClass>()

        recyclerView1 = binding.recyclerView1
        recyclerView2 = binding.recyclerView2
        recyclerView3 = binding.recyclerView3
        recyclerView4 = binding.recyclerView4
        recyclerView5 = binding.recyclerView5
        recyclerView6 = binding.recyclerView6
        recyclerView7 = binding.recyclerView7


        db.collection("Executive").document("01회장단").collection("userList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->


                for (document in result) {

                    val item = document.toObject(ExecutiveDataClass::class.java)

                    explainList.add(item)
                }


                val explainAdapter =
                    OneAdapter(ExplainGroupActivity(), explainList)
                recyclerView1.adapter = explainAdapter
                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
                recyclerView1.layoutManager =
                    gridLayoutManager

            }

        db.collection("Executive").document("02명예회장").collection("userList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->


                for (document in result) {

                    val item = document.toObject(ExecutiveDataClass::class.java)

                    explain2List.add(item)
                }


                val explain2Adapter =
                    TwoAdapter(ExplainGroupActivity(), explain2List)
                recyclerView2.adapter = explain2Adapter
                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
                recyclerView2.layoutManager =
                    gridLayoutManager
            }
        db.collection("Executive").document("03교수진").collection("userList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->


                for (document in result) {

                    val item = document.toObject(ExecutiveDataClass::class.java)

                    explain3List.add(item)
                }


                val explain3Adapter =
                    ThreeAdapter(ExplainGroupActivity(), explain3List)
                recyclerView3.adapter = explain3Adapter
                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
                recyclerView3.layoutManager =
                    gridLayoutManager
            }
        db.collection("Executive").document("04집행부").collection("userList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->


                for (document in result) {

                    val item = document.toObject(ExecutiveDataClass::class.java)

                    explain4List.add(item)
                }


                val explain4Adapter =
                    FourAdapter(ExplainGroupActivity(), explain4List)
                recyclerView4.adapter = explain4Adapter
                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
                recyclerView4.layoutManager =
                    gridLayoutManager
            }
        db.collection("Executive").document("05운영분과").collection("userList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->


                for (document in result) {

                    val item = document.toObject(ExecutiveDataClass::class.java)

                    explain5List.add(item)
                }


                val explain5Adapter =
                    FiveAdapter(ExplainGroupActivity(), explain5List)
                recyclerView5.adapter = explain5Adapter
                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
                recyclerView5.layoutManager =
                    gridLayoutManager
            }
        db.collection("Executive").document("06언론편집분과").collection("userList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->


                for (document in result) {

                    val item = document.toObject(ExecutiveDataClass::class.java)

                    explain6List.add(item)
                }


                val explain6Adapter =
                    SixAdapter(ExplainGroupActivity(), explain6List)
                recyclerView6.adapter = explain6Adapter
                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
                recyclerView6.layoutManager =
                    gridLayoutManager
            }
        db.collection("Executive").document("07동호회").collection("userList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->


                for (document in result) {

                    val item = document.toObject(ExecutiveDataClass::class.java)

                    explain7List.add(item)
                }


                val explain7Adapter =
                    SevenAdapter(ExplainGroupActivity(), explain7List)
                recyclerView7.adapter = explain7Adapter
                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
                recyclerView7.layoutManager =
                    gridLayoutManager
            }










        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    inner class OneAdapter(
        val context: Context,
        var explainList: ArrayList<ExecutiveDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_executive, parent, false)
            val olcYear = explainList[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as OneAdapter.ViewHolder).itemView
            val explain = explainList[position]

            holder.exPosition.text = explain.comPosition
            holder.exName.text = explain.name
            if (explain.files == null) {
                Glide.with(holder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                    .into(holder.exImage)
            } else {
                Glide.with(holder.itemView)
                    .load(explain.files)
                    .into(holder.exImage)

            }


        }

        override fun getItemCount(): Int {
            return explainList.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val exName: TextView = itemView.findViewById(R.id.exName)
            val exImage: ImageView = itemView.findViewById(R.id.exImage)
            val exPosition: TextView = itemView.findViewById(R.id.exPosition)
        }
    }

    inner class TwoAdapter(
        val context: Context,
        var explain2List: ArrayList<ExecutiveDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_executive, parent, false)
            val olcYear = explain2List[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as TwoAdapter.ViewHolder).itemView
            val explain = explain2List[position]

            holder.exPosition.text = explain.comPosition
            holder.exName.text = explain.name
            if (explain.files == null) {
                Glide.with(holder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                    .into(holder.exImage)
            } else {
                Glide.with(holder.itemView)
                    .load(explain.files)
                    .into(holder.exImage)

            }


        }

        override fun getItemCount(): Int {
            return explain2List.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val exName: TextView = itemView.findViewById(R.id.exName)
            val exImage: ImageView = itemView.findViewById(R.id.exImage)
            val exPosition: TextView = itemView.findViewById(R.id.exPosition)
        }
    }
    inner class ThreeAdapter(
        val context: Context,
        var explain3List: ArrayList<ExecutiveDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_executive, parent, false)
            val olcYear = explain3List[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ThreeAdapter.ViewHolder).itemView
            val explain = explain3List[position]

            holder.exPosition.text = explain.comPosition
            holder.exName.text = explain.name
            if (explain.files == null) {
                Glide.with(holder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                    .into(holder.exImage)
            } else {
                Glide.with(holder.itemView)
                    .load(explain.files)
                    .into(holder.exImage)

            }


        }

        override fun getItemCount(): Int {
            return explain3List.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val exName: TextView = itemView.findViewById(R.id.exName)
            val exImage: ImageView = itemView.findViewById(R.id.exImage)
            val exPosition: TextView = itemView.findViewById(R.id.exPosition)
        }
    }
    inner class FourAdapter(
        val context: Context,
        var explain4List: ArrayList<ExecutiveDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_executive, parent, false)
            val olcYear = explain4List[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as FourAdapter.ViewHolder).itemView
            val explain = explain4List[position]

            holder.exPosition.text = explain.comPosition
            holder.exName.text = explain.name
            if (explain.files == null) {
                Glide.with(holder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                    .into(holder.exImage)
            } else {
                Glide.with(holder.itemView)
                    .load(explain.files)
                    .into(holder.exImage)

            }


        }

        override fun getItemCount(): Int {
            return explain4List.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val exName: TextView = itemView.findViewById(R.id.exName)
            val exImage: ImageView = itemView.findViewById(R.id.exImage)
            val exPosition: TextView = itemView.findViewById(R.id.exPosition)
        }
    }
    inner class FiveAdapter(
        val context: Context,
        var explain5List: ArrayList<ExecutiveDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_executive, parent, false)
            val olcYear = explain5List[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as FiveAdapter.ViewHolder).itemView
            val explain = explain5List[position]

            holder.exPosition.text = explain.comPosition
            holder.exName.text = explain.name
            if (explain.files == null) {
                Glide.with(holder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                    .into(holder.exImage)
            } else {
                Glide.with(holder.itemView)
                    .load(explain.files)
                    .into(holder.exImage)

            }


        }

        override fun getItemCount(): Int {
            return explain5List.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val exName: TextView = itemView.findViewById(R.id.exName)
            val exImage: ImageView = itemView.findViewById(R.id.exImage)
            val exPosition: TextView = itemView.findViewById(R.id.exPosition)
        }
    }
    inner class SevenAdapter(
        val context: Context,
        var explain7List: ArrayList<ExecutiveDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_executive, parent, false)
            val olcYear = explain7List[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as SevenAdapter.ViewHolder).itemView
            val explain = explain7List[position]

            holder.exPosition.text = explain.comPosition
            holder.exName.text = explain.name
            if (explain.files == null) {
                Glide.with(holder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                    .into(holder.exImage)
            } else {
                Glide.with(holder.itemView)
                    .load(explain.files)
                    .into(holder.exImage)

            }


        }

        override fun getItemCount(): Int {
            return explain7List.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val exName: TextView = itemView.findViewById(R.id.exName)
            val exImage: ImageView = itemView.findViewById(R.id.exImage)
            val exPosition: TextView = itemView.findViewById(R.id.exPosition)
        }
    }
    inner class SixAdapter(
        val context: Context,
        var explain6List: ArrayList<ExecutiveDataClass>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_executive, parent, false)
            val olcYear = explain6List[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as SixAdapter.ViewHolder).itemView
            val explain = explain6List[position]

            holder.exPosition.text = explain.comPosition
            holder.exName.text = explain.name
            if (explain.files == null) {
                Glide.with(holder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                    .into(holder.exImage)
            } else {
                Glide.with(holder.itemView)
                    .load(explain.files)
                    .into(holder.exImage)

            }


        }

        override fun getItemCount(): Int {
            return explain6List.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val exName: TextView = itemView.findViewById(R.id.exName)
            val exImage: ImageView = itemView.findViewById(R.id.exImage)
            val exPosition: TextView = itemView.findViewById(R.id.exPosition)
        }
    }


}