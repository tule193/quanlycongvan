/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.QuanLy;

import play.mvc.*;
import controllers.*;

/**
 *
 * @author Nguyen Bao Ngoc
 */
@With(Secure.class)
@CRUD.For(models.ChucVu.class)
public class ChucVu extends CRUD{
    
}