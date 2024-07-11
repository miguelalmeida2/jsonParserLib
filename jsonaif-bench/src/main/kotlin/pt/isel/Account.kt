package pt.isel

data class Account (var id: Int, var balance: Int = 0, var transactions: List<Transaction>)