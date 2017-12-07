package de.hsmannheim.mso.wkd.WerKannDas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class RequestTagMapperService {

    public static String table = "request_has_tag";

    public static String schema = "";

    @Autowired
    private DataSource ds;
}
