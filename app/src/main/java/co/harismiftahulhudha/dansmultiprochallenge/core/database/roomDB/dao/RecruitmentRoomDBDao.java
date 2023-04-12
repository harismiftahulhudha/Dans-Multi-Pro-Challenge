package co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;

@Dao
public interface RecruitmentRoomDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RecruitmentEntity entity);

    @Query("SELECT * FROM recruitment WHERE description LIKE '%' || :description || '%' AND location LIKE '%' || :location || '%' AND type LIKE '%' || :type || '%' ORDER BY createdAt DESC LIMIT (:page * 10)")
    public abstract LiveData<List<RecruitmentEntity>> getAll(String description, String location, String type, int page);

    @Query("SELECT * FROM recruitment WHERE id = :id")
    public abstract LiveData<RecruitmentEntity> getDetail(String id);
}
