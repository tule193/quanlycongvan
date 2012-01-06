/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.FileNotFoundException;
import play.mvc.*;
import java.util.*;
import utils.*;
import models.*;
import play.data.binding.*;
import play.modules.elasticsearch.ElasticSearchPlugin;
import play.modules.guice.InjectSupport;
import dao.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Inject;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import play.Logger;
import play.db.jpa.Blob;
import play.i18n.Messages;
import play.libs.MimeTypes;
import utils.helpers.StringHelper;

/**
 *
 * @author to_viet_anh
 */
@InjectSupport
@With(Secure.class)
public class QuanLyCongVanDen extends AbstractController {

    @Inject
    static CongVanDenDao congVanDenDao;
    @Inject
    static UserDao userDao;
    @Inject
    static PhongBanDao phongBanDao;

    public static void taoCongVanDen(File congVanDinhKem) {
        String command = params.get("submit");
        List<PhongBan> dsPhongBan = phongBanDao.getDanhSachPhongBan().fetch();
        if (command == null) {
            render(dsPhongBan);
        }
        if (command.equals("create")) {
            //trường hợp người dùng yêu cầu tạo mới
            CongVanDen cvd = new CongVanDen();
            //bind dữ liệu vào đối tượng CongVanDen cvd
            Binder.bind(cvd, "CongVanDen", params.all());
            //bind file
            if (congVanDinhKem != null) {
                try {
                    cvd.congVanDinhKem = new Blob();
                    cvd.congVanDinhKem.set(new FileInputStream(congVanDinhKem), MimeTypes.getContentType(congVanDinhKem.getName()));
                } catch (FileNotFoundException ex) {
                    flash.put(Constants.MESSAGE.ERROR, Messages.get("TepDinhKemChuaDuocUploadViCoLoiXayRa.XinHayKiemTraLai"));
                }
            }
            //pre-save
            FeedBack<CongVanDen> result = congVanDenDao.save(cvd);
            if (!result.isSuccess) {
                flash.put(Constants.MESSAGE.ERROR, Messages.get("TaoMoiCongVanDenKhongThanhCong.XinHayThuLaiSau"));
                render(dsPhongBan);
            }
            cvd = result.data;
            //bind user - lay du lieu
            String chiHuy = params.get("CongVanDen.thuTruongPheDuyet");
            long phongBanId = Long.parseLong(params.get("CongVanDen.phongBan"));
            String nguoiThucHien = params.get("CongVanDen.nhanVienThucHienChinh");
            String nguoiHoTro = params.get("CongVanDen.nhanVienHoTro");
            //chi huy
            if (chiHuy != null) {
                String uname = StringHelper.filterUsername(chiHuy);
                if (uname != null) {
                    FeedBack<User> fb = userDao.findByUsername(uname);
                    if (fb.isSuccess) {
                        //luu thong tin thu Truong phe duyet
                        cvd.thuTruongPheDuyet = fb.data;
                        //luu thong tin vao CongVanDenUser
                        CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, cvd);
                        cvdu.type = CongVanDenUser.TYPE.CHI_HUY;
                        congVanDenDao.save(cvdu);
                    }
                }
            }
            //phong ban
            if (phongBanId > 0) {
                FeedBack<PhongBan> fb = phongBanDao.findById(phongBanId);
                if (fb.isSuccess) {
                    //luu thong tin phong ban
                    cvd.phongBan = fb.data;
                    //luu thong tin vao CongVanDenUser
                    List<User> truongPhongVathuKy = userDao.getTruongPhong(fb.data).fetch();
                    for (User u : truongPhongVathuKy) {
                        CongVanDenUser cvdu = new CongVanDenUser(false, u, cvd);
                        cvdu.type = CongVanDenUser.TYPE.TRUONG_PHONG;
                        congVanDenDao.save(cvdu);
                    }
                }
            }
            //thuc hien chinh
            if (nguoiThucHien != null) {
                String uname = StringHelper.filterUsername(nguoiThucHien);
                FeedBack<User> fb = userDao.findByUsername(uname);
                if (fb.isSuccess) {
                    //luu thong tin nguoi thuc hien chinh
                    cvd.nhanVienThucHien = fb.data;
                    //luu thong tin vao CongVanDenUser
                    CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, cvd);
                    cvdu.type = CongVanDenUser.TYPE.NHAN_VIEN_THUC_HIEN;
                    congVanDenDao.save(cvdu);
                }
            }
            //ho tro
            String term[] = nguoiHoTro.split(",\\s*");
            for (String uname : term) {
                uname = StringHelper.filterUsername(uname);
                if (uname != null && !uname.equals("")) {
                    FeedBack<User> fb = userDao.findByUsername(uname);
                    if (fb.isSuccess) {
                        //luu thong tin vao CongVanDenUser
                        CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, cvd);
                        cvdu.type = CongVanDenUser.TYPE.NHAN_VIEN;
                        congVanDenDao.save(cvdu);
                    }
                }
            }
            //kiem tra loai cong van
            int loaiCongVan = Integer.parseInt(params.get("LoaiVanBan"));
            //neu la qua ban giam hieu thi chuyen sang trang thai da luu
            if (loaiCongVan == CongVanDen.LOAI_VAN_BAN.GUI_QUA_BGH) {
                cvd.trangThai = CongVanDen.TRANG_THAI_VAN_BAN.DA_LUU;
            } else //neu la chuyen den phong ban thi chuyen sang trang thai da xin y kien
            if (loaiCongVan == CongVanDen.LOAI_VAN_BAN.GUI_PHONG_BAN) {
                cvd.trangThai = CongVanDen.TRANG_THAI_VAN_BAN.DA_XIN_Y_KIEN;
            } else //neu la chuyen den ca nhan thi chuyen sang trang thai da phan cong
            if (loaiCongVan == CongVanDen.LOAI_VAN_BAN.GUI_CA_NHAN) {
                cvd.trangThai = CongVanDen.TRANG_THAI_VAN_BAN.DA_PHAN_CONG;
            }
            //last save
            result = congVanDenDao.save(cvd);
            if (result.isSuccess) {
                flash.put(Constants.MESSAGE.SUCCESS, Messages.get("TaoMoiCongVanDenThanhCong"));
                danhSachCongVanDen();
            } else {
                flash.put(Constants.MESSAGE.ERROR, Messages.get("TaoMoiCongVanDenKhongThanhCong.XinHayThuLaiSau"));
            }
        }
        render(dsPhongBan);
    }

    public static void danhSachCongVanDen() {
        List<CongVanDen> dsCongVan = null;
        if (session.get("role").equals(User.ROLE.VAN_THU.toString())) {
            dsCongVan = congVanDenDao.getDanhSachCongVanDen().fetch();
        } else {
            dsCongVan = congVanDenDao.getDanhSachCongVanDenByUser(session.get("username")).fetch();
        }
        render(dsCongVan);
    }

    public static void chinhSuaCongVanDen(Long id) {
        //lay du lieu cong van den
        FeedBack<CongVanDen> fbCVD = congVanDenDao.findById(id);
        //verify de chac chan lay duoc cong van den
        if (!fbCVD.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
        CongVanDen currentCongVanDen = fbCVD.data;
        //check quyen nguoi dung
        //neu la van thu thi cho sua tat ca cac truong
        if (session.get("role").equals(User.ROLE.VAN_THU.toString())) {
            Binder.bind(currentCongVanDen, "CongVanDen", params.all());
            //bind user - lay du lieu
            String chiHuy = params.get("CongVanDen.thuTruongPheDuyet");
            long phongBanId = Long.parseLong(params.get("CongVanDen.phongBan"));
            String nguoiThucHien = params.get("CongVanDen.nhanVienThucHienChinh");
            String nguoiHoTro = params.get("CongVanDen.nhanVienHoTro");
            //xoa het thong tin lien quan
            //chi huy
            if (chiHuy != null) {
                String uname = StringHelper.filterUsername(chiHuy);
                if (uname != null) {
                    FeedBack<User> fb = userDao.findByUsername(uname);
                    if (fb.isSuccess) {
                        //luu thong tin thu Truong phe duyet
                        currentCongVanDen.thuTruongPheDuyet = fb.data;
                        //luu thong tin vao CongVanDenUser
                        if (!congVanDenDao.isCVDUExist(currentCongVanDen.id, uname)) {
                            CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, currentCongVanDen);
                            cvdu.type = CongVanDenUser.TYPE.CHI_HUY;
                            congVanDenDao.save(cvdu);
                        }
                    }
                }
            }
            //phong ban
            if (phongBanId > 0) {
                FeedBack<PhongBan> fb = phongBanDao.findById(phongBanId);
                if (fb.isSuccess) {
                    //luu thong tin phong ban
                    currentCongVanDen.phongBan = fb.data;
                    //luu thong tin vao CongVanDenUser
                    List<User> truongPhongVathuKy = userDao.getTruongPhong(fb.data).fetch();
                    for (User u : truongPhongVathuKy) {
                        if (!congVanDenDao.isCVDUExist(currentCongVanDen.id, u.username)) {
                            CongVanDenUser cvdu = new CongVanDenUser(false, u, currentCongVanDen);
                            cvdu.type = CongVanDenUser.TYPE.TRUONG_PHONG;
                            congVanDenDao.save(cvdu);
                        }
                    }
                }
            }
            //thuc hien chinh
            if (nguoiThucHien != null) {
                String uname = StringHelper.filterUsername(nguoiThucHien);
                FeedBack<User> fb = userDao.findByUsername(uname);
                if (fb.isSuccess) {
                    //luu thong tin nguoi thuc hien chinh
                    currentCongVanDen.nhanVienThucHien = fb.data;
                    //luu thong tin vao CongVanDenUser
                    if (!congVanDenDao.isCVDUExist(currentCongVanDen.id, uname)) {
                        CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, currentCongVanDen);
                        cvdu.type = CongVanDenUser.TYPE.NHAN_VIEN_THUC_HIEN;
                        congVanDenDao.save(cvdu);
                    }
                }
            }
            //ho tro
            String term[] = nguoiHoTro.split(",\\s*");
            for (String uname : term) {
                uname = StringHelper.filterUsername(uname);
                if (uname != null && !uname.equals("")) {
                    FeedBack<User> fb = userDao.findByUsername(uname);
                    if (fb.isSuccess) {
                        //luu thong tin vao CongVanDenUser
                        if (!congVanDenDao.isCVDUExist(currentCongVanDen.id, uname)) {
                            CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, currentCongVanDen);
                            cvdu.type = CongVanDenUser.TYPE.NHAN_VIEN;
                            congVanDenDao.save(cvdu);
                        }
                    }
                }
            }
        } //neu la truong phong thi cho sua nguoi thuc hien va nguoi tham gia
        else if (session.get("role").equals(User.ROLE.TRUONG_PHONG.toString())
                || session.get("role").equals(User.ROLE.THU_KY.toString())) {
            //bind user
            String nguoiThucHien = params.get("CongVanDen.nhanVienThucHienChinh");
            String nguoiHoTro = params.get("CongVanDen.nhanVienHoTro");
            //thuc hien chinh
            if (nguoiThucHien != null) {
                String uname = StringHelper.filterUsername(nguoiThucHien);
                FeedBack<User> fb = userDao.findByUsername(uname);
                if (fb.isSuccess) {
                    //luu thong tin nguoi thuc hien chinh
                    currentCongVanDen.nhanVienThucHien = fb.data;
                    //luu thong tin vao CongVanDenUser
                    if (!congVanDenDao.isCVDUExist(currentCongVanDen.id, uname)) {
                        CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, currentCongVanDen);
                        cvdu.type = CongVanDenUser.TYPE.NHAN_VIEN_THUC_HIEN;
                        congVanDenDao.save(cvdu);
                    }
                }
            }
            //ho tro
            String term[] = nguoiHoTro.split(",\\s*");
            for (String uname : term) {
                uname = StringHelper.filterUsername(uname);
                if (uname != null && !uname.equals("")) {
                    FeedBack<User> fb = userDao.findByUsername(uname);
                    if (fb.isSuccess) {
                        //luu thong tin vao CongVanDenUser
                        if (!congVanDenDao.isCVDUExist(currentCongVanDen.id, uname)) {
                            CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, currentCongVanDen);
                            cvdu.type = CongVanDenUser.TYPE.NHAN_VIEN;
                            congVanDenDao.save(cvdu);
                        }
                    }
                }
            }
        } //neu la nhan vien lien quan den cong van thi cho them nguoi lien quan
        else if (session.get("role").equals(User.ROLE.NHAN_VIEN.toString())) {
            //bind user
            String nguoiHoTro = params.get("CongVanDen.nhanVienHoTro");
            //ho tro
            String term[] = nguoiHoTro.split(",\\s*");
            for (String uname : term) {
                uname = StringHelper.filterUsername(uname);
                if (uname != null && !uname.equals("")) {
                    FeedBack<User> fb = userDao.findByUsername(uname);
                    if (fb.isSuccess) {
                        //luu thong tin vao CongVanDenUser
                        if (!congVanDenDao.isCVDUExist(currentCongVanDen.id, uname)) {
                            CongVanDenUser cvdu = new CongVanDenUser(false, fb.data, currentCongVanDen);
                            cvdu.type = CongVanDenUser.TYPE.NHAN_VIEN;
                            congVanDenDao.save(cvdu);
                        }
                    }
                }
            }
        } //neu la ban giam hieu thi khong cho lam gi
        else if (session.get("role").equals(User.ROLE.BAN_GIAM_HIEU.toString())) {
            //khong lam gi
        }
        //ghi du lieu vao database
        fbCVD = congVanDenDao.save(currentCongVanDen);
        if (!fbCVD.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
        } else {
            flash.put(Constants.MESSAGE.SUCCESS, Messages.get("LuuCongVanDenThanhCong"));
        }
        xemCongVanDen(id);
    }

    public static void xoa(Long id) {
        FeedBack<CongVanDen> result = congVanDenDao.deleteById(id);
        danhSachCongVanDen();
    }

    public static void xemCongVanDen(Long id) {
        //query cong van den
        FeedBack<CongVanDen> result = congVanDenDao.findById(id);
        if (!result.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
        CongVanDen cvd = result.data;
        //mark as read
        if (!congVanDenDao.markAsRead(id, session.get("username")).isSuccess) {
            Logger.info("Loi mark as read");
        };
        List<PhongBan> dsPhongBan = phongBanDao.getDanhSachPhongBan().fetch();
        render(cvd, dsPhongBan);
    }

    public static void taoComment(File[] fileDinhKem) {
        Long currentCongVanDenID = Long.parseLong(params.get("commentCongVanDen.congVanDenID").toString());
        FeedBack<CongVanDen> fbCVD = congVanDenDao.findById(currentCongVanDenID);
        //verify de chac chan lay duoc cong van den
        if (!fbCVD.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
        CongVanDen currentCongVanDen = fbCVD.data;
        //lay thong tin nguoi dung dang 
        FeedBack<User> fbUser = userDao.findByUsername(session.get("username"));
        //verify de chac chan da lay duoc nguoi dung
        if (!fbUser.isSuccess) {
            flash.put(utils.Constants.MESSAGE.ERROR, Messages.get("GuiYKienDongGopKhongThanhCong.XinHayThuLai"));
            xemCongVanDen(currentCongVanDenID);
        }
        User currentUser = fbUser.data;
        //tao comment moi va bind du lieu
        CommentCongVanDen comment = new CommentCongVanDen();
        comment.congVanDen = currentCongVanDen;
        comment.user = currentUser;
        comment.created = new Date();
        comment.noiDung = params.get("commentCongVanDen.noidung");
        //verify de chac chan nguoi dung da nhap noi dung
        if (comment.noiDung == null || comment.noiDung.equals("")) {
            flash.put(utils.Constants.MESSAGE.ERROR, Messages.get("GuiYKienDongGopKhongThanhCong.XinHayThuLai"));
            xemCongVanDen(currentCongVanDenID);
        }
        //ghi du lieu vao database
        FeedBack<CommentCongVanDen> result = congVanDenDao.save(comment);
        if (result.isSuccess) {
            flash.put(utils.Constants.MESSAGE.SUCCESS, Messages.get("GuiYKienDongGopThanhCong").toString());
        } else {
            flash.put(utils.Constants.MESSAGE.ERROR, Messages.get("GuiYKienDongGopKhongThanhCong.XinHayThuLai"));
        }
        xemCongVanDen(currentCongVanDenID);
    }

    public static void downloadCongVanDinhKem(Long id) {
        if (id == null) {
            flash.put(utils.Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiTepDinhKem.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
        FeedBack<CongVanDen> result = congVanDenDao.findById(id);
        if (result.isSuccess) {
            renderBinary(result.data.congVanDinhKem.get(), result.data.tieuDe+".pdf");
        } else {
            flash.put(utils.Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiTepDinhKem.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
    }

    public static void daCoYKienChiHuy(Long id) {
        //check quyen
        if (!session.get("role").equals(User.ROLE.VAN_THU.toString())) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("ChiVanThuMoiDuocSuDungChucNangNay"));
            xemCongVanDen(id);
        }
        FeedBack<CongVanDen> result = congVanDenDao.findById(id);
        if (!result.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
        CongVanDen cvd = result.data;
        cvd.trangThai = CongVanDen.TRANG_THAI_VAN_BAN.DA_XIN_Y_KIEN;
        result = congVanDenDao.save(cvd);
        if (!result.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
            xemCongVanDen(id);
        }
        flash.put(Constants.MESSAGE.SUCCESS, Messages.get("HoanThanhXongViecXinYKienChiHuy"));
        xemCongVanDen(id);
    }

    public static void daPhanCong(Long id) {
        //check quyen
        if (!session.get("role").equals(User.ROLE.TRUONG_PHONG.toString())) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("ChiTruongPhongHoacThuKyMoiDuocSuDungChucNangNay"));
            xemCongVanDen(id);
        }
        FeedBack<CongVanDen> result = congVanDenDao.findById(id);
        if (!result.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
        CongVanDen cvd = result.data;
        cvd.trangThai = CongVanDen.TRANG_THAI_VAN_BAN.DA_PHAN_CONG;
        result = congVanDenDao.save(cvd);
        if (!result.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
            xemCongVanDen(id);
        }
        flash.put(Constants.MESSAGE.SUCCESS, Messages.get("HoanThanhXongViecXinYKienChiHuy"));
        xemCongVanDen(id);
    }

    public static void daHoanThanh(Long id) {
        FeedBack<CongVanDen> result = congVanDenDao.findById(id);
        if (!result.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
            danhSachCongVanDen();
        }
        CongVanDen cvd = result.data;
        //TODO:check quyen
        cvd.trangThai = CongVanDen.TRANG_THAI_VAN_BAN.DA_XONG;
        result = congVanDenDao.save(cvd);
        if (!result.isSuccess) {
            flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
            xemCongVanDen(id);
        }
        
		// Index
		// Indexing
		Client client = ElasticSearchPlugin.client();
		try {
			String[] nguoiLienQuan = new String[cvd.nhanVien.size()];
			int i = 0;
			for (CongVanDenUser cvdu : cvd.nhanVien) {
				nguoiLienQuan[i] = cvdu.user.username;
				i++;
			}
			
			IndexResponse indexResponse = client.prepareIndex("test", "congvanden")
					.setSource(
							jsonBuilder()
							.startObject()
								.field("id", cvd.id)
								.field("tieuDe", cvd.tieuDe)
								.field("trichYeu", cvd.trichYeu)
								.field("nguoiTao", cvd.tacGia)								
								.field("nguoiLienQuan", nguoiLienQuan)
								.field("ngayThangVanBan", cvd.ngayThangVanBan)
								.field("mucDoMat", cvd.mucDoMat)
								.field("mucDoKhan", cvd.mucDoKhan)
								.field("loaiVanBan", cvd.loaiVanBan)
								.field("soTrang", cvd.soTrang)
								.field("soKyHieu", cvd.soKyHieu)
								.field("typeCongVan", KetQuaTimKiem.TYPE_KET_QUA.CONG_VAN_DEN)
							.endObject()
							).execute().actionGet();
		} catch (ElasticSearchException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        flash.put(Constants.MESSAGE.SUCCESS, Messages.get("HoanThanhXongViecXinYKienChiHuy"));
        xemCongVanDen(id);
    }

    public static void test() {
        //test linh tinh o day
    }
}
