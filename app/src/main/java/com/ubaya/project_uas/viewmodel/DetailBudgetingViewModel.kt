package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailBudgetingViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val budgetDao = UserDatabase(application).budgetDao()
    val budgetLD = MutableLiveData<Budget>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    val budgetingCreateStatusLD = MutableLiveData<Boolean>()
    private val db = UserDatabase(getApplication())

    fun addBudgeting(budget: Budget) {
        launch {
            db.budgetDao().insertBudget(budget)
            budgetingCreateStatusLD.postValue(true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getBudgetsByUser(userId: Int): LiveData<List<Budget>> {
        return budgetDao.getBudgetsByUser(userId)
    }

    fun fetchBudgetById(budgetId: Int) {
        launch(Dispatchers.IO) {
            val budget = db.budgetDao().getBudgetById(budgetId)
            budgetLD.postValue(budget)
        }
    }

    fun updateBudget(budget: Budget) {
        launch(Dispatchers.IO) {
            db.budgetDao().updateBudget(budget)
        }
    }

    fun deleteBudgetWithExpenses(budgetId: Int) {
        launch {
            // Hapus semua expenses yang terkait
            db.expenseDao().deleteExpensesByBudgetId(budgetId)
            // Hapus budget-nya
            db.budgetDao().deleteBudgetById(budgetId)
        }
    }
}