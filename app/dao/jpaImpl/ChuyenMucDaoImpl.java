/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import dao.*;
import models.*;
import utils.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.i18n.*;
import utils.helpers.*;

/**
 *
 * @author Nguyen Bao Ngoc
 */
public class ChuyenMucDaoImpl implements ChuyenMucDao {

    public int minLengthName = 1;

    // Tao chuyen muc moi
    public FeedBack create(ChuyenMuc chuyenMuc) {
        FeedBack result = new FeedBack(false, "", null);
        StringHelper stringHelper = new StringHelper();

        if (chuyenMuc == null) { // kiem tra neu ko co chuyenMuc, return error
            result.message = Messages.get("Error_InputError!");
        } else if (stringHelper.validateStringSize(chuyenMuc.ten, -1, minLengthName).isSuccess == false) { // kiem tra ten # rong~
            result.message = Messages.get("Error_InputError!");
        } else { // neu co chuyenMuc, luu lai vao database
            result.data = chuyenMuc.save();

            if (result.data == null) { // kiem tra lai 1 lan nua, neu ko save dc thi bao loi
                result.message = Messages.get("Error_CanNotSave");
            } else { // bao success neu save dc
                result.isSuccess = true;
            }
        }

        return result;
    }

    public FeedBack edit(ChuyenMuc chuyenMucMoi, ChuyenMuc chuyenMucCu) {
        FeedBack result = new FeedBack(false, "", null);
        StringHelper stringHelper = new StringHelper();

        if (chuyenMucCu == null) { // kiem tra neu chuyenMucCu null thi bao loi
            result.message = Messages.get("Error_InputError!");
        } else {
            if (chuyenMucMoi == null) { // kiem tra neu chuyenMucMoi null thi bao loi
                result.message = Messages.get("Error_InputError!");
            } else if (stringHelper.validateStringSize(chuyenMucMoi.ten, -1, minLengthName).isSuccess == false) { // kiem tra chuyenMucMoi.ten # ""
                result.message = Messages.get("Error_InputError!");
            } else { // save change
                ChuyenMuc tmp = null;
                
                try { // tim doi tuong chuyenMucCu trong database
                    tmp = ChuyenMuc.findById(chuyenMucCu.id);
                } catch (Exception e) { // neu ko tim thay doi tuong, return error message
                    result.message = Messages.get("Error_InputError!");
                    return result;
                }
                
                // neu tim thay, thay doi thong tin va save
                result.message = Messages.get("Error_InputError!");
                tmp.ten = chuyenMucMoi.ten;
                result.data = tmp.save();

                if (result.data == null) { // kiem tra lai 1 lan nua, neu ko save dc thi bao loi
                    result.message = Messages.get("Error_CanNotSave");
                } else { // bao success neu save dc
                    result.isSuccess = true;
                }
            }
        }
        return result;
    }

    public FeedBack delete(ChuyenMuc chuyenMuc) {
        FeedBack result = new FeedBack(false, "", null);
        
        if (chuyenMuc == null) { // kiem tra neu chuyenMuc null, bao' loi~
            result.message = Messages.get("Error_InputError!");
        } else {
            ChuyenMuc tmp = null;
            
            try { // tim doi tuong chuyenMuc trong database
                tmp = ChuyenMuc.findById(chuyenMuc.id);
            } catch(Exception e) { // neu ko thay, return error message
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // neu tim thay, delete doi tuong
            result.data = tmp.delete();
            
            // kiem tra xem da delete dc chua
            if (result.data == null) { // neu delete dc
                result.message = Messages.get("Error_CanNotDelete");
            } else { // neu ko delete dc
                result.isSuccess = true;
            }
        }
        
        return result;
    }
}