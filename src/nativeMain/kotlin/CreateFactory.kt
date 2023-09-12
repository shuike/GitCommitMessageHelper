import clipboard.Clipboard
import model.Type
import platform.posix.exit
import print.Color

object CreateFactory {
    fun execute() {
        val types = getTypes()
        println(typesContent(types))
        print("Please enter a type or number (empty will break): ")
        val typeOrNum = readlnOrNull()
        if (typeOrNum.isNullOrBlank()) {
            exit(0)
            return
        }

        val type = types.find { typeOrNum in arrayOf(it.name, it.num) }
        if (type == null) {
            exit(0)
            return
        }

        print("Please enter scope（nullable): ")
        var scope = readlnOrNull()
        if (scope.isNullOrBlank()) {
            scope = ""
        }
        fun readSubject(): String? = readlnOrNull()

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
        content.append(": ")
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
        val writeObjects = Clipboard.writeToClipBoard(content.toString())
        if (writeObjects) {
            print.println("\nHas been written to the clipboard.", Color.GREEN)
        }
    }

    private fun typesContent(types: MutableList<Type>): String {
        val sb = StringBuilder("type:\n")
        types.forEach {
            sb.append("    ${it.num}.${it.name}:${it.desc}\n")
        }
        return sb.toString()
    }

    private fun getTypes(): MutableList<Type> {
        val list = mutableListOf<Type>()
        list.add(Type("feat", "1", "新功能（feature）"))
        list.add(Type("fix", "2", "修补bug"))
        list.add(Type("docs", "3", "文档（documentation）"))
        list.add(Type("style", "4", " 格式（不影响代码运行的变动）"))
        list.add(Type("refactor", "5", "重构（即不是新增功能，也不是修改bug的代码变动）"))
        list.add(Type("test", "6", "增加测试"))
        list.add(Type("chore", "7", "构建过程或辅助工具的"))
        return list
    }

}