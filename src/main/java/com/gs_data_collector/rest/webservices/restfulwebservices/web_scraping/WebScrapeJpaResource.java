package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

import com.gs_data_collector.rest.webservices.restfulwebservices.todo.Todo;
import com.gs_data_collector.rest.webservices.restfulwebservices.todo.TodoHardcodedService;
import com.gs_data_collector.rest.webservices.restfulwebservices.todo.TodoJpaRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class WebScrapeJpaResource {

    @Autowired
    private TodoJpaRepository todoJpaRepository;
    @Autowired
    private WebJpaRepository webJpaRepository;

    @GetMapping(path = "/webscrape")
    public String helloWorld() throws IOException {

        Document d = Jsoup.connect("https://www.wikihow.com/wikiHowTo?search=signal+wifi").timeout(6000).get();
        Elements ele = d.select("div#searchresults_list");
        //System.out.println(ele);
        for (Element element : ele.select("div.result")) {
            String img_url = element.select("div.result_thumb img").attr("src");
            System.out.println(img_url);

            String title = element.select("div.result_title").text();
            System.out.println(title);
            System.out.println("\n");
        }
        return "Hello World";
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
