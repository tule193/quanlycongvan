/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerTest;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import org.junit.*;
/**
 *
 * @author to_viet_anh
 */
public class QuanLyCongVanTest extends FunctionalTest {
    @Test
    public void taoCongVanDenTest(){
        Response response = GET("/quanlycongvan/taocongvanden");
        assertStatus(200, response);
    }
}
