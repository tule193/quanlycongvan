/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 *
 * @author Nguyen Bao Ngoc
 */
@Entity
public class TinNhan_NguoiNhan extends Model {
    @ManyToOne
    @Required
    public TinNhan tinNhan;
    @ManyToOne
    @Required
    public User nguoiNhan;
    
    public boolean isRead;
}
