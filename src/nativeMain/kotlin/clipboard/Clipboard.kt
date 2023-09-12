package clipboard

import platform.AppKit.NSPasteboard
import platform.AppKit.NSPasteboardTypeString

object Clipboard {
    fun writeToClipBoard(content: String): Boolean {
        val nsPasteboard = NSPasteboard.generalPasteboard()
        nsPasteboard.clearContents()
        return nsPasteboard.setString(content, NSPasteboardTypeString)
    }
}