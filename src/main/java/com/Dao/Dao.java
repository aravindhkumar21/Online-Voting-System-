package com.Dao;

import com.Model.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Base64;

public class Dao {

    static Connection con = null;

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url =
                "jdbc:sqlserver://localhost:54078;databaseName=evoting;encrypt=false;trustServerCertificate=true";

            con = DriverManager.getConnection(url, "sa", "@ravindh56luffy");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet loginValidation(String sql) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }

    public static ResultSet adminValid(Model m) throws SQLException {
        String sql = "select adminId,username,password from admin where username=? and password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getUserName());
        ps.setString(2, m.getPass());
        return ps.executeQuery();
    }

    public static ResultSet voterValid(Model m) throws SQLException {
        String sql = "select voter_card_number,password,username from login where voter_card_number=? and password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getVoterId());
        ps.setString(2, m.getPass());
        return ps.executeQuery();
    }

    public static ResultSet valid1(String sql) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }

    // ✅ FIXED: NO duplicate voting allowed (SQL Server way)
    public static int votePublish(Model m) throws SQLException {

        int result = 0;

        // check if voter already voted
        String checkSql = "SELECT voter_card_number FROM voter WHERE voter_card_number=?";
        PreparedStatement checkPs = con.prepareStatement(checkSql);
        checkPs.setString(1, m.getVoterId());

        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {
            // already voted → return 0
            return 0;
        }

        // insert vote
        String insertSql =
            "INSERT INTO voter (voter_card_number, voter) VALUES (?, ?)";

        PreparedStatement ps = con.prepareStatement(insertSql);
        ps.setString(1, m.getVoterId());
        ps.setString(2, m.getVote());

        result = ps.executeUpdate();

        return result;
    }

    public static int register(Model m) throws SQLException {
        String sql =
            "INSERT INTO login(voter_card_number,name,username,gender,dob,email,password) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getVoterId());
        ps.setString(2, m.getFullName());
        ps.setString(3, m.getUserName());
        ps.setString(4, m.getGender());
        ps.setString(5, m.getDob());
        ps.setString(6, m.getEmail());
        ps.setString(7, m.getPass());

        return ps.executeUpdate();
    }

    public static int contact(Model m) throws SQLException {
        String sql = "INSERT INTO contact(name,company,email,message) VALUES(?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getFullName());
        ps.setString(2, m.getCompanyName());
        ps.setString(3, m.getEmail());
        ps.setString(4, m.getMessage());

        return ps.executeUpdate();
    }

    public static Model getPic(int id) throws SQLException, IOException {

        String sql = "SELECT * FROM partytable WHERE pid=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Model model = new Model();

            String partyCode = rs.getString("partyCode");
            String partyName = rs.getString("partyName");
            Blob blob = rs.getBlob("photo");

            InputStream inputStream = blob.getBinaryStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            String base64Image =
                Base64.getEncoder().encodeToString(outputStream.toByteArray());

            inputStream.close();
            outputStream.close();

            model.setPartyCode(partyCode);
            model.setPartyName(partyName);
            model.setBase64Image(base64Image);

            return model;
        }

        return null;
    }

    public static int getId(String partyCode) throws SQLException {
        String sql = "SELECT pid FROM partytable WHERE partyCode=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, partyCode);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return 0;
    }

    public static int registerAdmin(Model m) throws SQLException {
        String sql = "INSERT INTO admin(username,password) VALUES(?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getFullName());
        ps.setString(2, m.getPass());

        return ps.executeUpdate();
    }

    public static int deleteVoter(String voterid) throws SQLException {
        String sql = "DELETE FROM login WHERE voter_card_number=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, voterid);
        return ps.executeUpdate();
    }

    public static int register(String sql) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeUpdate();
    }
}