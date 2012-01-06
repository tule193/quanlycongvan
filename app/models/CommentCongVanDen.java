/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;
/**
 *
 * @author to_viet_anh
 */
@Entity
public class CommentCongVanDen extends Model{
    @ManyToOne
    @Required
    public User user;
    
    @ManyToOne
    @Required
    public CongVanDen congVanDen;
    
    @Required
    public String noiDung;
    
    @OneToMany
    public List<FileDinhKem> fileDinhKem;
    
    @Required
    public Date created;
}
