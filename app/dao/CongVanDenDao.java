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
 * @author to_viet_anh
 */
public interface CongVanDenDao {
    public JPAQuery getDanhSachCongVanDenByUser(String username);
    public JPAQuery getDanhSachCongVanDen();
    public FeedBack<CongVanDen> findById(Long id);
    public FeedBack<CongVanDen> save(CongVanDen congVanDen);
    public FeedBack<CongVanDen> deleteById(Long id);
    public FeedBack<CommentCongVanDen> save(CommentCongVanDen commentCongVanDen);
    public FeedBack<FileDinhKem> findFileDinhKemById(Long id);
    public FeedBack<CongVanDen> findById_all(Long id);
    public FeedBack<CongVanDenUser> save(CongVanDenUser congVanDenUser);
    public FeedBack markAsRead(long CongVanDenId,String username);
    public boolean isCVDUExist(long CongVanDenId,String username);
}