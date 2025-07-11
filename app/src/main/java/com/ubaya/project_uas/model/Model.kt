package com.ubaya.project_uas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "username") var username: String,

    @ColumnInfo(name = "first_name") var firstName: String,

    @ColumnInfo(name = "last_name") var lastName: String,

    @ColumnInfo(name = "password") var password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(
    tableName = "budgets",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["user_id"])]
)
data class Budget(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "created_at") var createdAt: Long = System.currentTimeMillis() / 1000,
    @ColumnInfo(name = "user_id") var userId: Int // foreign key to User
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


//STACK OVERFLOW tdk d ajarin di kelas
@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Budget::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("budget_id"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = [Index(value = ["budget_id"])] // Saat query room bisa langsung lompat ke index tersebut.
    //Dari segi query tidak cek 1 1, tapi seperti ada bookmark jadi langsung lompat

)
data class Expense(
    @ColumnInfo(name = "budget_id") var budgetId: Int,

    @ColumnInfo(name = "amount") var amount: Int,

    @ColumnInfo(name = "description") var description: String,

    @ColumnInfo(name = "created_at") var createdAt: Long = System.currentTimeMillis()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class ExpenseDisplay(
    val id: Int,
    val amount: Int,
    val description: String,
    val createdAt: Long,
    val budgetName: String
)

data class BudgetWithTotalUsed(
    val id: Int,
    val name: String,
    val amount: Int,
    val used: Int
)


