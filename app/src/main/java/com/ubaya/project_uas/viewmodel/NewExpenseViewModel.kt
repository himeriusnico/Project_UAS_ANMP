package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.Expense
import com.ubaya.project_uas.model.UserDatabase
import com.ubaya.project_uas.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val db = UserDatabase(application)
    private val sessionManager = SessionManager(application.applicationContext)
    private val userId = sessionManager.getUserId()

    fun getBudgetsByUser(userId: Int): LiveData<List<Budget>> {
        return db.budgetDao().getBudgetsByUser(userId)
    }

    fun getTotalSpentForBudget(budgetId: Int, userId:Int): LiveData<Int> {
        return db.expenseDao().getTotalSpentForBudget(budgetId,userId)
    }

    fun insertExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO){
            db.expenseDao().insertExpense(expense)
        }
    }
}
