package co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.dao;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.dao.RecruitmentDao;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;

public class RecruitmentRoomDBDaoImpl implements RecruitmentDao {

    private final RecruitmentRoomDBDao dao;

    public RecruitmentRoomDBDaoImpl(RecruitmentRoomDBDao dao) {
        this.dao = dao;
    }

    @Override
    public void insert(RecruitmentEntity entity) {
        dao.insert(entity);
    }

    @Override
    public LiveData<List<RecruitmentEntity>> getAll(String description, String location, String type, int page) {
        return dao.getAll(description, location, type, page);
    }

    @Override
    public LiveData<RecruitmentEntity> getDetail(String id) {
        return dao.getDetail(id);
    }
}
