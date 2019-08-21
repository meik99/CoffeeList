package rynkbit.tk.coffeelist.old.db.facade;

import android.content.Context;
import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.old.db.DbHelper;
import rynkbit.tk.coffeelist.old.db.entity.Invoice;
import rynkbit.tk.coffeelist.old.db.entity.Item;
import rynkbit.tk.coffeelist.old.db.entity.User;

/**
 * Created by michael on 11/19/16.
 */
public class InvoiceFacade {
    private static AsyncTask<Void, Void, Void> mAsnycTask;

    public interface InvoiceFacadeListener{
        void onInvoicesReady(List<Invoice> invoices);
    }

    private static List<InvoiceFacadeListener> mInvoiceListeners = new LinkedList<>();

    public static void addInvoiceListener(InvoiceFacadeListener listener){
        if(listener != null){
            mInvoiceListeners.add(listener);
        }
    }

    public static void removeInvoiceListener(InvoiceFacadeListener listener){
        if(listener != null){
            mInvoiceListeners.remove(listener);
        }
    }

    public static void book(Context context, User user, Item item) {
        Dao<Invoice, Integer> dao = getInvoiceDao(context);
        int itemStock = item.getStock();
        double userBalance = user.getBalance();

        Invoice invoice = new Invoice();
        invoice.setItem(item);
        invoice.setUser(user);

        if(dao == null){
            throw new RuntimeException("Database error");
        }

        try {
            item.setStock(itemStock-1);
            user.setBalance(userBalance+item.getPrice());

            dao.create(invoice);

            UserFacade.update(context, user);
            ItemsFacade.update(context, item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        appendProtocol(context, invoice);
    }

    private static void appendProtocol(Context context, Invoice invoice){
        Item item = invoice.getItem();
        User user = invoice.getUser();

        StringBuilder builder = new StringBuilder();
        builder
                .append(invoice.getDate())
                .append(";")
                .append(user.getId())
                .append(";")
                .append(user.getName())
                .append(";")
                .append(user.getBalance() - item.getPrice())
                .append(";")
                .append(user.getBalance())
                .append(";")
                .append(item.getId())
                .append(";")
                .append(item.getName())
                .append(";")
                .append(item.getPrice())
                .append(";")
                .append(item.getStock()+1)
                .append(";")
                .append(item.getStock());

        ProtocolFacade.writeLine(context, builder.toString());
    }

    private static Dao<Invoice, Integer> getInvoiceDao(Context context){
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao<Invoice, Integer> result = null;

        try {
            result = dbHelper.getDao(Invoice.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void getInvoices(final Context context) {
        if(mAsnycTask != null){
            mAsnycTask.cancel(true);
        }

        mAsnycTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                List<Invoice> result = new LinkedList<>();
                Dao<Invoice, Integer> invoiceDao = getInvoiceDao(context);

                try {
                    result = invoiceDao.queryForAll();
//                    result = Sort.bubble(result, true);
                    Collections
                    .sort(result, new Comparator<Invoice>() {
                        @Override
                        public int compare(Invoice invoice, Invoice t1) {
                            return t1.compareTo(invoice);
                        }
                    });

                    for (InvoiceFacadeListener listener :
                            mInvoiceListeners) {
                        listener.onInvoicesReady(result);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
        mAsnycTask.execute();
    }

    public static void remove(Context context, Invoice invoice) {
        Dao<Invoice, Integer> result = getInvoiceDao(context);
        User user = invoice.getUser();
        Item item = invoice.getItem();
        Invoice revertedInvoice = new Invoice();

        if(invoice.isReverted() == false) {
            user.setBalance(user.getBalance() - item.getPrice());
            item.setStock(item.getStock() + 1);
        }else{
            user.setBalance(user.getBalance() + item.getPrice());
            item.setStock(item.getStock() - 1);
        }

        revertedInvoice.setUser(user);
        revertedInvoice.setItem(item);
        revertedInvoice.setReverted(invoice.isReverted() == false);

        try {
            result.create(revertedInvoice);
            result.delete(invoice);
            UserFacade.update(context, user);
            ItemsFacade.update(context, item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        appendProtocol(context, invoice);
    }
}
