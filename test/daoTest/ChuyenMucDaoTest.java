/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTest;

import javax.inject.Inject;
import play.modules.guice.InjectSupport;
import dao.*;
import play.test.*;
import models.*;
import org.junit.*;
import play.Logger;
import utils.*;
import play.i18n.*;

/**
 *
 * @author Nguyen Bao Ngoc
 */
@InjectSupport
public class ChuyenMucDaoTest extends UnitTest {
    public String iError; // input error
    public String sError; // saving error
    
    // Annotate @Inject de su dung guice dependency injection
    @Inject
    static ChuyenMucDao chuyenMucDao;
    
    // Annotate @Before danh dau function se chay truoc khi chay cac function khac
    @Before
    // Khoi tao data mau
    public void setup() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data/chuyenMucData.yml");
        this.iError = Messages.get("Error_InputError!");
        this.sError = Messages.get("Error_CanNotSave");
    }
    
    // Test function create - tao chuyen muc moi
    @Test
    public void createTest() {
        // Khoi tao du lieu
        ChuyenMuc chuyenMuc = new ChuyenMuc();
        chuyenMuc.ten = "Chuyen Muc 1";
        
        // Truong hop dung
        FeedBack ketQua1 = chuyenMucDao.create(chuyenMuc);
        assertTrue(ketQua1.isSuccess);
        
//         Truong hop ten = ""
        chuyenMuc.ten = "";
        FeedBack ketQua2 = chuyenMucDao.create(chuyenMuc);
        assertFalse(ketQua2.isSuccess);
        assertEquals(iError, ketQua2.message);
        
        // Truong hop null
        FeedBack ketQua3 = chuyenMucDao.create(null);
        assertFalse(ketQua3.isSuccess);
        assertEquals(iError, ketQua3.message);
    }
    
    @Test
    public void editTest() {
        // Khoi tao du lieu dung
        ChuyenMuc chuyenMucCu = ChuyenMuc.all().first();
        ChuyenMuc chuyenMucMoi = new ChuyenMuc();
        chuyenMucMoi.ten = "Chuyen Muc Moi";
        
        // Khoi tao du lieu sai
        ChuyenMuc chuyenMucCuSai = new ChuyenMuc();
        ChuyenMuc chuyenMucMoiSai = new ChuyenMuc();
        chuyenMucMoiSai.ten = "";
        
        // Truong hop dung
        FeedBack ketQua = chuyenMucDao.edit(chuyenMucMoi, chuyenMucCu);
        assertTrue(ketQua.isSuccess);
        assertEquals(chuyenMucMoi.ten, ((ChuyenMuc)ketQua.data).ten);
        
        // Truong hop chuyenMucCu null
        ketQua = chuyenMucDao.edit(chuyenMucMoi, null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop chuyenMucMoi null
        ketQua = chuyenMucDao.edit(null, chuyenMucCu);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop chuyenMucMoi.ten = ""
        ketQua = chuyenMucDao.edit(chuyenMucMoiSai, chuyenMucCu);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop tmp null
        ketQua = chuyenMucDao.edit(chuyenMucMoi, chuyenMucCuSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
    
    @Test
    public void deleteTest () {
        // Khoi tao du lieu dung
        ChuyenMuc chuyenMuc = ChuyenMuc.all().first();
        
        // Khoi tao du lieu sai
        ChuyenMuc chuyenMucSai = new ChuyenMuc();
        
        // Truong hop dung
        FeedBack ketQua = chuyenMucDao.delete(chuyenMuc);
        assertTrue(ketQua.isSuccess);
        
        // Truong hop chuyenMuc null
        ketQua = chuyenMucDao.delete(null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop tmp null
        ketQua = chuyenMucDao.delete(chuyenMucSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
}
