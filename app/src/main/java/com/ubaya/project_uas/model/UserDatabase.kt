package com.ubaya.project_uas.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Budget::class, Expense::class], version = 3)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "expenseDB"
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context): UserDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}
