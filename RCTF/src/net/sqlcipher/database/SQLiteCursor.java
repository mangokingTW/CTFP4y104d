// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import net.sqlcipher.AbstractWindowedCursor;
import net.sqlcipher.CursorWindow;
import net.sqlcipher.SQLException;

// Referenced classes of package net.sqlcipher.database:
//            DatabaseObjectNotClosedException, SQLiteDatabase, SQLiteQuery, SQLiteCursorDriver, 
//            SQLiteDebug

public class SQLiteCursor extends AbstractWindowedCursor
{
    protected class MainThreadNotificationHandler extends Handler
    {

        final SQLiteCursor this$0;

        public void handleMessage(Message message)
        {
            notifyDataSetChange();
        }

        protected MainThreadNotificationHandler()
        {
            this$0 = SQLiteCursor.this;
            super();
        }
    }


    static final int NO_COUNT = -1;
    static final String TAG = "Cursor";
    private Map mColumnNameMap;
    private String mColumns[];
    private int mCount;
    private int mCursorState;
    private SQLiteDatabase mDatabase;
    private SQLiteCursorDriver mDriver;
    private String mEditTable;
    private int mInitialRead;
    private ReentrantLock mLock;
    private int mMaxRead;
    protected MainThreadNotificationHandler mNotificationHandler;
    private boolean mPendingData;
    private SQLiteQuery mQuery;
    private Throwable mStackTrace;

    public SQLiteCursor(SQLiteDatabase sqlitedatabase, SQLiteCursorDriver sqlitecursordriver, String s, SQLiteQuery sqlitequery)
    {
        mCount = -1;
        mMaxRead = 0x7fffffff;
        mInitialRead = 0x7fffffff;
        mCursorState = 0;
        mLock = null;
        mPendingData = false;
        mStackTrace = (new DatabaseObjectNotClosedException()).fillInStackTrace();
        mDatabase = sqlitedatabase;
        mDriver = sqlitecursordriver;
        mEditTable = s;
        mColumnNameMap = null;
        mQuery = sqlitequery;
        int j;
        sqlitedatabase.lock();
        j = mQuery.columnCountLocked();
        mColumns = new String[j];
        int i = 0;
_L3:
        if (i >= j) goto _L2; else goto _L1
_L1:
        sqlitecursordriver = mQuery.columnNameLocked(i);
        mColumns[i] = sqlitecursordriver;
        if ("_id".equals(sqlitecursordriver))
        {
            mRowIdColumnIndex = i;
        }
        i++;
          goto _L3
_L2:
        sqlitedatabase.unlock();
        return;
        sqlitecursordriver;
        sqlitedatabase.unlock();
        throw sqlitecursordriver;
    }

    private void deactivateCommon()
    {
        mCursorState = 0;
        if (mWindow != null)
        {
            mWindow.close();
            mWindow = null;
        }
    }

