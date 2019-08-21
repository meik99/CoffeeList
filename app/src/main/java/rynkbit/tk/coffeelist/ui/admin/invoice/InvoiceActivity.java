package rynkbit.tk.coffeelist.ui.admin.invoice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
