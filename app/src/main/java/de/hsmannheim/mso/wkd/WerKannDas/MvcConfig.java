package de.hsmannheim.mso.wkd.WerKannDas;

import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@Configuration
@Import({WebSecurityConfig.class})
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/home").setViewName("home");
        //registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("home");
    }

    @Bean(name = "bcryptPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "ds")
    public DataSource ds() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        driverManagerDataSource.setUrl("jdbc:hsqldb:file:wkd_database.sql");
        driverManagerDataSource.setUsername("sa");
        driverManagerDataSource.setPassword("");

        try {
            PasswordEncoder bcryptPasswordEncoder = passwordEncoder();
            driverManagerDataSource.getConnection().createStatement().execute(UserService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(UserService.schemaAuthorities);
            User admin = new User(0, "admin@mail.de", "admin", "", "password", "password", "Admin", "Admin", "", "", Date.valueOf(LocalDate.of(1,1,1)));
            PreparedStatement pstmt = driverManagerDataSource.getConnection().prepareStatement(UserService.queryAdd, Statement.RETURN_GENERATED_KEYS);
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
            pstmt = driverManagerDataSource.getConnection().prepareStatement("insert into user_roles values(?,?)", Statement.NO_GENERATED_KEYS);
            pstmt.setString(1, userName);
            pstmt.setString(2, "USER");
            pstmt.execute();
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(AchievementService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(UserAchievementMapperService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(SettingService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(UserSettingMapperService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(RequestService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(RequestResponseService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(RequestTagService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(RequestTagMapperService.schema);
        } catch(SQLException e) {
            //e.printStackTrace();
        }
        try
        {
            driverManagerDataSource.getConnection().createStatement().execute(ChatService.schema);
        } catch (SQLException e) {
            //e.printStackTrace();
        }

        return driverManagerDataSource;
    }
}
