package rynkbit.tk.coffeelist.ui.admin.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.ui.entity.UIItem
import java.text.NumberFormat
import java.util.*

class ManageItemsAdapter : RecyclerView.Adapter<ManageItemsAdapter.ViewHolder>() {
    private val items = mutableListOf<UIItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.edit_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

        holder.apply{
            txtId.text = item.id.toString()
            editName.setText(item.name)
            editPrice.setText(currencyFormat.format(item.price))
            editStock.setText(item.stock)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtId: TextView = itemView.findViewById(R.id.txtId)
        val editName: EditText = itemView.findViewById(R.id.editName)
        val editPrice: EditText = itemView.findViewById(R.id.editPrice)
        val editStock: EditText = itemView.findViewById(R.id.editStock)
    }
}