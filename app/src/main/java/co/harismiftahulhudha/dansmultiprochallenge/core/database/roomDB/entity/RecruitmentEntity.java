package co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recruitment")
public class RecruitmentEntity {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private final String id;
    private final String type;
    private final String url;
    private final String createdAt;
    private final String company;
    private final String companyUrl;
    private final String location;
    private final String title;
    private final String description;
    private final String howToApply;
    private final String companyLogo;

    public RecruitmentEntity(@NonNull String id, String type, String url, String createdAt, String company, String companyUrl, String location, String title, String description, String howToApply, String companyLogo) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.createdAt = createdAt;
        this.company = company;
        this.companyUrl = companyUrl;
        this.location = location;
        this.title = title;
        this.description = description;
        this.howToApply = howToApply;
        this.companyLogo = companyLogo;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getHowToApply() {
        return howToApply;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }
}
