package co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.harismiftahulhudha.dansmultiprochallenge.BuildConfig;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.dao.RecruitmentRoomDBDao;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;

@Database(entities = {RecruitmentEntity.class}, version = 1)
public abstract class AppRoomDB extends RoomDatabase {
    public static AppRoomDB getInstance(Context context) {
        if (BuildConfig.DEBUG) {
            return Room.databaseBuilder(context, AppRoomDB.class, "dmp.db")
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build();
        } else {
            return Room.databaseBuilder(context, AppRoomDB.class, "dmp.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }
    abstract public RecruitmentRoomDBDao recruitmentDao();
}
