package co.harismiftahulhudha.dansmultiprochallenge.core.datasource.source;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.dao.RecruitmentDao;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository.RecruitmentApiService;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.response.RecruitmentResponse;
import co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload.RecruitmentQueryPayload;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.ApiResponse;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.AppExecutors;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.NetworkBoundResource;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;

public class GetListRecruitmentSource {
    private final RecruitmentApiService apiService;
    private final RecruitmentDao dao;
    private final AppExecutors appExecutors;

    public GetListRecruitmentSource(RecruitmentApiService apiService, RecruitmentDao dao, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.dao = dao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<RecruitmentEntity>>> run(int page, RecruitmentQueryPayload queryPayload) {
        return new NetworkBoundResource<List<RecruitmentEntity>, List<RecruitmentResponse>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<RecruitmentResponse> item) {
                for (RecruitmentResponse data : item) {
                    if (data != null) {
                        dao.insert(data.toEntity());
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RecruitmentEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RecruitmentEntity>> loadFromDb() {
                String type = "";
                if (queryPayload.isFullTime() != null && !queryPayload.isFullTime().equals("")) {
                    type = "Full Time";
                }
                return dao.getAll(queryPayload.getDescription(), queryPayload.getLocation(), type, page);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<RecruitmentResponse>>> createCall() {
                return apiService.getListRecruitment(page, queryPayload.toHashMap());
            }
        }.asLiveData();
    }
}
