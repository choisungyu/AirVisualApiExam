package com.csg.airvisualapiexam.repository;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.csg.airvisualapiexam.models.Memo;


@Dao
public interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateMemo(Memo memo);

    @Query("SELECT * FROM memo WHERE id=:id")
    Memo getMemo(String id);
}
