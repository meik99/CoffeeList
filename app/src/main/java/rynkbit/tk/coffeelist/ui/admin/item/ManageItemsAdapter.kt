package rynkbit.tk.coffeelist.ui.admin.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.ui.entity.UIItem

class ManageItemsAdapter(
        private val onUpdateItemName: ((UIItem) -> Unit)?,
        private val onUpdateItemPrice: ((UIItem) -> Unit)?,
        private val onUpdateItemStock: ((UIItem) -> Unit)?,
        private val onRemoveItem: ((item: UIItem) -> Unit)?
) : RecyclerView.Adapter<ManageItemsAdapter.ViewHolder>() {
    private val items = mutableListOf<UIItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.edit_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply{
            txtId.text = item.id.toString()
            editName.setText(item.name)
            editPrice.setText(item.price.toString())
            editStock.setText(item.stock.toString())

            editName.addTextChangedListener { s -> onUpdateItemName?.apply {
                this(UIItem(item.id, s.toString(), item.price, item.stock)) }
            }
            editPrice.addTextChangedListener { onUpdateItemPrice?.apply {
                val price = editPrice.text.toString().toDoubleOrNull()
                this(UIItem(item.id, item.name, price ?: item.price, item.stock)) }
            }
            editStock.addTextChangedListener { onUpdateItemStock?.apply {
                val stock = editStock.text.toString().toIntOrNull()
                this(UIItem(item.id, item.name, item.price, stock ?: item.stock)) }
            }
            btnRemove.setOnClickListener {
                onRemoveItem?.apply { this(item) }
            }
        }
    }

    fun updateItems(itemList: List<UIItem>) {
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtId: TextView = itemView.findViewById(R.id.txtId)
        val editName: EditText = itemView.findViewById(R.id.editName)
        val editPrice: EditText = itemView.findViewById(R.id.editPrice)
        val editStock: EditText = itemView.findViewById(R.id.editStock)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemoveItem)
    }
}