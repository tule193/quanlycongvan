/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelTest;

import org.junit.*;
import play.test.*;
import play.i18n.*;
import models.*;
/**
 *
 * @author to_viet_anh
 */
public class UserTest extends UnitTest {
    @Test
    public void hassPasswordTest(){
        //Trường hợp đúng
        String firstPassword = "aljfd409832$#$#@";
        String secondPassword = firstPassword;
        assertEquals(User.hashPassword(firstPassword), User.hashPassword(secondPassword));
        //Trường hợp sai
        secondPassword = "2493724098lkfjd";
        assertNotSame(User.hashPassword(firstPassword), User.hashPassword(secondPassword));
        //Trường hợp dữ liệu input bị lỗi
        secondPassword = null;
        assertEquals(Messages.get("Error_InputError!"),User.hashPassword(secondPassword));
    }
}
