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
public class HoSo extends Model{
    public String ten;
    public User userId;
    public String maHoSo;
    public String tieuDe;
    public String thoiGianBatDau;
    public String thoiGianKetThuc;
    public String thoiHanBaoQuan;
    public int soLuongTo;
    public String hanCheSuDung;
    public boolean hoSoDaKetThuc;
    public boolean daLuuTru;
}
