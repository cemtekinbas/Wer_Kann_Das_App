package de.hsmannheim.mso.wkd.WerKannDas;

import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/css/**", "/font/**", "/fonts/**", "/img/**", "/js/**", "/sass/**", "/register", "/font-awesome-4.7.0/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .passwordParameter(UserService.colPassword)
                .usernameParameter(UserService.colUserName)
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .and()
            .exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    PasswordEncoder bcryptPasswordEncoder;

    @Autowired
    DataSource ds;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication()
        //        .withUser("user").password("password").roles("USER");
        auth.jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(UserService.queryLogin)
                .authoritiesByUsernameQuery("select username, role from user_roles where username=?")
                .passwordEncoder(bcryptPasswordEncoder);
    }
}
