package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Request;
import de.hsmannheim.mso.wkd.WerKannDas.Models.RequestTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestTagMapperService {

    public static String table = "request_has_tag";
    public static String colRequestFk = "request_fk";
    public static String colTagFk = "tag_fk";

    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " (" +
            colRequestFk + " INTEGER NOT NULL, " +
            colTagFk + " INTEGER NOT NULL, " +
            " PRIMARY KEY (" + colRequestFk + ", " + colTagFk + ")," +
            " CONSTRAINT request_has_tag_request_fk FOREIGN KEY (" + colRequestFk + ") REFERENCES " +
            RequestService.table + " (" + RequestService.colPk + ")" +
            " ON DELETE CASCADE" +
            " ON UPDATE CASCADE," +
            "  CONSTRAINT request_has_tag_tag_fk FOREIGN KEY (" + colTagFk + ") REFERENCES " +
            RequestTagService.table + " (" + RequestTagService.colPk + ")" +
            " ON DELETE CASCADE" +
            " ON UPDATE CASCADE" +
            ");";

    private String combinedCols = colRequestFk + ", " + colTagFk;
    private String queryByRequest = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colRequestFk + " = ?";
    private String queryByRequestTag = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colRequestFk + " = ?";
    private String queryAdd = "INSERT INTO " + table + " (" + colRequestFk + ", " + colTagFk + ") VALUES (?,?)";

    @Autowired
    private DataSource ds;
    @Autowired
    private RequestTagService requestTagService;
    @Autowired
    private RequestService requestService;

    public List<RequestTag> getTagsForRequest(Request request) {
        List<RequestTag> requestTags = new ArrayList<>();
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByRequest);
            pstmt.setInt(1, request.getPk());
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                int requestTagPk = results.getInt(RequestTagService.colPk);
                requestTags.add(requestTagService.getByID(requestTagPk));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requestTags;
    }

    public List<Request> getRequestsForTag(RequestTag requestTag) {
        List<Request> requests = new ArrayList<>();
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByRequestTag);
            pstmt.setInt(1, requestTag.getPk());
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                int requestPk = results.getInt(RequestTagService.colPk);
                requests.add(requestService.getByID(requestPk));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean saveRequestTag(Request request, RequestTag requestTag) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryAdd);
//            if (request.getPk() == 0){
//                request = requestService.save(request);
//            }
            if (requestTag.getPk() == 0) {
                requestTag = requestTagService.save(requestTag);
            }
            pstmt.setInt(1, request.getPk());
            pstmt.setInt(2, requestTag.getPk());
            ResultSet results = pstmt.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
