package me.yogendra.idd

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by yogendra on 1/1/18.
 */
class Prefs(context: Context) {


    private val ENABLED = "enabled"
    private val PREFIX = "prefix"
    private val LOCAL = "local"

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    var enabled: Boolean
        get() = prefs.getBoolean(ENABLED, false)
        set(value) = prefs.edit().putBoolean(ENABLED, value).apply()

    var prefix: String
        get() = prefs.getString(PREFIX, "018")!!
        set(value) = prefs.edit().putString(PREFIX, value).apply()
    var local: String
        get() = prefs.getString(LOCAL, "65")!!
        set(value) = prefs.edit().putString(LOCAL, value).apply()
    val skipPrefix
        get() = arrayOf(prefix, "+"+local)

}