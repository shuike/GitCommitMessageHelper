fun main(vararg args: String) {
    if (args.isNotEmpty()) {
        val arg = args.first()
        when (arg) {
            "--version",
            "-version" -> {
                Version.versionPrint()
            }
        }
        return
    }

    CreateFactory.execute()
}
