package co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.source.GetDetailRecruitmentSource;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.source.GetListRecruitmentSource;
import co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload.RecruitmentQueryPayload;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;

public class RecruitmentRepositoryImpl implements RecruitmentRepository {

    private final GetListRecruitmentSource getListRecruitmentSource;
    private final GetDetailRecruitmentSource getDetailRecruitmentSource;

    public RecruitmentRepositoryImpl(GetListRecruitmentSource getListRecruitmentSource, GetDetailRecruitmentSource getDetailRecruitmentSource) {
        this.getListRecruitmentSource = getListRecruitmentSource;
        this.getDetailRecruitmentSource = getDetailRecruitmentSource;
    }

    @Override
    public LiveData<Resource<List<RecruitmentEntity>>> getListRecruitment(int page, RecruitmentQueryPayload queryPayload) {
        return getListRecruitmentSource.run(page, queryPayload);
    }

    @Override
    public LiveData<Resource<RecruitmentEntity>> getDetailRecruitment(String id) {
        return getDetailRecruitmentSource.run(id);
    }
}
