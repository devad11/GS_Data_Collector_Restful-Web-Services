package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

import com.gs_data_collector.rest.webservices.restfulwebservices.todo.Todo;
import com.gs_data_collector.rest.webservices.restfulwebservices.todo.TodoHardcodedService;
import com.gs_data_collector.rest.webservices.restfulwebservices.todo.TodoJpaRepository;
import com.sun.xml.fastinfoset.util.StringArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class WebScrapeJpaResource {

    @Autowired
    private TodoJpaRepository todoJpaRepository;
    @Autowired
    private WebJpaRepository webJpaRepository;

    @GetMapping(path = "/webscrape", produces = "text/plain")
    public String helloWorld() throws IOException {
//        "http://www.javatpoint.com"
//        Document doc = Jsoup.connect("https://www.wikihow.com/wikiHowTo?search=signal+wifi").get();

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

//        Element root = doc.body();



//        Elements root = doc.select("*[id]:not([id=\"\"])");



//        System.out.println(root.cssSelector());
//
//        System.out.println(root);





//        System.out.println(root.eachAttr("a"));




//        System.out.println(root);

//        Elements sections = doc.getAllElements();
//
//        for (Element element : sections) {
//            String title = element.select("div").attr("id");
//            String img_url = element.select("div.result_thumb img").attr("src");
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

        System.out.println(htmlData);

        String virus = "Hello Baby";

        return virus;
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
