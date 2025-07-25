package com.ubaya.project_uas.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertExpense(expense: Expense)

    // List all expenses, newest first
    @Query("""
    SELECT e.* FROM expense e
    INNER JOIN budgets b ON e.budget_id = b.id
    WHERE b.user_id = :userId
    ORDER BY e.created_at DESC
""")
    fun getAllExpensesByUser(userId: Int): List<Expense>


    @Query("SELECT * FROM expense WHERE id = :expenseId LIMIT 1")
    fun getExpenseById(expenseId: Int): Expense?

    @Query("SELECT * FROM expense WHERE budget_id = :budgetId ORDER BY created_at DESC")
    fun getExpensesByBudget(budgetId: Int): List<Expense>

    @Query("""
    SELECT SUM(e.amount) 
    FROM expense e 
    JOIN budgets b ON e.budget_id = b.id 
    WHERE e.budget_id = :budgetId AND b.user_id = :userId
""")
    fun getTotalSpentForBudget(budgetId: Int, userId: Int): LiveData<Int>


    @Query("""
    SELECT e.id, e.amount, e.description, 
           e.created_at AS createdAt, 
           b.name AS budgetName
    FROM Expense e
    INNER JOIN budgets b ON e.budget_id = b.id
    WHERE b.user_id = :userId
    ORDER BY e.created_at DESC
""")
    fun getAllExpensesWithBudgetNameByUser(userId: Int): LiveData<List<ExpenseDisplay>>


    @Query("SELECT b.id, b.name, b.amount, IFNULL(SUM(e.amount), 0) AS used FROM budgets b LEFT JOIN expense e ON e.budget_id = b.id " +
            "WHERE b.user_id = :userId " +
            "GROUP BY b.id"
    )
    fun getBudgetsWithTotalUsed(userId: Int): LiveData<List<BudgetWithTotalUsed>>

    @Query("DELETE FROM expense WHERE budget_id = :budgetId")
    fun deleteExpensesByBudgetId(budgetId: Int)
}

