package rynkbit.tk.coffeelist.ui.admin.backup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DirectoryChooserAdapter: RecyclerView.Adapter<DirectoryChooserAdapter.ViewHolder>() {
    private val items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(android.R.layout.simple_list_item_1, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtItem.text = items[position]
    }

    fun add(value: String){
        items.add(value)
    }

    fun addAll(vararg values: String){
        items.addAll(values)
    }

    fun clear(){
        items.clear()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem: TextView = itemView.findViewById(android.R.id.text1)
    }
}