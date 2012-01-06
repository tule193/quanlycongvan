package dao;

import java.io.File;
import java.util.*;
import models.*;
import play.db.jpa.GenericModel.JPAQuery;
import utils.*;

public interface TinNhanDao {
        public JPAQuery getAllTinNhanTuNguoiNhan (String username);
        public FeedBack luuTinNhan (User nguoiGui, List<User> nguoiNhan, String noiDung, List<FileDinhKem> dinhKem);
        public FeedBack xoaTinNhanTuNguoiNhan (Long id);
        public int getTinNhanChuaDocTuNguoiNhan (String username);
        public FeedBack daDocTinNhan (TinNhan_NguoiNhan tn);
}