package me.yogendra.idd

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_idd.*
import android.content.Intent
import android.net.Uri


class IDD : AppCompatActivity() {

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
    }

}
