/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.helpers;

import java.lang.reflect.Field;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import play.i18n.*;
import utils.*;
import models.*;
import org.joda.time.DateTime;

/**
 *
 * @author Nguyen Bao Ngoc
 */
public class StringHelper {

    /**
     * Function kiem tra do dai cua string
     * @param s: string
     * @param maxSize: do dai lon nhat (-1 la ko gioi han)
     * @param minSize: do dai nho nhat (-1 la ko gioi han)
     * @return: Feedback,
     *      false neu string null hoac khong thoa man min max
     *      , true neu thoa man
     */
    public FeedBack validateStringSize(String s, int maxSize, int minSize) {
        FeedBack result = new FeedBack(false, "", null);

        if (s == null) { // kiem tra neu string null thi return loi
            result.message = Messages.get("Error_InputError!");
            return result;
        } else { // tiep tuc kiem tra
            if (maxSize == -1) {
                if (s.length() >= minSize) { // neu thoa man, return success
                    result.isSuccess = true;
                } else { // neu khong, return loi
                    result.message = Messages.get("Error_MinStringError!", minSize);
                }
            } else if (minSize == -1) {
                if (s.length() <= maxSize) { // neu thoa man, return success
                    result.isSuccess = true;
                } else { // neu khong, return loi
                    result.message = Messages.get("Error_MaxStringError!", maxSize);
                }
            } else {
                if (s.length() >= minSize && s.length() <= maxSize) { // neu thoa man, return success
                    result.isSuccess = true;
                } else { // neu khong, return loi
                    result.message = Messages.get("Error_MinMaxStringError!", minSize, maxSize);
                }
            }

            return result;
        }
    }

