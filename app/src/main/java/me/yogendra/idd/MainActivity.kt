package me.yogendra.idd

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class MainActivity : Activity() {
    fun needPermissions() = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
    fun requestPermissions() = ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needPermissions()) {
            requestPermissions()
        }
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

    }
}