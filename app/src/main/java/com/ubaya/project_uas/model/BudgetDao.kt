package com.ubaya.project_uas.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBudget(budget: Budget)

    @Query("SELECT * FROM budgets")
    fun getAllBudgets(): List<Budget>

    @Query("SELECT * FROM budgets WHERE id = :budgetId LIMIT 1")
    fun getBudgetById(budgetId: Int): Budget?

    @Update
    fun updateBudget(budget: Budget)

    @Query("DELETE FROM budgets WHERE id = :budgetId")
    fun deleteBudget(budgetId: Int)
}
