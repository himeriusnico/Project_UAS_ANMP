package com.ubaya.project_uas.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.project_uas.model.User
import com.ubaya.project_uas.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    val userLiveData = MutableLiveData<User?>()
    val errorLiveData = MutableLiveData<String?>()

    private val db = UserDatabase(application)

    fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = db.userDao().login(username, password)
                if (user != null) {
//                    saveSession(user.id)
                    userLiveData.postValue(user)
                    errorLiveData.postValue(null)
                } else {
                    userLiveData.postValue(null)
                    errorLiveData.postValue("Username atau password salah")
                }
            } catch (e: Exception) {
                userLiveData.postValue(null)
                errorLiveData.postValue("Terjadi kesalahan saat login")
            }
        }
    }

    private fun saveSession(userId: Int) {
        val sharedPref = getApplication<Application>()
            .getSharedPreferences("com.ubaya.project_uas.PREF", Context.MODE_PRIVATE)
        sharedPref.edit().putInt("user_id", userId).apply()
    }

    fun clearSession() {
        val sharedPref = getApplication<Application>()
            .getSharedPreferences("com.ubaya.project_uas.PREF", Context.MODE_PRIVATE)
        sharedPref.edit().remove("user_id").apply()
    }
}
