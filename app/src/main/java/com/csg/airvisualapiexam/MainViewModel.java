package com.csg.airvisualapiexam;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.csg.airvisualapiexam.models.Favorite;
import com.csg.airvisualapiexam.models.Memo;
import com.csg.airvisualapiexam.repository.AppDatabase;

import java.util.List;


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

    public void completeChanged(Favorite favorite, boolean isChecked) {
        if (isChecked) {
            mDb.favoriteDao().insertFavorite(favorite);
        } else {
            mDb.favoriteDao().deleteFavorite(favorite);
        }
    }

    public LiveData<List<Favorite>> favorites() {
        return mDb.favoriteDao().getAll();
    }

}