    private void fillWindow(int i)
    {
        if (mWindow != null) goto _L2; else goto _L1
_L1:
        mWindow = new CursorWindow(true);
_L4:
        mWindow.setStartPosition(i);
        mCount = mQuery.fillWindow(mWindow, mInitialRead, 0);
        if (mCount == -1)
        {
            mCount = mInitialRead + i;
            (new Thread(new QueryThread(mCursorState), "query thread")).start();
        }
        return;
_L2:
        mCursorState = mCursorState + 1;
        queryThreadLock();
        mWindow.clear();
        queryThreadUnlock();
        if (true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        queryThreadUnlock();
        throw exception;
    }

    private void queryThreadLock()
    {
        if (mLock != null)
        {
            mLock.lock();
        }
    }

    private void queryThreadUnlock()
    {
        if (mLock != null)
        {
            mLock.unlock();
        }
    }

    public void close()
    {
        close();
        deactivateCommon();
        mQuery.close();
        mDriver.cursorClosed();
    }

    public boolean commitUpdates(Map map)
    {
        if (!supportsUpdates())
        {
            Log.e("Cursor", "commitUpdates not supported on this cursor, did you include the _id column?");
            return false;
        }
        HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (map == null)
        {
            break MISSING_BLOCK_LABEL_36;
        }
        mUpdatedRows.putAll(map);
        if (mUpdatedRows.size() != 0)
        {
            break MISSING_BLOCK_LABEL_55;
        }
        hashmap;
        JVM INSTR monitorexit ;
        return true;
        map;
        hashmap;
        JVM INSTR monitorexit ;
        throw map;
        mDatabase.beginTransaction();
        Iterator iterator;
        map = new StringBuilder(128);
        iterator = mUpdatedRows.entrySet().iterator();
_L6:
        if (!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Map map1;
        Object obj;
        obj = (java.util.Map.Entry)iterator.next();
        map1 = (Map)((java.util.Map.Entry) (obj)).getValue();
        obj = (Long)((java.util.Map.Entry) (obj)).getKey();
        if (obj != null && map1 != null) goto _L4; else goto _L3
_L3:
        throw new IllegalStateException((new StringBuilder()).append("null rowId or values found! rowId = ").append(obj).append(", values = ").append(map1).toString());
        map;
        mDatabase.endTransaction();
        throw map;
_L4:
        if (map1.size() == 0) goto _L6; else goto _L5
_L5:
        Object aobj[];
        long l;
        l = ((Long) (obj)).longValue();
        obj = map1.entrySet().iterator();
        map.setLength(0);
        map.append((new StringBuilder()).append("UPDATE ").append(mEditTable).append(" SET ").toString());
        aobj = new Object[map1.size()];
        int i = 0;
_L7:
        if (((Iterator) (obj)).hasNext())
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)((Iterator) (obj)).next();
            map.append((String)entry.getKey());
            map.append("=?");
            aobj[i] = entry.getValue();
            if (((Iterator) (obj)).hasNext())
            {
                map.append(", ");
            }
            break MISSING_BLOCK_LABEL_462;
        }
        map.append((new StringBuilder()).append(" WHERE ").append(mColumns[mRowIdColumnIndex]).append('=').append(l).toString());
        map.append(';');
        mDatabase.execSQL(map.toString(), aobj);
        mDatabase.rowUpdated(mEditTable, l);
          goto _L6
_L2:
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mUpdatedRows.clear();
        hashmap;
        JVM INSTR monitorexit ;
        onChange(true);
        return true;
        i++;
          goto _L7
    }

    public void deactivate()
    {
        deactivate();
        deactivateCommon();
        mDriver.cursorDeactivated();
    }

    public boolean deleteRow()
    {
        checkPosition();
        if (mRowIdColumnIndex == -1 || mCurrentRowID == null)
        {
            Log.e("Cursor", "Could not delete row because either the row ID column is not available or ithas not been read.");
            return false;
        }
        mDatabase.lock();
        mDatabase.delete(mEditTable, (new StringBuilder()).append(mColumns[mRowIdColumnIndex]).append("=?").toString(), new String[] {
            mCurrentRowID.toString()
        });
        boolean flag = true;
_L2:
        int i = mPos;
        requery();
        moveToPosition(i);
        mDatabase.unlock();
        Object obj;
        if (flag)
        {
            onChange(true);
            return true;
        } else
        {
            return false;
        }
        obj;
        flag = false;
        if (true) goto _L2; else goto _L1
_L1:
        obj;
        mDatabase.unlock();
        throw obj;
    }

    public void fillWindow(int i, android.database.CursorWindow cursorwindow)
    {
        if (mWindow != null) goto _L2; else goto _L1
_L1:
        mWindow = new CursorWindow(true);
_L4:
        mWindow.setStartPosition(i);
        mCount = mQuery.fillWindow(mWindow, mInitialRead, 0);
        if (mCount == -1)
        {
            mCount = mInitialRead + i;
            (new Thread(new QueryThread(mCursorState), "query thread")).start();
        }
        return;
_L2:
        mCursorState = mCursorState + 1;
        queryThreadLock();
        mWindow.clear();
        queryThreadUnlock();
        if (true) goto _L4; else goto _L3
_L3:
        cursorwindow;
        queryThreadUnlock();
        throw cursorwindow;
    }

    protected void finalize()
    {
        StringBuilder stringbuilder;
        String s;
        int j;
        if (mWindow == null)
        {
            break MISSING_BLOCK_LABEL_113;
        }
        j = mQuery.mSql.length();
        stringbuilder = (new StringBuilder()).append("Finalizing a Cursor that has not been deactivated or closed. database = ").append(mDatabase.getPath()).append(", table = ").append(mEditTable).append(", query = ");
        s = mQuery.mSql;
        int i;
        i = j;
        if (j > 100)
        {
            i = 100;
        }
        Log.e("Cursor", stringbuilder.append(s.substring(0, i)).toString(), mStackTrace);
        close();
        SQLiteDebug.notifyActiveCursorFinalized();
        finalize();
        return;
        Exception exception;
        exception;
        finalize();
        throw exception;
    }

