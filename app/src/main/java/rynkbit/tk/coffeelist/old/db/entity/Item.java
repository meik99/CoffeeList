package rynkbit.tk.coffeelist.old.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import rynkbit.tk.coffeelist.old.db.contract.NamedEntity;

/**
 * Created by michael on 13/11/16.
 */
@DatabaseTable
public class Item implements Serializable, NamedEntity, Parcelable{
    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private double price;
    @DatabaseField
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static final Parcelable.Creator<Item> CREATOR =
            new Creator<Item>() {
                @Override
                public Item createFromParcel(Parcel parcel) {
                    return new Item(parcel);
                }

                @Override
                public Item[] newArray(int i) {
                    return new Item[i];
                }
            };

    public Item(){}
    public Item(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
        price = parcel.readDouble();
        stock = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(stock);
    }
}
