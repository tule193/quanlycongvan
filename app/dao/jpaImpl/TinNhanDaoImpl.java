/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import dao.TinNhanDao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import javax.activation.MimeType;
import models.*;
import play.Logger;
import play.db.jpa.Blob;
import play.db.jpa.GenericModel.JPAQuery;
import play.i18n.Messages;
import play.libs.MimeTypes;
import utils.FeedBack;

/**
 *
 * @author Nguyen Bao Ngoc
 */
public class TinNhanDaoImpl implements TinNhanDao {

    public JPAQuery getAllTinNhanTuNguoiNhan(String username) {
        return TinNhan_NguoiNhan.find("select tn from TinNhan_NguoiNhan tn join tn.tinNhan t where tn.nguoiNhan.username = ? order by t.created desc", username);
        //TODO: test
    }

    public int getTinNhanChuaDocTuNguoiNhan(String username) {
        return (int)Integer.parseInt(TinNhan.em().createQuery("select count(t) from TinNhan t join t.nguoiNhan nn where nn.nguoiNhan.username = :username and nn.isRead = false").setParameter("username", username).getSingleResult().toString());
        //TODO: test
    }
    
    public FeedBack daDocTinNhan(TinNhan_NguoiNhan tn) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra xem input co null khong
        if (tn == null) {
            result.message = Messages.get("Error_InputError!");
        } else {
            TinNhan_NguoiNhan tmp = null;
            
            // kiem tra xem co trong database khong
            try {
                tmp = TinNhan_NguoiNhan.findById(tn.id);
            } catch (Exception e) {
                result.message = Messages.get("Error_InputError!");
                return result;
            }
            
            // chuyen trang thai thanh da doc va save
            tmp.isRead = true;
            result.data = tmp.save();
            
            // kiem tra xem co save dc khong
            if (result.data == null) {
                result.message = Messages.get("Error_CanNotSave");
            } else {
                result.isSuccess = true;
            }
        }
        return result;
    }
    
    public FeedBack luuTinNhan(User nguoiGui, List<User> nguoiNhan, String noiDung, List<FileDinhKem> dinhKem) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra thong tin dau vao co null khong
        if (nguoiGui == null || nguoiNhan == null || noiDung == null || noiDung == "") { // neu co, return error
            result.message = Messages.get("Error_InputError!");
        } else { // neu khong, luu tin nhan
            TinNhan tinNhan = new TinNhan();
            tinNhan.nguoiGui = nguoiGui;
            tinNhan.noiDung = noiDung;
            tinNhan.created = new Date();
            tinNhan.dinhKem = dinhKem;
            
            Logger.info(String.valueOf(dinhKem.size()));
                        
            // luu tin nhan
            result.data = tinNhan.save();
            
            // kiem tra xem co save dc khong
            if (result.data == null) {
                result.message = Messages.get("Message");
            } else {
                
                // lay doi tuong tin nhan vua luu
                TinNhan tmp = (TinNhan)result.data;
                
                
                // save tin nhan cho moi doi' tuong nguoi nhan
                result.isSuccess = true;
                
                for (User u : nguoiNhan) {
                    TinNhan_NguoiNhan tn = new TinNhan_NguoiNhan();
                    
                    tn.tinNhan = tmp;
                    tn.nguoiNhan = u;
                    tn.isRead = false;
                    
                    // Luu doi tuong tin nhan
                    result.data = tn.save();
                    
                    // Kiem tra xem co save dc het khong
                    if (result.data == null) {
                        result.isSuccess = false;
                    }
                }
                
            }
        }
        
        return result;
    }

    public FeedBack xoaTinNhanTuNguoiNhan(Long id) {
        FeedBack result = new FeedBack(false, "", null);
        
        // kiem tra xem id co null khong
        if (id == null) {
            result.message = Messages.get("Error_InputError!");
        } else {
            TinNhan_NguoiNhan t = null;
            
            try {
                t = TinNhan_NguoiNhan.findById(id);
            } catch (Exception e) {
                result.message = Messages.get("Error_InputError!");
            }
            
            // xoa
            result.data = t.delete();
            
            // kiem tra xem da xoa dc chua
            if (result.data == null) {
                result.message = Messages.get("Error_CanNotDelete");
            } else {
                result.isSuccess = true;
            }
        }
        
        return result;
    }


    
}