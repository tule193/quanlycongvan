/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import models.*;
import play.db.jpa.GenericModel.JPAQuery;
import utils.FeedBack;

/**
 *
 * @author to_viet_anh
 */
public interface CongVanDiDao {
    public FeedBack<CongVanDi> findById(long id);
    public FeedBack<CongVanDi> save(CongVanDi congVanDi);    
    public FeedBack<CommentCongVanDi> save(CommentCongVanDi comment);
    public JPAQuery getAllCongVanDiByUser(String username);
    public JPAQuery getAllCongVanDi();
    public FeedBack deleteById(long id);
    public FeedBack markAsRead(long congVanDiId,String username);
    public boolean canRead(long congVanDiId,String username);
    
    public FeedBack<CongVanDiUser> save(CongVanDiUser congVanDiUser);
    public boolean isCongVanDiUser(CongVanDi congVanDi, String username);
    public boolean isRead(CongVanDi congVanDi, String username);
    public long countCongVanDiByUser(String username);
    public void markAllUnread(long congVanDiId);
}
