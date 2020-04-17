package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Data_collector {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true, nullable=false)
    private String name;

    @Column(nullable=false)
    private Long collection_type;

    private Long frequency;

    public String getColumn_names() {
        return column_names;
    }

    public void setColumn_names(String column_names) {
        this.column_names = column_names;
    }

    private String made_by;
    private String source;
    private String selectors;
    private String column_names;
    private LocalDateTime created;
    private boolean is_active;
    private boolean proceed;

    public Data_collector(){

    }

    public Data_collector(String name, Long collection_type, Long frequency, String made_by, String source, String selectors, String column_names, LocalDateTime created, boolean is_active, boolean proceed) {
        this.name = name;
        this.collection_type = collection_type;
        this.frequency = frequency;
        this.made_by = made_by;
        this.source = source;
        this.selectors = selectors;
        this.column_names = column_names;
        this.created = created;
        this.is_active = is_active;
        this.proceed = proceed;
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

    public Long getCollection_type() {
        return collection_type;
    }

    public void setCollection_type(Long collection_type) {
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

    public boolean isProceed() {
        return proceed;
    }

    public void setProceed(boolean proceed) {
        this.proceed = proceed;
    }

    @Override
    public String toString() {
        return "Data_collector{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", collection_type=" + collection_type +
                ", frequency=" + frequency +
                ", made_by='" + made_by + '\'' +
                ", source='" + source + '\'' +
                ", selectors='" + selectors + '\'' +
                ", column_names='" + column_names + '\'' +
                ", created=" + created +
                ", is_active=" + is_active +
                ", proceed=" + proceed +
                '}';
    }
}
