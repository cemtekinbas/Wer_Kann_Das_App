package de.hsmannheim.mso.wkd.WerKannDas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/css", "/font", "/fonts", "/img", "/js", "/sass").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .failureForwardUrl("/login?error")
                .successForwardUrl("/dashboard")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    PasswordEncoder bcryptPasswordEncoder;

    @Autowired
    DataSource ds;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery("select username,password, enabled from users where username=?")
                .passwordEncoder(bcryptPasswordEncoder);
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}
