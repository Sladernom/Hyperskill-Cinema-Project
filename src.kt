package cinema

var totalIncome = 0

fun setupCinema(): Pair<Int, Int> {
    println("Enter the number of rows:")
    val numRows: Int = readln()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow: Int = readln()!!.toInt()
    return Pair(numRows, seatsInRow)
}

fun printCinema(topList: MutableList<MutableList<String>>) {
    println("Cinema:")
    print(" ")
    for (rowInit in topList[0].indices) print(" ${rowInit + 1}")
    for (rowIndex in topList.indices) {
    print("\n${rowIndex + 1}")
    for (seatIndex in topList[rowIndex].indices) print(" ${topList[rowIndex][seatIndex]}")
    }
    println()
}

fun pickSeat(numRows: Int, seatsInRow: Int, numSeats: Int, topList: MutableList<MutableList<String>>) {
    while (true) {
        println("\nEnter a row number:")
        val rowPick = readln()!!.toInt()
        println("Enter a seat number in that row:")
        val seatPick = readln()!!.toInt()
        if (rowPick !in 1..numRows || seatPick !in 1..seatsInRow) {
            println("Wrong input!")
            // pickSeat(numRows, seatsInRow, numSeats, topList)
            continue
        }
        val holdingVar = topList[rowPick - 1][seatPick - 1]
        if (holdingVar == "B") {
            println("That ticket has already been purchased!")
            // pickSeat(numRows, seatsInRow, numSeats, topList)
            continue
        }

        var seatCost = if (numSeats <= 60 || rowPick <= numRows / 2) 10 else 8
        println("Ticket price: $$seatCost")
        topList[rowPick - 1][seatPick - 1] = "B"
        totalIncome += seatCost
        break
    }
}

fun fetchStats(totalIncome: Int, topList: MutableList<MutableList<String>>, numRows: Int, seatsInRow: Int, numSeats: Int) {
    val boughtTickets = topList.flatten().count { it == "B" }
    println("Number of purchased tickets: $boughtTickets")

    val boughtRoughP = boughtTickets.toDouble() / topList.flatten().size * 100
    val boughtPercent: String = "%.2f".format(boughtRoughP)
    println("Percentage: $boughtPercent%")

    println("Current income: $$totalIncome")

    val maxIncome = if (numSeats <= 60) numSeats * 10 else {
        numRows / 2  * seatsInRow * 10 + (numRows / 2 + 1) * seatsInRow * 8
    }
    println("Total income: $$maxIncome")
}

fun main() {
    val (numRows, seatsInRow) = setupCinema()

    val topList = MutableList(numRows) { MutableList(seatsInRow) { "S" } }
    val numSeats = topList.flatten().size
    
    while(true) {
    println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n")
    
    when (readln()!!.toInt()) {
        1 -> { printCinema(topList) }
        2 -> { pickSeat(numRows, seatsInRow, numSeats, topList) }
        3 -> { fetchStats(totalIncome, topList, numRows, seatsInRow, numSeats) }
        0 -> return
    }
    }
}
