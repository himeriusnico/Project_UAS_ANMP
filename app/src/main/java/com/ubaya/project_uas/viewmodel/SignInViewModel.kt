package com.ubaya.project_uas.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope // Import yang benar
import com.ubaya.project_uas.model.User
import com.ubaya.project_uas.model.UserDatabase
import kotlinx.coroutines.launch

class SignInViewModel(application: Application): AndroidViewModel(application) {

    val userLiveData = MutableLiveData<User?>()
    val errorLiveData = MutableLiveData<String?>()

    private val db = UserDatabase(getApplication())

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = db.userDao().login(username, password)
            if (user != null) {
                saveSession(user.id)
                userLiveData.postValue(user)
                errorLiveData.postValue(null)
            } else {
                userLiveData.postValue(null)
                errorLiveData.postValue("Invalid username or password")
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