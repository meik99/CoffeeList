package rynkbit.tk.coffeelist.ui.admin.item;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.ui.admin.edit.mvc.EditNamedObjectActivity;
import rynkbit.tk.coffeelist.old.db.entity.Item;
import rynkbit.tk.coffeelist.old.db.facade.ItemsFacade;

/**
 * Created by michael on 11/14/16.
 */
public class ManageItemsController {
    private ManageItemsActivity mActivity;
    private ManageItemsAdapter listAdapter;

    public ManageItemsController(ManageItemsActivity manageItemsActivity) {
        mActivity = manageItemsActivity;
        listAdapter = new ManageItemsAdapter(this);
    }

    public void refreshListAdapter(boolean reverted){
        listAdapter.setItems(ItemsFacade.getItems(mActivity, reverted));
    }

    public void refreshListAdapter(){
        refreshListAdapter(true);
    }

    public RecyclerView.Adapter getListAdapter() {
        return listAdapter;
    }

    public void changePrice(final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        final View view =
                LayoutInflater
                        .from(mActivity)
                        .inflate(R.layout.item_change_number, null, false);
        final EditText txtNumber = (EditText) view.findViewById(R.id.txtItemNumber);
        txtNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        txtNumber.setText(new String());

        builder.setTitle(R.string.change_price);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                double price  = Double.parseDouble(txtNumber.getText().toString());

                item.setPrice(price);
                ItemsFacade.update(view.getContext(), item);
                refreshListAdapter();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void changeStock(final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        final View view =
                LayoutInflater
                        .from(mActivity)
                        .inflate(R.layout.item_change_number, null, false);
        final EditText txtNumber = (EditText) view.findViewById(R.id.txtItemNumber);
        txtNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtNumber.setText(new String());

        builder.setTitle(R.string.change_stock);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int stock = Integer.parseInt(txtNumber.getText().toString());

                item.setStock(stock);
                ItemsFacade.update(view.getContext(), item);
                refreshListAdapter();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void renameItem(final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        final View view =
                LayoutInflater
                        .from(mActivity)
                        .inflate(R.layout.manage_rename, null, false);
        final EditText txtName = (EditText) view.findViewById(R.id.txtRenameName);
        txtName.setText(item.getName());

        builder.setTitle(R.string.rename);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = txtName.getText().toString();

                item.setName(name);
                ItemsFacade.update(view.getContext(), item);
                refreshListAdapter();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void removeItem(final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        builder.setTitle(R.string.remove);
        builder.setMessage(String.format(
                mActivity.getString(R.string.remove_item_confirmation),
                item.getName()
        ));

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ItemsFacade.remove(mActivity, item);
                refreshListAdapter();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        final View view =
                LayoutInflater
                        .from(mActivity)
                        .inflate(R.layout.manage_rename, null, false);
        final EditText txtName = (EditText) view.findViewById(R.id.txtRenameName);

        builder.setTitle(R.string.add);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = txtName.getText().toString();

                Item item = new Item();
                item.setName(name);

                ItemsFacade.insert(mActivity, item);
                refreshListAdapter(true);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void editItems(Item item) {
        Intent intent = new Intent(mActivity, EditNamedObjectActivity.class);
        intent.putExtra(EditNamedObjectActivity.EXTRA_ITEM, (Parcelable) item);
        mActivity.startActivity(intent);
    }
}
