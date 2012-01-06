/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import dao.*;
import models.*;
import utils.*;
import utils.helpers.*;
import play.i18n.*;
/**
 *
 * @author Nguyen Bao Ngoc
 */
public class ChucVuDaoImpl implements ChucVuDao {
    public int minLengthName = 1;

    public FeedBack create(ChucVu chucVu) {
        FeedBack result = new FeedBack(false, "", null);
        StringHelper stringHelper = new StringHelper();
        
        if (chucVu == null) { // kiem tra xem chucVu co null khong, neu co return loi~
            result.message = Messages.get("Error_InputError!");
        } else if ((stringHelper.validateStringSize(chucVu.ten, -1, minLengthName)).isSuccess == false) { // kiem tra xem chucVu.ten co la xau rong~ ko
            result.message = Messages.get("Error_InputError!");
        } else { // neu khong loi, save vao database
            result.data = chucVu.save();
            
            // kiem tra lai xem co save dc khong
            if (result.data == null) { // neu khong save dc, bao loi~
                result.message = Messages.get("Error_InputError!");
            } else { // neu save dc, bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }

    public FeedBack edit(ChucVu chucVuMoi, ChucVu chucVuCu) {
        FeedBack result = new FeedBack(false, "", null);
        StringHelper stringHelper = new StringHelper();
        
        if (chucVuCu == null || chucVuMoi == null) { // kiem tra xem chucVuCu va chucVuMoi co null khong, neu co return loi~
            result.message = Messages.get("Error_InputError!");
        } else if ((stringHelper.validateStringSize(chucVuMoi.ten, -1, minLengthName)).isSuccess == false) { // kiem tra xem chucVuMoi.ten co null khong, neu co return loi~
            result.message = Messages.get("Error_InputError!");
        } else { // neu ko loi, bat dau save change
            
            // tim doi tuong chucVuCu trong database
            ChucVu tmp = null;
            
            // kiem tra xem co tim thay doi tuong chucVuCu trong database khong
            try {
                tmp = ChucVu.findById(chucVuCu.id);
            } catch (Exception e) { // neu khong thay, return loi~
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // neu tim thay, thay doi thong tin va save change
            tmp.ten = chucVuMoi.ten;
            result.data = tmp.save();
            
            // kiem tra lai xem save dc chua
            if (result.data == null) { // neu khong save duoc, bao loi
                result.message = Messages.get("Error_InputError!");
            } else { // neu tim thay, bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }

    public FeedBack delete(ChucVu chucVu) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra xem chucVu co null khong
        if (chucVu == null) { // neu null return error
            result.message = Messages.get("Error_InputError!");
        } else { // neu khong null
            
            // tim doi tuong chucVu trong database
            ChucVu tmp = null;
            
            // kiem tra xem co trong database khong
            try {
                tmp = ChucVu.findById(chucVu.id);
            } catch (Exception e) {
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // neu co, delete
            result.data = tmp.delete();
            
            // kiem tra lai xem co delete dc khong
            if (result.data == null) { // neu khong delete duoc, bao loi
                result.message = Messages.get("Error_InputError!");
            } else { // neu delete duoc, bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }
    
}