package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetDetailRecruitmentUseCase;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Status;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RecruitmentDetailViewModel extends ViewModel {
    private final GetDetailRecruitmentUseCase getDetailRecruitmentUseCase;

    public String id = "";
    public String companyUrl = "";

    @Inject
    public RecruitmentDetailViewModel(GetDetailRecruitmentUseCase getDetailRecruitmentUseCase, SavedStateHandle stateHandle) {
        this.getDetailRecruitmentUseCase = getDetailRecruitmentUseCase;
        id = (String) stateHandle.getLiveData("id").getValue();
    }

    private MediatorLiveData<Resource<RecruitmentEntity>> detailRecruitment = new MediatorLiveData<>();

    public LiveData<Resource<RecruitmentEntity>> observeDetailRecruitment() {
        return detailRecruitment;
    }

    public void getDetailRecruitment() {
        LiveData<Resource<RecruitmentEntity>> source = getDetailRecruitmentUseCase.run(id);
        detailRecruitment.addSource(source, data -> {
            if (data != null) {
                detailRecruitment.postValue(data);
                if (data.status != Status.LOADING) {
                    detailRecruitment.removeSource(source);
                }
            } else {
                detailRecruitment.removeSource(source);
            }
        });
    }
}
