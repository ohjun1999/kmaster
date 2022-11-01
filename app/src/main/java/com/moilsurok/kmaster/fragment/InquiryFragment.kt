package com.moilsurok.kmaster.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.activity.InquiryActivity
import com.moilsurok.kmaster.adapter.InquiryAdapter
import com.moilsurok.kmaster.dataClass.InquiryDataClass
import com.moilsurok.kmaster.databinding.FragmentInquiryBinding


class InquiryFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentInquiryBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var inquiryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInquiryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth

        val theUid = MySharedPreferences.getUid(requireContext())
        inquiryRecyclerView = binding.inquiryRecyclerView
        var InquiryList = arrayListOf<InquiryDataClass>()
        Log.d("test1", theUid)
        db
            .collection("Question").whereEqualTo("uid", theUid)
            .addSnapshotListener { result, _ ->
                InquiryList.clear()
                for (document in result!!) {

                    val item = document.toObject(InquiryDataClass::class.java)

                    InquiryList.add(item)

                }

                val inquiryAdapter = InquiryAdapter(InquiryActivity(), InquiryList)
                inquiryRecyclerView.adapter = inquiryAdapter
                inquiryRecyclerView.layoutManager =
                    LinearLayoutManager(InquiryActivity(), RecyclerView.VERTICAL, false)
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


}