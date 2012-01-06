/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;
/**
 *
 * @author to_viet_anh
 */
@Entity
public class ChucVu extends Model {
    @Required
    public String ten;

    @Override
    public String toString() {
        return this.ten;
    }
    
    
}
