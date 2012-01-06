/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.*;
import javax.inject.Inject;
import play.modules.guice.*;
import org.h2.util.IOUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import models.*;
import play.*;
import utils.helpers.StringHelper;

/**
 *
 * @author to_viet_anh
 */
@InjectSupport
public class Ajax extends AbstractController {
    
    public static int AUTOCOMPLETE_MAX = 10;
    
    @Inject
    static PhongBanDao phongBanDao;
    @Inject
    static UserDao userDao;

    public static void userpicker() {
    }

    public static void upload(String qqFile) {
        
        if (request.isNew) {
            FileOutputStream moveTo = null;
            // Another way I used to grab the name of the file
            String filename = request.headers.get("x-file-name").value();
            try {
                InputStream data = request.body;
                moveTo = new FileOutputStream(new File(Play.getFile("").getAbsolutePath()
                        + File.separator + "public" + File.separator +"attatchments" + File.separator + filename));
                IOUtils.copy(request.body, moveTo);
                
            } catch (Exception ex) {
                play.Logger.info("loi:"+ex.getMessage());
                // catch file exception
                // catch IO Exception later on
                renderJSON("{success: false}");
            }
        }
        renderJSON("{success: true}");
    }
    
    public static List<AutocompleteValue> danhSachPhongBan() {
            List<PhongBan> tmp = phongBanDao.getDanhSachPhongBan().fetch();
            List<AutocompleteValue> results = new ArrayList<AutocompleteValue>();
            StringHelper stringHelper = new StringHelper();
            String term = "";
            
            for (PhongBan p : tmp) {
                term = p.ten;
                results.add(new AutocompleteValue(stringHelper.convertToFriendlyText(term), term));
            }
            
            return results;
        }
        
    public static void autocompletePhongBan(String term) {
            String tmp[] = term.split(",\\s*");
            term = tmp[tmp.length - 1];

            List<String> response = new ArrayList<String>();
            List<String> tmp1 = new ArrayList<String>();
            List<String> tmp2 = new ArrayList<String>();
            int size = 0;
            for (AutocompleteValue u : danhSachPhongBan()) {
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

    public static List<AutocompleteValue> dsChiHuy() {
            List<User> tmp = userDao.getChiHuy().fetch();
            List<AutocompleteValue> results = new ArrayList<AutocompleteValue>();
            StringHelper stringHelper = new StringHelper();
            String term = "";
            for (User u : tmp) {
                term = "\"" + stringHelper.fullName(u) + "\" " + u.username;
                results.add(new AutocompleteValue(stringHelper.convertToFriendlyText(term), term));
            }
            return results;
        }
        
        public static void autocompleteChiHuy(String term) {
            String tmp[] = term.split(",\\s*");
            term = tmp[tmp.length - 1];

            List<String> response = new ArrayList<String>();
            List<String> tmp1 = new ArrayList<String>();
            List<String> tmp2 = new ArrayList<String>();

            int size = 0;

            for (AutocompleteValue u : dsChiHuy()) {
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
