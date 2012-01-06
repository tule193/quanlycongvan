/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import play.mvc.*;
import play.modules.guice.*;
import dao.*;
import javax.inject.Inject;
import utils.FeedBack;
import models.*;
import play.i18n.Messages;
import utils.Constants;
/**
 *
 * @author to_viet_anh
 */
@InjectSupport
@With(Secure.class)
public class NguoiDung extends AbstractController{
    @Inject
    static UserDao userDao;
    
    public static void xemThongTin(String username){
    }
    
    public static void doiMatKhau(String referrerUrl){
        //lay gia tri mat khau
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        String confirmPassword = params.get("confirmPassword");
        //kiem tra mat khau cu
        FeedBack<User> fbUser = userDao.login(session.get("username"), oldPassword);
        if(!fbUser.isSuccess){
            flash.put(Constants.MESSAGE.ERROR, Messages.get("MatKhauKhongDung"));
        }else{
            //kiem tra mat khau moi va xac nhan giong nhau
            if(!newPassword.equals(confirmPassword)){
                flash.put(Constants.MESSAGE.ERROR, Messages.get("MatKhauMoiVaMatKhauXacNhanKhongGiongNhau"));
            }else{
                FeedBack result = userDao.changePassword(fbUser.data, newPassword);
                if(!result.isSuccess){
                    flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
                }else{
                    flash.put(Constants.MESSAGE.SUCCESS, Messages.get("ThayDoiMatKhauThanhCong"));
                }
            }
        }
        userDao.changePassword(null, referrerUrl);
        if(referrerUrl==null){
            Application.index();
        }
        redirect(referrerUrl);
    }
}
