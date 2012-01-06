/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import utils.*;
import models.*;

/**
 *
 * @author to_viet_anh
 */
public interface ChuyenMucDao {
    
    // tao chuyen muc moi
    public FeedBack create(ChuyenMuc chuyenMuc);
    
    // edit chuyen muc
    public FeedBack edit(ChuyenMuc chuyenMucMoi, ChuyenMuc chuyenMucCu);
    
    // xoa chuyen muc
    public FeedBack delete(ChuyenMuc chuyenMuc);
    
    // kiem tra xem chuyen muc co null ko
//    public boolean isChuyenMucNull(ChuyenMuc chuyenMuc);
}