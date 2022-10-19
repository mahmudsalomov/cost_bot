package uz.maniac4j.start;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class Runner implements CommandLineRunner {

    @Value("${test}")
    private boolean test;

    @Override
    public void run(String... args) throws Exception {
        if (test){
            FileInputStream credentialsStream = new FileInputStream(new ClassPathResource("credentials.json").getFile());


            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(List.of(SheetsScopes.SPREADSHEETS, DriveScopes.DRIVE));
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                    credentials);

            // Create the sheets API client
            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("Sheets samples")
                    .build();

            // Create new spreadsheet with a title
            Spreadsheet spreadsheet = new Spreadsheet()
                    .setProperties(new SpreadsheetProperties()
                            .setTitle("title1234"));
            spreadsheet = service.spreadsheets().create(spreadsheet)
                    .setFields("*")
                    .execute();
            // Prints the new spreadsheet id
            System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetUrl());


            Drive drive = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer)
                    .build();

            File file = drive.files()
                    .get(spreadsheet.getSpreadsheetId())
                    .setFields("*")
                    .execute();

            Permission newPermission = new Permission();

            newPermission.setType("user");
            newPermission.setRole("writer");
            newPermission.setEmailAddress("koval4uk.artur@gmail.com");

            drive.permissions()
                    .create(spreadsheet.getSpreadsheetId(), newPermission)
                    .execute();

            System.err.println(file);
        }

    }
}