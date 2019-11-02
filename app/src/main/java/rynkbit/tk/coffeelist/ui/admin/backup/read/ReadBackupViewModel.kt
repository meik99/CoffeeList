package rynkbit.tk.coffeelist.ui.admin.backup.read

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReadBackupViewModel: ViewModel(){
    val currentUri: MutableLiveData<Uri> = MutableLiveData()
}