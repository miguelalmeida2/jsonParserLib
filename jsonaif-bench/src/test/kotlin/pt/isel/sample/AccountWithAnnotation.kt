package pt.isel.sample

import pt.isel.JsonConvert
import pt.isel.JsonToTransactions
import pt.isel.Transaction

data class AccountWithAnnotation (var id: Int, var balance: Int = 0, @JsonConvert(JsonToTransactions::class) var transactions: List<Transaction>)