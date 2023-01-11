package com.moilsurok.kmaster.dataClass


data class ProfileDataClass(
    var company: String? = null,
    var email: String? = null,
    var field:String? = null,
    var modifiedDate:String? = null,
    var name: String? = null,
    var num: String? = null,
    var occupation: String? = null,
    var phoneNum: ArrayList<Any>? = ArrayList(),
    var pubDate:String? = null,
    var year: String? = null,
    var check :String? = null,
    var user: String? = null,
)
