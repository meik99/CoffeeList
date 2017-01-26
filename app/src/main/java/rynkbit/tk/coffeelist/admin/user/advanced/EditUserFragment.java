package rynkbit.tk.coffeelist.admin.user.advanced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.admin.user.advanced.binding.EditBalanceTextWatcher;
import rynkbit.tk.coffeelist.admin.user.advanced.binding.EditNameTextWatcher;
import rynkbit.tk.coffeelist.admin.user.advanced.mvc.EditUserActivity;
import rynkbit.tk.coffeelist.admin.user.advanced.mvc.EditUserController;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditUserFragment extends Fragment {

    private User mUser;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        mUser = args.getParcelable(EditUserActivity.USER_EXTRA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater
                .inflate(R.layout.fragment_edit_user, container, false);

        EditText editUsername = (EditText) fragmentView.findViewById(R.id.editUsername);
        EditText editBalance = (EditText) fragmentView.findViewById(R.id.editBalance);

        if(savedInstanceState != null) {
            User user =
                    savedInstanceState
                            .getParcelable(
                                    EditUserActivity.USER_EXTRA);
            mUser = user;
        }
        if(mUser != null){
            editUsername.setText(mUser.getName());
            editBalance.setText(String.valueOf(mUser.getBalance()));

            editUsername.addTextChangedListener(
                    new EditNameTextWatcher(fragmentView.getContext(), mUser)
            );
            editBalance.addTextChangedListener(
                    new EditBalanceTextWatcher(fragmentView.getContext(), mUser)
            );
        }

        return fragmentView;
    }
}
