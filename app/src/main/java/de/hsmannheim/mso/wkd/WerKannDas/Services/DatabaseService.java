package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseService {

    private static int currentVersion = 2;


    public static String table = "database_version";
    public static String colVersion = "version";
    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " ( " +
            colVersion + " INTEGER NOT NULL);";


    private static String queryGet = "SELECT " + colVersion + " FROM " + table;
    private static String queryAdd = "INSERT INTO " + table + " (" + colVersion + ") VALUES (?)";

    public void checkVersion(DriverManagerDataSource ds, PasswordEncoder bcryptPasswordEncoder) {
        int version = 0;
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryGet);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                version = results.getInt(colVersion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (version < currentVersion) {
            dropTables(ds);
            createTables(ds);
            insertDefaults(ds, bcryptPasswordEncoder);
        }

    }

    private void createTables(DriverManagerDataSource ds) {
        try {
            ds.getConnection().createStatement().execute(DatabaseService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(UserService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(UserService.schemaAuthorities);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(AchievementService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(UserAchievementMapperService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(SettingService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(UserSettingMapperService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(RequestService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(RequestResponseService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(RequestTagService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(RequestTagMapperService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute(ChatService.schema);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertDefaults(DriverManagerDataSource ds, PasswordEncoder bcryptPasswordEncoder) {
        try {
            User admin = new User(0, "admin@mail.de", "admin", "", "password", "password", "Admin", "Admin", "", "", Date.valueOf(LocalDate.of(1, 1, 1)));
            PreparedStatement pstmt = null;
            pstmt = ds.getConnection().prepareStatement(UserService.queryAdd, Statement.RETURN_GENERATED_KEYS);
            String mail = admin.getMail();
            String userName = admin.getUser_name();
            String password = bcryptPasswordEncoder.encode(admin.getPasswordClear());
            String forename = admin.getForename();
            String surname = admin.getSurname();
            Date birthday = admin.getBirthday();
            String plz = admin.getPlz();
            String village = admin.getVillage();
            String streetWithNumber = admin.getStreet_with_number();
            pstmt.setString(1, mail);
            pstmt.setString(2, userName);
            pstmt.setString(3, password);
            pstmt.setString(4, forename);
            pstmt.setString(5, surname);
            pstmt.setDate(6, birthday);
            pstmt.setString(7, plz);
            pstmt.setString(8, village);
            pstmt.setString(9, streetWithNumber);
            pstmt.setBoolean(10, true);
            pstmt.execute();
            pstmt = ds.getConnection().prepareStatement("INSERT INTO " + UserService.tableAuthorities + " VALUES(?,?)", Statement.NO_GENERATED_KEYS);
            pstmt.setString(1, userName);
            pstmt.setString(2, "USER");
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement pstmt = null;
            pstmt = ds.getConnection().prepareStatement(DatabaseService.queryAdd, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, currentVersion);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTables(DriverManagerDataSource ds) {
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + UserAchievementMapperService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + UserSettingMapperService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + RequestTagMapperService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + RequestTagService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + RequestResponseService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + ChatService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + SettingService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + AchievementService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + UserService.tableAuthorities);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + RequestService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + UserService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ds.getConnection().createStatement().execute("DROP TABLE " + DatabaseService.table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
