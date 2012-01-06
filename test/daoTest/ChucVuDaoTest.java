/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTest;

import dao.ChucVuDao;
import javax.inject.Inject;
import models.ChucVu;
import org.junit.Before;
import org.junit.Test;
import play.i18n.Messages;
import play.modules.guice.InjectSupport;
import play.test.Fixtures;
import play.test.UnitTest;
import utils.FeedBack;

/**
 *
 * @author Nguyen Bao Ngoc
 */
@InjectSupport
public class ChucVuDaoTest extends UnitTest {
    public String iError; // input error message
    
    // Anotate @Inject de su dung guice dependency injection
    @Inject
    static ChucVuDao chucVuDao;
    
    // Anotate @Before de danh dau function se chay truoc cac function khac
    @Before
    // Khoi tao data
    public void setup () {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data/chucVuData.yml");
        this.iError = Messages.get("Error_InputError!");
    }
    
    // Test create Chuc Vu moi
    @Test
    public void createTest () {
        // Khoi tao data
        ChucVu chucVu = new ChucVu();
        chucVu.ten = "Chuc Vu Moi";
        
        // Truong hop dung
        FeedBack ketQua = chucVuDao.create(chucVu);
        assertTrue(ketQua.isSuccess);
        assertEquals("Chuc Vu Moi", ((ChucVu)ketQua.data).ten);
        
        // Truong hop sai, chucVu.ten la xau rong~
        chucVu.ten = "";
        ketQua = chucVuDao.create(chucVu);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop null
        ketQua = chucVuDao.create(null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }
    
    @Test
    public void editTest () {
        // Khoi tao data dung
        ChucVu chucVuCuDung = ChucVu.all().first();
        ChucVu chucVuMoiDung = new ChucVu();
        chucVuMoiDung.ten = "Chuc Vu Moi day";
        
        // Khoi tao data sai
        ChucVu chucVuCuSai = new ChucVu();
        ChucVu chucVuMoiSai = new ChucVu();
        chucVuMoiSai.ten = "";
        
        // Truong hop dung
        FeedBack ketQua = chucVuDao.edit(chucVuMoiDung, chucVuCuDung);
        assertTrue(ketQua.isSuccess);
        assertEquals("Chuc Vu Moi day", ((ChucVu)ketQua.data).ten);
        
        // Truong hop chucVuCu null
        ketQua = chucVuDao.edit(chucVuMoiDung, null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop chucVuMoi null
        ketQua = chucVuDao.edit(null, chucVuCuDung);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop chucVuCu khong co trong database
        ketQua = chucVuDao.edit(chucVuMoiDung, chucVuCuSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop chucVuMoi.ten la xau rong
        ketQua = chucVuDao.edit(chucVuMoiSai, chucVuCuDung);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
    }

    // Test delete Chuc Vu
    @Test
    public void deleteTest() {
        // Khoi tao data dung
        ChucVu chucVu = ChucVu.all().first();
        
        // Khoi tao data sai
        ChucVu chucVuSai = new ChucVu();
        
        // Truong hop dung
        FeedBack ketQua = chucVuDao.delete(chucVu);
        assertTrue(ketQua.isSuccess);
        assertNotNull(ketQua.data);
        
        // Truong hop sai, khong tim thay Chuc Vu trong database
        ketQua = chucVuDao.delete(chucVuSai);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
        // Truong hop null
        ketQua = chucVuDao.delete(null);
        assertFalse(ketQua.isSuccess);
        assertEquals(iError, ketQua.message);
        
    }
}