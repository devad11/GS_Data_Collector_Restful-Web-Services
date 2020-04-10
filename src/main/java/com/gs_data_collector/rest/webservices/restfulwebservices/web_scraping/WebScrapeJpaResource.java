package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class WebScrapeJpaResource {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private WebJpaRepository webJpaRepository;

    @GetMapping(path = "/webscrape", produces = "text/plain")
    public String helloWorld(jdbcTemplate) throws IOException, ParseException {

        Document doc = Jsoup.connect("https://irish.national-lottery.com/irish-lotto/past-results").get();

        List<String> dates = new ArrayList<String>();
        Elements test = doc.select("#content > table > tbody > tr > td.noBefore.colour > a");

        for (Element el : test) {

            String[] breakDate = el.text().split(" ");
            int day = Integer.parseInt(breakDate[1].replaceAll("[^\\d]", ""));
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("MMM").parse(breakDate[2]));
            int month = cal.get(Calendar.MONTH) + 1;
//            System.out.println(cal.get(Calendar.MONTH));
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
        Elements test2 = doc.select("#content > table > tbody > tr > td.noBefore.nowrap");

        for (Element el2 : test2) {
            numbers.add(el2.text());
        }

        List<String> jackpots = new ArrayList<String>();
        Elements test3 = doc.select("#content > table > tbody > tr > td:nth-child(3)");

        for (Element el3 : test3) {
            jackpots.add(el3.ownText().replace("€", "").replace(",", ""));
        }

        ArrayList<ArrayList<String> > scrapes = new ArrayList<>();
        for(int i = 0; i < jackpots.size() - 1; i++) {
            scrapes.add(new ArrayList<String>());

            scrapes.get(i).add(0, dates.get(i));
            scrapes.get(i).add(1, numbers.get(i));
            scrapes.get(i).add(2, jackpots.get(i));
        }

        System.out.println(scrapes);

        String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS lotto_numbers ("
                + "id INT NOT NULL AUTO_INCREMENT,"
                + "date DATETIME NOT NULL,"
                + "number VARCHAR(45) NOT NULL,"
                + "jackpot INT NOT NULL,"
                + "PRIMARY KEY (id))";

        jdbcTemplate.execute(CREATE_TABLE_SQL);



        for(int i = 0; i < dates.size() - 1; i++) {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(scrapes.get(i).get(0));
            jdbcTemplate.update(
                    "insert into lotto_numbers (date, number, jackpot) values(?,?,?)",
                    date1, scrapes.get(i).get(1), Integer.parseInt(scrapes.get(i).get(2)));
        }




        return ("Hello");
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
