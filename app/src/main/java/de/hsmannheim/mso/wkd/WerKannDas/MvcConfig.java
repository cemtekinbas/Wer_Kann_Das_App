package de.hsmannheim.mso.wkd.WerKannDas;

import de.hsmannheim.mso.wkd.WerKannDas.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Import({WebSecurityConfig.class})
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/home").setViewName("home");
        //registry.addViewController("/").setViewName("home");
        //registry.addViewController("/login").setViewName("login");
        //registry.addViewController("/logout").setViewName("logout");
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
            driverManagerDataSource.getConnection().createStatement().execute(UserService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(AchievementService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(UserAchievementMapperService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(SettingService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(UserSettingMapperService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(RequestService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(RequestResponseService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(RequestTagService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(RequestTagMapperService.schema);
            driverManagerDataSource.getConnection().createStatement().execute(ChatService.schema);
        } catch (SQLException e) {
            //e.printStackTrace();
        }

        return driverManagerDataSource;
    }
}
