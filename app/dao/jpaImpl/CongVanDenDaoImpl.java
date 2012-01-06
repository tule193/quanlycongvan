/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;
import dao.*;
import models.CommentCongVanDen;
import models.CongVanDen;
import models.CongVanDenUser;
import models.FileDinhKem;
import models.User;
import play.db.jpa.GenericModel.JPAQuery;
import play.i18n.Messages;
import utils.FeedBack;

/**
 *
 * @author to_viet_anh
 */
public class CongVanDenDaoImpl implements CongVanDenDao {

    public JPAQuery getDanhSachCongVanDenByUser(String username) {
        return CongVanDen.find("select distinct cvd from CongVanDen cvd join cvd.nhanVien nv where nv.user.username=?", username);
    }

    public JPAQuery getDanhSachCongVanDen() {
        return CongVanDen.all();
    }

    public FeedBack<CongVanDen> save(CongVanDen congVanDen) {
        FeedBack<CongVanDen> result = new FeedBack<CongVanDen>(false, "", null);
        try{
            result.data = congVanDen.save();
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

    public FeedBack<CongVanDen> deleteById(Long id) {
        FeedBack<CongVanDen> result = new FeedBack<CongVanDen>(false, "", null);
        try{
            if(CongVanDen.delete("byId", id)>=1){
                result.isSuccess = true;
            }else{
                result.setMessage(Messages.get("Error_CanNotDelete"));
            }
        }catch(Exception ex){
            result.setMessage(Messages.get("Error_InputError!"));
            return result;
        }
        return result;
    }

    public FeedBack<CongVanDen> findById(Long id) {
        FeedBack<CongVanDen> result = new FeedBack<CongVanDen>(false, "", null);
        if(id==null){
            result.setMessage(Messages.get("Lỗi - Thông tin đầu vào bị sai!"));
        }else{
            result.data = CongVanDen.find("select cvd from CongVanDen cvd where cvd.id=?",id).first();
            if (result.data == null) {
                result.setMessage(Messages.get("Error_CongVanDenNotFound!"));
            }else{
                result.isSuccess=true;
            }
        }
        return result;
    }

    public FeedBack<CommentCongVanDen> save(CommentCongVanDen commentCongVanDen) {
        FeedBack<CommentCongVanDen> result = new FeedBack<CommentCongVanDen>(false, "", null);
        try{
            result.data = commentCongVanDen.save();
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

    public FeedBack<FileDinhKem> findFileDinhKemById(Long id) {
        FeedBack<FileDinhKem> result = new FeedBack<FileDinhKem>(false, "", null);
        if(id==null){
            result.setMessage(Messages.get("Lỗi - Thông tin đầu vào bị sai!"));
        }else{
            result.data = FileDinhKem.findById(id);
            if (result.data == null) {
                result.setMessage(Messages.get("Error_CongVanDenNotFound!"));
            }else{
                result.isSuccess=true;
            }
        }
        return result;
    }

    public FeedBack<CongVanDen> findById_all(Long id) {
        FeedBack<CongVanDen> result = new FeedBack<CongVanDen>(false, "", null);
        if(id==null){
            result.setMessage(Messages.get("Lỗi - Thông tin đầu vào bị sai!"));
        }else{
            result.data = CongVanDen.find("select distinct cvd from CongVanDen cvd join fetch cvd.comments where cvd.id=?",id).first();
            if (result.data == null) {
                result.setMessage(Messages.get("Error_CongVanDenNotFound!"));
            }else{
                result.isSuccess=true;
            }
        }
        return result;
    }

    public FeedBack<CongVanDenUser> save(CongVanDenUser congVanDenUser) {
        FeedBack<CongVanDenUser> result = new FeedBack<CongVanDenUser>(false, "", null);
        if(congVanDenUser==null){
            result.setMessage(Messages.get("Error_InputError!"));
        }else{
            result.data = congVanDenUser.save();
            if(result.data==null){
                result.setMessage(Messages.get("Error_CanNotSave"));
            }else{
                result.isSuccess=true;
            }
        }
        return result;
    }

    public FeedBack<CongVanDenUser> markAsRead(long CongVanDenId, String username) {
        FeedBack<CongVanDenUser> result = new FeedBack(false, "", null);
        CongVanDenUser cvdu = CongVanDenUser.find("select cvdu from CongVanDenUser cvdu where cvdu.user.username=? and cvdu.congVanDen.id=?", username,CongVanDenId).first();
        if(cvdu==null){
            result.setMessage(Messages.get("Error_CanNotFind!"));
        }else{
            cvdu.isRead=true;
            result.data = cvdu.save();
            if(result.data==null){
                result.setMessage(Messages.get("Error_CanNotSave"));
            }else{
                result.isSuccess=true;
            }
        }
        return result;
    }

    public boolean isCVDUExist(long CongVanDenId, String username) {
        return (0<CongVanDenUser.find("select cvdu from CongVanDenUser cvdu where cvdu.congVanDen.id=? and cvdu.user.username=?", CongVanDenId,username).fetch().size());
    }

    
}