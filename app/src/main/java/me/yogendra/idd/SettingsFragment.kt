package me.yogendra.idd


import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v4.app.Fragment


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : PreferenceFragment(), Preference.OnPreferenceChangeListener {
    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        updateSummary(preference, newValue.toString());
        return true;
    }

    private fun updateSummary(preference: Preference?, value: String) {
        val sep = System.lineSeparator()
        val summary = preference!!.summary.split(sep)[0]
        preference.summary = "$summary$sep$value"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference);
        initEditTextPreference("local")
        initEditTextPreference("prefix")

    }

    private fun initEditTextPreference(key: String) {
        val p = findPreference(key) as EditTextPreference
        p.onPreferenceChangeListener = this
        updateSummary(p, p.text.toString())
    }

}
