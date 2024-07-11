package pt.isel

data class AccountWithAnnotation (var id: Int, var balance: Int = 0, @JsonConvert(JsonToTransactions::class) var transactions: List<Transaction>)