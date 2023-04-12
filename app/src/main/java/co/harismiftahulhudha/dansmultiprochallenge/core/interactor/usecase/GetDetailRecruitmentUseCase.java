package co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase;

import androidx.lifecycle.LiveData;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;

public interface GetDetailRecruitmentUseCase {
    LiveData<Resource<RecruitmentEntity>> run(String id);
}
