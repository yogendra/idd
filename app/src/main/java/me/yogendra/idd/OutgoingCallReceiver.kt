package me.yogendra.idd

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import android.provider.SyncStateContract.Helpers.update
import android.util.Base64
import java.security.MessageDigest


class OutgoingCallReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val analytics = FirebaseAnalytics.getInstance(context);

        val phoneNumber = getPhoneNumber(intent)

        if (shouldProcess(phoneNumber)) {

            analytics.logEvent(Analytics.EVENT_PREFIXED, params(phoneNumber))

            dial(phoneNumber, context)
        } else {
            Log.d("dialer", "Skip processing phoneNumber:" + phoneNumber)
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


    private fun params(phoneNumber: String): Bundle {

        val params = Bundle()
        params.putString(Analytics.PARAM_PHONENUMBER_HASH, hash(phoneNumber))
        return params;

    }

    private fun hash(text:String): String {
        val md = MessageDigest.getInstance("SHA-1")
        val textBytes = text.toByteArray(Charsets.UTF_8)
        md.update(textBytes, 0, textBytes.size)
        val sha1hash = md.digest()
        return Base64.encodeToString(sha1hash, Base64.NO_WRAP)
    }
}

