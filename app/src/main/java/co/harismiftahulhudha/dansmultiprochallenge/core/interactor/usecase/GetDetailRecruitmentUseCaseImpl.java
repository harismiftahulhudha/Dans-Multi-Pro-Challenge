package co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase;

import androidx.lifecycle.LiveData;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository.RecruitmentRepository;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;

public class GetDetailRecruitmentUseCaseImpl implements GetDetailRecruitmentUseCase {

    public final RecruitmentRepository repository;

    public GetDetailRecruitmentUseCaseImpl(RecruitmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public LiveData<Resource<RecruitmentEntity>> run(String id) {
        return repository.getDetailRecruitment(id);
    }
}
