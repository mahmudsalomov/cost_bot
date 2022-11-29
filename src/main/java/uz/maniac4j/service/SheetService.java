package uz.maniac4j.service;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uz.maniac4j.model.Item;
import uz.maniac4j.model.Request;
import uz.maniac4j.repository.ItemRepository;
import uz.maniac4j.repository.RequestRepository;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SheetService {
    @Value("${sheet.id}")
    private String sheetId;
    private final String c= """
        {
          "type": "service_account",
          "project_id": "scada-365311",
          "private_key_id": "790e52dac5fd2b554a1ac1f500a116da799b9edd",
          "private_key": "-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdOSQRpmOzipBr\\n88kxG1r/D1/bnIy+uUk7WoMSmXS+/WmkTQHVuZHZ0/fXg/9CyVCTux2zXHWqVa8G\\n8780SzeMZ2NXT68235NQoVs/gXscbqk8K0QnTHDbNSDBFuyt70kfXrDHRfcN/Y8G\\njS8ZZFRPVMn1KpMpJA3O7sZ2pSq3iG+bGK1JtaWS8xlBZjwXd32JgWGOiGLXb3gP\\n5aXCs3zvhlNefxBKafTPzRkxyVW1N5qOayFC20/ZfFBK08udA7S5UKFP2TTl0L33\\nLlAsc5yUmPsC6wZB5EZjIFx+L+Bws5iYGT1dIj8NI/tNIfBeCVtF19miVvu/pllG\\ncHW8wI0TAgMBAAECggEASuMcuwt2Ed6+S60i97D/9t1r2z8GCxJWP18tb36IjTBH\\nUWscpKBvSEIemxjUTQt2xecqjnRL14ZYiAcx28a3/riAKOZbntd/Y7h3rhgOe8x5\\n757sJ4vRdZgosdApi++d9MM1kLVSp+dOaOB2vrF7Nw474/sTfVUe/qHdBkZFwoUK\\nvVWn6dLxParZVBNE2GZR88XgBIq3S+2m2ppc/zLHHO2ZUzBaiLwLd8OHvnolhShI\\n7VPfPrOG/CFCP+EFE+gpEmlpQA2Tl76lEmzSmbluVw4OYGhqaIM98B6+A2ITKf0e\\n7zjVRuFznX1Bvw8SLF7td1XrCOHSOp6fVUvritWigQKBgQDTzAj4AJtfLJS5HjCF\\nG01bv1Ekmy+84wD5HRPjftrpv6ZdfdZ5ZX03Ju7HmE5S7ulmtcPHtESo1aIzkBDC\\nIlZSWK9lRIH6bppSxLZQQBuofE0BGM19Xoa5W8PHjXiiNZ7mZpY+v2rrI/Z/cPJE\\nInrIWJh/j+eRIu9Jaaah6OOCBQKBgQC+CVFFwdzGl3mcX7pMvJCoM8CLEbM4d5sW\\nCkv9Gh8d4ig1YQnWlw96ckqTMMFwvQHZ6wzOlFbV3I0aOiUDiL1q0dkJUVp6lgeZ\\n5pxvqhzXOULgu99ADmxTREXEB2pMK/0irKidyCytzTjGSgGbNkyfmmxWzS36+kOk\\nfyPVel2GNwKBgQCwvHI/gUImCW2RjD+w0zead+ueQGF1nXgSaNFP5WuH4wVQvtFa\\nO/ZhL3vR0qaJThQ2QTc+To9808imfnF9jmm6DYdPWfucnu7oYn5AOT7bOd6IK8pN\\n87M4ufqFauhfc59FZBW8Br+RuFDZx8ZiVONgi4215L+3vQ8wk5kZQt2WgQKBgQCU\\nDsZXRvcvcoy5HIwVhURfCBMXRq8Pa4BU56jUhSmBKtaQ4A2+NutERw97Jh1QLLHo\\niozr1f7hmHgG2w5DdqG9O8mQzOVlZMZ/TYDMLwlRVbeZSEZjrdkP9r00QWK7/fm2\\nrjag13iX4LA/LSGbP+7q2nFWH32XcKyK2NOeWzvKeQKBgArVZhU07DT1o1heYLXA\\nNU6DSeN/n7yzfFB54MaWycutojfgwpXOCPwjJSCz1d5jzaB8oBCc1+muwK7X2Rbk\\ncdcvxpoTSXW/IEoynYw2rXIFR1XVHjO9gtvJ5JWm1PHbnyv5u026DLDr89RTz0ns\\nY3DO6Q32JzmBagUcWrWdOtnn\\n-----END PRIVATE KEY-----\\n",
          "client_email": "manager@scada-365311.iam.gserviceaccount.com",
          "client_id": "114358197844141636692",
          "auth_uri": "https://accounts.google.com/o/oauth2/auth",
          "token_uri": "https://oauth2.googleapis.com/token",
          "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
          "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/manager%40scada-365311.iam.gserviceaccount.com"
        }
        """;
//    private final FileInputStream credentialsStream = new FileInputStream(new ClassPathResource("C:\\Users\\mahmu\\IdeaProjects\\raw_material_request\\src\\main\\resources\\credentials.json").getFile());
    private final InputStream credentialsStream2 = new ByteArrayInputStream(c.getBytes());

    private final GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream2)
            .createScoped(List.of(SheetsScopes.SPREADSHEETS, DriveScopes.DRIVE));
    private final HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
            credentials);

    // Create the sheets API client
    private final Sheets service = new Sheets.Builder(new NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            requestInitializer)
            .setApplicationName("Sheets samples")
            .build();

    public SheetService() throws IOException {
    }

    public void write() throws IOException {
        List<String> ranges = service.spreadsheets().get(sheetId).getRanges();
//        System.out.println("Ranges");
//        System.out.println(ranges);
        BatchGetValuesResponse batchGetValuesResponse = batchGetValues(sheetId, List.of("A1", "H1"));
        BatchGetValuesResponse batchGetValuesResponse2 = batchGetValues(sheetId, List.of("I1"));
//        System.out.println(batchGetValuesResponse);
//        System.out.println(batchGetValuesResponse2);
//        System.out.println(batchGetValuesResponse2.getValueRanges());
//        System.out.println(batchGetValuesResponse2.getValueRanges().get(0));
//        System.out.println(batchGetValuesResponse2.getValueRanges().get(0).getValues());
//        System.out.println(batchGetValuesResponse2.getValueRanges().get(0).getValues().get(0).get(0));

        int size=Integer.parseInt(batchGetValuesResponse2.getValueRanges().get(0).getValues().get(0).get(0).toString());


//        System.out.println(batchGetValuesResponse2.get("I1"));
    }

    public void write(Request request,List<Item> items) throws IOException {

//        List<Item> items = itemService.allByRequest(request);

        List<String> ranges = service.spreadsheets().get(sheetId).getRanges();
//        System.out.println("Ranges");
//        System.out.println(ranges);
        BatchGetValuesResponse batchGetValuesResponseI1 = batchGetValues(sheetId, List.of("J1"));



        int size=Integer.parseInt(batchGetValuesResponseI1.getValueRanges().get(0).getValues().get(0).get(0).toString());
        BatchGetValuesResponse batchGetValuesResponseA = batchGetValues(sheetId, List.of("A"+size));

        String s = batchGetValuesResponseA.getValueRanges().get(0).getValues().get(0).get(0).toString();
        int i=0;
        if (!Objects.equals(s, "№")) i=Integer.parseInt(s);

        List<List<Object>> all=new ArrayList<>();
        List<Object> temp;
        String pattern = "MM.dd.yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        for (Item item : items) {
//            i++;
//            temp=new ArrayList<>();
//            temp.add(i);
//            temp.add(item.getName());
//            temp.add(String.valueOf(item.getAmount()));
//            temp.add(item.getSection().getRu());
//            temp.add(simpleDateFormat.format(new Date()));
//            temp.add(request.getUser().getId().toString());
//            temp.add(request.getUser().getUsername()!=null?"@"+request.getUser().getUsername():"");
//            temp.add(item.getDescription());
//            all.add(temp);

            i++;
            temp=new ArrayList<>();
            temp.add(i);
            temp.add(item.getSectionType().getRu());
            temp.add(item.getSection().getRu());
            temp.add(item.getAmount());
            temp.add(item.getAmountUsd());
//            temp.add(item.getName());
            temp.add(simpleDateFormat.format(new Date()));
            temp.add(request.getUser().getId().toString());
            temp.add(request.getUser().getUsername()!=null?"@"+request.getUser().getUsername():"");
            temp.add(item.getDescription());
            all.add(temp);
        }

        ValueRange requestBody = new ValueRange()
                .setValues(all);
        UpdateValuesResponse result = service.spreadsheets().values()
                .update(sheetId, "A"+(size+1), requestBody)
                .setValueInputOption("RAW")
                .execute();
//        System.out.println(result);

    }

    public BatchGetValuesResponse batchGetValues(String spreadsheetId,
                                                        List<String> ranges)
            throws IOException {
        BatchGetValuesResponse result = null;
        try {
            // Gets the values of the cells in the specified range.
            result = service.spreadsheets().values().batchGet(spreadsheetId)
                    .setRanges(ranges).execute();
            System.out.printf("%d ranges retrieved.", result.getValueRanges().size());
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                System.out.printf("Spreadsheet not found with id '%s'.\n", spreadsheetId);
            } else {
                throw e;
            }
        }
        return result;
    }
}
