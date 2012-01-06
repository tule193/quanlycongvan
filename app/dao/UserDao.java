/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author to_viet_anh
 */
import java.util.List;
import utils.*;
import models.*;
import play.db.jpa.GenericModel.JPAQuery;
public interface UserDao {
    public FeedBack changePassword(User user, String newPassword);
    public FeedBack<User> login(String username, String password);
    public FeedBack<User> register(User user);
    public FeedBack logout(User user);
    public FeedBack updateProfile(User oldUser, User newUser);
    public FeedBack inactive(User user);
    public FeedBack active(User user);
    public FeedBack<User> findByUsername(String username);
    public JPAQuery getAllUsers();
    public JPAQuery getChiHuy();
    public JPAQuery getTruongPhong(PhongBan phongban);
    public JPAQuery getVanThu();
}