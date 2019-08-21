package rynkbit.tk.coffeelist.preference

import android.content.Context

class AdminSettings{
    fun areCredentialsValid(context: Context, password: String): Boolean{
        val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

        return !sharedPreferences.contains(KEY_PASSWORD) ||
                sharedPreferences.getString(KEY_PASSWORD, "") == password
    }

    companion object{
        const val NAME = "admin_preferences"
        const val KEY_PASSWORD = "password"
    }
}