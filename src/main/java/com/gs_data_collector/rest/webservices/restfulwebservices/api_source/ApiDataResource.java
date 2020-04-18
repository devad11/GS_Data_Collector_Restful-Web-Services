package com.gs_data_collector.rest.webservices.restfulwebservices.api_source;

import com.gs_data_collector.rest.webservices.restfulwebservices.scheduling.Data_collector;
import com.gs_data_collector.rest.webservices.restfulwebservices.scheduling.SchedulingTasks;
import com.gs_data_collector.rest.webservices.restfulwebservices.todo.Todo;
import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebJpaRepository;
import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebScrape;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class ApiDataResource implements Runnable{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private SchedulingTasks schedulingTasks;

    @PostMapping("/apidata")
    public String apiDataReceiver(
            @RequestBody String apiData) throws JSONException, IOException {

        List<Data_collector> metadata = schedulingTasks.getApiMetaData();
        System.out.println(metadata);
        String sourceUrl = "";
        String taskName = "";

        for (Data_collector dc : metadata){
            if (dc.isProceed() && dc.getCollection_type() == 1){
                sourceUrl = dc.getSource();
                taskName = dc.getName();
                System.out.println(sourceUrl);
                dc.setProceed(false);
            }
        }
        apiData = MyGETRequest(sourceUrl);

        if (apiData != null) {
            String apiDataToParse = "{" + "\"data\":" + apiData + "}";
            JSONObject obj = new JSONObject(apiDataToParse);
            JSONArray data = obj.getJSONArray("data");
            int n = data.length();

            String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + taskName + "("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "com VARCHAR(64),"
                    + "json_info LONGTEXT NOT NULL,"
                    + "PRIMARY KEY (id))";

            jdbcTemplate.execute(CREATE_TABLE_SQL);

            for (int i = 0; i < n; ++i) {
                JSONObject v = data.getJSONObject(i);
                jdbcTemplate.update(
                        "insert into " + taskName + " (com, json_info)"
                                + " values(?,?)",
                        taskName, v.toString());
            }

            //        for(int i = 0; i < dates.size() - 1; i++) {
            //            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(scrapes.get(i).get(0));
            //            jdbcTemplate.update(
            //                    "insert into lotto_numbers (id, date, number, jackpot) values(?,?,?,?)",
            //                    i, date1, scrapes.get(i).get(1), Integer.parseInt(scrapes.get(i).get(2)));
            //        }

            return "Success";
        }
        return null;
    }

    private static String MyGETRequest(String sourceUrl) throws IOException {
        if (!sourceUrl.equals("")) {
            URL urlForGetRequest = new URL(sourceUrl);
            String readLine;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            //        conection.setRequestProperty("userId", "a1bcdef");
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                // print result
                System.out.println("JSON String Result " + response.toString());
                //GetAndPost.POSTRequest(response.toString());
                return response.toString();
            } else {
                return "GET REQUEST FAILED";
            }

        }
        return  null;
    }

    @Override
    public void run() {
        try {
            apiDataReceiver("");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
