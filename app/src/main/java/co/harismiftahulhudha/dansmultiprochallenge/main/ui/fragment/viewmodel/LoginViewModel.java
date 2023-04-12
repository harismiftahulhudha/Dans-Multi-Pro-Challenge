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
public class LoginViewModel extends ViewModel {

    @Inject
    public LoginViewModel() {
        //
    }

    private final MediatorLiveData<String> _statusLogin = new MediatorLiveData<>();
    public LiveData<String> observeStatusLogin = _statusLogin;

    public void setStatusLogin(String status) {
        _statusLogin.postValue(status);
    }
}
