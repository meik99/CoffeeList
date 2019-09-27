package rynkbit.tk.coffeelist.ui.admin.item

import androidx.lifecycle.ViewModel
import rynkbit.tk.coffeelist.db.facade.ItemFacade

class ManageItemsViewModel : ViewModel() {
    val itemsFacade = ItemFacade()
}
