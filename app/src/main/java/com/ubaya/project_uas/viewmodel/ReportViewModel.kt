package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.BudgetWithTotalUsed
import com.ubaya.project_uas.model.UserDatabase
import com.ubaya.project_uas.utils.SessionManager

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application.applicationContext)
    private val userId = sessionManager.getUserId()
    private val budgetDao = UserDatabase(application).expenseDao()
    fun getBudgetsWithTotalUsed(userId: Int): LiveData<List<BudgetWithTotalUsed>> {
        return budgetDao.getBudgetsWithTotalUsed(userId)
    }
}

