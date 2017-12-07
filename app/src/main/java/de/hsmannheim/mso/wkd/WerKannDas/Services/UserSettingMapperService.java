package de.hsmannheim.mso.wkd.WerKannDas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UserSettingMapperService {

    public static String table = "user_has_setting";

    public static String schema = "";

    @Autowired
    private DataSource ds;
}
