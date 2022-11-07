package com.moilsurok.kmaster.dataClass

data class UserDataClass(
    val company: String? = null,
    val email: String? = null,
    val field:String? = null,
    val filenames:String? = null,
    val files:String? = null,
    val modifiedDate:String? = null,
    val name: String? = null,
    val num: String? = null,
    val occupation: String? = null,
    val phoneNum: ArrayList<Any>? = ArrayList(),
    val pubDate:String? = null,
    val uid:String? = null,
    val year: String? = null,
    val bookmark: ArrayList<Any>? = ArrayList(),
)