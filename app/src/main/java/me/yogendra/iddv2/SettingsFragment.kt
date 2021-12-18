package me.yogendra.iddv2


import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
  override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
    updateSummary(preference, newValue.toString())
    return true
  }

  private fun updateSummary(preference: Preference?, value: String) {
    val sep = System.lineSeparator()
    val summary = preference!!.summary.split(sep)[0]
    preference.summary = "$summary$sep$value"
  }
  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.idd_settings)
    initEditTextPreference("local")
    initEditTextPreference("prefix")

  }

  private fun initEditTextPreference(key: String) {
    val p = findPreference(key) as EditTextPreference?
    p?.onPreferenceChangeListener = this
    updateSummary(p, p?.text.toString())
  }
  private fun initSwitchPreference(key: String){
    val switchPreference = findPreference<SwitchPreference>(key)
    switchPreference?.onPreferenceChangeListener = this;

    updateSummary(switchPreference, "foo");

  }
}
