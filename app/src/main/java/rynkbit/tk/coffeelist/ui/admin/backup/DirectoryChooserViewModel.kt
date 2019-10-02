package rynkbit.tk.coffeelist.ui.admin.backup

import androidx.lifecycle.ViewModel
import java.util.*

class DirectoryChooserViewModel: ViewModel(){
    private var currentPath = Stack<String>()

    fun back() {
        if (currentPath.size > 0){
            currentPath.pop()
        }
    }

    fun appendPath(directory: String){
        var directoryName = directory.trim()

        while(directoryName.startsWith("/")) directoryName = directoryName.removePrefix("/")
        while(directoryName.endsWith("/")) directoryName = directoryName.removeSuffix("/")

        currentPath.push(directory)
    }

    fun isRoot(): Boolean {
        return currentPath.isEmpty()
    }
}