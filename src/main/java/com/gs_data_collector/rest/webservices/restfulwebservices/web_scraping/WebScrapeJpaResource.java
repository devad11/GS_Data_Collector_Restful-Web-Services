package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

import com.gs_data_collector.rest.webservices.restfulwebservices.todo.TodoJpaRepository;
import com.sun.xml.fastinfoset.util.StringArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.json.*;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class WebScrapeJpaResource {

    @Autowired
    private TodoJpaRepository todoJpaRepository;
    @Autowired
    private WebJpaRepository webJpaRepository;

    @GetMapping(path = "/webscrape", produces = "text/plain")
    public String helloWorld() throws IOException, JSONException {
//        "http://www.javatpoint.com"
        Document doc = Jsoup.connect("https://irish.national-lottery.com/irish-lotto/past-results").get();

        String inputLine;
        String htmlData = "";
        File htmlFile = new File("htmlFile.html");

//        URL oracle = new URL("https://www.wikihow.com/wikiHowTo?search=signal+wifi");
//        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
//        BufferedWriter bw = new BufferedWriter(new FileWriter(htmlFile));
//
//        while ((inputLine = in.readLine()) != null)
////            bw.write(inputLine + "\n");
//            htmlData += inputLine + "\n";
//        in.close();
//        bw.close();

//        Element root = doc.body();
//        String cimek = doc.select("#search > div.s-desktop-width-max.s-desktop-content.sg-row > div.sg-col-20-of-24.sg-col-28-of-32.sg-col-16-of-20.sg-col.sg-col-32-of-36.sg-col-8-of-12.sg-col-12-of-16.sg-col-24-of-28 > div > span:nth-child(5) > div:nth-child(1) > div:nth-child(2) > div > span > div > div > div:nth-child(3) > h2 > a > span").text();
//        System.out.println(cimek);
//        for (Element el : doc.select("#content > table > tbody"))
//            System.out.println(el.text());

        List<String> dates = new ArrayList<String>();
        Elements test = doc.select("#content > table > tbody > tr > td.noBefore.colour > a");

        for (Element el : test) {
            dates.add(el.text());
        }

//        for (String date : dates) {
//            System.out.println(date);
//        }

        List<String> numbers = new ArrayList<String>();
        Elements test2 = doc.select("#content > table > tbody > tr > td.noBefore.nowrap");

        for (Element el2 : test2) {
            numbers.add(el2.text());
        }

//        for (String number : numbers) {
//            System.out.println(number);
//        }

        List<String> jackpots = new ArrayList<String>();
        Elements test3 = doc.select("#content > table > tbody > tr > td:nth-child(3)");

        for (Element el3 : test3) {
            jackpots.add(el3.ownText().replace("€", "").replace(",", ""));
        }

//        for (String jackpot : jackpots) {
//            System.out.println(jackpot);
//        }
        ArrayList<ArrayList<String> > scrapes = new ArrayList<>();
        for(int i = 0; i < jackpots.size() - 1; i++) {
            scrapes.add(new ArrayList<String>());

            scrapes.get(i).add(0, dates.get(i));
            scrapes.get(i).add(1, numbers.get(i));
            scrapes.get(i).add(2, jackpots.get(i));
        }

        System.out.println(scrapes);

//        System.out.println(doc.select("").text());


//        Elements root = doc.select("*[id]:not([id=\"\"])");

//        Elements elem = doc.select("*[@id=\"search\"]/div[1]/div[2]/div/span[4]/div[1]/div[2]/div/span/div/div/div[2]/h2/a/span");
//        Elements elem = doc.select("#content > table");
//        Elements children = null;
//        Elements children2 = null;
//        for (Element ele : elem){
//            children = ele.children();
////            System.out.println(children.text());
//        }
//        for (Element child : children){
//            System.out.println(child.select("h4").text());
//            children2 = child.select("div > div.winning-numbers > div > div > div > label");
//            for (Element child2 : children2){
//                System.out.println(child2.text());
//            }
//        }

//        System.out.println(root.cssSelector());
//
//        System.out.println(root);
//        System.out.println("------------------------------------------");

//        System.out.println(root.eachAttr("a href"));
//        System.out.println("------------------------------------------");



//        System.out.println(root);

//        Elements sections = doc.getAllElements();
//
//        for (Element element : sections) {
//            String title = element.select("h1").attr("class");
//            String img_url = element.select("cikkcim").attr("a[href]");
//            System.out.println(title);
//        }
//        System.out.println(sections);

//        Elements allParents = sections.parents();
//
//
//        Element firstSection = sections.first();
//
//        Element lastSection = sections.last();
//        Element secondSection = sections.get(2);
//        Element parent = firstSection.parent();
//        System.out.println(parent.cssSelector());
//        Elements children = firstSection.children();
//        Elements siblings = firstSection.siblingElements();

//        String keywords = doc.select("meta[name=keywords]").first().attr("content");
//        System.out.println("Meta keyword : " + keywords);
//        String description = doc.select("meta[name=description]").get(0).attr("content");
//        System.out.println("Meta description : " + description);

//        Elements elem = doc.select("div");
//        for (Element element : elem) {
//            String title = element.select("div.id").text();
//            String img_url = element.select("div.result_thumb img").attr("src");
//            System.out.println(title);
//        }

//        System.out.println(htmlData);

        return ("Hello");
    }

    @RequestMapping(path = "/webscrape2", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> sendSourceHtml() throws IOException {

        String inputLine;
        String htmlData = "";
        File htmlFile = new File("htmlFile.html");

        URL oracle = new URL("https://www.wikihow.com/wikiHowTo?search=signal+wifi");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        BufferedWriter bw = new BufferedWriter(new FileWriter(htmlFile));

        while ((inputLine = in.readLine()) != null)
//            bw.write(inputLine + "\n");
            htmlData += inputLine + "\n";
        in.close();
        bw.close();

        return new ResponseEntity<String>(htmlData, HttpStatus.OK);

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
