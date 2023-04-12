package co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload.RecruitmentQueryPayload;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;

public interface RecruitmentRepository {
    LiveData<Resource<List<RecruitmentEntity>>> getListRecruitment(int page, RecruitmentQueryPayload queryPayload);
    LiveData<Resource<RecruitmentEntity>> getDetailRecruitment(String id);
}
