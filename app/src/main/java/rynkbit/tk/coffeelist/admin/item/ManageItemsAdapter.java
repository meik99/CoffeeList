package rynkbit.tk.coffeelist.admin.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.entity.Item;

/**
 * Created by michael on 11/14/16.
 */
public class ManageItemsAdapter extends RecyclerView.Adapter {
    private ManageItemsController mController;
    private List<Item> items;

    public ManageItemsAdapter(ManageItemsController controller) {
        mController = controller;
        items = new LinkedList<>();
    }

    public void setItems(List<Item> items){
        if(items != null) {
            this.items = items;
            this.notifyDataSetChanged();
        }
    }

    public class ManageItemsHolder extends RecyclerView.ViewHolder{
        View view;
        TextView itemName;
        TextView itemDetail;

        ImageButton itemMenuButton;

        public ManageItemsHolder(View itemView) {
            super(itemView);

            view = itemView;
            itemName = (TextView) itemView.findViewById(R.id.manage_list_item_title);
            itemDetail = (TextView) itemView.findViewById(R.id.manage_list_item_detail);
            itemMenuButton = (ImageButton) itemView.findViewById(R.id.btnListItem);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.admin_manage_list_item, parent, false);
        ManageItemsHolder holder = new ManageItemsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);
        ManageItemsHolder itemsHolder = (ManageItemsHolder) holder;

        itemsHolder.itemName.setText(item.getName());
        itemsHolder.itemDetail.setText(
                String.format(itemsHolder.view.getContext().getString(
                        R.string.item_detail)
                    , item.getPrice(), item.getStock())
        );
        itemsHolder.itemMenuButton.setOnClickListener(
                new ManageItemsMenuClickListener(mController, item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
