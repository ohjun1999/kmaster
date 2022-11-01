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
import com.moilsurok.kmaster.databinding.FragmentGroupTwoBinding

class GroupTwoFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentGroupTwoBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var committeeRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupTwoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
//        committeeRecyclerView = binding.committeeRecyclerView
//        var committeeList = arrayListOf<CommitteeDataClass>()


//        db.collection("teams")
//            .document("mBoNPzkybOUeD9UJH9w1")
//            .collection("Committee")
//            .get().addOnSuccessListener { result ->
//
//
//                for (document in result) {
//
//                    val item = document.toObject(CommitteeDataClass::class.java)
//
//                    committeeList.add(item)
//                }
//
//
//                val committeeAdapter =
//                    CommitteeAdapter(ExplainGroupActivity(), committeeList)
//                committeeRecyclerView.adapter = committeeAdapter
//                val gridLayoutManager = GridLayoutManager(ExplainGroupActivity(), 2)
//                committeeRecyclerView.layoutManager =
//                    gridLayoutManager
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