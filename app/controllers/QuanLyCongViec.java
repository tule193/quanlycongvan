/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import flexjson.JSONContext;
import flexjson.JSONSerializer;
import java.util.Date;
import java.util.List;
import play.db.jpa.JPABase;
import play.mvc.*;
import play.modules.guice.*;
import models.*;
import play.*;

/**
 *
 * @author Greenleaves
 */
public class QuanLyCongViec extends AbstractController {

    public static void Lich() {
        render();
    }
    
    public static void XemLich() {
        render();
    }

    public static void JSONLich() {
        List<Lich> lichCongViec = Lich.findAll();
        renderJSON(lichCongViec);
    }

    public static void nhapLich(String body) {        
        Gson gson = new Gson();
        Lich congViec = gson.fromJson(body, Lich.class);
        String start = utils.helpers.StringHelper.Joda(congViec.start.toString());
        String end = utils.helpers.StringHelper.Joda(congViec.end.toString());               
        Lich lich = new Lich(start, end, congViec.noidung, congViec.thanhphan, congViec.chutri, congViec.diadiem);
        lich.create();
    }   

    public static void suaLich(String body) {        
        Gson gson = new Gson();
        Lich congViec = gson.fromJson(body, Lich.class);
        Lich currentLich = Lich.findById(congViec.id);        
        String start = utils.helpers.StringHelper.Joda(congViec.start.toString());
        String end = utils.helpers.StringHelper.Joda(congViec.end.toString()); 
        currentLich.Edit(start, end, congViec.noidung, congViec.thanhphan, congViec.chutri, congViec.diadiem);
        currentLich.save();        
    }                
    
    public static void xoaLich(String body) {        
        long l = Long.parseLong(body);
        Lich lichTheoID = Lich.findById(l);
        lichTheoID.delete();
    }
}
