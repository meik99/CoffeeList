package rynkbit.tk.coffeelist.admin.user.advanced.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.admin.user.advanced.mvc.EditUserController;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/25/17.
 */

public class EditUserListAdapter extends RecyclerView.Adapter{
    private final EditUserController mController;
    private List<User> mUserList;

    private class ViewHolder extends RecyclerView.ViewHolder{
        final TextView username;
        final TextView balance;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(android.R.id.text1);
            balance = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }

    public EditUserListAdapter(EditUserController controller,
                               List<User> userList){
        mUserList = userList;
        mController = controller;
    }

    public void setUserList(List<User> userList){
        mUserList = userList;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);

        return new EditUserListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mUserList != null && mUserList.size() > position){
            final int userPosition = position;
            EditUserListAdapter.ViewHolder userHolder = (ViewHolder) holder;

            userHolder.username.setText(mUserList.get(position).getName());
            userHolder.username.setTextColor(Color.WHITE);
            userHolder.balance.setText(
               String.format(
                       holder.itemView.getResources().getString(R.string.user_balance),
                       mUserList.get(position).getBalance()
               )
            );
            userHolder.balance.setTextColor(Color.WHITE);
            userHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mController.updateUsers();
                    mController.setCurrentUser(mUserList.get(userPosition));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mUserList != null ? mUserList.size() : 0;
    }
}
