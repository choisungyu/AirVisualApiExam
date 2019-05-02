package com.csg.airvisualapiexam;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.persistence.room.Room;

import android.support.annotation.NonNull;

import com.csg.airvisualapiexam.models.Memo;
import com.csg.airvisualapiexam.repository.AppDatabase;


public class MainViewModel extends AndroidViewModel{
    private AppDatabase mDb;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mDb = Room.databaseBuilder(application,
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();
    }

    public void insertOrUpdateMemo(Memo memo) {
        mDb.memoDao().insertOrUpdateMemo(memo);
    }

    public Memo getMemo(String id) {
        return mDb.memoDao().getMemo(id);
    }

}
