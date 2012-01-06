/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.i18n.Messages;

/**
 *
 * @author to_viet_anh
 */
@Entity
public class CongVanDenUser extends Model{
    public boolean isRead=false;
    @ManyToOne
    @Required
    public User user;
    @ManyToOne
    @Required
    public CongVanDen congVanDen;
    public TYPE type = TYPE.NHAN_VIEN;

    public CongVanDenUser(boolean isRead, User user, CongVanDen congVanDen) {
        this.isRead = isRead;
        this.user = user;
        this.congVanDen = congVanDen;
    }
    
    public enum TYPE{
        CHI_HUY,TRUONG_PHONG,NHAN_VIEN_THUC_HIEN,NHAN_VIEN;

        @Override
        public String toString() {
            return Messages.get(super.toString());
        }
        
    }
}
