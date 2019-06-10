package com.example.count.view;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public final class CounterRepository {

    // Reference: https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7

    private CounterDao counterDao;
    private MutableLiveData<List<Counter>> allCounters;

    public CounterRepository(Application application) {
        CounterRoomDatabase rdb = CounterRoomDatabase.getInstance(application);
        counterDao = rdb.counterDao();
        allCounters = counterDao.getAllCounters();
    }

    public MutableLiveData<List<Counter>> getAllCounters() {
        return allCounters;
    }

    public void insert(Counter counter) {
        new insertAsyncTask(counterDao).execute(counter);
    }

    public void delete(Counter counter) {
        new deleteAsyncTask(counterDao).execute(counter);
    }

    public void update(Counter counter) {
        new updateAsyncTask(counterDao).execute(counter);
    }

    private static class insertAsyncTask extends AsyncTask<Counter, Void, Void> {
        private CounterDao asyncTaskDao;

        insertAsyncTask(CounterDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Counter... counters) {
            asyncTaskDao.insert(counters[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Counter, Void, Void> {
        private CounterDao asyncTaskDao;

        public deleteAsyncTask(CounterDao counterDao) {
            this.asyncTaskDao = counterDao;
        }


        @Override
        protected Void doInBackground(Counter... counters) {
            asyncTaskDao.delete(counters[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Counter, Void, Void>{

        private CounterDao asyncTaskDao;

        public updateAsyncTask(CounterDao counterDao) {
            this.asyncTaskDao = counterDao;
        }


        @Override
        protected Void doInBackground(Counter... counters) {
            asyncTaskDao.update(counters[0]);
            return null;
        }
    }
}
