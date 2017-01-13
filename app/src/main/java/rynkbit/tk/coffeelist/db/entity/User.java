package rynkbit.tk.coffeelist.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by michael on 13/11/16.
 */
@DatabaseTable
public class User implements Parcelable, Comparable, Serializable{
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private double balance;

    public User(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        balance = parcel.readDouble();
    }

    public User() {

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(balance);
    }

    public static final Parcelable.Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel parcel) {
                    return new User(parcel);
                }

                @Override
                public Object[] newArray(int i) {
                    return new User[i];
                }
            };

    @Override
    public int compareTo(Object o) {
        if(o instanceof User){
            User u = (User)o;
            return this.getName().compareTo(u.getName());
        }
        throw new IllegalArgumentException("Object not of type User");
    }
}
