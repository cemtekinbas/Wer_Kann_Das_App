package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.SettingService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserSettingMapperService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Setting {
    private int pk;
    private String name;
    private String value;

    public Setting(int pk, String name) {
        this.pk = pk;
        this.name = name;
    }

    public Setting(int pk, String name, String value) {
        this.pk = pk;
        this.name = name;
        this.value = value;
    }

    public Setting(ResultSet results) throws SQLException {
        this.pk = results.getInt(SettingService.colPk);
        this.name = results.getString(SettingService.colName);
        try {
            this.value = results.getString(UserSettingMapperService.colSettingValue);
        } catch (SQLException ignore){}
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
