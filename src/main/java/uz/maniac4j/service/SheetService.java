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

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SheetService {
    @Value("${sheet.id}")
    private String sheetId;
//    private final RequestService requestService;
//    private final RequestRepository requestRepository;
//    private final ItemService itemService;
//    private final ItemRepository itemRepository;
    private final FileInputStream credentialsStream = new FileInputStream(new ClassPathResource("credentials.json").getFile());

    private final GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
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
        BatchGetValuesResponse batchGetValuesResponseI1 = batchGetValues(sheetId, List.of("I1"));



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
            i++;
            temp=new ArrayList<>();
            temp.add(i);
            temp.add(item.getName());
            temp.add(String.valueOf(item.getAmount()));
            temp.add(item.getSection().getRu());
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
