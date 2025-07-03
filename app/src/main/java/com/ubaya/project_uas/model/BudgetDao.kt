package com.ubaya.project_uas.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBudget(budget: Budget)

    // ✅ Only show budgets that belong to a specific user
    @Query("SELECT * FROM budgets WHERE user_id = :userId ORDER BY created_at DESC")
    fun getBudgetsByUser(userId: Int): LiveData<List<Budget>>

    // ✅ Get a specific budget by its ID and the user who owns it
    @Query("SELECT * FROM budgets WHERE id = :budgetId AND user_id = :userId LIMIT 1")
    fun getBudgetByIdAndUser(budgetId: Int, userId: Int): LiveData<Budget>


    // ✅ Update (safe because you’ll pass the full budget object, which contains user_id already)
    @Update
    fun updateBudget(budget: Budget)

    // ✅ Delete budget (we assume you're already verifying ownership elsewhere)
    @Query("DELETE FROM budgets WHERE id = :budgetId")
    fun deleteBudgetById(budgetId: Int)

    @Query("SELECT * FROM budgets WHERE id = :budgetId")
    fun getBudgetById(budgetId: Int): Budget

    @Query("UPDATE budgets SET name = :name, amount = :amount WHERE id = :budgetId")
    fun updateBudget(budgetId: Int, name: String, amount: Int)
}

