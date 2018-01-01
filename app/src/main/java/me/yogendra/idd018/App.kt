package me.yogendra.idd018

import android.app.Application


class App: Application(){
    companion object {
        var prefs: Prefs? = null
    }
    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}
val prefs: Prefs by lazy {
    App.prefs!!
}