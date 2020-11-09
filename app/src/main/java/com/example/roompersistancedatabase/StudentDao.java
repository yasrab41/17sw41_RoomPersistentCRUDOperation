package com.example.roompersistancedatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM entitystudentrecord")
    List<EntitySutdentRecord> getAll();

    @Query("SELECT * FROM entitystudentrecord WHERE id LIKE :id")
    EntitySutdentRecord findStudentById(int id);

    @Insert
    void insert(EntitySutdentRecord entitySutdentRecord);
    @Delete
    void delete(EntitySutdentRecord entitySutdentRecord);
    @Update
    void update(EntitySutdentRecord entitySutdentRecord);

}
