package rynkbit.tk.coffeelist.ui.item

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Item

/**
 * Created by michael on 13/11/16.
 */
class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var items = mutableListOf<Item>()

    var onClickListener: (() -> Unit)? = null

    fun updateItems(itemList: List<Item>) {
        items.clear()
        items.addAll(itemList)
        this.notifyDataSetChanged()
    }

    class ViewHolder(internal var view: View) : RecyclerView.ViewHolder(view) {
        internal var txtItemName: TextView = view.findViewById<View>(R.id.txtItemName) as TextView
        internal var txtItemPrice: TextView = view.findViewById<View>(R.id.txtItemPrice) as TextView
        internal var txtItemStock: TextView = view.findViewById<View>(R.id.txtItemStock) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val itemView = View.inflate(parent.context, R.layout.item_card, null)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.txtItemName.text = item.name
        holder.txtItemStock.text = String.format(
                holder.view.context.getString(R.string.item_stock),
                item.stock
        )

        holder.txtItemPrice.text = String.format(
                holder.view.context.getString(R.string.item_price),
                item.price
        )

        holder.view.setOnClickListener {
            onClickListener?.apply { this() }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
