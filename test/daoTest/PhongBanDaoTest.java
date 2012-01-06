/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTest;

import dao.*;
import play.modules.guice.InjectSupport;
import javax.inject.Inject;
import models.*;
import org.junit.*;
import play.test.*;
import play.i18n.*;
import utils.*;

/**
 *
 * @author Nguyen Bao Ngoc
*/
@InjectSupport
public class PhongBanDaoTest extends UnitTest {
    public String iError; //input error message
    
    // Anotate @Inject de su dung guice dependency injecttion
    @Inject
    static PhongBanDao phongBanDao;
    
    // Anotate @Before danh dau function se chay truoc cac function khac
    @Before
    // Khoi tao data mau
    public void setup() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data/phongBanData.yml");
        this.iError = Messages.get("Error_InputError!");
    }
    
    // Test create Phong Ban moi
    @Test
    public void createTest() {
        // Khoi tao data
        PhongBan phongBan = new PhongBan();
        phongBan.ten = "Phong Ban 1";
        
        // Truong hop dung
        FeedBack ketQua = phongBanDao.create(phongBan);
        assertTrue(ketQua.isSuccess);
        assertEquals("Phong Ban 1", ((PhongBan)ketQua.data).ten);
        
        // Truong hop sai, phongBan.ten la xau rong~
        phongBan.ten = "";
        ketQua = phongBanDao.create(phongBan);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop null
        ketQua = phongBanDao.create(null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
    
    // Test edit Phong Ban
    @Test
    public void editTest() {
        // Khoi tao data dung
        PhongBan phongBanCuDung = PhongBan.all().first();
        PhongBan phongBanMoiDung = new PhongBan();
        phongBanMoiDung.ten = "Phong Ban Moi day";
        
        // Khoi tao data sai
        PhongBan phongBanCuSai = new PhongBan();
        PhongBan phongBanMoiSai = new PhongBan();
        phongBanMoiSai.ten = "";
        
        // Truong hop dung
        FeedBack ketQua = phongBanDao.edit(phongBanMoiDung, phongBanCuDung);
        assertTrue(ketQua.isSuccess);
        assertEquals("Phong Ban Moi day", ((PhongBan)ketQua.data).ten);
        
        // Truong hop phongBanCu null
        ketQua = phongBanDao.edit(phongBanMoiDung, null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop phongBanMoi null
        ketQua = phongBanDao.edit(null, phongBanCuDung);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop khong tim thay phongBanCu trong database
        ketQua = phongBanDao.edit(phongBanMoiDung, phongBanCuSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop phongBanMoi.ten la xau rong~
        ketQua = phongBanDao.edit(phongBanMoiSai, phongBanCuDung);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
    
    // Test delete Phong Ban
    @Test
    public void deleteTest() {
        // Khoi tao data dung
        PhongBan phongBan = PhongBan.all().first();
        
        // Khoi tao data sai
        PhongBan phongBanSai = new PhongBan();
        
        // Truong hop dung
        FeedBack ketQua = phongBanDao.delete(phongBan);
        assertTrue(ketQua.isSuccess);
        assertNotNull(ketQua.data);
        
        // Truong hop phongBan null
        ketQua = phongBanDao.delete(null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop data sai, khong tim thay phongBan
        ketQua = phongBanDao.delete(phongBanSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
    }
}