package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

import com.mysql.cj.xdevapi.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class WebScrapeJpaResource {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private WebJpaRepository webJpaRepository;
    ArrayList<String> info = new ArrayList<String>();

    @GetMapping(path = "/webscrape", produces = "text/plain")
    public String webMining() throws IOException, ParseException {

        String sql = "SELECT loc FROM gs_data.scrape_info where name LIKE \"lotto\"";

        String scrapingData = jdbcTemplate.queryForObject(sql, String.class);

        String[] scrapingInfo = scrapingData.split(",");

        System.out.println(Arrays.toString(scrapingInfo));

        String asfg = "[cvbn dsf";

        if (scrapingInfo.length > 3){
            Document doc = Jsoup.connect(scrapingInfo[0]).get();

            List<String> dates = new ArrayList<String>();
            Elements test = doc.select(scrapingInfo[1]);

            for (Element el : test) {

                String[] breakDate = el.text().split(" ");
                int day = Integer.parseInt(breakDate[1].replaceAll("[^\\d]", ""));
                Calendar cal = Calendar.getInstance();
                cal.setTime(new SimpleDateFormat("MMM").parse(breakDate[2]));
                int month = cal.get(Calendar.MONTH) + 1;
                int year = Integer.parseInt(breakDate[3]);
                String dateString = day + "/" + month + "/" + year;


                //            String dateInString = el.text();
                //            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("cccc dd LLLL yyyy", Locale.ENGLISH);
                //            LocalDate dateTime = LocalDate.parse(dateInString, formatter);
                //            System.out.println(dateTime);

                //            try {
                //                LocalDate localDate = LocalDate.parse("16-ABC-2016", dtf);
                //                System.out.println(dtf.format(localDate));
                //            } catch (DateTimeParseException e) {
                //                System.err.println("Unable to parse the date!");
                //                //e.printStackTrace();
                //            }

                dates.add(dateString);
            }

            List<String> numbers = new ArrayList<String>();
            Elements test2 = doc.select(scrapingInfo[2]);

            for (Element el2 : test2) {
                numbers.add(el2.text());
            }

            List<String> jackpots = new ArrayList<String>();
            Elements test3 = doc.select(scrapingInfo[3]);

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

            String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS lotto_numbers ("
                    + "date_of_game DATETIME NOT NULL,"
                    + "number VARCHAR(45) NOT NULL,"
                    + "jackpot INT NOT NULL,"
                    + "PRIMARY KEY (date_of_game))";

            jdbcTemplate.execute(CREATE_TABLE_SQL);


            for (int i = 0; i < dates.size() - 1; i++) {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(scrapes.get(i).get(0));
                jdbcTemplate.update(
                        "insert into lotto_numbers (date_of_game, number, jackpot) values(?,?,?) ON DUPLICATE KEY UPDATE date_of_game = ?",
                        date1, scrapes.get(i).get(1), Integer.parseInt(scrapes.get(i).get(2)), date1);
            }


        }
        System.out.println("Hello");

        return ("Hello");
    }

    @PostMapping("/webscrape/selector")
    public ResponseEntity<Void> saveSelector(
            @RequestBody ArrayList<String> inSelector){
        info = inSelector;

        String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS scrape_info ("
                + "name VARCHAR(40) NOT NULL,"
                + "loc VARCHAR(1024) NOT NULL,"
                + "PRIMARY KEY (name))";

        jdbcTemplate.execute(CREATE_TABLE_SQL);

        jdbcTemplate.update(
                "insert into scrape_info (name, loc) values(?,?) ON DUPLICATE KEY UPDATE name = ?",
                "lotto", info.toString().replace("[","").replace("]",""), "lotto");

        System.out.println(info);

        return null;
    }


    @PostMapping("/webscrape/save")
    public ResponseEntity<Void> createWebScrape() throws IOException {


        Document d = Jsoup.connect("https://www.wikihow.com/wikiHowTo?search=signal+wifi").timeout(6000).get();
        Elements ele = d.select("div#searchresults_list");
        for (Element element : ele.select("div.result")) {
            String img_url = element.select("div.result_thumb img").attr("src");
            String title = element.select("div.result_title").text();

            WebScrape webScrape = new WebScrape(img_url, title);

            WebScrape createWebscrape = webJpaRepository.save(webScrape);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(createWebscrape.getId()).toUri();

            return ResponseEntity.created(uri).build();
        }
        return null;
    }

}