/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.MaxSize;
import play.db.jpa.*;
import play.i18n.Messages;

/**
 *
 * @author to_viet_anh
 */
@Entity
public class CongVanDen extends Model {

    public Long soThuTu;
    public Date ngayDen;
    public String tacGia;
    public String soKyHieu;
    public String tieuDe;
    @Lob@MaxSize(10000)
    public String trichYeu;
    public String maHoSo;
    public Date ngayThangVanBan;
    public int loaiVanBan;
    public int mucDoMat;
    public int mucDoKhan;
    public int soTrang;
    public String yKienPhanPhoi;
    public Date thoiHanGiaiQuyet;
    @ManyToOne
    public PhongBan phongBan;
    @OneToMany(mappedBy="congVanDen",cascade= CascadeType.ALL)
    public List<CongVanDenUser> nhanVien;
    @ManyToOne
    public User thuTruongPheDuyet;
    @ManyToOne
    public User nhanVienThucHien;
    public Blob congVanDinhKem;
    @OneToMany(mappedBy="congVanDen", cascade= CascadeType.ALL)
    public List<CommentCongVanDen> comments;
    public TRANG_THAI_VAN_BAN trangThai = TRANG_THAI_VAN_BAN.DA_LUU;
    
    public class LOAI_VAN_BAN {

        public static final int GUI_QUA_BGH = 0;
        public static final int GUI_PHONG_BAN = 1;
        public static final int GUI_CA_NHAN = 2;
    }

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
    
    public enum TRANG_THAI_VAN_BAN{
        DA_LUU,DA_XIN_Y_KIEN,DA_PHAN_CONG,DA_XONG;

        @Override
        public String toString() {
            return Messages.get(super.toString());
        }
        
    }

}