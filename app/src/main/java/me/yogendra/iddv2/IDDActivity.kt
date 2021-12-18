package me.yogendra.iddv2

import android.app.role.RoleManager
import android.app.role.RoleManager.ROLE_CALL_REDIRECTION
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class IDDActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_idd)
    if (!isRedirection())
      roleAcquire(ROLE_CALL_REDIRECTION)
  }

  private fun isRedirection(): Boolean {
    return isRoleHeldByApp(ROLE_CALL_REDIRECTION)
  }

  private fun isRoleHeldByApp(roleName: String): Boolean {
    val roleManager = getSystemService(RoleManager::class.java)
    return roleManager.isRoleHeld(roleName)
  }

  private fun roleAcquire(roleName: String) {
    if (roleAvailable(roleName)) {
      val roleManager = getSystemService(RoleManager::class.java)
      val intent = roleManager.createRequestRoleIntent(roleName)
      startActivityForResult(intent, ROLE_ACQUIRE_REQUEST_CODE)
    } else {
      Toast.makeText(this, "Redirection call with role in not available", Toast.LENGTH_SHORT)
        .show()
    }
  }
  private fun roleAvailable(roleName: String): Boolean {
    val roleManager = getSystemService(RoleManager::class.java)
    return roleManager.isRoleAvailable(roleName)
  }

  companion object {
    private const val ROLE_ACQUIRE_REQUEST_CODE = 4378
  }

}
