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
    @Query("SELECT * FROM expense ORDER BY created_at DESC")
    fun getAllExpenses(): List<Expense>

    // Get a specific expense by ID (if needed)
    @Query("SELECT * FROM expense WHERE id = :expenseId LIMIT 1")
    fun getExpenseById(expenseId: Int): Expense?

    // Get all expenses for a specific budget
    @Query("SELECT * FROM expense WHERE budget_id = :budgetId ORDER BY created_at DESC")
    fun getExpensesByBudget(budgetId: Int): List<Expense>

    // Get total spent for a specific budget (used in budgeting module)
    @Query("SELECT SUM(amount) FROM expense WHERE budget_id = :budgetId")
    fun getTotalSpentForBudget(budgetId: Int): LiveData<Int>

    @Query("SELECT e.id, e.amount, e.description, \n" +
            "           e.created_at AS createdAt, \n" +
            "           b.name AS budgetName\n" +
            "    FROM Expense e\n" +
            "    INNER JOIN budgets b ON e.budget_id = b.id\n" +
            "    ORDER BY e.created_at DESC"
    )
    fun getAllExpensesWithBudgetName(): LiveData<List<ExpenseDisplay>>

    @Query("SELECT b.id, b.name, b.amount, IFNULL(SUM(e.amount), 0) AS used FROM budgets b LEFT JOIN expense e ON e.budget_id = b.id GROUP BY b.id"
    )
    fun getBudgetsWithTotalUsed(): LiveData<List<BudgetWithTotalUsed>>
}
