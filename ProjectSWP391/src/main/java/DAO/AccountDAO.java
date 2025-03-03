/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import DBContext.DBContext;

import DB.DBContext;

import Model.Account;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class AccountDAO {


    public ResultSet getAll() {
        String sql = "SELECT * FROM Account";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Error retrieving accounts", ex);
            return null;
        }
    }

    public int Create(Account newInfo) {
        String sql = "INSERT INTO Account (UserEmail, UserPassword, UserName, UserRole, IdentityCard, UserAddress, UserImage) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newInfo.getUserEmail());
            ps.setString(2, newInfo.getUserPassword()); // Lưu mật khẩu trực tiếp, không băm
            ps.setString(3, newInfo.getUserName());
            ps.setString(4, newInfo.getUserRole());
            ps.setString(5, newInfo.getIdentityCard());
            ps.setString(6, newInfo.getUserAddress());
            ps.setString(7, newInfo.getUserImage());

            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Error creating account", ex);
        }
        return 0;
    }

    public Account getAccountId(int id) {
        String sql = "SELECT * FROM Account WHERE UserId = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account acc = new Account();
                    acc.setUserId(rs.getInt("UserId"));
                    acc.setUserEmail(rs.getString("UserEmail"));
                    acc.setUserPassword(rs.getString("UserPassword")); // Không băm mật khẩu
                    acc.setUserName(rs.getString("UserName"));
                    acc.setUserRole(rs.getString("UserRole"));
                    acc.setIdentityCard(rs.getString("IdentityCard"));
                    acc.setUserAddress(rs.getString("UserAddress"));
                    acc.setUserImage(rs.getString("UserImage"));
                    return acc;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Error retrieving account by ID", ex);
        }
        return null;
    }

    public int Update(int id, Account newInfo) {
        String sql = "UPDATE Account SET UserEmail=?, UserPassword=?, UserName=?, UserRole=?, IdentityCard=?, UserAddress=?, UserImage=? WHERE UserId=?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newInfo.getUserEmail());
            ps.setString(2, newInfo.getUserPassword()); // Lưu mật khẩu trực tiếp, không băm
            ps.setString(3, newInfo.getUserName());
            ps.setString(4, newInfo.getUserRole());
            ps.setString(5, newInfo.getIdentityCard());
            ps.setString(6, newInfo.getUserAddress());
            ps.setString(7, newInfo.getUserImage());
            ps.setInt(8, id);

            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Error updating account", ex);
        }
        return 0;
    }

    public int Delete(int id) {
        String sql = "DELETE FROM Account WHERE UserId=?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Error deleting account", ex);
        }
        return 0;
    }

    private Connection conn;
    private String sql;

    public AccountDAO() {
        conn = DBContext.getConnection();
    }

    public ResultSet getAll() {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            sql = "SELECT * FROM Account";
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(); // **THÊM DÒNG NÀY ĐỂ IN LỖI SQL CHI TIẾT RA LOG**
        }
        return rs;
    }

    public int Create(Account newInfo) {
        int count = 0;
        try {
            sql = "INSERT INTO Account (UserEmail, UserPassword, UserName, UserRole, IdentityCard, UserAddress, UserImage) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, newInfo.getUserEmail());
            pst.setString(2, newInfo.getUserPassword());
            pst.setString(3, newInfo.getUserName());
            pst.setString(4, newInfo.getUserRole());
            pst.setString(5, newInfo.getIdentityCard());
            pst.setString(6, newInfo.getUserAddress());
            pst.setString(7, newInfo.getUserImage());

            count = pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(); // **Dòng log 10: In stack trace đầy đủ của lỗi SQL**
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex); // Giữ lại dòng log này
        }
        return count;
    }

    public Account getAccountId(int id) {
        Account obj = null;
        try {
            sql = "SELECT UserId, UserEmail, UserPassword, UserName, UserRole, IdentityCard, UserAddress, UserImage FROM Account WHERE UserId = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                obj = new Account();
                obj.setUserId(rs.getInt("UserId"));
                obj.setUserEmail(rs.getString("UserEmail"));
                obj.setUserPassword(rs.getString("UserPassword"));
                obj.setUserName(rs.getString("UserName"));
                obj.setUserRole(rs.getString("UserRole"));
                obj.setIdentityCard(rs.getString("IdentityCard"));
                obj.setUserAddress(rs.getString("UserAddress"));
                obj.setUserImage(rs.getString("UserImage"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    public int Update(int id, Account newInfo) {
        int count = 0;
        try {
            sql = "UPDATE Account SET UserEmail=?, UserPassword=?, UserName=?, UserRole=?, IdentityCard=?, UserAddress=?, UserImage=? WHERE UserId=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newInfo.getUserEmail());
            pst.setString(2, newInfo.getUserPassword()); // Nếu cần băm mật khẩu, hãy gọi hàm băm tại đây
            pst.setString(3, newInfo.getUserName());
            pst.setString(4, newInfo.getUserRole());
            pst.setString(5, newInfo.getIdentityCard());
            pst.setString(6, newInfo.getUserAddress());
            pst.setString(7, newInfo.getUserImage());
            pst.setInt(8, id);

            count = pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int Delete(int id) {
        int count = 0;
        try {
            sql = "delete from Account where UserId=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            count = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    
    


}
