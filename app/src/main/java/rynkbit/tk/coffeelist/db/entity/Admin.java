package rynkbit.tk.coffeelist.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by michael on 11/13/16.
 */
@DatabaseTable
public class Admin {
    public static final String ADMIN_STANDARD_PASSWORD = "";

    @DatabaseField(dataType = DataType.STRING)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
