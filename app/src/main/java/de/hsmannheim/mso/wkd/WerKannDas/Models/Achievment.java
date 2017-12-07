package de.hsmannheim.mso.wkd.WerKannDas.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Achievment {
    private int pk;
    private String name;
    private String description;
    private String icon_path;
    private String unlock_condition;

    public Achievment(int pk, String name, String description, String icon_path, String unlock_condition) {
        this.pk = pk;
        this.name = name;
        this.description = description;
        this.icon_path = icon_path;
        this.unlock_condition = unlock_condition;
    }

    public Achievment (ResultSet results) throws SQLException {
        this.pk = results.getInt("pk");
        this.name = results.getString("name");
        this.description = results.getString("description");
        this.icon_path = results.getString("icon_path");
        this.unlock_condition = results.getString("unlock_condition");
    }


    public int getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public String getUnlock_condition() {
        return unlock_condition;
    }
}
