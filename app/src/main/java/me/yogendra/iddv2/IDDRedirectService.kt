package me.yogendra.iddv2

import android.net.Uri
import android.os.Bundle
import android.telecom.CallRedirectionService
import android.telecom.PhoneAccountHandle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

class IDDRedirectService : CallRedirectionService() {

  override fun onPlaceCall(handle: Uri, initialPhoneAccount: PhoneAccountHandle, allowInteractiveResponse: Boolean) {
    Log.d("dialer", "handle:$handle , initialPhoneAccount:$initialPhoneAccount , allowInteractiveResponse:$allowInteractiveResponse")
    val analytics = FirebaseAnalytics.getInstance(applicationContext)

    val phoneNumber = getPhoneNumber(handle)

    if (shouldProcess(phoneNumber)) {
      Log.d("dialer", "Processing: $phoneNumber")
      analytics.logEvent(Analytics.EVENT_PREFIXED, Bundle.EMPTY)
      dial(phoneNumber, initialPhoneAccount, allowInteractiveResponse)
    } else {
      Log.d("dialer", "Skip processing phoneNumber:" + phoneNumber)
      placeCallUnmodified()
    }
  }

  private fun getPhoneNumber(handle: Uri): String {
    Log.d("dialer", handle.toString())
    val phoneNumber = handle.schemeSpecificPart
    Log.d("dialer", "Extracted Phone Number:$phoneNumber")
    return phoneNumber
  }
  private fun dial(phoneNumber: String, phoneAccount: PhoneAccountHandle, confirmFirst: Boolean) {
    val toDial = phoneNumber.replace("+", prefs.prefix)
    val gateway = Uri.fromParts("tel", toDial, "")
//    val gateway = Uri.fromParts("tel","91857055","");
    Log.d("dialer", "Number to Dial:$toDial, gateway: $gateway")
    redirectCall(gateway, phoneAccount, confirmFirst)
    redirectCall(gateway, phoneAccount, confirmFirst)

  }

  private fun shouldProcess(phoneNumber: String): Boolean {
    Log.d( "dialer", "prefs(enabled:${prefs.enabled}, local:${prefs.local}, prefix:${prefs.prefix}, skipPrefix:${prefs.skipPrefix.joinToString()}}")
    return prefs.enabled and
      phoneNumber.startsWith("+") and
      prefs.skipPrefix.none { phoneNumber.startsWith(it) }
  }
}
