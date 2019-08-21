package rynkbit.tk.coffeelist.old.db.entity;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import rynkbit.tk.coffeelist.R;

/**
 * Created by michael on 11/19/16.
 */
@DatabaseTable(tableName = "DatabaseInvoice")
public class Invoice implements Comparable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Item item;
    @DatabaseField(canBeNull = false)
    private Date date;
    @DatabaseField
    private boolean reverted;

    public Invoice(){
        date = new Date();
        reverted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetails(Context context) {
        StringBuilder detailString = new StringBuilder();
        if(reverted == false) {
            detailString
                    .append(user.getName())
                    .append(" ")
                    .append(context.getString(R.string.bought))
                    .append(" ")
                    .append(item.getName())
                    .append(" ")
                    .append(context.getString(R.string.fors))
                    .append(" ")
                    .append(String.format("%1$.2f€", item.getPrice()));
        }else{
            detailString
                    .append(
                            String.format(
                                    context.getString(R.string.reverted),
                                    item.getName(),
                                    item.getPrice(),
                                    user.getName()));
        }
        return detailString.toString();
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Invoice == false){
            throw new IllegalArgumentException("Object not of type DatabaseInvoice");
        }
        Invoice other = (Invoice) o;

        return this.getDate().compareTo(other.getDate());
    }


    public void setReverted(boolean reverted) {
        this.reverted = reverted;
    }

    public boolean isReverted() {
        return reverted;
    }
}
