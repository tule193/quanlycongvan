/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.Model;
import play.i18n.Messages;

/**
 *
 * @author to_viet_anh
 */
@Entity
public class CongVanDiUser extends Model{
    @Required@ManyToOne
    public User user;
    @Required@ManyToOne
    public CongVanDi congVanDi;
    public boolean isRead = false;
    public TYPE type = TYPE.NHAN_VIEN;

    public CongVanDiUser(User user, CongVanDi congVanDi,boolean isRead,TYPE type) {
        this.user = user;
        this.congVanDi = congVanDi;
        this.isRead = isRead;
        this.type = type;
    }
    
    
    public enum TYPE{
        NHAN_VIEN,TRUONG_PHONG,BGH,VAN_THU;
        
        @Override
        public String toString() {
            return Messages.get(super.toString());
        }
    }
}
