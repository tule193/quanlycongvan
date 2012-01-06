/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import dao.*;
import play.db.jpa.GenericModel.JPAQuery;
import utils.*;
import utils.helpers.*;
import models.*;
import play.i18n.*;

/**
 *
 * @author Nguyen Bao Ngoc
 */
public class PhongBanDaoImpl implements PhongBanDao {
    public int minLengthName = 1;
    
    public FeedBack create(PhongBan phongBan) {
        FeedBack result = new FeedBack(false, "", null);
        StringHelper stringHelper = new StringHelper();
        
        if (phongBan == null) { // kiem tra phongBan co null khong, neu null return error
            result.message = Messages.get("Error_InputError!");
        } else if (stringHelper.validateStringSize(phongBan.ten, -1, minLengthName).isSuccess == false) { // kiem tra phongBan.ten # ""
            result.message = Messages.get("Error_InputError!");
        } else {
            // Save phongBan
            result.data = phongBan.save();
            
            // kiem tra xem co save dc khong
            if (result.data == null) { // neu khong save dc thi bao loi
                result.message = Messages.get("Error_InputError!");
            } else { // neu save dc thi bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }

    public FeedBack edit(PhongBan phongBanMoi, PhongBan phongBanCu) {
        FeedBack result = new FeedBack(false, "", null);
        StringHelper stringHelper = new StringHelper();
        
        if (phongBanCu == null || phongBanMoi == null) { // kiem tra va phongBanMoi phongBanCu co null khong, neu null return error
            result.message = Messages.get("Error_InputError!");
        } else if ((stringHelper.validateStringSize(phongBanMoi.ten, -1, minLengthName)).isSuccess == false) { // kiem tra ten cua phongBanMoi xem co null khong
            result.message = Messages.get("Error_InputError!");
        } else { // neu khong loi, save change
            
            // tim doi tuong phongBanCu trong database
            PhongBan tmp = null;
            
            // kiem tra tmp
            try { // neu null, return loi
                tmp =  PhongBan.findById(phongBanCu.id);
            } catch (Exception e) {
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // neu khong loi, save change
            tmp.ten = phongBanMoi.ten;
            result.data = tmp.save();

            // kiem tra xem co save duoc khong
            if (result.data == null) { // neu khong save dc, bao loi
                result.message = Messages.get("Error_CanNotSave");
            } else { // neu save duoc, bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }

    public FeedBack delete(PhongBan phongBan) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra xem phongBan co null ko
        if (phongBan == null) { // neu null, return loi~
            result.message = Messages.get("Error_InputError!");
        } else { // neu ko, bat dau delete
            
            // tim doi tuong phongBan trong database = tmp
            PhongBan tmp = null;
            
            // kiem tra xem co doi tuong tmp khong
            try {
                tmp = PhongBan.findById(phongBan.id);;
            } catch (Exception e) { // neu khong co, return error
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // delete
            result.data = tmp.delete();
            
            // kiem tra lai xem da delete dc chua
            if (result.data == null) { // neu chua delete dc, bao loi
                result.message = Messages.get("Error_CanNotDelete");
            } else { // neu delete duoc, bao thanh cong
                result.isSuccess = true;
            }
        }
        
        return result;
    }

    public JPAQuery getDanhSachPhongBan() {
        return PhongBan.all();
    }

    public FeedBack<PhongBan> findById(long id) {
        FeedBack<PhongBan> result = new FeedBack<PhongBan>(false, "", null);
        result.data = PhongBan.findById(id);
        if(result.data!=null){
            result.isSuccess=true;
        }else{
            result.message=Messages.get("Error_CanNotFind!");
        }
        return result;
        
    }
    
}