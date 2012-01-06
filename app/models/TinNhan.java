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
public class TinNhan extends Model{
    @Required
    public String noiDung;
    @ManyToOne
    public User nguoiGui;
    @OneToMany(mappedBy="tinNhan", cascade=CascadeType.ALL)
    public List<TinNhan_NguoiNhan> nguoiNhan;
    @ManyToMany(cascade= CascadeType.ALL)
    public List<FileDinhKem> dinhKem;
    public Date created;
}