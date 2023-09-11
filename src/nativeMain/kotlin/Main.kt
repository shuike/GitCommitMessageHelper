import platform.AppKit.NSPasteboard
import platform.AppKit.NSPasteboardTypeString
import platform.posix.exit

data class Type(
    val name: String,
    val num: String,
    val desc: String,
)

fun main() {
    val list = mutableListOf<Type>()
    list.add(Type("feat", "1", "新功能（feature）"))
    list.add(Type("fix", "2", "修补bug"))
    list.add(Type("docs", "3", "文档（documentation）"))
    list.add(Type("style", "4", " 格式（不影响代码运行的变动）"))
    list.add(Type("refactor", "5", "重构（即不是新增功能，也不是修改bug的代码变动）"))
    list.add(Type("test", "6", "增加测试"))
    list.add(Type("chore", "7", "构建过程或辅助工具的"))
    val sb = StringBuilder("type:\n")
    list.forEach {
        sb.append("    ${it.num}.${it.name}:${it.desc}\n")
    }
    println(sb)
    print("Please enter a type or number (empty will break): ")
    val typeOrNum = readlnOrNull()
    if (typeOrNum.isNullOrBlank()) {
        exit(0)
        return
    }
    val type = list.find { typeOrNum in arrayOf(it.name, it.num) }
    if (type == null) {
        exit(0)
        return
    }
    print("Please enter scope（nullable): ")
    var scope = readlnOrNull()
    if (scope.isNullOrBlank()) {
        scope = ""
    }
    fun readSubject(): String? {
        val subject = readlnOrNull()
        return subject
    }

    var subject: String?
    do {
        print("Place enter subject (not null): ")
        subject = readSubject()
    } while (subject.isNullOrBlank())

    print("Place enter body (nullable): ")
    var body = readlnOrNull() ?: ""
    if (body.isBlank()) {
        body = ""
    }
    print("Place enter footer (nullable): ")
    var footer = readlnOrNull() ?: ""
    if (footer.isBlank()) {
        footer = ""
    }


    val content = StringBuilder(type.name)
    if (scope.isNotEmpty()) {
        content.append("(${scope})")
    }
    content.append(":")
    content.append(subject)

    if (body.isNotEmpty()) {
        content.append("\n")
        content.append(body)
    }
    if (footer.isNotEmpty()) {
        content.append("\n")
        content.append(footer)
    }

    println("Result: ")
    println(content)
    val writeObjects = writeToClipBoard(content)
    if (writeObjects) {
        println("\nHas been written to the clipboard.".color(Color.GREEN))
    }
}

private fun writeToClipBoard(content: StringBuilder): Boolean {
    val nsPasteboard = NSPasteboard.generalPasteboard()
    nsPasteboard.clearContents()
    return nsPasteboard.setString(content.toString(), NSPasteboardTypeString)
}


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
