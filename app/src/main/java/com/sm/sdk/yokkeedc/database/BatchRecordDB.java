package com.sm.sdk.yokkeedc.database;

import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sm.sdk.yokkeedc.transaction.BatchRecord;

import java.sql.SQLException;

public class BatchRecordDB {
    private static final String TAG = "BatchRecordDB";
    private RuntimeExceptionDao<BatchRecord, Integer> batchRecordDao = null;
    private static BatchRecordDB instance;

    private BaseDbHelper dbHelper;

    public BatchRecordDB() {
        dbHelper = BaseDbHelper.getInstance();
    }

    public static synchronized BatchRecordDB getInstance() {
        if (instance == null) {
            instance = new BatchRecordDB();
        }

        return instance;
    }

    private RuntimeExceptionDao<BatchRecord, Integer> getBatchRecordDao() {
        if(batchRecordDao == null) {
            batchRecordDao = dbHelper.getRuntimeExceptionDao(BatchRecord.class);
        }
        return batchRecordDao;
    }

    /**
     * insert trans parameter
     */
    public boolean insertBatchRecordDb(BatchRecord batchRecord) {
        try{
            RuntimeExceptionDao<BatchRecord, Integer> dao = getBatchRecordDao();
            dao.create(batchRecord);
            return true;
        } catch(RuntimeException e) {
            Log.e(TAG,"ERROR INSERT BATCH RECORD",e);
        }
        return false;
    }

    public boolean updateBatchRecord(BatchRecord batchRecord) {
        try {
            RuntimeExceptionDao<BatchRecord,Integer> dao = getBatchRecordDao();
            dao.update(batchRecord);
            return true;
        } catch(RuntimeException e) {
            Log.e(TAG,"ERROR UPDATE BATCH RECORD",e);
        }
        return false;
    }

    public BatchRecord findTransData(int id) {
        try {
            RuntimeExceptionDao<BatchRecord, Integer> dao = getBatchRecordDao();
            return dao.queryForId(id);
        } catch (RuntimeException e) {
            Log.e(TAG,"ERROR FIND BATCH RECORD by id",e);
        }

        return null;
    }

    public BatchRecord findBatchRecordByTraceNo(String traceNo) {
        try {
            RuntimeExceptionDao<BatchRecord, Integer> dao = getBatchRecordDao();
            QueryBuilder<BatchRecord, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq(BatchRecord.TRACE_NO_FIELD, traceNo);
            return queryBuilder.queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, "ERROR findBatchRecordByTraceNo", e);
        }

        return null;
    }
}