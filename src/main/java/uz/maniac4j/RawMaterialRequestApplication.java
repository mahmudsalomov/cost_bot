package uz.maniac4j;

import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
public class RawMaterialRequestApplication {


    public static void main(String[] args) throws GeneralSecurityException, IOException {
        SpringApplication.run(RawMaterialRequestApplication.class, args);
//        Sheets sheets = SheetsServiceUtil.getSheetsService();
//        System.out.println(sheets);
//        System.out.println(sheets.spreadsheets());
//        System.out.println(sheets.spreadsheets().sheets());
//        System.out.println("AAAA");
    }

}
