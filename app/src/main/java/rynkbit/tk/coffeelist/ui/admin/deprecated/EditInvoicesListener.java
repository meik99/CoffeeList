package rynkbit.tk.coffeelist.ui.admin.deprecated;

import android.content.Intent;
import android.view.View;

import rynkbit.tk.coffeelist.ui.admin.invoice.InvoiceActivity;

/**
 * Created by michael on 11/19/16.
 */
public class EditInvoicesListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), InvoiceActivity.class);
        view.getContext().startActivity(intent);
    }
}
