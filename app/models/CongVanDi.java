/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.*;
import java.util.*;

import play.db.jpa.*;
import play.i18n.Messages;
import models.*;
import models.CongVanDen.TRANG_THAI_VAN_BAN;
import play.data.validation.Required;

/**
 * 
 * @author to_viet_anh
 */
@Entity
public class CongVanDi extends Model {

    // Thong tin danh cho van thu dien them khi gui di
    public String soKyHieu;
    public Date ngayThangGui;
    public String loaiCongVan;    
    // Thong tin danh cho nguoi tao van ban dien
    public String tieuDe;
    @Lob
    public String trichYeu;
    public int mucDoMat = MUC_DO_MAT.MAT;
    public int mucDoKhan = MUC_DO_KHAN.KHAN;
    public int soTrang;
    @Required
    @ManyToOne
    public User nguoiTao;
    @ManyToOne
    public User nguoiPheDuyet;
    public String noiNhan;
    @Required
    public Blob congVanDinhKem;
    // others
    @OneToMany(mappedBy = "congVanDi", cascade = CascadeType.ALL)
    public List<CommentCongVanDi> comments;
    @OneToMany(mappedBy = "congVanDi", cascade = CascadeType.ALL)
    public List<CongVanDiUser> nhanVien;
    public TRANG_THAI_CV trangThai = TRANG_THAI_CV.DA_TAO;

    public class MUC_DO_MAT {

        public static final int MAT = 0;
        public static final int TUYET_MAT = 1;
        public static final int TOI_MAT = 2;
    }

    public class MUC_DO_KHAN {

        public static final int KHAN = 0;
        public static final int THUONG_KHAN = 1;
        public static final int HOA_TOC = 2;
    }   

    public enum TRANG_THAI_CV {

        DA_TAO, DA_GUI_TRUONG_PHONG, DA_GUI_BGH, DA_PHE_DUYET, DA_LUU_VA_GUI_DI;

        @Override
        public String toString() {
            return Messages.get(super.toString());
        }
    }
}
