package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;


import com.gs_data_collector.rest.webservices.restfulwebservices.dao.SchedulerDao;
import com.gs_data_collector.rest.webservices.restfulwebservices.scheduling.Data_collector;
import com.gs_data_collector.rest.webservices.restfulwebservices.scheduling.SchedulingTasks;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class WebScrapeJpaResource implements Runnable{

    @Autowired
    SchedulerDao schedulerDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WebJpaRepository webJpaRepository;
    @Autowired
    private SchedulingTasks schedulingTasks;


    ArrayList<String> info = new ArrayList<String>();

    @GetMapping(path = "/webscrape", produces = "text/plain")
    public void run() {

        String sourceUrl = "";
        ArrayList<String> scrapingInfo = new ArrayList<String>();
        String taskName = "";
        ArrayList<String> columnNames = new ArrayList<String>();
        List<Data_collector> metadata = schedulingTasks.getWebMetaData();

        for (Data_collector dc : metadata) {
            if (dc.isProceed() && dc.getCollection_type() == 2){
                sourceUrl = dc.getSource();
                String[] selectors = dc.getSelectors().split(",");
                Collections.addAll(scrapingInfo, selectors);
                taskName = dc.getName();
                String[] column = dc.getColumn_names().split(",");
                Collections.addAll(columnNames, column);
                dc.setProceed(false);
            }


            // web scrape starts
            Document doc = null;
            if (!sourceUrl.equals("")) {
                try {
                    doc = Jsoup.connect(sourceUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<String> dates = new ArrayList<String>();
                Elements test = doc.select(scrapingInfo.get(0));

                for (Element el : test) {

                    String[] breakDate = el.text().split(" ");
                    int day = Integer.parseInt(breakDate[1].replaceAll("[^\\d]", ""));
                    Calendar cal = Calendar.getInstance();
                    try {
                        cal.setTime(new SimpleDateFormat("MMM").parse(breakDate[2]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int month = cal.get(Calendar.MONTH) + 1;
                    int year = Integer.parseInt(breakDate[3]);
                    String dateString = day + "/" + month + "/" + year;

                    dates.add(dateString);
                }

                List<String> numbers = new ArrayList<String>();
                Elements test2 = doc.select(scrapingInfo.get(1));

                for (Element el2 : test2) {
                    numbers.add(el2.text());
                }

                List<String> jackpots = new ArrayList<String>();
                Elements test3 = doc.select(scrapingInfo.get(2));

                for (Element el3 : test3) {
                    jackpots.add(el3.ownText().replace("€", "").replace(",", ""));
                }

                ArrayList<ArrayList<String>> scrapes = new ArrayList<>();
                for (int i = 0; i < jackpots.size() - 1; i++) {
                    scrapes.add(new ArrayList<String>());

                    scrapes.get(i).add(0, dates.get(i));
                    scrapes.get(i).add(1, numbers.get(i));
                    scrapes.get(i).add(2, jackpots.get(i));
                }

                System.out.println(scrapes);

                String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + taskName + "("
                        + columnNames.get(0) + " DATETIME NOT NULL,"
                        + columnNames.get(1) + " VARCHAR(400) NOT NULL,"
                        + columnNames.get(2) + " INT NOT NULL,"
                        + "PRIMARY KEY (" + columnNames.get(0) + "))";

                jdbcTemplate.execute(CREATE_TABLE_SQL);

                for (int i = 0; i < dates.size() - 1; i++) {
                    Date date1 = null;
                    try {
                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse(scrapes.get(i).get(0));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    jdbcTemplate.update(
                            "insert into " + taskName + " values (?,?,?) ON DUPLICATE KEY UPDATE " + columnNames.get(0) + " = ?",
                            date1,
                            scrapes.get(i).get(1),
                            Integer.parseInt(scrapes.get(i).get(2)),
                            date1);
                }
            }
        }

    }

    @PostMapping("/webscrape/selector")
    public String saveSelector(
            @RequestBody Data_collector data_collector){

        System.out.println(data_collector.toString());
        Data_collector createdData = webJpaRepository.save(data_collector);

        return "Hello";
    }

    @PostMapping("/getwebscrape")
    public String testScraper(
            @RequestBody Data_collector data_collector){

        String res = "";

        String sourceUrl = data_collector.getSource();
        String[] selectors = data_collector.getSelectors().split(",");
        ArrayList<String> scrapingInfo = new ArrayList<>();
        Collections.addAll(scrapingInfo, selectors);
        String taskName = data_collector.getName();
        String[] column = data_collector.getColumn_names().split(",");
        ArrayList<String> columnNames = new ArrayList<>();
        Collections.addAll(columnNames, column);

    // web scrape starts
    Document doc = null;
            if (!sourceUrl.equals("")) {
        try {
            doc = Jsoup.connect(sourceUrl).get();
            System.out.println(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> dates = new ArrayList<String>();
        Elements test = doc.select(scrapingInfo.get(0));

        for (Element el : test) {

            String[] breakDate = el.text().split(" ");
            int day = Integer.parseInt(breakDate[1].replaceAll("[^\\d]", ""));
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(new SimpleDateFormat("MMM").parse(breakDate[2]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int month = cal.get(Calendar.MONTH) + 1;
            int year = Integer.parseInt(breakDate[3]);
            String dateString = day + "/" + month + "/" + year;

            dates.add(dateString);
        }

        List<String> numbers = new ArrayList<String>();
        Elements test2 = doc.select(scrapingInfo.get(1));

        for (Element el2 : test2) {
            numbers.add(el2.text());
        }

        List<String> jackpots = new ArrayList<String>();
        Elements test3 = doc.select(scrapingInfo.get(2));

        for (Element el3 : test3) {
            jackpots.add(el3.ownText().replace("€", "").replace(",", ""));
        }

        ArrayList<ArrayList<String>> scrapes = new ArrayList<>();
        for (int i = 0; i < jackpots.size() - 1; i++) {
            scrapes.add(new ArrayList<String>());

            scrapes.get(i).add(0, dates.get(i));
            scrapes.get(i).add(1, numbers.get(i));
            scrapes.get(i).add(2, jackpots.get(i));
        }

        System.out.println(scrapes);
                res = scrapes.toString();
        return res;
    }
    return "nope";
    }

    @GetMapping("/table/{tableName}")
    public String getTable(@PathVariable String tableName) throws SQLException, JSONException {

        JSONArray json = new JSONArray();
        StringBuilder jsonData = new StringBuilder("[");

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gs_data","springuser","password");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();

                for (int i=1; i<numColumns+1; i++) {
                    String column_name = rsmd.getColumnName(i);

                    if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                        obj.put(column_name, rs.getArray(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                        obj.put(column_name, rs.getInt(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                        obj.put(column_name, rs.getBoolean(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                        obj.put(column_name, rs.getBlob(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                        obj.put(column_name, rs.getDouble(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                        obj.put(column_name, rs.getFloat(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                        obj.put(column_name, rs.getInt(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                        obj.put(column_name, rs.getNString(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                        obj.put(column_name, rs.getString(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                        obj.put(column_name, rs.getInt(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                        obj.put(column_name, rs.getInt(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                        obj.put(column_name, rs.getDate(column_name));
                    }
                    else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                        obj.put(column_name, rs.getTimestamp(column_name));
                    }

                    else{
                        obj.put(column_name, rs.getString(column_name));
                        jsonData.append(rs.getString(column_name)).append(",");
                    }
                }
                json.put(obj);
            }
        con.close();
            }catch(Exception e){ System.out.println(e);}

        jsonData = new StringBuilder(jsonData.substring(0, jsonData.length() - 1));
        jsonData.append("]");
        System.out.println(jsonData);

        return jsonData.toString();
    }

    @GetMapping("/users/{username}/datacollector")
    public List<Data_collector> getAllDataCollector(@PathVariable String username){
        return schedulerDao.findBymade_by(username);
    }

}