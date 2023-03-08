fun main() {
    val first = readln()
    val second = readln()

    println("common: " + common(first, second))
}

fun common(first: String, second: String) = "$first $second"