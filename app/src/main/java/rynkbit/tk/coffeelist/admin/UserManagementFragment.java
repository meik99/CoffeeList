package rynkbit.tk.coffeelist.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rynkbit.tk.coffeelist.R;

/**
 * Created by michael on 1/11/17.
 */

public class UserManagementFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_user_management, container, false);
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
