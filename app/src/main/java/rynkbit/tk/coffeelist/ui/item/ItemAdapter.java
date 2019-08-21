package rynkbit.tk.coffeelist.ui.item;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.old.db.entity.Item;
import rynkbit.tk.coffeelist.old.db.entity.User;

/**
 * Created by michael on 13/11/16.
 */
public class ItemAdapter extends RecyclerView.Adapter {
    private User mUser;
    private List<Item> mItemList;
    private ItemController mItemController;

    public ItemAdapter(ItemController itemController, User user) {
        mUser = user;
        mItemList = new LinkedList<>();
        mItemController = itemController;
    }

    public void setItems(List<Item> items) {
        this.mItemList = items;
        this.notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        View view;
        TextView txtItemName;
        TextView txtItemPrice;
        TextView txtItemStock;

        public ItemHolder(View itemView) {
            super(itemView);

            view = itemView;
            txtItemName = (TextView) itemView.findViewById(R.id.txtItemName);
            txtItemPrice = (TextView) itemView.findViewById(R.id.txtItemPrice);
            txtItemStock = (TextView) itemView.findViewById(R.id.txtItemStock);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_card, null);
        ItemHolder itemHolder = new ItemHolder(itemView);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = mItemList.get(position);
        ItemHolder itemHolder = (ItemHolder)holder;

        itemHolder.txtItemName.setText(item.getName());
        itemHolder.txtItemStock.setText(
                String.format(
                        ((ItemHolder) holder).view.getContext().getString(R.string.item_stock),
                        item.getStock()
                ));

        itemHolder.txtItemPrice.setText(
                String.format(
                        ((ItemHolder) holder).view.getContext().getString(R.string.item_price),
                        item.getPrice()
                ));

        itemHolder.view.setOnClickListener(new ItemClickListener(mItemController, item));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
