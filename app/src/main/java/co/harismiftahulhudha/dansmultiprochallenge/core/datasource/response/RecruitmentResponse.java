package co.harismiftahulhudha.dansmultiprochallenge.core.datasource.response;

import com.google.gson.annotations.SerializedName;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.helper.DefaultValueHelper;

public class RecruitmentResponse {
    @SerializedName("id")
    public String id;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("company")
    public String company;
    @SerializedName("company_url")
    public String companyUrl;
    @SerializedName("location")
    public String location;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("how_to_apply")
    public String howToApply;
    @SerializedName("company_logo")
    public String companyLogo;

    public RecruitmentEntity toEntity() {
        return new RecruitmentEntity(
                DefaultValueHelper.orBlank(this.id),
                DefaultValueHelper.orBlank(this.type),
                DefaultValueHelper.orBlank(this.url),
                DefaultValueHelper.orBlank(this.createdAt),
                DefaultValueHelper.orBlank(this.company),
                DefaultValueHelper.orBlank(this.companyUrl),
                DefaultValueHelper.orBlank(this.location),
                DefaultValueHelper.orBlank(this.title),
                DefaultValueHelper.orBlank(this.description),
                DefaultValueHelper.orBlank(this.howToApply),
                DefaultValueHelper.orBlank(this.companyLogo)
        );
    }
}
