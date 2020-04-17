package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Data_collector {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true, nullable=false)
    private String name;

    @Column(nullable=false)
    private String collection_type;

    private Long frequency;
    private String made_by;
    private String source;
    private String selectors;
    private LocalDateTime created;
    private boolean is_active;

    public Data_collector(){

    }

    public Data_collector(String name, String collection_type, Long frequency, String made_by, String source, String selectors, LocalDateTime created, boolean is_active) {
        this.name = name;
        this.collection_type = collection_type;
        this.frequency = frequency;
        this.made_by = made_by;
        this.source = source;
        this.selectors = selectors;
        this.created = created;
        this.is_active = is_active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollection_type() {
        return collection_type;
    }

    public void setCollection_type(String collection_type) {
        this.collection_type = collection_type;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public String getMade_by() {
        return made_by;
    }

    public void setMade_by(String made_by) {
        this.made_by = made_by;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSelectors() {
        return selectors;
    }

    public void setSelectors(String selectors) {
        this.selectors = selectors;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "data_collector{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", collection_type='" + collection_type + '\'' +
                ", frequency=" + frequency +
                ", made_by='" + made_by + '\'' +
                ", source='" + source + '\'' +
                ", selectors='" + selectors + '\'' +
                ", next_schedule=" + created +
                ", is_active=" + is_active +
                '}';
    }
}
