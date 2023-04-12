package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload.RecruitmentQueryPayload;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetListRecruitmentUseCase;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Status;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RecruitmentViewModel extends ViewModel {
    private final GetListRecruitmentUseCase getListRecruitmentUseCase;
    private final MediatorLiveData<Boolean> _isMenuFilterOpen = new MediatorLiveData<>();
    public LiveData<Boolean> observeIsMenuFilterOpen = _isMenuFilterOpen;
    private boolean isMenuFilterOpen;

    public boolean isFulltime = false;
    public String inputQuery = "";
    public String inputLocation = "";

    public int page = 1;

    @Inject
    public RecruitmentViewModel(GetListRecruitmentUseCase getListRecruitmentUseCase) {
        this.getListRecruitmentUseCase = getListRecruitmentUseCase;
        _isMenuFilterOpen.postValue(false);
    }

    private final MediatorLiveData<Resource<List<RecruitmentEntity>>> listRecruitment  = new MediatorLiveData<>();
    public LiveData<Resource<List<RecruitmentEntity>>> observeListRecruitment() {
        return listRecruitment;
    }
    public void getListRecruitment(boolean isNextPage) {
        if (isNextPage) {
            page++;
        } else {
            page = 1;
        }

        String fullTime = "";
        if (isFulltime) {
            fullTime = "Full Time";
        }
        LiveData<Resource<List<RecruitmentEntity>>> source = getListRecruitmentUseCase.run(page, new RecruitmentQueryPayload(inputQuery, inputLocation, fullTime));
        listRecruitment.addSource(source, data -> {
            if (data != null) {
                listRecruitment.postValue(data);
                if (data.status != Status.LOADING) {
                    listRecruitment.removeSource(source);
                }
            } else {
                listRecruitment.removeSource(source);
            }
        });
    }

    public void setIsMenuFilterOpen() {
        isMenuFilterOpen = !isMenuFilterOpen;
        _isMenuFilterOpen.postValue(isMenuFilterOpen);
    }

    public void setInputQuery(String query) {
        inputQuery = query;
    }

    public void setIsFulltime(Boolean isFulltime) {
        this.isFulltime = isFulltime;
    }

    public void setInputLocation(String location) {
        inputLocation = location;
    }
}
