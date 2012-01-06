/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import play.db.jpa.GenericModel.JPAQuery;
import utils.*;
import dao.*;
import java.util.ArrayList;
import java.util.List;
import models.*;
import play.i18n.*;
/**
 *
 * @author to_viet_anh
 */
public class UserDaoImpl implements UserDao {

    public FeedBack changePassword(User user, String newPassword) {
        FeedBack result = new FeedBack(false, "", null);
        //Kiểm tra user khác null
        if(null==user){
            result.setMessage(Messages.get("Error_UserNotFound!"));
            return result;
        }
        //tìm user 
        User u = User.findById(user.id);
        //Nếu không tìm thấy người dùng thì return false và thông báo lại
        if(null==u){
            result.setMessage(Messages.get("Error_UserNotFound!"));
            return result;
        }
        //đổi password
        u.password = User.hashPassword(newPassword);
        result.data = u.save();
        if(result.data==null){
            result.setMessage(Messages.get("Error_CanNotSave"));
            return result;
        }
        result.setIsSuccess(true);
        return result;
    }

    public FeedBack<User> login(String username, String password) {
        FeedBack<User> result = new FeedBack<User>(false, "", null);
        //kiểm tra dữ liệu đầu vào
        if(username==null||password==null){
            result.message = Messages.get("Error_InputError!");
        }
        User user = User.find("byUsernameAndPassword", username,User.hashPassword(password)).first();
        //Kiểm tra kết quả và trả về dữ liệu
        if(user==null)return result;
        else{
            result.data = user;
            result.isSuccess = true;
            return result;
        }
    }

    public FeedBack<User> register(User user) {
        FeedBack<User> result = new FeedBack<User>(false, "", null);
        if(user==null){
            result.message = Messages.get("Error_InputError!");
            return result;
        }
        user.password = User.hashPassword(user.password);
        result.data = user.save();
        if(result.data==null) return result;
        else{
            result.isSuccess = true;
            return result;
        }
    }

    public FeedBack logout(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FeedBack updateProfile(User oldUser, User newUser) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra xem oldUser va new User co null khong
        if (oldUser == null || newUser == null) { // neu null, return loi
            result.message = Messages.get("Error_InputError!");
        } else { // neu khong null, tim doi tuong oldUser trong database
            User tmp = null;
            
            try {
                tmp = User.findById(oldUser.id);
            } catch (Exception e) { // neu khong tim thay, return loi
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // neu tim thay, thay doi data va save change
            tmp.ho = newUser.ho;
            tmp.ten = newUser.ten;
            tmp.tenDem = newUser.tenDem;
            tmp.capBac = newUser.capBac;
            tmp.phongBan = newUser.phongBan;
            tmp.chucVu = newUser.chucVu;
            result.data = tmp.save();
            
            // kiem tra xem co save dc khong
            if (result.data == null) { // neu khong save duoc, bao loi
                result.message = Messages.get("Error_InputError!");
            } else { // neu save dc, bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }

    public FeedBack inactive(User user) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra xem user co null khong
        if (user == null) { // neu null, return loi
            result.message = Messages.get("Error_InputError!");
        } else { // neu ko null, tim doi tuong user trong database
            User temp = null;
            
            // neu khong tim thay return loi
            try {
                temp = User.findById(user.id);
            } catch (Exception e) {
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // neu khong loi, thay doi data va save change
            temp.isActive = false;
            result.data = temp.save();
            
            // kiem tra xem co save dc khong
            if (result.data == null) { // neu khong save dc, return loi
                result.message = Messages.get("Error_CanNotSave");
            } else { // neu save duoc, bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }

    public FeedBack active(User user) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra xem user co null khong
        if (user == null) { // neu null, return loi
            result.message = Messages.get("Error_InputError!");
        } else { // neu ko null, tim doi tuong user trong database
            User temp = null;
            
            // neu khong tim thay return loi
            try {
                temp = User.findById(user.id);
            } catch (Exception e) {
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // neu khong loi, thay doi data va save change
            temp.isActive = true;
            result.data = temp.save();
            
            // kiem tra xem co save dc khong
            if (result.data == null) { // neu khong save dc, return loi
                result.message = Messages.get("Error_CanNotSave");
            } else { // neu save duoc, bao thanh cong
                result.isSuccess = true;
            }
        }
        return result;
    }

    public FeedBack<User> findByUsername(String username) {
        FeedBack<User> result = new FeedBack<User>(false, "", null);
        if(username==null){
            result.setMessage(Messages.get("Error_InputError!"));
            return result;
        }
        result.data = User.find("byUsername", username).first();
        if(result.data==null){
            result.setMessage(Messages.get("Error_UserNotFound!"));
        }else{
            result.isSuccess = true;
        }
        return result;
    }

    public JPAQuery getAllUsers() {
        return User.all();
    }

    public JPAQuery getChiHuy() {
        return User.find("byPhanQuyen", User.ROLE.BAN_GIAM_HIEU);
    }

    public JPAQuery getTruongPhong(PhongBan phongban) {
        return User.find("select u from User u join u.phongBan p where p.id=? and (u.phanQuyen=? or u.phanQuyen=?)"
                ,phongban.id,User.ROLE.TRUONG_PHONG,User.ROLE.THU_KY);
    }
    
    public JPAQuery getVanThu() {
    	return User.find("byPhanQuyen", User.ROLE.VAN_THU);
    }
}
