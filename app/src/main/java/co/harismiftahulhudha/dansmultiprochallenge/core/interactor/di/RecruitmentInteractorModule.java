package co.harismiftahulhudha.dansmultiprochallenge.core.interactor.di;

import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository.RecruitmentRepository;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetDetailRecruitmentUseCase;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetDetailRecruitmentUseCaseImpl;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetListRecruitmentUseCase;
import co.harismiftahulhudha.dansmultiprochallenge.core.interactor.usecase.GetListRecruitmentUseCaseImpl;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@Module
@InstallIn(ViewModelComponent.class)
public class RecruitmentInteractorModule {
    @Provides
    @ViewModelScoped
    public GetListRecruitmentUseCase provideGetListRecruitmentUseCase(RecruitmentRepository repository) {
        return new GetListRecruitmentUseCaseImpl(repository);
    }
    @Provides
    @ViewModelScoped
    public GetDetailRecruitmentUseCase provideGetDetailRecruitmentUseCase(RecruitmentRepository repository) {
        return new GetDetailRecruitmentUseCaseImpl(repository);
    }
}
