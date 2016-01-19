package com.prt2121.everywhere

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by pt2121 on 1/18/16.
 */
class TokenStorage(val context: Context) : ITokenStorage {
  val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
  val s = "TOKEN_KEY"

  override fun save(token: String) {
    val editor = sharedPref.edit()
    editor.putString(s, token)
    editor.commit()
  }

  override fun retrieve(): String? {
    return sharedPref.getString(s, "")
  }
}