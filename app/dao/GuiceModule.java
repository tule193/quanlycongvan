/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import com.google.inject.AbstractModule;
import dao.jpaImpl.*;
/**
 *
 * @author to_viet_anh
 */
public class GuiceModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(ChuyenMucDao.class).to(ChuyenMucDaoImpl.class);
        bind(PhongBanDao.class).to(PhongBanDaoImpl.class);
        bind(ChucVuDao.class).to(ChucVuDaoImpl.class);
        bind(CongVanDenDao.class).to(CongVanDenDaoImpl.class);
        bind(CongVanDiDao.class).to(CongVanDiDaoImpl.class);
        bind(TinNhanDao.class).to(TinNhanDaoImpl.class);
    }
    
}
