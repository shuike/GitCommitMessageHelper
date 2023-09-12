package print

private const val reset = "\u001B[0m"

enum class Color(val key: String) {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001b[33m"),
    BLUE("\u001b[34m"),
}

fun String.color(color: Color): String {
    return "${color.key}$this$reset"
}

fun println(string: String, color: Color) {
    println(string.color(color))
}