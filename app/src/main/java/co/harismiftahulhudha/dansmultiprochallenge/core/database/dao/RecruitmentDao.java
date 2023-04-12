package co.harismiftahulhudha.dansmultiprochallenge.core.database.dao;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;

public interface RecruitmentDao {
    public abstract void insert(RecruitmentEntity entity);
    public abstract LiveData<List<RecruitmentEntity>> getAll(String description, String location, String type, int page);
    public abstract LiveData<RecruitmentEntity> getDetail(String id);
}
