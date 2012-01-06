/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import dao.*;
import play.modules.guice.InjectSupport;
import javax.inject.Inject;
import utils.FeedBack;
import models.User;
/**
 *
 * @author to_viet_anh
 */
@InjectSupport
public class Security extends Secure.Security {
    
    @Inject
    static UserDao userDao;
    
    static boolean authenticate(String username, String password){
        FeedBack result = userDao.login(username, password);
        if(result.isSuccess){
            session.put("role", ((User)result.data).phanQuyen);
        }
        return result.isSuccess;
    }
    
    
}