    /**
     * degug()
     * @param o - đối tượng bất kỳ
     * @return - cấu trúc và giá trị attribute của đối tượng
     */
    public static String debug(Object o) {
        String result = "";
        if (o == null) {
            return "null";
        } else if (o.getClass().isPrimitive() || o.getClass().equals(Integer.class)
                || o.getClass().equals(String.class) || o.getClass().equals(Long.class)
                || o.getClass().equals(Boolean.class) || o.getClass().equals(Character.class)
                || o.getClass().equals(Float.class) || o.getClass().equals(Double.class)
                || o.getClass().equals(Short.class) || o.getClass().equals(Byte.class)
                || o.getClass().equals(Date.class)) {
            return o.getClass().getSimpleName() + " -> " + o.toString();
        } else if (o instanceof List) {
            result += "list -> <ul>";
            for (Object item : (List) o) {
                result += "<li>" + debug(item) + "</li>";
            }
            result += "</ul>";
            return result;
        }
        result += o.getClass().getSimpleName() + " -> <ul>";
        for (Field field : o.getClass().getFields()) {
            try {
                if (field.get(o) == null) {
                    result += "<li>" + field.getName() + " -> null</li> ";
                } else if (field.getType().isPrimitive() || field.getType().equals(Integer.class)
                        || field.getType().equals(String.class) || field.getType().equals(Long.class)
                        || field.getType().equals(Boolean.class) || field.getType().equals(Character.class)
                        || field.getType().equals(Float.class) || field.getType().equals(Double.class)
                        || field.getType().equals(Short.class) || field.getType().equals(Byte.class)
                        || field.getType().equals(Date.class)) {
                    result += "<li>" + field.getName() + " -> " + field.get(o) + "</li> ";
                } else {
                    result += "<li>" + field.getName() + " -> " + debug(field.get(o)) + "</li> ";
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        result += "</ul>";
        return result;
    }

    /**
     * 
     * @param input
     * @param numChar
     * @return summary
     */
    public static String summary(String input, int numChar) {
        if (input == null) {
            return null;
        }
        if (input.length() <= numChar) {
            return (input + "...");
        }
        String output = input.substring(0, numChar + 1);
        int lastSpace = output.lastIndexOf(" ");
        output = output.substring(0, lastSpace);
        output += "...";
        return output;
    }

    /**
     * 
     * @param d
     * @return dd/MM/yyyy 
     */
    public static String ngayThangNam(Date d) {
        if (d == null) {
            return "";
        }
        Format formater = new SimpleDateFormat("dd/MM/yyyy");
        return formater.format(d);
    }

    public static String fullName(User user) {
        if (user == null) {
            return "";
        }
        return String.format("%s %s %s", user.ho, user.tenDem, user.ten);
    }
    private static char[] SPECIAL_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
        'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
        'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
        'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
        'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
        'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
        'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
        'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
        'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
        'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự',};
    private static char[] REPLACEMENTS = {'A', 'A', 'A', 'A', 'E', 'E', 'E',
        'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
        'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
        'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
        'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
        'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
        'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
        'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
        'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
        'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
        'U', 'u',};

    // chuyen cac ki tu co dau thanh khong dau
    public static String convertToFriendlyText(String s) {
        if (s == null) {
            return null;
        }

        int maxLength = Math.min(s.length(), 236);
        char[] buffer = new char[maxLength];
        int n = 0;
        for (int i = 0; i < maxLength; i++) {
            char ch = s.charAt(i);
            int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
            if (index >= 0) {
                buffer[n] = REPLACEMENTS[index];
            } else {
                buffer[n] = ch;
            }
            // skip not printable characters
            if (buffer[n] > 31) {
                n++;
            }
        }
        // skip trailing slashes
        while (n > 0 && buffer[n - 1] == '/') {
            n--;
        }
        return String.valueOf(buffer, 0, n);
    }

    public static String filterUsername(String s) {
        if (s == null) {
            return null;
        }

        String result = new String();
        String tmp[] = s.split("\"\\s*");

        if (s == "") {
            result = "Empty String";
        } else if (tmp.length == 3 || tmp.length == 1) {
            result = tmp[tmp.length - 1];
        } else {
            result = "error";
        }

        return result;
    }

    public static String listUser(List<CongVanDenUser> dsCanBo, CongVanDenUser.TYPE type) {
        String result = "";
        if(dsCanBo==null)return result;
        for (CongVanDenUser canbo : dsCanBo) {
            if (canbo.type == type) {
                result+="\""+canbo.user.ho+" "+canbo.user.tenDem+" "+canbo.user.ten+"\""+canbo.user.username+",";
            }
        }
        if(result.endsWith(","))result = result.substring(0, result.length()-1);
        return result;
    }
    
    public static String listUserToText(List<CongVanDenUser> dsCanBo, CongVanDenUser.TYPE type) {
        String result = "";
        if(dsCanBo==null)return result;
        for (CongVanDenUser canbo : dsCanBo) {
            if (canbo.type == type) {
                result+= fullName(canbo.user)+","+" ";
            }
        }
        if(result.endsWith(" "))result = result.substring(0, result.length()-2);
        return result;
    }
    
    public static String mucDoMat(int mdm){
        if (mdm == 0) return "MAT";
        if (mdm == 1) return "TUYET_MAT";
        if (mdm == 2) return "TOI_MAT";
        return "MAT";
    }
    
    public static String mucDoMatToText(int mdm){
        if (mdm == 0) return Messages.get("MAT");
        if (mdm == 1) return Messages.get("TUYET_MAT");
        if (mdm == 2) return Messages.get("TOI_MAT");
        return Messages.get("MAT");
    }
    
    public static String mucDoKhan(int mdk){
        if (mdk == 0) return "KHAN";
        if (mdk == 1) return "THUONG_KHAN";
        if (mdk == 2) return "HOA_TOC";
        return "KHAN";
    }
    
    public static String mucDoKhanToText(int mdk) {
        if (mdk == 0) return Messages.get("KHAN");
        if (mdk == 1) return Messages.get("THUONG_KHAN");
        if (mdk == 2) return Messages.get("HOA_TOC");
        return Messages.get("KHAN");
    }
    
    public static String loaiVanBanToText(int lvb) {
        if (lvb == 0) return Messages.get("GUI_QUA_BGH");
        if (lvb == 1) return Messages.get("GUI_PHONG_BAN");
        if (lvb == 2) return Messages.get("GUI_CA_NHAN");
        return Messages.get("GUI_QUA_BGH");
    }   
    
    public static String loaiVanBanDiToText(int lcv) {
        if (lcv == 0) return Messages.get("NOI_BO");
        if (lcv == 1) return Messages.get("NOI_BO_QUA_BGH");
        if (lcv == 2) return Messages.get("NGOAI_GIAO");
        return Messages.get("NOI_BO");
    } 
    
    public static String Joda (String str) {        
        DateTime dt = new DateTime(str);        
        return dt.toString();
    }
}