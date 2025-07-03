package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.project_uas.model.User
import com.ubaya.project_uas.model.UserDao
import com.ubaya.project_uas.model.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: UserDao

    // LiveData untuk memberi tahu Fragment jika registrasi berhasil
    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> get() = _registrationSuccess

    // LiveData untuk mengirim pesan error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        userDao = UserDatabase(getApplication()).userDao()
    }

    /**
     * Fungsi untuk mendaftarkan pengguna baru.
     */
    fun register(username: String, firstName: String, lastName: String, password: String, confirm:String) {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            _error.postValue("Semua kolom harus diisi")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (userDao.getUserByUsername(username) != null) {
                    _error.postValue("Username sudah digunakan, silakan pilih yang lain")
                } else if(!password.equals(confirm)){
                    _error.postValue("Password tidak sama!")

                }

                else {
                    val newUser = User(
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        password = password
                    )
                    userDao.insertUser(newUser)
                    _registrationSuccess.postValue(true)
                }
            } catch (e: Exception) {
                _error.postValue("Terjadi kesalahan: ${e.message}")
            }
        }
    }
}