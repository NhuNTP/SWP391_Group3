/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
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
}
