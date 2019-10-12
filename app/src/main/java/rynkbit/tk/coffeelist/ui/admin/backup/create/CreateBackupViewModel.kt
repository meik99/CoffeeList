package rynkbit.tk.coffeelist.ui.admin.backup.create

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.nio.charset.Charset

class CreateBackupViewModel : ViewModel(){
    val currentUri: MutableLiveData<Uri> = MutableLiveData()
    val backupData: ByteArray
        get() {
            val builder = StringBuilder()
            builder.append("-1\n")
            builder.append("#Customer\n")
            return builder.toString().toByteArray(Charset.defaultCharset())
        }
}