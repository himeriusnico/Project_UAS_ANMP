package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.project_uas.model.User
import com.ubaya.project_uas.model.UserDao
import com.ubaya.project_uas.model.UserDatabase
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
    fun register(username: String, firstName: String, lastName: String, password: String) {
        // 1. Validasi Input
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            _error.postValue("Semua kolom harus diisi")
            return
        }

        // 2. Jalankan proses di background thread
        viewModelScope.launch {
            // 3. Cek apakah username sudah ada
            if (userDao.getUserByUsername(username) != null) {
                _error.postValue("Username sudah digunakan, silakan pilih yang lain")
            } else {
                // 4. Jika belum ada, buat objek User dan masukkan ke database
                val newUser = User(
                    username = username,
                    firstName = firstName,
                    lastName = lastName,
                    password = password // PENTING: Di aplikasi nyata, password harus di-hash!
                )
                userDao.insertUser(newUser)

                // 5. Beri tahu Fragment bahwa registrasi berhasil
                _registrationSuccess.postValue(true)
            }
        }
    }
}