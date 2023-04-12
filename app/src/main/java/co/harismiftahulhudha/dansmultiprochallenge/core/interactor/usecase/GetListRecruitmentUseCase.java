package co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload.RecruitmentQueryPayload;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;

public interface GetListRecruitmentUseCase {
    LiveData<Resource<List<RecruitmentEntity>>> run(int page, RecruitmentQueryPayload queryPayload);
}
