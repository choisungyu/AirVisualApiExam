package com.csg.airvisualapiexam.repository;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.csg.airvisualapiexam.models.Favorite;
import com.csg.airvisualapiexam.models.Memo;

@Database(entities = {Favorite.class, Memo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

    public abstract MemoDao memoDao();
}
