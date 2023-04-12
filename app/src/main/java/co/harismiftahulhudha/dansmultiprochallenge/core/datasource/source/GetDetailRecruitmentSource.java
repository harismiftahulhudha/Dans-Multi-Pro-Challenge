package co.harismiftahulhudha.dansmultiprochallenge.core.datasource.source;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.dao.RecruitmentDao;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository.RecruitmentApiService;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.response.RecruitmentResponse;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.ApiResponse;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.AppExecutors;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.NetworkBoundResource;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;

public class GetDetailRecruitmentSource {
    private final RecruitmentApiService apiService;
    private final RecruitmentDao dao;
    private final AppExecutors appExecutors;

    public GetDetailRecruitmentSource(RecruitmentApiService apiService, RecruitmentDao dao, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.dao = dao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<RecruitmentEntity>> run(String id) {
        return new NetworkBoundResource<RecruitmentEntity, RecruitmentResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull RecruitmentResponse item) {
                dao.insert(item.toEntity());
            }

            @Override
            protected boolean shouldFetch(@Nullable RecruitmentEntity data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<RecruitmentEntity> loadFromDb() {
                return dao.getDetail(id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RecruitmentResponse>> createCall() {
                return apiService.getDetailRecruitment(id);
            }
        }.asLiveData();
    }
}
