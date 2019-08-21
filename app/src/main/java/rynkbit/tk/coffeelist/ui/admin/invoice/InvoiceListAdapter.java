package rynkbit.tk.coffeelist.ui.admin.invoice;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.old.db.entity.Invoice;

/**
 * Created by michael on 11/19/16.
 */
public class InvoiceListAdapter extends RecyclerView.Adapter{
    public class InvoiceHolder extends RecyclerView.ViewHolder{
        TextView invoiceDate;
        TextView invoiceDetail;
        View view;

        public InvoiceHolder(View itemView) {
            super(itemView);

            view = itemView;
            invoiceDate = (TextView) itemView.findViewById(R.id.txtInvoiceDate);
            invoiceDetail = (TextView) itemView.findViewById(R.id.txtInvoiceDetail);
        }
    }

    private List<Invoice> invoiceList = new LinkedList<>();
    private InvoiceController controller;

    public InvoiceListAdapter(InvoiceController controller) {
        this.controller = controller;
    }

    public void setInvoiceList(List<Invoice> invoiceList){
        this.invoiceList = invoiceList;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.invoice_list_element, parent, false);
        InvoiceHolder holder = new InvoiceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Invoice invoice = invoiceList.get(position);
        InvoiceHolder invoiceHolder = (InvoiceHolder)holder;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        invoiceHolder.invoiceDate.setText(sdf.format(invoice.getDate()));
        invoiceHolder.invoiceDetail.setText(invoice.getDetails(invoiceHolder.view.getContext()));
        invoiceHolder.view.setTag(invoice);
        invoiceHolder.view.setOnClickListener(new InvoiceListItemClickListener(controller));
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }
}
