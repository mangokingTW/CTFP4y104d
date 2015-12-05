// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.content.Context;
import android.util.Log;
import java.io.File;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase, SQLiteException

public abstract class SQLiteOpenHelper
{

    private static final String TAG = net/sqlcipher/database/SQLiteOpenHelper.getSimpleName();
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private final SQLiteDatabase.CursorFactory mFactory;
    private boolean mIsInitializing;
    private final String mName;
    private final int mNewVersion;

    public SQLiteOpenHelper(Context context, String s, SQLiteDatabase.CursorFactory cursorfactory, int i)
    {
        mDatabase = null;
        mIsInitializing = false;
        if (i < 1)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Version must be >= 1, was ").append(i).toString());
        } else
        {
            mContext = context;
            mName = s;
            mFactory = cursorfactory;
            mNewVersion = i;
            return;
        }
    }

    public void close()
    {
        this;
        JVM INSTR monitorenter ;
        if (mIsInitializing)
        {
            throw new IllegalStateException("Closed during initialization");
        }
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if (mDatabase != null && mDatabase.isOpen())
        {
            mDatabase.close();
            mDatabase = null;
        }
        this;
        JVM INSTR monitorexit ;
    }

    public SQLiteDatabase getReadableDatabase(String s)
    {
        this;
        JVM INSTR monitorenter ;
        if (mDatabase == null || !mDatabase.isOpen()) goto _L2; else goto _L1
_L1:
        s = mDatabase;
_L4:
        this;
        JVM INSTR monitorexit ;
        return s;
_L2:
        if (mIsInitializing)
        {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }
        break MISSING_BLOCK_LABEL_50;
        s;
        this;
        JVM INSTR monitorexit ;
        throw s;
        SQLiteDatabase sqlitedatabase = getWritableDatabase(s);
        s = sqlitedatabase;
        continue; /* Loop/switch isn't completed */
        SQLiteException sqliteexception;
        sqliteexception;
        if (mName == null)
        {
            throw sqliteexception;
        }
        Log.e(TAG, (new StringBuilder()).append("Couldn't open ").append(mName).append(" for writing (will try read-only):").toString(), sqliteexception);
        SQLiteDatabase sqlitedatabase1;
        SQLiteDatabase sqlitedatabase2;
        sqlitedatabase2 = null;
        sqlitedatabase1 = null;
        sqliteexception = sqlitedatabase2;
        mIsInitializing = true;
        sqliteexception = sqlitedatabase2;
        String s1 = mContext.getDatabasePath(mName).getPath();
        sqliteexception = sqlitedatabase2;
        File file = new File(s1);
        sqliteexception = sqlitedatabase2;
        File file1 = new File(mContext.getDatabasePath(mName).getParent());
        sqliteexception = sqlitedatabase2;
        if (file1.exists())
        {
            break MISSING_BLOCK_LABEL_198;
        }
        sqliteexception = sqlitedatabase2;
        file1.mkdirs();
        sqliteexception = sqlitedatabase2;
        if (file.exists())
        {
            break MISSING_BLOCK_LABEL_239;
        }
        sqliteexception = sqlitedatabase2;
        mIsInitializing = false;
        sqliteexception = sqlitedatabase2;
        sqlitedatabase1 = getWritableDatabase(s);
        sqliteexception = sqlitedatabase1;
        mIsInitializing = true;
        sqliteexception = sqlitedatabase1;
        sqlitedatabase1.close();
        sqliteexception = sqlitedatabase1;
        sqlitedatabase1 = SQLiteDatabase.openDatabase(s1, s, mFactory, 1);
        sqliteexception = sqlitedatabase1;
        if (sqlitedatabase1.getVersion() == mNewVersion)
        {
            break MISSING_BLOCK_LABEL_344;
        }
        sqliteexception = sqlitedatabase1;
        throw new SQLiteException((new StringBuilder()).append("Can't upgrade read-only database from version ").append(sqlitedatabase1.getVersion()).append(" to ").append(mNewVersion).append(": ").append(s1).toString());
        s;
        mIsInitializing = false;
        if (sqliteexception == null)
        {
            break MISSING_BLOCK_LABEL_342;
        }
        if (sqliteexception != mDatabase)
        {
            sqliteexception.close();
        }
        throw s;
        sqliteexception = sqlitedatabase1;
        onOpen(sqlitedatabase1);
        sqliteexception = sqlitedatabase1;
        Log.w(TAG, (new StringBuilder()).append("Opened ").append(mName).append(" in read-only mode").toString());
        sqliteexception = sqlitedatabase1;
        mDatabase = sqlitedatabase1;
        sqliteexception = sqlitedatabase1;
        sqlitedatabase2 = mDatabase;
        mIsInitializing = false;
        s = sqlitedatabase2;
        if (sqlitedatabase1 == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        s = sqlitedatabase2;
        if (sqlitedatabase1 == mDatabase)
        {
            continue; /* Loop/switch isn't completed */
        }
        sqlitedatabase1.close();
        s = sqlitedatabase2;
        if (true) goto _L4; else goto _L3
_L3:
    }

    public SQLiteDatabase getWritableDatabase(String s)
    {
        this;
        JVM INSTR monitorenter ;
        if (mDatabase == null || !mDatabase.isOpen() || mDatabase.isReadOnly()) goto _L2; else goto _L1
_L1:
        Object obj = mDatabase;
_L11:
        this;
        JVM INSTR monitorexit ;
        return ((SQLiteDatabase) (obj));
_L2:
        if (mIsInitializing)
        {
            throw new IllegalStateException("getWritableDatabase called recursively");
        }
        break MISSING_BLOCK_LABEL_60;
        s;
        this;
        JVM INSTR monitorexit ;
        throw s;
        SQLiteDatabase sqlitedatabase = null;
        if (mDatabase != null)
        {
            mDatabase.lock();
        }
        obj = sqlitedatabase;
        mIsInitializing = true;
        obj = sqlitedatabase;
        if (mName != null) goto _L4; else goto _L3
_L3:
        obj = sqlitedatabase;
        s = SQLiteDatabase.create(null, s);
_L12:
        obj = s;
        int i = s.getVersion();
        obj = s;
        if (i == mNewVersion) goto _L6; else goto _L5
_L5:
        obj = s;
        s.beginTransaction();
        if (i != 0) goto _L8; else goto _L7
_L7:
        onCreate(s);
_L13:
        s.setVersion(mNewVersion);
        s.setTransactionSuccessful();
        obj = s;
        s.endTransaction();
_L6:
        obj = s;
        onOpen(s);
        mIsInitializing = false;
        if (false) goto _L10; else goto _L9
_L9:
        obj = mDatabase;
        if (obj == null)
        {
            break MISSING_BLOCK_LABEL_192;
        }
        Exception exception1;
        String s1;
        File file;
        try
        {
            mDatabase.close();
        }
        catch (Exception exception) { }
        mDatabase.unlock();
        mDatabase = s;
        obj = s;
          goto _L11
_L4:
        obj = sqlitedatabase;
        s1 = mContext.getDatabasePath(mName).getPath();
        obj = sqlitedatabase;
        file = new File(s1);
        obj = sqlitedatabase;
        if (file.exists())
        {
            break MISSING_BLOCK_LABEL_254;
        }
        obj = sqlitedatabase;
        file.getParentFile().mkdirs();
        obj = sqlitedatabase;
        s = SQLiteDatabase.openOrCreateDatabase(s1, s, mFactory);
          goto _L12
_L8:
        onUpgrade(s, i, mNewVersion);
          goto _L13
        exception1;
        obj = s;
        s.endTransaction();
        obj = s;
        throw exception1;
        s;
        mIsInitializing = false;
        if (true) goto _L15; else goto _L14
_L14:
        exception1 = mDatabase;
        if (exception1 == null)
        {
            break MISSING_BLOCK_LABEL_328;
        }
        try
        {
            mDatabase.close();
        }
        catch (Exception exception2) { }
        mDatabase.unlock();
        mDatabase = ((SQLiteDatabase) (obj));
_L18:
        throw s;
_L10:
        if (mDatabase != null)
        {
            mDatabase.unlock();
        }
        obj = s;
        if (s == null) goto _L11; else goto _L16
_L16:
        s.close();
        obj = s;
          goto _L11
_L15:
        if (mDatabase != null)
        {
            mDatabase.unlock();
        }
        if (obj == null) goto _L18; else goto _L17
_L17:
        ((SQLiteDatabase) (obj)).close();
          goto _L18
    }

    public abstract void onCreate(SQLiteDatabase sqlitedatabase);

    public void onOpen(SQLiteDatabase sqlitedatabase)
    {
    }

    public abstract void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j);

}
