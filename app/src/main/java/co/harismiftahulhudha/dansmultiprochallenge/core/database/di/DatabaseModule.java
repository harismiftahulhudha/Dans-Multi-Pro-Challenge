package co.harismiftahulhudha.dansmultiprochallenge.core.database.di;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.dao.RecruitmentDao;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.AppRoomDB;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.dao.RecruitmentRoomDBDao;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.dao.RecruitmentRoomDBDaoImpl;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    AppRoomDB provideAppRoomDB(@ApplicationContext Context context) {
        return AppRoomDB.getInstance(context);
    }

    @Provides
    @Singleton
    RecruitmentRoomDBDao provideRecruitmentRoomDBDao(AppRoomDB db) {
        return db.recruitmentDao();
    }

    @Provides
    @Singleton
    @Named("RecruitmentRoomDBDaoImpl")
    RecruitmentDao provideRecruitmentDao(RecruitmentRoomDBDao dao) {
        return new RecruitmentRoomDBDaoImpl(dao);
    }
}
