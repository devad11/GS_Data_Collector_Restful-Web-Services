package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class WebScrape {

    @Id
    @GeneratedValue
    private Long id;

    private String image;
    private String title;

    public WebScrape() {

    }

    public WebScrape(long id, String image, String title) {
        super();
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public WebScrape(String img_url, String title) {
        super();
        this.image = image;
        this.title = title;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WebScrape other = (WebScrape) obj;
        if (id != other.id)
            return false;
        return true;
    }



}

