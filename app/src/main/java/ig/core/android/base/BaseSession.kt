@file:Suppress("LeakingThis")

package ig.core.android.base

import android.content.Context
import android.content.SharedPreferences

abstract class BaseSession(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    protected abstract val preferenceName: String

    init {
        sharedPreferences = context.getSharedPreferences("ig_$preferenceName.xml", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun save(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun save(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun save(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun save(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    operator fun get(key: String, def: String): String? {
        return sharedPreferences.getString(key, def)
    }

    fun `is`(key: String, def: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, def)
    }

    operator fun get(key: String, def: Int): Int {
        return sharedPreferences.getInt(key, def)
    }

    operator fun get(key: String, def: Long): Long {
        return sharedPreferences.getLong(key, def)
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }
}