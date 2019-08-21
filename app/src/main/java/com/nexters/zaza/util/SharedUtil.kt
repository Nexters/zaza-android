package com.nexters.zaza.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedUtil(val context: Context) {

    companion object {
        const val ALARM_TEXT = "alarm_text"
        const val DEFAULT_VALUE = "default_value"
    }
    val sharedPreference: SharedPreferences
    init {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveStringPreference(key: String, value: String){
        val editor = sharedPreference.edit()
        if(getStringPreference(key, DEFAULT_VALUE).equals(DEFAULT_VALUE))
            removeStringPreference(key)
        editor.putString(key, value)
        editor.apply()
    }


    fun getStringPreference(key: String, defValue: String):String{
        return sharedPreference.getString(key, defValue)!!
    }

    fun removeStringPreference(key: String){
        val editor = sharedPreference.edit()
        editor.remove(key)
        editor.apply()
    }
}