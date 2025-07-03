package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.BudgetWithTotalUsed
import com.ubaya.project_uas.model.UserDatabase

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val budgetDao = UserDatabase(application).expenseDao()

    val budgets: LiveData<List<BudgetWithTotalUsed>> = budgetDao.getBudgetsWithTotalUsed()
}

