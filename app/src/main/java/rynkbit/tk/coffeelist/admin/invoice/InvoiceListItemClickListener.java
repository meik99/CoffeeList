package rynkbit.tk.coffeelist.admin.invoice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.entity.Invoice;

/**
 * Created by michael on 11/19/16.
 */
public class InvoiceListItemClickListener implements View.OnClickListener {
    InvoiceController mController;

    public InvoiceListItemClickListener(InvoiceController controller) {
        mController = controller;
    }

    @Override
    public void onClick(View view) {
        final Invoice invoice = (Invoice) view.getTag();

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if(invoice.isReverted() == false) {
            builder.setTitle(R.string.invoice_delete);
            builder.setMessage(String.format(
                    view.getContext().getString(R.string.really_delete_invoice),
                    invoice.getItem().getName(),
                    invoice.getItem().getPrice(),
                    invoice.getUser().getName()
            ));
        }else{
            builder.setTitle(R.string.invoice_reverted_delete);
            builder.setMessage(String.format(
                    view.getContext().getString(R.string.really_delete_invoice_reverted),
                    invoice.getItem().getName(),
                    invoice.getItem().getPrice(),
                    invoice.getUser().getName()
            ));
        }
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mController.deleteInvoice(invoice);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
