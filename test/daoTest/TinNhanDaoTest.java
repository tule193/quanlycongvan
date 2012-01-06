/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTest;

import dao.*;
import java.util.*;
import models.*;
import javax.inject.Inject;
import org.junit.*;
import play.i18n.Messages;
import play.modules.guice.InjectSupport;
import play.test.*;
import utils.FeedBack;

/**
 *
 * @author Nguyen Bao Ngoc
 */
@InjectSupport
public class TinNhanDaoTest extends UnitTest {
    @Inject
    static TinNhanDao tinNhanDao;
    
    @Inject
    static UserDao userDao;
    
    public String iError;
        
//    @Test
//    public void luuTinNhanTest() {
//        // Khoi tao data
//        FeedBack<User> u = userDao.findByUsername("ngocnb");
//        User sender = u.data;
//        assertNotNull(sender);
//        
//        List<User> receivers = new ArrayList<User>();
//        u = userDao.findByUsername("anhtv");
//        User receiver = u.data;
//        receivers.add(receiver);
//        assertNotNull(receivers);
//        
//        assertNotNull(tinNhanDao);
//        
//        String content = "test noi dung";
//        
//        // Test truong hop dung, 1 nguoi nhan
//        FeedBack ketQua = tinNhanDao.luuTinNhan(sender, receivers, content);
//        assertTrue(ketQua.isSuccess);
//        
//        // Test truong hop dung, nhieu nguoi nhan
//        receivers.add(sender);
//        ketQua = tinNhanDao.luuTinNhan(sender, receivers, content);
//        assertTrue(ketQua.isSuccess);        
//        
//        // Test truong hop sai, nguoi gui null
//        ketQua = tinNhanDao.luuTinNhan(null, receivers, content);
//        assertFalse(ketQua.isSuccess);
//        
//        // Test truong hop sai, nguoi nhan null
//        ketQua = tinNhanDao.luuTinNhan(sender, null, content);
//        assertFalse(ketQua.isSuccess);
//        
//        // Test truong hop sai, noi dung null
//        ketQua = tinNhanDao.luuTinNhan(sender, receivers, null);
//        assertFalse(ketQua.isSuccess);
//        
//        // Test truong hop sai, noi dung ""
//        ketQua = tinNhanDao.luuTinNhan(sender, receivers, "");
//        assertFalse(ketQua.isSuccess);
//    }
}