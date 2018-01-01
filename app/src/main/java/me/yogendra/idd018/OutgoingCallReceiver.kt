package me.yogendra.idd018

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

class OutgoingCallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val phoneNumber = getPhoneNumber(intent)

        if (shouldProcess(phoneNumber)) {
            dial(phoneNumber, context)
        } else {
            Log.d("dialer", "Skip processing phoneNUmber:" + phoneNumber);
        }
    }

    private fun getPhoneNumber(intent: Intent): String {
        Log.d("dialer", intent.toString())
        var phoneNumber = resultData
        if (phoneNumber == null) {
            // No reformatted number, use the original
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        }

        Log.d("dialer", "phoneNumber:" + phoneNumber)
        return phoneNumber
    }

    private fun dial(phoneNumber: String, context: Context) {
        val toDial = phoneNumber.replace("+", prefs.prefix)
        Log.d("dialer", "Number to Dial:" + toDial);

        resultData = null

        val uri = Uri.fromParts("tel", toDial, null)
        val newIntent = Intent(Intent.ACTION_CALL, uri)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(newIntent)
        } catch (e: SecurityException) {
            Log.e("dialer", "Permission not given");
        }
    }

    private fun shouldProcess(phoneNumber: String): Boolean {
        Log.d( "dialer", "prefs(enabled:${prefs.enabled}, local:${prefs.local}, prefix:${prefs.prefix}, skipPrefix:${prefs.skipPrefix.joinToString()}}")
        return prefs.enabled and
                phoneNumber.startsWith("+") and
                prefs.skipPrefix.none { phoneNumber.startsWith(it) }


    }
}

