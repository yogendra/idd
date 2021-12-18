package me.yogendra.iddv2

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * Created by yogendra on 1/1/18.
 */
class Prefs(context: Context) {


    private val _ENABLED = "enabled"
    private val _PREFIX = "prefix"
    private val _LOCAL = "local"

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var enabled: Boolean
        get() = prefs.getBoolean(_ENABLED, false)
        set(value) = prefs.edit().putBoolean(_ENABLED, value).apply()

    var prefix: String
        get() = prefs.getString(_PREFIX, "018")!!
        set(value) = prefs.edit().putString(_PREFIX, value).apply()
    var local: String
        get() = prefs.getString(_LOCAL, "65")!!
        set(value) = prefs.edit().putString(_LOCAL, value).apply()
    val skipPrefix
        get() = arrayOf(prefix, "+$local")

}
