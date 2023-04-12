package co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository;

import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.response.RecruitmentResponse;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RecruitmentApiService {
    @GET("recruitment/positions.json")
    LiveData<ApiResponse<List<RecruitmentResponse>>> getListRecruitment(@Query("page") int page, @QueryMap HashMap<String, String> query);

    @GET("recruitment/positions/{id}")
    LiveData<ApiResponse<RecruitmentResponse>> getDetailRecruitment(@Path("id") String id);
}