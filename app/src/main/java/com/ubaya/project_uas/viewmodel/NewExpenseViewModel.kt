package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.Expense
import com.ubaya.project_uas.model.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val db = UserDatabase(application)

    val budgets: LiveData<List<Budget>> = db.budgetDao().getBudgetsByUser(1)

    fun getTotalSpentForBudget(budgetId: Int): LiveData<Int> {
        return db.expenseDao().getTotalSpentForBudget(budgetId)
    }

    fun insertExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO){
            db.expenseDao().insertExpense(expense)
        }
    }
}
