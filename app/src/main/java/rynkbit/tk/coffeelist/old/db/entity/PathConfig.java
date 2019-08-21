package rynkbit.tk.coffeelist.old.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;

/**
 * Created by michael on 11/15/16.
 */
@DatabaseTable
public class PathConfig {
    @DatabaseField
    private String path;
    @DatabaseField(unique = true)
    private PathType pathType;

    public PathType getPathType() {
        return pathType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path, PathType pathType) {
        this.path = path + File.separator + "protocol.csv";
        this.pathType = pathType;
    }
}
