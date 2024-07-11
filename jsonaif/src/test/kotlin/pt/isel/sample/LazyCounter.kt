package pt.isel.sample



object LazyCounter {
    private var count=0

    fun incLazyCounter(){
        count++;
    }
    fun  getLazyCounter():Int{return count}

}