    public int getColumnIndex(String s)
    {
        byte byte0 = -1;
        if (mColumnNameMap == null)
        {
            String as[] = mColumns;
            int k = as.length;
            HashMap hashmap = new HashMap(k, 1.0F);
            for (int i = 0; i < k; i++)
            {
                hashmap.put(as[i], Integer.valueOf(i));
            }

            mColumnNameMap = hashmap;
        }
        int j = s.lastIndexOf('.');
        Object obj = s;
        if (j != -1)
        {
            obj = new Exception();
            Log.e("Cursor", (new StringBuilder()).append("requesting column name with table name -- ").append(s).toString(), ((Throwable) (obj)));
            obj = s.substring(j + 1);
        }
        s = (Integer)mColumnNameMap.get(obj);
        j = byte0;
        if (s != null)
        {
            j = s.intValue();
        }
        return j;
    }

    public String[] getColumnNames()
    {
        return mColumns;
    }

    public int getCount()
    {
        if (mCount == -1)
        {
            fillWindow(0);
        }
        return mCount;
    }

    public SQLiteDatabase getDatabase()
    {
        return mDatabase;
    }

    public boolean onMove(int i, int j)
    {
        if (mWindow == null || j < mWindow.getStartPosition() || j >= mWindow.getStartPosition() + mWindow.getNumRows())
        {
            fillWindow(j);
        }
        return true;
    }

    public void registerDataSetObserver(DataSetObserver datasetobserver)
    {
        registerDataSetObserver(datasetobserver);
        if (0x7fffffff == mMaxRead && 0x7fffffff == mInitialRead || mNotificationHandler != null)
        {
            break MISSING_BLOCK_LABEL_66;
        }
        queryThreadLock();
        mNotificationHandler = new MainThreadNotificationHandler();
        if (mPendingData)
        {
            notifyDataSetChange();
            mPendingData = false;
        }
        queryThreadUnlock();
        return;
        datasetobserver;
        queryThreadUnlock();
        throw datasetobserver;
    }

    public boolean requery()
    {
        if (isClosed())
        {
            return false;
        }
        mDatabase.lock();
        if (mWindow != null)
        {
            mWindow.clear();
        }
        mPos = -1;
        mDriver.cursorRequeried(this);
        mCount = -1;
        mCursorState = mCursorState + 1;
        queryThreadLock();
        mQuery.requery();
        queryThreadUnlock();
        mDatabase.unlock();
        return requery();
        Exception exception;
        exception;
        queryThreadUnlock();
        throw exception;
        exception;
        mDatabase.unlock();
        throw exception;
    }

    public void setLoadStyle(int i, int j)
    {
        mMaxRead = j;
        mInitialRead = i;
        mLock = new ReentrantLock(true);
    }

    public void setSelectionArguments(String as[])
    {
        mDriver.setBindArguments(as);
    }

    public void setWindow(CursorWindow cursorwindow)
    {
        if (mWindow == null)
        {
            break MISSING_BLOCK_LABEL_37;
        }
        mCursorState = mCursorState + 1;
        queryThreadLock();
        mWindow.close();
        queryThreadUnlock();
        mCount = -1;
        mWindow = cursorwindow;
        return;
        cursorwindow;
        queryThreadUnlock();
        throw cursorwindow;
    }

    public boolean supportsUpdates()
    {
        return !TextUtils.isEmpty(mEditTable);
    }


/*
    static boolean access$002(SQLiteCursor sqlitecursor, boolean flag)
    {
        sqlitecursor.mPendingData = flag;
        return flag;
    }

*/







/*
    static int access$502(SQLiteCursor sqlitecursor, int i)
    {
        sqlitecursor.mCount = i;
        return i;
    }

*/


/*
    static int access$512(SQLiteCursor sqlitecursor, int i)
    {
        i = sqlitecursor.mCount + i;
        sqlitecursor.mCount = i;
        return i;
    }

*/


}
