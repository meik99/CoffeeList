package rynkbit.tk.coffeelist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/13/17.
 */

public class UserCard {
    private User mUser;

    public UserCard(User user){
        mUser = user;
    }

    public View createView(Context context, @Nullable ViewGroup parent){
        View userCard = LayoutInflater
                .from(context)
                .inflate(R.layout.user_card, parent, false);
        TextView username = (TextView) userCard.findViewById(R.id.txtUsername);
        TextView balance = (TextView) userCard.findViewById(R.id.txtUserbalance);

        username.setText(
                mUser.getName()
        );
        balance.setText(
                userCard.getContext().getString(R.string.user_balance, mUser.getBalance())
        );
        username.setPadding(5, 0, 5, 0);
        balance.setPadding(5, 0, 5, 0);
        userCard.setOnClickListener(new UserClickListener(mUser));

        return userCard;
    }
}
