fun main() {
    val l = readLine()!!.split(" ").map{it.toInt()};
    var sum = 0
    for (i in 0 until l[1]) {
        sum += readLine()!!.toInt()
    }
    println(((sum - (3.0 * (l[0] - l[1]))) / l[0]).toString() + " " + ((sum + 3.0 * (l[0] - l[1])) / l[0]).toString())
}

