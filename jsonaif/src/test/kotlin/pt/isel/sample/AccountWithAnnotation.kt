package pt.isel.sample

import pt.isel.JsonConvert

data class AccountWithAnnotation (var id: Int, var balance: Int = 0, @JsonConvert(JsonToTransactions::class) var transactions: List<Transaction>)