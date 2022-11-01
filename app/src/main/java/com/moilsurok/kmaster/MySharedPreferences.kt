package com.moilsurok.kmaster

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private val MY_ACCOUNT: String = "account"
    private val MY_ACCOUNT1: String = "account1"
    private val EVENT_DATE: String = "eventDate"
    private val CHECK_BOX: String = "check"
    private val PHONE_NUM: String = "phoneN"
    private val USER_ID: String = "uid"
    private val QUES_UID: String = "question"
    private val USER_UID: String = "id"
    private val USER_YEAR: String = "year"
    private val USER_SEARCH: String = "search"
    private val END_YEAR: String = "endYear"
    private val FIRST_YEAR: String = "firstYear"

    fun setFirstYear(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(FIRST_YEAR, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_FIRST_YEAR", input)
        editor.apply()
    }

    fun getFirstYear(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(FIRST_YEAR, Context.MODE_PRIVATE)
        return prefs.getString("MY_FIRST_YEAR", "").toString()
    }
    fun setEndYear(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(END_YEAR, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_END_YEAR", input)
        editor.apply()
    }

    fun getEndYear(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(END_YEAR, Context.MODE_PRIVATE)
        return prefs.getString("MY_END_YEAR", "").toString()
    }
    fun setYear(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_YEAR, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_USER_YEAR", input)
        editor.apply()
    }

    fun getYear(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_YEAR, Context.MODE_PRIVATE)
        return prefs.getString("MY_USER_YEAR", "").toString()
    }
    fun setSearch(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_SEARCH, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("USER_SEARCH", input)
        editor.apply()
    }

    fun getSearch(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_SEARCH, Context.MODE_PRIVATE)
        return prefs.getString("USER_SEARCH", "").toString()
    }
    fun setUserId(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT1, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.apply()
    }

    fun getUserId(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT1, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }
    fun getQues(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(QUES_UID, Context.MODE_PRIVATE)
        return prefs.getString("MY_QUES_UID", "").toString()
    }

    fun setQues(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(QUES_UID, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_QUES_UID", input)
        editor.apply()
    }

    fun setUserPass(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PASS", input)
        editor.apply()
    }

    fun getUserPass(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PASS", "").toString()
    }

    fun setUserUid(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_UID", input)
        editor.apply()
    }
    fun getUid(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(USER_UID, Context.MODE_PRIVATE)
        return prefs.getString("USER_UID", "").toString()
    }

    fun setUid(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(USER_UID, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("USER_UID", input)
        editor.apply()
    }

    fun getUserUid(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        return prefs.getString("MY_UID", "").toString()
    }

    fun setCheckBox(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(CHECK_BOX, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_BOOKMARK", input)
        editor.apply()
    }

    fun getPhoneNum(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PHONE_NUM, Context.MODE_PRIVATE)
        return prefs.getString("MY_PHONE_NUM", "").toString()
    }

    fun setPhoneNum(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PHONE_NUM, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PHONE_NUM", input)
        editor.apply()
    }

    fun getCheckBox(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(CHECK_BOX, Context.MODE_PRIVATE)
        return prefs.getString("MY_BOOKMARK", "").toString()
    }

    fun setEventDate(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("EVENT_DATE", input)
        editor.apply()
    }


    fun clearUser(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}