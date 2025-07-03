package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ubaya.project_uas.model.ExpenseDisplay
import com.ubaya.project_uas.model.UserDatabase

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val expenseDao = UserDatabase(application).expenseDao()
    val expenses: LiveData<List<ExpenseDisplay>> = expenseDao.getAllExpensesWithBudgetNameByUser(1)
}
