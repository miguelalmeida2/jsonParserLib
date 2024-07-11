package pt.isel.sample

import pt.isel.Formatter
import pt.isel.Transaction

class JsonToTransactions : Formatter {
    override fun format(value: Any?): Any {
        val transactions = value as ArrayList<String>
        val transactionsResult : MutableList<Transaction> = mutableListOf()

        transactions.forEach {
            val transactionValues = it.split("-")
            transactionsResult.add( Transaction(transactionValues[0].toInt(),transactionValues[1].toInt(),transactionValues[2].toInt()) )
        }

        return transactionsResult
    }
}