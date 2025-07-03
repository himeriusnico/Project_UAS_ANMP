package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.UserDatabase

class BudgetingViewModel(application: Application) : AndroidViewModel(application) {
    private val budgetDao = UserDatabase(application).budgetDao()

    // Replace 1 with actual user ID from session/login
    fun getBudgetsByUser(userId: Int): LiveData<List<Budget>> {
        return budgetDao.getBudgetsByUser(userId)
    }
}
