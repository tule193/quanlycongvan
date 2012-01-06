/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTest;

import org.junit.*;
import java.util.*;
import javax.inject.Inject;
import play.test.*;
import play.i18n.*;
import dao.*;
import models.*;
import play.modules.guice.InjectSupport;
import utils.*;
/**
 *
 * @author to_viet_anh
 */
@InjectSupport
public class UserDaoTest extends UnitTest {
    public String iError; // input error message
    
    //Annotate @Inject để sử dụng guice dependency injection
    @Inject
    static UserDao userDao;
    //Annotate @Before để đánh dấu function sẽ được chạy trước khi chạy các hàm test
    @Before
    //Khởi tạo dữ liệu database
    public void setup(){
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data/chucVuData.yml");
        Fixtures.loadModels("data/phongBanData.yml");
        Fixtures.loadModels("data/UserData.yml");
        
        iError = Messages.get("Error_InputError!");
    }
    //Test function changePassword()
    @Test
    public void changePasswordTest(){
        FeedBack ketQua;
        //Trường hợp đúng - kiểm tra 1 trường hợp
        String randomPassword = "lkfjlsajfksak.483fjd98$#@$@";
        User user = User.all().first();
        ketQua = userDao.changePassword(user, randomPassword);
        assertEquals(User.hashPassword(randomPassword), ((User)ketQua.getData()).password);
        //Trường hợp sai - tạm thời chưa có
        //Trường hợp nhập sai số liệu - kiểm tra 2 trường hợp
        //trường hợp 1 -> user = null
        user = null;
        ketQua = userDao.changePassword(user, randomPassword);
        assertEquals(false, ketQua.isSuccess);
        assertEquals(Messages.get("Error_UserNotFound!"), ketQua.getMessage());
        //trường hợp 2 -> user lạ, không tìm thấy user
        user = User.all().first();
        long temp = user.id;//luu id cu cua user
        user.id = 100l;//doi id cua user
        ketQua = userDao.changePassword(user, randomPassword);
        user.id = temp;//sau khi su dung fake user thi gan lai thong tin id cho user de save.
        user = user.save();
        assertEquals(false, ketQua.isSuccess);
        assertEquals(Messages.get("Error_UserNotFound!"), ketQua.getMessage());
        //Trường hợp cận biên - không có
    }
    //Test function login
    @Test
    public void loginTest(){
        //Khởi tạo dữ liệu
        String username = "nguyenvana";
        String password = "bimat";
        User u = User.find("byUsername", username).first();
        userDao.changePassword(u, password);
        //Trường hợp đúng
        FeedBack<User> ketQua = userDao.login(username, password);
        assertEquals(true, ketQua.isSuccess);
        assertEquals(u, ketQua.data);
        //Trường hợp sai
        password = "saimatkhau";
        ketQua = userDao.login(username, password);
        assertEquals(false, ketQua.isSuccess);
        assertNull(ketQua.data);
        //Trường hợp lỗi nhập liệu
        username = null;
        password = null;
        ketQua = userDao.login(username, password);
        assertEquals(false, ketQua.isSuccess);
        assertEquals(Messages.get("Error_InputError!"),ketQua.message);
        
    }
    @Test
    public void registerTest(){
        //Khởi tạo dữ liệu
        User user = new User();
        user.username = "nguyenvana";
        user.password = "bimatnhe!";
        user.ten = "A";
        user.ho = "Nguyễn";
        user.tenDem = "Văn";
        //Trường hợp đúng
        FeedBack<User> ketQua = userDao.register(user);
        //Trường hợp sai
        //Trường hợp null
    }
    
    @Test
    public void inactiveTest() {
        // Khoi tao du lieu dung
        User user = User.find("byUsername", "active").first();
        
        // Khoi tao du lieu sai
        User userSai = new User();
        
        // Truong hop dung
        FeedBack ketQua = userDao.inactive(user);
        assertTrue(ketQua.isSuccess);
        assertFalse(((User)ketQua.data).isActive);
        
        // Truong hop sai, khong tim thay user trong database
        ketQua = userDao.inactive(userSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop null
        ketQua = userDao.inactive(null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
    
    @Test
    public void activeTest() {
        // Khoi tao du lieu dung
        User user = User.find("byUsername", "inactive").first();
        
        // Khoi tao du lieu sai
        User userSai = new User();
        
        // Truong hop dung
        FeedBack ketQua = userDao.active(user);
        assertTrue(ketQua.isSuccess);
        assertTrue(((User)ketQua.data).isActive);
        
        // Truong hop sai, khong tim thay user trong database
        ketQua = userDao.active(userSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop null
        ketQua = userDao.active(null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
    
    @Test
    public void updateProfileTest () {
        // Khoi tao du lieu dung
        User oldUserDung = User.all().first();
        User newUserDung = new User();
        newUserDung.ho = "tran";
        newUserDung.tenDem = "van";
        newUserDung.ten = "b";
        
        // Khoi tao du lieu sai
        User oldUserSai = new User();
        
        // Truong hop dung
        FeedBack ketQua = userDao.updateProfile(oldUserDung, newUserDung);
        assertTrue(ketQua.isSuccess);
        assertEquals("tran", ((User)ketQua.data).ho);
        
        // Truong hop oldUser null
        ketQua = userDao.updateProfile(null, newUserDung);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop newUser null
        ketQua = userDao.updateProfile(oldUserDung, null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop khong tim thay oldUser trong database
        ketQua = userDao.updateProfile(oldUserSai, newUserDung);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
}