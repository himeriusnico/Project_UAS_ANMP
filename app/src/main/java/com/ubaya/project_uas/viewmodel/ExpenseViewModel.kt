package com.ubaya.project_uas.viewmodel

import android.app.Application
import android.content.Context
import android.se.omapi.Session
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ubaya.project_uas.model.ExpenseDisplay
import com.ubaya.project_uas.model.UserDatabase
import com.ubaya.project_uas.utils.SessionManager

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseDao = UserDatabase(application).expenseDao()
    fun getExpensesByUser(userId: Int): LiveData<List<ExpenseDisplay>> {
        return expenseDao.getAllExpensesWithBudgetNameByUser(userId)
    }
}
