package pt.isel.sample

import pt.isel.Transaction

data class Account (var id: Int, var balance: Int = 0, var transactions: List<Transaction>)