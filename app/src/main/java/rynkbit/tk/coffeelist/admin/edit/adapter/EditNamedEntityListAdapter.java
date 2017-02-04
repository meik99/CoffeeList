package rynkbit.tk.coffeelist.admin.edit.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.admin.edit.mvc.EditNamedObjectController;
import rynkbit.tk.coffeelist.db.contract.NamedEntity;
import rynkbit.tk.coffeelist.db.entity.Item;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/25/17.
 */

public class EditNamedEntityListAdapter extends RecyclerView.Adapter{
    private final EditNamedObjectController mController;
    private List<NamedEntity> mNamedEntities;

    private class ViewHolder extends RecyclerView.ViewHolder{
        final TextView username;
        final TextView info;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(android.R.id.text1);
            info = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }

    public EditNamedEntityListAdapter(EditNamedObjectController controller,
                                      List<NamedEntity> namedEntities){
        mNamedEntities = namedEntities;
        mController = controller;
    }

    public void setNamedEntities(List<NamedEntity> namedEntities){
        mNamedEntities = namedEntities;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);

        return new EditNamedEntityListAdapter.ViewHolder(v);
    }

    public void updateList(){
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mNamedEntities != null && mNamedEntities.size() > position){
            final int entityPosition = position;
            final NamedEntity entity = mNamedEntities.get(position);

            EditNamedEntityListAdapter.ViewHolder userHolder = (ViewHolder) holder;

            userHolder.username.setText(mNamedEntities.get(position).getName());
            userHolder.username.setTextColor(Color.WHITE);
            userHolder.info.setTextColor(Color.WHITE);

            if(entity instanceof User){
                User user = (User)entity;

                userHolder.info.setText(
                   String.format(
                           holder.itemView.getResources().getString(R.string.user_balance),
                           user.getBalance()
                   )
                );
                userHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mController.updateObjects(User.class);
                        mController.setCurrentObject(entity);
                    }
                });
            }else{
                Item item = (Item)entity;
                userHolder.info.setText(
                        String.format(
                                holder.itemView.getResources().getString(R.string.item_detail),
                                item.getPrice(),
                                item.getStock()
                        )
                );
                userHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mController.updateObjects(Item.class);
                        mController.setCurrentObject(entity);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNamedEntities != null ? mNamedEntities.size() : 0;
    }
}
