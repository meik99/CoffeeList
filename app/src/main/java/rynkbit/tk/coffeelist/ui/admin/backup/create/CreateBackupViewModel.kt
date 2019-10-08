package rynkbit.tk.coffeelist.ui.admin.backup.create

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateBackupViewModel : ViewModel(){
    val currentUri: MutableLiveData<Uri> = MutableLiveData()
}