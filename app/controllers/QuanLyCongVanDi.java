/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import dao.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import models.*;
import play.Logger;
import play.data.binding.Binder;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.i18n.Messages;
import play.libs.MimeTypes;
import play.modules.elasticsearch.ElasticSearchPlugin;
import play.modules.guice.InjectSupport;
import play.mvc.With;
import utils.Constants;
import utils.FeedBack;

/**
 * 
 * @author to_viet_anh
 */
@InjectSupport
@With(Secure.class)
public class QuanLyCongVanDi extends AbstractController {
	@Inject
	static UserDao userDao;
	@Inject
	static CongVanDiDao congVanDiDao;

	public static void taoCongVanDi() {
		CongVanDi congVanDi = new CongVanDi();
		render(congVanDi);
	}
	
	public static void taoCongVanDi(CongVanDi congVanDi) {
		render(congVanDi);
	}

	public static void luuCongVanDi(@Required File congVanDinhKem) {
		User user = userDao.findByUsername(session.get("username")).data;
		
		// luu du lieu
		// bind du lieu
		CongVanDi congVanDi = new CongVanDi();
		Binder.bind(congVanDi, "CongVanDi", params.all());
		
		if (user == null) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("Error_UserNotFound!"));
			danhSachCongVanDi(1);
		}

		// Validate
		if (validation.hasErrors()) {
			try {
				if (Integer.valueOf(params.get("CongVanDi.tieuDe")) > 0) {
					flash.put(
							Constants.MESSAGE.ERROR,
							Messages.get("SoTrangPhaiLonHonKhong"));
				}
				else {
					flash.put(
							Constants.MESSAGE.ERROR,
							Messages.get("PhaiDienDuTatCaCacTruong"));
				}
			} catch (NumberFormatException e) {
				flash.put(
						Constants.MESSAGE.ERROR,
						Messages.get("SoTrangPhaiLaSoNguyen"));				
			}
			taoCongVanDi(congVanDi);
		}

		// bind file
		if (congVanDinhKem != null) {
			try {
				congVanDi.congVanDinhKem = new Blob();
				congVanDi.congVanDinhKem.set(
						new FileInputStream(congVanDinhKem),
						MimeTypes.getContentType(congVanDinhKem.getName()));
			} catch (FileNotFoundException ex) {
				flash.put(
						Constants.MESSAGE.ERROR,
						Messages.get("TepDinhKemChuaDuocUploadViCoLoiXayRa.XinHayKiemTraLai"));
			}
		}

		// Creator
		congVanDi.nguoiTao = user;
		if (user.phanQuyen == User.ROLE.TRUONG_PHONG) {
			congVanDi.trangThai = CongVanDi.TRANG_THAI_CV.DA_GUI_TRUONG_PHONG;
		} else if (user.phanQuyen == User.ROLE.BAN_GIAM_HIEU) {
			congVanDi.trangThai = CongVanDi.TRANG_THAI_CV.DA_GUI_BGH;
		}

		// First save - Basic information
		FeedBack<CongVanDi> result = congVanDiDao.save(congVanDi);

		// Add related User - CongVanDiUser
		// Get the current NHAN_VIEN
		new CongVanDiUser(user, congVanDi, false, CongVanDiUser.TYPE.NHAN_VIEN)
				.save();

		if (result.isSuccess) {
			flash.put(Constants.MESSAGE.SUCCESS,
					Messages.get("TaoMoiCongVanDiThanhCong"));
			danhSachCongVanDi(1);
		} else {
			flash.put(Constants.MESSAGE.ERROR, Messages
					.get("TaoMoiCongVanDiKhongThanhCong.XinHayThuLaiSau"));
		}
	}

	public static void suaCongVanDi(long id, File congVanDinhKem) {
		// Search for CongVanDi with id
		FeedBack<CongVanDi> fbCVD = congVanDiDao.findById(id);
		if (!fbCVD.isSuccess) {
			// No result
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		CongVanDi currentCongVanDi = fbCVD.data;

		// Edit CongVanDi - Only people with Role and CongVanDiUser can edit
		// Check CongVanDiUser
		if (!congVanDiDao.isCongVanDiUser(currentCongVanDi,
				session.get("username"))) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongCoQuyenVoiCongVan"));
			danhSachCongVanDi(1);
		}

		// VanThu - Edit from PheDuyet until LuuVaGuiDi
		// VanThu only edit loaiCongVan, ngayThangGui, soKyHieu
		if (session.get("role").equals(User.ROLE.VAN_THU.toString())) {
			// Check if nguoiTao role is VanThu
			if (currentCongVanDi.nguoiTao.username.equalsIgnoreCase(session
					.get("username"))) {
				// Before Phe Duyet
				if (currentCongVanDi.trangThai.compareTo(CongVanDi.TRANG_THAI_CV.DA_PHE_DUYET) < 0) {
					currentCongVanDi.tieuDe = params.get("CongVanDi.tieuDe");
					currentCongVanDi.trichYeu = params.get("CongVanDi.trichYeu");
					currentCongVanDi.noiNhan = params.get("CongVanDi.noiNhan");
					currentCongVanDi.mucDoKhan = params.get("CongVanDi.mucDoKhan",
							int.class);
					currentCongVanDi.mucDoMat = params.get("CongVanDi.mucDoMat",
							int.class);
					currentCongVanDi.soTrang = params.get("CongVanDi.soTrang",
							int.class);
					
					if (congVanDinhKem != null) {
						try {
							currentCongVanDi.congVanDinhKem.set(
									new FileInputStream(congVanDinhKem), MimeTypes
											.getContentType(congVanDinhKem
													.getName()));
						} catch (FileNotFoundException ex) {
							flash.put(
									Constants.MESSAGE.ERROR,
									Messages.get("TepDinhKemChuaDuocUploadViCoLoiXayRa.XinHayKiemTraLai"));
						}
					}					
				}
			}
			// Check trangThai for edit option
			else if (!currentCongVanDi.trangThai
					.equals(CongVanDi.TRANG_THAI_CV.DA_PHE_DUYET)) {
				flash.put(Constants.MESSAGE.ERROR,
						Messages.get("KhongSuaCongVanDuocNua"));
				xemCongVanDi(id);
			}
			
			currentCongVanDi.loaiCongVan = params.get("CongVanDi.loaiCongVan");
			currentCongVanDi.soKyHieu = params.get("CongVanDi.soKyHieu");
			currentCongVanDi.ngayThangGui = params.get(
					"CongVanDi.ngayThangGui", Date.class);
		}

		else {
			// Check trangThai for edit option
			if (currentCongVanDi.trangThai
					.compareTo(CongVanDi.TRANG_THAI_CV.DA_PHE_DUYET) >= 0) {
				flash.put(Constants.MESSAGE.ERROR,
						Messages.get("KhongSuaCongVanDuocNua"));
				xemCongVanDi(id);
			}

			currentCongVanDi.tieuDe = params.get("CongVanDi.tieuDe");
			currentCongVanDi.trichYeu = params.get("CongVanDi.trichYeu");
			currentCongVanDi.noiNhan = params.get("CongVanDi.noiNhan");
			currentCongVanDi.mucDoKhan = params.get("CongVanDi.mucDoKhan",
					int.class);
			currentCongVanDi.mucDoMat = params.get("CongVanDi.mucDoMat",
					int.class);
			currentCongVanDi.soTrang = params.get("CongVanDi.soTrang",
					int.class);

			// NguoiTao can edit attach file
			if (session.get("username").equals(currentCongVanDi.nguoiTao.username)) {
				if (congVanDinhKem != null) {
					try {
						currentCongVanDi.congVanDinhKem.set(
								new FileInputStream(congVanDinhKem), MimeTypes
										.getContentType(congVanDinhKem
												.getName()));
					} catch (FileNotFoundException ex) {
						flash.put(
								Constants.MESSAGE.ERROR,
								Messages.get("TepDinhKemChuaDuocUploadViCoLoiXayRa.XinHayKiemTraLai"));
					}
				}
			}
		}

		// Validate
		if (validation.hasErrors()) {
			try {
				if (Integer.valueOf(params.get("CongVanDi.tieuDe")) > 0) {
					flash.put(
							Constants.MESSAGE.ERROR,
							Messages.get("SoTrangPhaiLonHonKhong"));
				}
				else {
					flash.put(
							Constants.MESSAGE.ERROR,
							Messages.get("PhaiDienDuTatCaCacTruong"));
				}
			} catch (NumberFormatException e) {
				flash.put(
						Constants.MESSAGE.ERROR,
						Messages.get("SoTrangPhaiLaSoNguyen"));				
			}
			xemCongVanDi(id);
		}
		
		// unMark isRead all CongVanDiUser connection to this		
		congVanDiDao.markAllUnread(id);
		
		// Save
		currentCongVanDi.save();

		flash.put(Constants.MESSAGE.SUCCESS,
				Messages.get("LuuCongVanDiThanhCong"));
		
		xemCongVanDi(id);
	}

	public static void xemCongVanDi(long id) {
		FeedBack<CongVanDi> result = congVanDiDao.findById(id);
		if (!result.isSuccess) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		CongVanDi cvd = result.data;

		if (congVanDiDao.isCongVanDiUser(cvd, params.get("username"))) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongCoQuyenVoiCongVan"));
			danhSachCongVanDi(1);
		}

		if (!congVanDiDao.markAsRead(id, session.get("username")).isSuccess) {
			Logger.info("Loi mark as read");
		}

		render(cvd);
	}

	public static void danhSachCongVanDi(int page) {
		List<CongVanDi> dsCongVan = new ArrayList<CongVanDi>();
		List<CongVanDiUser> dsCongVanDiUser = null;
		int pageLength = 8;
		
		int pageCount = (int) congVanDiDao.countCongVanDiByUser(session.get("username")) / pageLength + 1;
		
		if (page > pageCount) {
			page = pageCount;
		}
		
		dsCongVanDiUser = congVanDiDao.getAllCongVanDiByUser(
				session.get("username")).fetch(page, pageLength);
		for (CongVanDiUser cvdu : dsCongVanDiUser) {
                        Logger.info(cvdu.congVanDi.tieuDe);
			dsCongVan.add(cvdu.congVanDi);
		}
		render(dsCongVan, page, pageCount);
	}

	public static void taoComment(File[] fileDinhKem) {
		Long currentCongVanDiID = Long.parseLong(params.get(
				"commentCongVanDi.congVanDiID").toString());
		FeedBack<CongVanDi> fbCVD = congVanDiDao.findById(currentCongVanDiID);
		if (!fbCVD.isSuccess) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		CongVanDi currentCongVanDi = fbCVD.data;

		FeedBack<User> fbUser = userDao.findByUsername(session.get("username"));

		if (!fbUser.isSuccess) {
			flash.put(utils.Constants.MESSAGE.ERROR,
					Messages.get("GuiYKienDongGopKhongThanhCong.XinHayThuLai"));
			xemCongVanDi(currentCongVanDiID);
		}
		User currentUser = fbUser.data;

		CommentCongVanDi comment = new CommentCongVanDi();
		comment.congVanDi = currentCongVanDi;
		comment.user = currentUser;
		comment.created = new Date();
		comment.noiDung = params.get("commentCongVanDi.noidung");

		if (comment.noiDung == null || comment.noiDung.equals("")) {
			flash.put(utils.Constants.MESSAGE.ERROR,
					Messages.get("GuiYKienDongGopKhongThanhCong.XinHayThuLai"));
			xemCongVanDi(currentCongVanDiID);
		}

		FeedBack<CommentCongVanDi> result = congVanDiDao.save(comment);
		if (result.isSuccess) {
			flash.put(utils.Constants.MESSAGE.SUCCESS,
					Messages.get("GuiYKienDongGopThanhCong").toString());
		} else {
			flash.put(utils.Constants.MESSAGE.ERROR,
					Messages.get("GuiYKienDongGopKhongThanhCong.XinHayThuLai"));
		}
		xemCongVanDi(currentCongVanDiID);
	}

	public static void guiTruongPhong(long id) {

		FeedBack<CongVanDi> result = congVanDiDao.findById(id);
		if (!result.isSuccess) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		CongVanDi cvd = result.data;

		// 2. Check CongVanDiUser
		if (!congVanDiDao.isCongVanDiUser(cvd, session.get("username"))) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongCoQuyenVoiCongVan"));
			xemCongVanDi(id);
		}

		// 3. Current trangThai
		if (cvd.trangThai != CongVanDi.TRANG_THAI_CV.DA_TAO) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongPhaiLucGuiTruongPhong"));
			xemCongVanDi(id);
		}

		// Change trangThai and save
		cvd.trangThai = CongVanDi.TRANG_THAI_CV.DA_GUI_TRUONG_PHONG;
		result = congVanDiDao.save(cvd);
		if (!result.isSuccess) {
			flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
			xemCongVanDi(id);
		}

		// CongVanDiUser for TruongPhong or ThuKy
		User user = userDao.findByUsername(session.get("username")).data;
		if (user == null) {
			flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotFind"));
			xemCongVanDi(id);
		}
		List<User> listTruongPhong = userDao.getTruongPhong(user.phongBan)
				.fetch();
		for (User truongPhong : listTruongPhong) {
			new CongVanDiUser(truongPhong, cvd, false,
					CongVanDiUser.TYPE.TRUONG_PHONG).save();
		}

		flash.put(Constants.MESSAGE.SUCCESS,
				Messages.get("HoanThanhXongViecGuiTruongPhong"));
		xemCongVanDi(id);
	}

	public static void guiBGH(long id) {
		// check quyen
		// 1. BGH
		if (!session.get("role").equals(User.ROLE.TRUONG_PHONG.toString())) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("ChiTruongPhongMoiDuocSuDungChucNangNay"));
			xemCongVanDi(id);
		}

		FeedBack<CongVanDi> result = congVanDiDao.findById(id);
		if (!result.isSuccess) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		CongVanDi cvd = result.data;

		// 2. Check CongVanDiUser
		if (!congVanDiDao.isCongVanDiUser(cvd, session.get("username"))) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongCoQuyenVoiCongVan"));
			xemCongVanDi(id);
		}

		// 3. Current trangThai
		if (cvd.trangThai != CongVanDi.TRANG_THAI_CV.DA_GUI_TRUONG_PHONG) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongPhaiLucGuiBGH"));
			xemCongVanDi(id);
		}

		// Change trangThai and save
		cvd.trangThai = CongVanDi.TRANG_THAI_CV.DA_GUI_BGH;
		result = congVanDiDao.save(cvd);
		if (!result.isSuccess) {
			flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
			xemCongVanDi(id);
		}

		// CongVanDiUser for BGH
		List<User> banGiamHieu = userDao.getChiHuy().fetch();
		for (User chihuy : banGiamHieu) {
			new CongVanDiUser(chihuy, cvd, false, CongVanDiUser.TYPE.BGH)
					.save();
		}

		flash.put(Constants.MESSAGE.SUCCESS,
				Messages.get("HoanThanhXongViecGuiBGH"));
		xemCongVanDi(id);
	}

	public static void pheduyet(long id) {
		// check quyen
		// 1. Ban Giam Hieu
		if (!session.get("role").equals(User.ROLE.BAN_GIAM_HIEU.toString())) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("ChiTruongPhongMoiDuocSuDungChucNangNay"));
			xemCongVanDi(id);
		}
		
		FeedBack<CongVanDi> result = congVanDiDao.findById(id);
		if (!result.isSuccess) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		CongVanDi cvd = result.data;

		// 2. CongVanDiUser
		if (!congVanDiDao.isCongVanDiUser(cvd, session.get("username"))) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongCoQuyenVoiCongVan"));
			xemCongVanDi(id);
		}

		// 3. Current trangThai
		if (cvd.trangThai != CongVanDi.TRANG_THAI_CV.DA_GUI_BGH) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongPhaiLucPheDuyet"));
			xemCongVanDi(id);
		}

		// Change trangThai and save
		// Set User PheDuyet
		cvd.trangThai = CongVanDi.TRANG_THAI_CV.DA_PHE_DUYET;

		User user = userDao.findByUsername(session.get("username")).data;
		if (user == null) {
			flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotFind"));
			xemCongVanDi(id);
		}

		cvd.nguoiPheDuyet = user;

		// CongVanDiUser for VanThu
		List<User> vanThu = userDao.getVanThu().fetch();
		for (User vanthu : vanThu) {
			if (!congVanDiDao.isCongVanDiUser(cvd, vanthu.username)) {
				new CongVanDiUser(vanthu, cvd, false, CongVanDiUser.TYPE.VAN_THU)
						.save();
			}
		}

		result = congVanDiDao.save(cvd);
		if (!result.isSuccess) {
			flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
			xemCongVanDi(id);
		}
		flash.put(Constants.MESSAGE.SUCCESS,
				Messages.get("HoanThanhXongViecPheDuyet"));
		xemCongVanDi(id);
	}

	public static void luuVaGuiDi(long id) {
		// check quyen
		// 1. VAN THU
		if (!session.get("role").equals(User.ROLE.VAN_THU.toString())) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("ChiVanThuMoiDuocSuDungChucNangNay"));
			xemCongVanDi(id);
		}

		FeedBack<CongVanDi> result = congVanDiDao.findById(id);
		if (!result.isSuccess) {
			flash.put(
					Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiCongVanHoacCongVanDaBiXoa.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		CongVanDi cvd = result.data;

		// 2. Current trangThai
		if (cvd.trangThai != CongVanDi.TRANG_THAI_CV.DA_PHE_DUYET) {
			flash.put(Constants.MESSAGE.ERROR,
					Messages.get("KhongPhaiLucLuuVaGuiDi"));
			xemCongVanDi(id);
		}

		// Change trangThai and save
		cvd.trangThai = CongVanDi.TRANG_THAI_CV.DA_LUU_VA_GUI_DI;
		result = congVanDiDao.save(cvd);
		if (!result.isSuccess) {
			flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_CanNotSave"));
			xemCongVanDi(id);
		}
		
		// Index
		// Indexing
		Client client = ElasticSearchPlugin.client();
		try {
			String[] nguoiLienQuan = new String[cvd.nhanVien.size()];
			int i = 0;
			for (CongVanDiUser cvdu : cvd.nhanVien) {
				nguoiLienQuan[i] = cvdu.user.username;
				i++;
			}
			
			IndexResponse indexResponse = client.prepareIndex("test", "congvandi")
					.setSource(
							jsonBuilder()
							.startObject()
								.field("id", cvd.id)
								.field("tieuDe", cvd.tieuDe)
								.field("trichYeu", cvd.trichYeu)
								.field("nguoiTao", cvd.nguoiTao.ho + " " + cvd.nguoiTao.tenDem + " " + cvd.nguoiTao.ten)
								.field("nguoiPheDuyet", cvd.nguoiPheDuyet.ho + " " + cvd.nguoiPheDuyet.tenDem + " " + cvd.nguoiPheDuyet.ten)
								.field("nguoiLienQuan", nguoiLienQuan)
								.field("ngayThangGui", cvd.ngayThangGui)
								.field("mucDoMat", cvd.mucDoMat)
								.field("mucDoKhan", cvd.mucDoKhan)
								.field("loaiCongVan", cvd.loaiCongVan)
								.field("soTrang", cvd.soTrang)
								.field("soKyHieu", cvd.soKyHieu)
								.field("typeCongVan", KetQuaTimKiem.TYPE_KET_QUA.CONG_VAN_DI)
							.endObject()
							).execute().actionGet();
		} catch (ElasticSearchException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		flash.put(Constants.MESSAGE.SUCCESS,
				Messages.get("HoanThanhXongViecLuuVaGuiDi"));
		xemCongVanDi(id);
	}

	public static void downloadCongVanDinhKem(Long id) {
		if (id == null) {
			flash.put(utils.Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiTepDinhKem.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
		FeedBack<CongVanDi> result = congVanDiDao.findById(id);
		if (result.isSuccess) {
			renderBinary(result.data.congVanDinhKem.get(), result.data.tieuDe
					+ ".pdf");
		} else {
			flash.put(utils.Constants.MESSAGE.ERROR,
					Messages.get("KhongTonTaiTepDinhKem.XinHayKiemTraLai"));
			danhSachCongVanDi(1);
		}
	}
}
