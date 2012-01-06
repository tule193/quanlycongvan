/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.*;
import javax.persistence.*;

import org.joda.time.DateTime;
import play.data.validation.*;
import play.db.jpa.*;
import play.modules.elasticsearch.annotations.ElasticSearchable;

/**
 *
 * @author Greenleaves
 */
@ElasticSearchable(indexName="test")
@Entity
public class Lich extends Model {

    public String start;
    public String end;
    
    @Lob
    @MaxSize(250)
    public String noidung;
    
    public String thanhphan;
    public String diadiem;
    public String chutri;

    public Lich(String start, String end, String noidung, String thanhphan, String chutri, String diadiem) {
        this.start = start;
        this.end = end;
        this.noidung = noidung;
        this.thanhphan = thanhphan;
        this.diadiem = diadiem;
        this.chutri = chutri;
    }

    public Lich Edit(String start, String end, String noidung, String thanhphan, String chutri, String diadiem) {
        this.start = start;
        this.end = end;
        this.noidung = noidung;
        this.thanhphan = thanhphan;
        this.diadiem = diadiem;
        this.chutri = chutri;
        return this;
    }
}
