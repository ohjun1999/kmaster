package com.moilsurok.kmaster.dataClass

data class GalleryDataClass(
    val content: String? = null,
    val creator: String? = null,
    val files: ArrayList<Any>? = ArrayList(),
    val modifiedDate: String? = null,
    val pubDate: String? = null,
    val title: String? = null,
)
