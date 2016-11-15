package rynkbit.tk.coffeelist.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;

/**
 * Created by michael on 11/15/16.
 */
@DatabaseTable
public class Protocol {
    @DatabaseField
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path + File.separator + "protocol.csv";
    }
}
