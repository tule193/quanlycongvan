/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.io.*;
import java.util.logging.Level;
import models.*;
import play.modules.guice.InjectSupport;
import play.mvc.*;
import play.Logger;
import java.util.*;
import play.db.jpa.*;
/**
 *
 * @author to_viet_anh
 */
@InjectSupport
class AbstractController extends Controller {
    //Viết những cái chung ở đây
    
    // Upload file dinh kem, return doi file dinh kem
    public static List<FileDinhKem> uploadFile(File[] files) {
        List<FileDinhKem> results = new ArrayList<FileDinhKem>();
        
        for(File f : files) {
            // kiem tra xem file co null khong
            if (f != null) { 
                try {
                    // neu khong thi save lai
                    FileDinhKem tmp = new FileDinhKem();
                    tmp.fileName = f.getName();

                    Logger.info(tmp.fileName);

                    tmp.fileDinhKem = new Blob();
                    tmp.fileDinhKem.set(new FileInputStream(f), f.getName());
                    
                    if(tmp.save() != null) { // neu save dc thi moi add vao results
                        results.add(tmp);
                    }
                } catch (FileNotFoundException ex) {
                    java.util.logging.Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return results;
    }
    

}