package com.example.restaurants.domain.poco

import com.example.restaurants.data.local.dao.TransactionDao
import com.example.restaurants.data.local.poco.LocalRestaurant
import com.example.restaurants.data.local.poco.Transactions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

data class Transactions(
    val id: String,
    val name: String,
    val restaurantId: String
)

suspend fun mapTransactionObject(transactionDao: TransactionDao, localRestaurant: LocalRestaurant): List<String> {
    return withContext(Dispatchers.IO) {
        val transactions: List<Transactions> = transactionDao.getAll()
        transactions.filter { it.restaurantId == localRestaurant.id }.map { transaction ->
            transaction.name
        }
    }
}

fun createTransactionList(transactionsNames: List<String>, restaurantId: String):
        List<Transactions> {
    return ArrayList<Transactions>().apply {
        transactionsNames.map { transaction ->
            val transactionId = UUID.randomUUID().toString()
            val transaction = Transactions(transactionId, transaction, restaurantId)
            this.add(transaction)
        }
    }
}



