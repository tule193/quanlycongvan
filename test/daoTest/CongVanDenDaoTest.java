/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTest;

import dao.CongVanDenDao;
import javax.inject.Inject;
import models.*;
import org.junit.Before;
import org.junit.Test;
import play.db.jpa.GenericModel;
import play.modules.guice.InjectSupport;
import play.test.*;

/**
 *
 * @author to_viet_anh
 */
@InjectSupport
public class CongVanDenDaoTest extends UnitTest {
    @Inject
    CongVanDenDao congVanDenDao;
    
    @Before
    public void setup(){
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data/chucVuData.yml");
        Fixtures.loadModels("data/phongBanData.yml");
        Fixtures.loadModels("data/UserData.yml");
        Fixtures.loadModels("data/congVanDenData.yml");
    }
    @Test
    public void importantTest(){
        Long num = CongVanDen.count();
        assertEquals("4", num.toString());
    }
}
