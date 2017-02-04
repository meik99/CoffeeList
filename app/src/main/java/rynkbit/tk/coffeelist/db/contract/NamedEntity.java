package rynkbit.tk.coffeelist.db.contract;

import android.os.Parcelable;

/**
 * Created by michael on 1/26/17.
 */

public interface NamedEntity extends Parcelable {
    String getName();
    void setName(String name);
    int getId();
    void setId(int id);
}
