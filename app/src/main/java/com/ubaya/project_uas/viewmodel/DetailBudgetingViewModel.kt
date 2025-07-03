package com.ubaya.project_uas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.project_uas.model.Budget
import com.ubaya.project_uas.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class NewBudgetingViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
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
}