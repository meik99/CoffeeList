package rynkbit.tk.coffeelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/13/17.
 */

public class UserRecyclerViewAdapter extends RecyclerView.Adapter{

        private List<User> users = null;

        public UserRecyclerViewAdapter(){
            users = new LinkedList<>();
        }

        public void setUsers(List<User> users) {
            this.users = users;
            this.notifyDataSetChanged();
        }

        public class UserCard extends RecyclerView.ViewHolder{
            public TextView txtUsername;
            public TextView txtUserbalance;
            public View view;

            public UserCard(View itemView) {
                super(itemView);

                txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
                txtUserbalance = (TextView) itemView.findViewById(R.id.txtUserbalance);
                view = itemView;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View userCard = View.inflate(parent.getContext(), R.layout.user_card, null);
            UserCard userCardHolder = new UserCard(userCard);
            return userCardHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            User user = users.get(position);
            UserCard userCard = (UserCard) holder;

            userCard.view.setOnClickListener(
                    new UserClickListener(user)
            );
            userCard.txtUsername.setText(user.getName());
            userCard.txtUserbalance.setText(
                    String.format(
                            userCard.txtUserbalance.getContext().getString(R.string.user_balance),
                            user.getBalance()
                    )
            );
        }

        @Override
        public int getItemCount() {
            return users.size();
        }


}
