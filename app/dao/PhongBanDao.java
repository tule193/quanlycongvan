/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import utils.*;
import models.*;
import play.db.jpa.GenericModel.JPAQuery;

/**
 *
 * @author Nguyen Bao Ngoc
 */
public interface PhongBanDao {
    
    // tao Phong Ban moi
    public FeedBack create(PhongBan phongBan);
    
    // sua thong tin Phong Ban
    public FeedBack edit(PhongBan phongBanMoi, PhongBan phongBanCu);
    
    // delete Phong Ban
    public FeedBack delete(PhongBan phongBan);
    
    //Get danh sach phong ban
    public JPAQuery getDanhSachPhongBan();
    
    //findById
    public FeedBack<PhongBan> findById(long id);
}
