package rynkbit.tk.coffeelist;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 13/11/16.
 */

public class UserAdapter extends BaseAdapter{
    private List<User> mUsers;
    private GridView mUserGrid;

    public UserAdapter(GridView userGrid){
        mUsers = new LinkedList<>();
        mUserGrid = userGrid;
    }

    public void setUser(List<User> mUsers) {
        this.mUsers = mUsers;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mUsers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = mUsers.get(position);
        UserCard userCard = new UserCard(user);
        return userCard.createView(parent.getContext(), parent);
    }

//    @Override
//    public boolean isEmpty() {
//        return mUsers.size() <= 0;
//    }
}
