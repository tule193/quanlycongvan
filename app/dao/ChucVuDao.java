/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import models.*;
import utils.*;

/**
 *
 * @author Nguyen Bao Ngoc
 */
public interface ChucVuDao {
    
    // tao Chuc Vu moi
    public FeedBack create(ChucVu chucVu);
    
    // sua thong tin chuc vu
    public FeedBack edit (ChucVu chucVuMoi, ChucVu chucVuCu);
    
    // xoa Chuc Vu
    public FeedBack delete (ChucVu chucVu);
}