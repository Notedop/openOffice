package com.rvh.openoffice.parts.main.config;

import java.time.ZonedDateTime;

public class CoreConfig extends Config {

    private String creator = "com.rvh.openOffice";
    private ZonedDateTime created = ZonedDateTime.now();
    private String title = "My excel report";
    private String subject = "Data";
    private String language = "EN";
    private String version = "1.0";
    private String lastModifiedBy = "com.rvh.openOffice";
    private ZonedDateTime modified = ZonedDateTime.now();
    private String contentStatus = "REVIEWED";
    private String category = "Uncategorized";
    private String description = "Open Office XML created by com.rvh.openOffice";

    public CoreConfig() {
        super("core", null, "docProps/core.xml");
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getModified() {
        return modified;
    }

    public void setModified(ZonedDateTime modified) {
        this.modified = modified;
    }

    public String getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
