/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilsTest.helpersTest;

import java.util.Date;
import org.junit.Test;
import play.test.UnitTest;
import utils.*;
import utils.helpers.*;
import play.i18n.*;

/**
 *
 * @author Nguyen Bao Ngoc
 */
public class StringHelperTest extends UnitTest {
    
    @Test
    public void validateStringSizeTest() {
        // Khoi tao data
        StringHelper sH = new StringHelper();
        int minSize = 5;
        int maxSize = 10;
        String errorMessage = Messages.get("Error_MinMaxStringError!", String.valueOf(minSize), String.valueOf(maxSize));
        String errorMinMessage = Messages.get("Error_MinStringError!", String.valueOf(minSize));
        String errorMaxMessage = Messages.get("Error_MaxStringError!", String.valueOf(maxSize));
        
        // Truong hop dung
        FeedBack ketQua1 = sH.validateStringSize("123456", maxSize, minSize);
        assertTrue(ketQua1.isSuccess);
        
        // Truong hop nho hon minSize
        FeedBack ketQua2 = sH.validateStringSize("123", maxSize, minSize);
        assertFalse(ketQua2.isSuccess);
        assertEquals(errorMessage, ketQua2.message);
        
        // Truong hop lon hon maxSize
        FeedBack ketQua3 = sH.validateStringSize("1234567890123", maxSize, minSize);
        assertFalse(ketQua3.isSuccess);
        assertEquals(errorMessage, ketQua3.message);
        
        // Truong hop = minSize
        FeedBack ketQua4 = sH.validateStringSize("12345", maxSize, minSize);
        assertTrue(ketQua4.isSuccess);
        
        // Truong hop = maxSize
        FeedBack ketQua5 = sH.validateStringSize("1234567890", maxSize, minSize);
        assertTrue(ketQua5.isSuccess);
        
        // Truong hop null
        FeedBack ketQua6 = sH.validateStringSize(null, maxSize, minSize);
        assertFalse(ketQua6.isSuccess);
        
        // Truong hop minSize = -1, string test lon hon va nho hon maxSize
        FeedBack ketQua7 = sH.validateStringSize("12345", maxSize, -1);
        assertTrue(ketQua7.isSuccess);
        FeedBack ketQua8 = sH.validateStringSize("1234567890123", maxSize, -1);
        assertFalse(ketQua8.isSuccess);
        assertEquals(errorMaxMessage, ketQua8.message);
        
        // Truong hop max Size = -1, string test lon hon va nho hon minSize
        FeedBack ketQua9 = sH.validateStringSize("123456", -1, minSize);
        assertTrue(ketQua9.isSuccess);
        FeedBack ketQua10 = sH.validateStringSize("1234", -1, minSize);
        assertFalse(ketQua10.isSuccess);
        assertEquals(errorMinMessage, ketQua10.message);
    }
    
    @Test
    public void debugTest(){
        assertTrue(true);
        //Hàm này không dùng khi chương trình chạy thật mà chỉ dùng để test
        //nên không cần viết unittest.
        //Tuy nhiên thành viên nào trong team có hứng thú thì nên viết test cho
        //hàm này. Cũng mệt phết
        //Tô Việt Anh
    }
    
    @Test
    public void sumarryTest(){
        String text = "Là người đặt câu hỏi đầu tiên, đại biểu Đặng Thế Vinh"+
                " (Long An) xới lại vấn đề được nhiều lần đề cập: 'Khi giá xăng"+
                " dầu thế giới tăng, trong nước cũng tăng theo. Tuy nhiên, khi"+
                " thế giới giảm thì giá trong nước không giảm, gây bức xúc"+
                " trong dư luận. Xin Bộ trưởng cho biết giải pháp điều hành"+
                " trong thời gian tới như thế nào?'. Trong khi đó, đại biểu"+
                " Nguyễn Thu Anh (Lâm Đồng) yêu cầu Bộ trưởng Huệ công khai kết"+
                " quả thanh tra xăng dầu.";
        String result = "";
        //Trường hợp đúng
        result = StringHelper.summary(text, 20);
        assertEquals("Là người đặt câu hỏi...", result);
        result = StringHelper.summary(text, 33);
        assertEquals("Là người đặt câu hỏi đầu tiên,...", result);
        //Trường hợp null
        result = StringHelper.summary(null, 20);
        assertEquals(null, result);
        //Trường hợp đặc biệt - xau truyen vao ngan hon so ky tu
        result = StringHelper.summary("short string", 40);
        assertEquals("short string...", result);
       
    }
    
    @Test
    public void ngayThangNamTest(){
        Date d = new Date(110, 10, 20, 3, 23, 14);
        //Truong hop dung
        String result = StringHelper.ngayThangNam(d);
        assertEquals("20/11/2010", result);
        //Truong hop sai: khong co
        //Truong hop null
        result = StringHelper.ngayThangNam(null);
        assertEquals("", result);
        //Truong hop dac biet
        d.setDate(0);
        result = StringHelper.ngayThangNam(d);
        assertEquals("31/10/2010", result);
    }
    
    @Test
    public void fullNameTest(){
       //TODO:viet test cho ham fullName
    }
    public void convertToFriendlyTextTest() {
        String test1 = "Là người đặt câu hỏi đầu tiên,";
        String result = "";
        
        // Truong hop dung
        result = StringHelper.convertToFriendlyText(test1);
        assertEquals("La nguoi dat cau hoi dau tien,", result);
        
        // Truong hop ""
        result = StringHelper.convertToFriendlyText("");
        assertEquals("", result);
        
        // Truong hop null
        result = StringHelper.convertToFriendlyText(null);
        assertEquals(null, result);
        
        // Truong hop 2 ki tu co dau o canh nhau
        result = StringHelper.convertToFriendlyText("Làà người");
        assertEquals("Laa nguoi", result);
    }
    
    @Test
    public void filterUsernameTest() {
        StringHelper stringHelper = new StringHelper();
        String test1 = "ngoc";
        String test2 = "\"asdfasdfasdf\" ngoc";
        String test3 = "\"asdfasdf ngoc";
        String result = "";
        
        // Truong hop dung, khong co ""
        result = stringHelper.filterUsername(test1);
        assertEquals("ngoc", result);
        
        // Truong hop dung, co ""
        result = stringHelper.filterUsername(test2);
        assertEquals("ngoc", result);
        
        // Truong hop sai, empty string
        result = stringHelper.filterUsername("");
        assertEquals("Empty String", result);
        
        // Truong hop co 1 dau "
        result = stringHelper.filterUsername(test3);
        assertEquals("error", result);
        
        // Truong hop null
        result = stringHelper.filterUsername(null);
        assertEquals(null, result);
    }
}