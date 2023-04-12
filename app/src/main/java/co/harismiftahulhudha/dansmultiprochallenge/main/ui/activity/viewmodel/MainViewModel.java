package co.harismiftahulhudha.dansmultiprochallenge.main.ui.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload.RecruitmentQueryPayload;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetDetailRecruitmentUseCase;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetListRecruitmentUseCase;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final GetListRecruitmentUseCase getListRecruitmentUseCase;
    private final GetDetailRecruitmentUseCase getDetailRecruitmentUseCase;

    @Inject
    public MainViewModel(GetListRecruitmentUseCase getListRecruitmentUseCase, GetDetailRecruitmentUseCase getDetailRecruitmentUseCase) {
        this.getListRecruitmentUseCase = getListRecruitmentUseCase;
        this.getDetailRecruitmentUseCase = getDetailRecruitmentUseCase;
    }

    public LiveData<Resource<List<RecruitmentEntity>>> recruitments;

    public void getListRecruitment(int page, RecruitmentQueryPayload queryPayload) {
        recruitments = getListRecruitmentUseCase.run(page, queryPayload);
    }

    public LiveData<Resource<RecruitmentEntity>> recruitment;

    public void getDetailRecruitment(String id) {
        recruitment = getDetailRecruitmentUseCase.run(id);
    }

    private MutableLiveData<Boolean> _isShowMenu = new MediatorLiveData<>();
    public LiveData<Boolean> isShowMenu = _isShowMenu;
    public void setShowMenu(boolean isShowMenu) {
        _isShowMenu.postValue(isShowMenu);
    }
}
