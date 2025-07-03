package com.ubaya.project_uas.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.project_uas.model.UserDatabase
import com.ubaya.project_uas.utils.SessionManager
import com.ubaya.project_uas.view.AuthActivity
import com.ubaya.project_uas.view.SignInFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val oldPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
    val repeatPassword = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    val navigateToLogin = MutableLiveData<Boolean>()

    private val db = UserDatabase(application)
    private val userDao = db.userDao()
    private val sharedPref = application.getSharedPreferences("com.ubaya.project_uas.PREF", Context.MODE_PRIVATE)


    fun changePassword() {
        val old = oldPassword.value ?: ""
        val new = newPassword.value ?: ""
        val repeat = repeatPassword.value ?: ""

        if (new != repeat) {
            message.value = "New passwords do not match"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val userId = sharedPref.getInt("user_id", -1)
            val currentUser = userDao.getUserById(userId)

            if (currentUser?.password != old) {
                message.postValue("Old password is incorrect")
                return@launch
            }

            userDao.updatePassword(userId, new)
            message.postValue("Password changed successfully")
            navigateToLogin.postValue(true)
        }
    }

    fun logout(context: Context) {
        sharedPref.edit().clear().apply()
        val intent = Intent(context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}
