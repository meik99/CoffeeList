package rynkbit.tk.coffeelist.admin.invoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rynkbit.tk.coffeelist.R;

public class InvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        InvoiceController controller = new InvoiceController(this);

        RecyclerView invoiceView = (RecyclerView) findViewById(R.id.invoiceView);
        invoiceView.setLayoutManager(new LinearLayoutManager(this));
        invoiceView.setAdapter(controller.getListAdapter());
    }
}
