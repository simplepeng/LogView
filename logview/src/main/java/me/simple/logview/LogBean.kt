package me.simple.logview

data class LogBean(
        val priority: String,
        val tag: String,
        val time: String,
        val msg: String
) {
    companion object {
        const val VERBOSE = "V"
        const val DEBUG = "D"
        const val INFO = "I"
        const val WARN = "W"
        const val ERROR = "E"
    }
}