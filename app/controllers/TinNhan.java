package controllers;

import javax.inject.Inject;

import dao.*;

import java.io.File;
import java.util.*;
import models.*;
import play.Logger;
import play.i18n.Messages;
import utils.helpers.*;
import play.modules.guice.*;
import play.modules.paginate.ModelPaginator;
import play.modules.paginate.ValuePaginator;
import play.mvc.*;
import utils.Constants;
import utils.FeedBack;

@InjectSupport
@With(Secure.class)
public class TinNhan extends AbstractController {
    public static int AUTOCOMPLETE_MAX = 10;
    public static int PAGINATION_PAGESIZE = 10;
    
	@Inject
	static TinNhanDao tinNhanDao;
        
        @Inject
        static UserDao userDao;
        
        public static void homThu() {
            // lay thong tin nguoi dung tu session
            FeedBack<User> fbUser = userDao.findByUsername(session.get("username"));
            
            // kiem tra xem da lay dc nguoi dung chua
            if (!fbUser.isSuccess) {
                //TODO: verify
            }
            
            // lay tat ca tin nhan tu username
            User currentUser = fbUser.data;
            List<models.TinNhan_NguoiNhan> tn = tinNhanDao.getAllTinNhanTuNguoiNhan(currentUser.username).fetch();
            
            // truyen tin nhan vao doi tuong paginator
            ValuePaginator paginator = new ValuePaginator(tn);
            paginator.setPageSize(PAGINATION_PAGESIZE);
            
            // lay so luong tin nhan chua doc
            int unread = 0;
            
            // mark message unread thanh read va so message unread++;
            for (models.TinNhan_NguoiNhan tnnn : tn) {
                if (tnnn != null) {
                    if(tnnn.isRead == false) {
                        tinNhanDao.daDocTinNhan(tnnn);
                        unread++;
                    }
                }
            }
            
//            render(paginator, size);
            String action = "homThu";
            render(tn, paginator, unread, action);
        }
        
        public static void delete (Long id) {
            tinNhanDao.xoaTinNhanTuNguoiNhan(id);
            flash.put(Constants.MESSAGE.SUCCESS, Messages.get("XoaTinNhanThanhCong"));
            homThu();
        }
	
	public static void taoTinNhan () {
            // lay thong tin nguoi dung tu session
            FeedBack<User> fbUser = userDao.findByUsername(session.get("username"));
            
            // kiem tra xem da lay dc nguoi dung chua
            if (!fbUser.isSuccess) {
                //TODO: verify
            }
            
            // lay tat ca tin nhan tu username
            User currentUser = fbUser.data;
            int unread = tinNhanDao.getTinNhanChuaDocTuNguoiNhan(currentUser.username);
            String action = "taoTinNhan";
            
            render(unread, action);
	}
        
        public static void luuTinNhan (String nguoiNhan, File[] files, String noiDung) {
            StringHelper stringHelper = new StringHelper();
            
            if (nguoiNhan == null || nguoiNhan.equalsIgnoreCase("")) {
                flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_EmptyNguoiNhan"));
            } else if (noiDung == null || noiDung.equalsIgnoreCase("")) {
                flash.put(Constants.MESSAGE.ERROR, Messages.get("Error_EmptyNoiDung"));
            } else {
                
                // lay thong tin nguoi gui tu session
                FeedBack<User> fbUser = userDao.findByUsername(session.get("username"));

                // kiem tra xem da lay dc nguoi gui chua
                if (!fbUser.isSuccess) {
                    //TODO: verify
                }


                User sender = fbUser.data;
                List<User> receivers = new ArrayList<User>();

                String term[] = nguoiNhan.split(",\\s*");
                for (String tmp : term) {
                    tmp = stringHelper.filterUsername(tmp);

                    if (tmp != null && tmp.equalsIgnoreCase("") == false) {
                        // tim user nguoiNhan trong database
                        FeedBack<User> fbReceiver = userDao.findByUsername(tmp);

                        // verify
                        if (!fbReceiver.isSuccess) {
                            //TODO: verify
                        }

                        User receiver = fbReceiver.data;
                        receivers.add(receiver);
                    }
                }
                
                Logger.info(files[0].getName());
                
                // Luu file dinh kem
                List<FileDinhKem> dinhKem = uploadFile(files);
                
//                 luu tin nhan
                tinNhanDao.luuTinNhan(sender, receivers, noiDung, dinhKem);
                flash.put(Constants.MESSAGE.SUCCESS, Messages.get("GuiTinNhanThanhCong"));
            }

            
            taoTinNhan();
        }
        
        public static List<AutocompleteValue> listUsers() {
            List<User> tmp = userDao.getAllUsers().fetch();
            List<AutocompleteValue> results = new ArrayList<AutocompleteValue>();
            StringHelper stringHelper = new StringHelper();
            String term = "";
            
            for (User u : tmp) {
                term = "\"" + stringHelper.fullName(u) + "\" " + u.username;
                results.add(new AutocompleteValue(stringHelper.convertToFriendlyText(term), term));
            }
            
            return results;
        }
        
        public static void autocomplete(String term) {
            String tmp[] = term.split(",\\s*");
            term = tmp[tmp.length - 1];

            List<String> response = new ArrayList<String>();
            List<String> tmp1 = new ArrayList<String>();
            List<String> tmp2 = new ArrayList<String>();

            int size = 0;

            for (AutocompleteValue u : listUsers()) {
                String label = u.label;
                String value = u.value;

                if (label.toLowerCase().startsWith(term.toLowerCase()) || value.toLowerCase().startsWith(term.toLowerCase())) {
                    tmp1.add(label);
                    size++;
                } else if (label.toLowerCase().contains(term.toLowerCase()) || value.toLowerCase().contains(term.toLowerCase())) {
                    tmp2.add(label);
                    size++;
                }
                if (size == AUTOCOMPLETE_MAX) {
                    break;
                }
            }

            response.addAll(tmp1);
            response.addAll(tmp2);

            renderJSON(response);            
        }
}