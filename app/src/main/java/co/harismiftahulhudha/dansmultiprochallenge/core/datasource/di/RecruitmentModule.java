package co.harismiftahulhudha.dansmultiprochallenge.core.datasource.di;

import javax.inject.Named;
import javax.inject.Singleton;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.dao.RecruitmentDao;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository.RecruitmentApiService;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository.RecruitmentRepository;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.repository.RecruitmentRepositoryImpl;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.source.GetDetailRecruitmentSource;
import co.harismiftahulhudha.dansmultiprochallenge.core.datasource.source.GetListRecruitmentSource;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.AppExecutors;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class RecruitmentModule {
    @Provides
    @Singleton
    public RecruitmentApiService provideRecruitmentApiService(Retrofit retrofit) {
        return retrofit.create(RecruitmentApiService.class);
    }

    @Provides
    @Singleton
    public RecruitmentRepository provideRecruitmentRepository(GetListRecruitmentSource getListRecruitmentSource, GetDetailRecruitmentSource getDetailRecruitmentSource) {
        return new RecruitmentRepositoryImpl(getListRecruitmentSource, getDetailRecruitmentSource);
    }

    @Provides
    @Singleton
    public GetListRecruitmentSource provideGetListRecruitmentSource(RecruitmentApiService apiService, @Named("RecruitmentRoomDBDaoImpl") RecruitmentDao dao, AppExecutors appExecutors) {
        return new GetListRecruitmentSource(apiService, dao, appExecutors);
    }

    @Provides
    @Singleton
    public GetDetailRecruitmentSource provideGetDetailRecruitmentSource(RecruitmentApiService apiService, @Named("RecruitmentRoomDBDaoImpl") RecruitmentDao dao, AppExecutors appExecutors) {
        return new GetDetailRecruitmentSource(apiService, dao, appExecutors);
    }
}
