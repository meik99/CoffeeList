package rynkbit.tk.coffeelist.admin.user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.admin.edit.ManageUserAdvancedClickListener;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 11/13/16.
 */
public class ManageUserAdapter extends RecyclerView.Adapter {
    private List<User> mUsers = new LinkedList<>();
    private ManageUserController mManageUserController;

    public ManageUserAdapter(ManageUserController controller){
        mManageUserController = controller;
    }

    public void setUsers(List<User> users){
        mUsers = users;
        this.notifyDataSetChanged();
    }

    public class ManageUserHolder extends RecyclerView.ViewHolder{
        View view;
        TextView txtUsername;
        TextView txtBalance;

        public ManageUserHolder(View itemView) {
            super(itemView);
            view = itemView;
            txtUsername = (TextView) view.findViewById(R.id.manage_list_item_title);
            txtBalance = (TextView) view.findViewById(R.id.manage_list_item_detail);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_manage_list_item, parent, false);
        ManageUserHolder holder = new ManageUserHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = mUsers.get(position);
        ManageUserHolder manageUserHolder = (ManageUserHolder) holder;
        ImageButton btnUserMenu =
                (ImageButton) manageUserHolder.view.findViewById(R.id.btnListItem);

        manageUserHolder.txtUsername.setText(user.getName());
        manageUserHolder.txtBalance.setText(String.format(
                ((ManageUserHolder) holder).view.getContext().getString(R.string.user_balance),
                user.getBalance()
        ));
        manageUserHolder.view.setOnClickListener(new ManageUserAdvancedClickListener());
        manageUserHolder.view.setTag(user);

        btnUserMenu.setOnClickListener(new ManageUserMenuClickListener(mManageUserController));
        btnUserMenu.setTag(user);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
