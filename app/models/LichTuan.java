/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
/**
 *
 * @author to_viet_anh
 */
@Entity
public class LichTuan extends Model{
    public PhongBan phongBan;
    public FileDinhKem fileDinhKem;
    public Date thoiGianBatDau;
    public Date thoiGianKetThuc;
}
