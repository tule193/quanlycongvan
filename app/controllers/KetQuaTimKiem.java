package controllers;

import models.CongVanDen;

public class KetQuaTimKiem {
	
	public String id;
	public String tieuDe;
	public String highlight;
	public int loaiKetQua = TYPE_KET_QUA.CONG_VAN_DEN;
	
    public class TYPE_KET_QUA {
        public static final int CONG_VAN_DEN = 0;
        public static final int CONG_VAN_DI = 1;
        public static final int LICH = 2;
    }
	
	public KetQuaTimKiem(String id, String tieuDe, String highlight, int loaiCongVan) {
		this.id = id;
		this.tieuDe = tieuDe;
		this.highlight = highlight;
		this.loaiKetQua = loaiCongVan;
	}
}
