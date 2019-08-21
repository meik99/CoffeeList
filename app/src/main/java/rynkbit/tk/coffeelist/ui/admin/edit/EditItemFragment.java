package rynkbit.tk.coffeelist.ui.admin.edit;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.ui.admin.edit.binding.item.EditNameTextWatcher;
import rynkbit.tk.coffeelist.ui.admin.edit.binding.item.EditPriceListener;
import rynkbit.tk.coffeelist.ui.admin.edit.binding.item.EditStockListener;
import rynkbit.tk.coffeelist.ui.admin.edit.mvc.EditNamedObjectActivity;
import rynkbit.tk.coffeelist.old.db.entity.Item;


public class EditItemFragment extends Fragment {
    private Item mItem;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        mItem = args.getParcelable(EditNamedObjectActivity.EXTRA_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater
                .inflate(R.layout.fragment_edit_item, container, false);

        EditText editItemName = (EditText) fragmentView.findViewById(R.id.editItemname);
        EditText editStock = (EditText) fragmentView.findViewById(R.id.editStock);
        EditText editPrice = (EditText) fragmentView.findViewById(R.id.editPrice);

        if(savedInstanceState != null) {
            Item item =
                    savedInstanceState
                            .getParcelable(
                                    EditNamedObjectActivity.EXTRA_USER);
            mItem = item;
        }
        if(mItem != null){
            editItemName.setText(mItem.getName());
            editStock.setText(String.valueOf(mItem.getStock()));
            editPrice.setText(String.valueOf(mItem.getPrice()));

            editItemName.addTextChangedListener(
                    new EditNameTextWatcher(fragmentView.getContext(), mItem)
            );
            editPrice.addTextChangedListener(
                    new EditPriceListener(fragmentView.getContext(), mItem)
            );
            editStock.addTextChangedListener(
                    new EditStockListener(fragmentView.getContext(), mItem)
            );
        }

        return fragmentView;
    }
}
