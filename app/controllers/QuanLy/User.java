/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.QuanLy;

import play.mvc.*;
import controllers.*;
import play.db.Model;

/**
 *
 * @author Nguyen Bao Ngoc
 */
@With(Secure.class)
@CRUD.For(models.User.class)
public class User extends CRUD{
    public static String defaultPassword = String.valueOf(123456);
    
    // Chuyen trang thai cua user sang inactive
    public static void inactive(int id) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        models.User user = (models.User) object;
        user.isActive = false;
        user.save();
        redirect(request.controller + ".list");
    }
    
    // Chuyen trang thai cua user sang acive
    public static void active(int id) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        models.User user = (models.User) object;
        user.isActive = true;
        user.save();
        redirect(request.controller + ".list");        
    }
    
    // Reset password cua user ve gia tri defaultPassword
    public static void resetPassword (int id) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        models.User user = (models.User) object;
        user.password = models.User.hashPassword(defaultPassword);
        user.save();
        redirect(request.controller + ".list");
    }
}