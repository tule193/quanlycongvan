/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import java.util.List;

import dao.*;
import models.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.i18n.Messages;
import utils.FeedBack;

/**
 *
 * @author to_viet_anh
 */
public class CongVanDiDaoImpl implements CongVanDiDao {

    public FeedBack<CongVanDi> save(CongVanDi congVanDi) {
        FeedBack<CongVanDi> result = new FeedBack<CongVanDi>(false, "", null);
        if (congVanDi == null) {
            result.setMessage(Messages.get("Error_InputError!"));
        } else {
            result.data = congVanDi.save();
            if (result.data == null) {
                result.setMessage(Messages.get("Error_CanNotSave"));
            } else {
                result.isSuccess = true;
            }
        }
        return result;
    }

    public FeedBack<CommentCongVanDi> save(CommentCongVanDi commentCongVanDi) {
        FeedBack<CommentCongVanDi> result = new FeedBack<CommentCongVanDi>(false, "", null);
        try{
            result.data = commentCongVanDi.save();
        }catch(Exception ex){
            result.setMessage(Messages.get("Error_InputError!"));
            return result;
        }
        if(result.data==null){
            result.setMessage(Messages.get("Error_CanNotSave"));
        }
        result.isSuccess=true;
        return result;
    }

    public JPAQuery getAllCongVanDiByUser(String username) {
        return CongVanDiUser.find("select cvdu from CongVanDiUser cvdu where cvdu.user.username=? order by id desc", username);        
    }

    public long countCongVanDiByUser(String username) {    	
    	return CongVanDiUser.count("select count(*) from CongVanDiUser cvdu where cvdu.user.username=?", username);
    }
    
    public JPAQuery getAllCongVanDi() {
        return CongVanDi.all();
    }

    public FeedBack deleteById(long id) {
        FeedBack result = new FeedBack<CongVanDi>(false, "", null);
        int num = CongVanDi.delete("byId", id);
        if (num == 1) {
            result.isSuccess = true;
        } else if (num <= 0) {
            result.setMessage(Messages.get("Error_CanNotDelete"));
        } else if (num > 1) {
            result.setMessage("QuaNhieuBanGhiBiXoa.XinHayKiemTraLai");
        }
        return result;
    }

    public FeedBack markAsRead(long congVanDiId, String username) {
        FeedBack<CongVanDiUser> result = new FeedBack(false, "", null);
        CongVanDiUser cvdu = CongVanDiUser.find("select cvdu from CongVanDiUser cvdu where cvdu.user.username=? and cvdu.congVanDi.id=?", username, congVanDiId).first();
        if (cvdu == null) {
            result.setMessage(Messages.get("Error_CanNotFind!"));
        } else {
            cvdu.isRead = true;
            result.data = cvdu.save();
            if (result.data == null) {
                result.setMessage(Messages.get("Error_CanNotSave"));
            } else {
                result.isSuccess = true;
            }
        }
        return result;
    }
 
    public void markAllUnread(long congVanDiId) {
        List<CongVanDiUser> cvdulist = CongVanDiUser.find("select cvdu from CongVanDiUser cvdu where cvdu.congVanDi.id=?", congVanDiId).fetch();
        for (CongVanDiUser cvdu : cvdulist) {
        	cvdu.isRead = false;
        	cvdu.save();
        }
    }
    
    public boolean canRead(long congVanDiId, String username) {
        return (0 < CongVanDiUser.find("select cvdu from CongVanDiUser cvdu where cvdu.user.username=? and cvdu.congVanDi.id=?", username, congVanDiId).fetch().size());
    }

    public FeedBack<CongVanDi> findById(long id) {
        FeedBack<CongVanDi> result = new FeedBack(false, "", null);
        result.data = CongVanDi.find("select cvd from CongVanDi cvd where cvd.id=?", id).first();
        if (result.data == null) {
            result.setMessage(Messages.get("Error_CongVanDiNotFound!"));
        } else {
            result.isSuccess = true;
        }
        return result;
    }

	@Override
	public FeedBack<CongVanDiUser> save(CongVanDiUser congVanDiUser) {
        FeedBack<CongVanDiUser> result = new FeedBack<CongVanDiUser>(false, "", null);
        if(congVanDiUser==null){
            result.setMessage(Messages.get("Error_InputError!"));
        }else{
            result.data = congVanDiUser.save();
            if(result.data==null){
                result.setMessage(Messages.get("Error_CanNotSave"));
            }else{
                result.isSuccess=true;
            }
        }
        return result;
	}

    public boolean isCongVanDiUser(CongVanDi congVanDi, String username) {
    	CongVanDiUser cvdu = CongVanDiUser.find("select u from CongVanDiUser u where u.congVanDi = ? and u.user.username = ?", congVanDi, username).first();
    	if (cvdu != null) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isRead(CongVanDi congVanDi, String username) {
    	CongVanDiUser cvdu = CongVanDiUser.find("select u from CongVanDiUser u where u.congVanDi = ? and u.user.username = ?", congVanDi, username).first();
    	if (cvdu != null) {
    		if (cvdu.isRead) {
    			return true;
    		}
    	}
    	return false;
    }
}
