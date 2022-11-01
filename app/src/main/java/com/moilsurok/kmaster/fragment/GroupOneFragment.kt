package com.moilsurok.kmaster.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.databinding.FragmentGroupOneBinding

class GroupOneFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentGroupOneBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var honoraryChairmanRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupOneBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
//        honoraryChairmanRecyclerView = binding.honoraryChairmanRecyclerView
//        var honoraryChairmanList = arrayListOf<HonoraryChairmanDataClass>()


//        db.collection("teams")
//            .document("mBoNPzkybOUeD9UJH9w1")
//            .collection("Executive")
//            .get().addOnSuccessListener { result ->
//
//
//                for (document in result!!.documents) {
//                    if (document.getString("comPosition")!!.contains("기")) {
//                        val item = document.toObject(HonoraryChairmanDataClass::class.java)
//
//                        honoraryChairmanList.add(item!!)
//                    }
//
//
//                    val honoraryChairmanAdapter =
//                        HonoraryChairmanAdapter(ExplainGroupActivity(), honoraryChairmanList)
//                    honoraryChairmanRecyclerView.adapter = honoraryChairmanAdapter
//                    val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
//                    honoraryChairmanRecyclerView.layoutManager =
//                        gridLayoutManager
//                }
//            }








        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
