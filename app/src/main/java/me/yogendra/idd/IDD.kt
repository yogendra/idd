package me.yogendra.idd

import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.PROCESS_OUTGOING_CALLS
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_idd.*


class IDD : AppCompatActivity() {
    val permissions = arrayOf(CALL_PHONE, PROCESS_OUTGOING_CALLS)

    fun requestPermissions() = ActivityCompat.requestPermissions(this, permissions, 1)

    fun needPermissions(): Boolean  = permissions.map { p -> ContextCompat.checkSelfPermission(this, p) != PERMISSION_GRANTED }
                .any { g -> g == true }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idd)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Navigating to Project Home", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            val projectHomeLaunch = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.project_home)))
            startActivity(projectHomeLaunch)
        }
        if (needPermissions()) {
            requestPermissions()
        }
    }

}
