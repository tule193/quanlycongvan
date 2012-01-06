/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;
import play.i18n.Messages;

/**
 *
 * @author to_viet_anh
 */
@Entity
public class User extends Model {

    //function dùng để mã hóa mật khẩu.
    public static String hashPassword(String password) {
        if (password == null) {
            return Messages.get("Error_InputError!");
        }
        String hashword = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
        }
        return pad(hashword,32,'0');
    }
    private static String pad(String s, int length, char pad) {
        StringBuilder buffer = new StringBuilder(s);
        while (buffer.length() < length) {
            buffer.insert(0, pad);
        }
        return buffer.toString();
    }
    
    @Required
    public String username;

    // password - default la 123456
    @Password
    public String password = hashPassword(String.valueOf(123456));
    @Required
    public String ho;
    public String tenDem;
    @Required
    public String ten;
    public CB capBac;
    @ManyToOne
    public PhongBan phongBan;
    @ManyToOne
    public ChucVu chucVu;
    public boolean isActive = true;
    public ROLE phanQuyen = ROLE.NHAN_VIEN;
    @OneToMany(mappedBy="user",cascade= CascadeType.ALL)
    public List<CongVanDenUser> congVanDen;
    @OneToMany(mappedBy="nguoiNhan", cascade= CascadeType.ALL)
    public List<TinNhan_NguoiNhan> tinNhan;
  
    
    // enum cap bac
    public static enum CB {
        BINH_NHAT, BINH_NHI, HOC_VIEN, HA_SI, TRUNG_SI, THUONG_SI,
        THIEU_UY, TRUNG_UY, THUONG_UY, DAI_UY, THIEU_TA, TRUNG_TA,
        THUONG_TA, DAI_TA, THIEU_TUONG, TRUNG_TUONG, THUONG_TUONG, DAI_TUONG;
        @Override
        public String toString() {
            return Messages.get(super.toString());
        }
    }
    //enum role
    public static enum ROLE{
        VAN_THU,BAN_GIAM_HIEU,TRUONG_PHONG,THU_KY,NHAN_VIEN;

        @Override
        public String toString() {
            return Messages.get(super.toString());
        }
        
    }
}