/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.QuanLy;
import play.mvc.*;
import controllers.*;
/**
 *
 * @author to_viet_anh
 */
@With(Secure.class)
@CRUD.For(models.PhongBan.class)
public class PhongBan extends CRUD{
    
}