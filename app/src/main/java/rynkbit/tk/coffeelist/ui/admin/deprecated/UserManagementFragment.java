package rynkbit.tk.coffeelist.ui.admin.deprecated;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        Button btnManageUser = (Button) view.findViewById(R.id.btnAdminManageCustomer);
        Button btnManageItems = (Button) view.findViewById(R.id.btnAdminManageItems);
        Button btnEditInvoices = (Button) view.findViewById(R.id.btnAdminManageInvoices);

        btnManageUser.setOnClickListener(new ManageUserClickListener());
        btnManageItems.setOnClickListener(new ManageItemsClickListener());
        btnEditInvoices.setOnClickListener(new EditInvoicesListener());

        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
