package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.AchievementService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Achievement {
    private int pk;
    private String name;
    private String description;
    private String icon_path;
    private String unlock_condition;

    public Achievement(int pk, String name, String description, String icon_path, String unlock_condition) {
        this.pk = pk;
        this.name = name;
        this.description = description;
        this.icon_path = icon_path;
        this.unlock_condition = unlock_condition;
    }

    public Achievement(ResultSet results) throws SQLException {
        this.pk = results.getInt(AchievementService.colPk);
        this.name = results.getString(AchievementService.colName);
        this.description = results.getString(AchievementService.colDescription);
        this.icon_path = results.getString(AchievementService.colIconPath);
        this.unlock_condition = results.getString(AchievementService.colUnlockCondition);
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
