// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Debug;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;
import net.sqlcipher.CrossProcessCursorWrapper;
import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.SQLException;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteClosable, DatabaseObjectNotClosedException, SQLiteDatabaseHook, SQLiteDebug, 
//            SQLiteCompiledSql, SQLiteStatement, SQLiteDatabaseCorruptException, SQLiteTransactionListener, 
//            SQLiteQueryBuilder, SQLiteCursor, SQLiteDirectCursorDriver, SQLiteCursorDriver, 
//            SQLiteQuery

public class SQLiteDatabase extends SQLiteClosable
{
    static class ActiveDatabases
    {

        private static final ActiveDatabases activeDatabases = new ActiveDatabases();
        private HashSet mActiveDatabases;

        static ActiveDatabases getInstance()
        {
            return activeDatabases;
        }



        private ActiveDatabases()
        {
            mActiveDatabases = new HashSet();
        }
    }

    public static interface CursorFactory
    {

        public abstract Cursor newCursor(SQLiteDatabase sqlitedatabase, SQLiteCursorDriver sqlitecursordriver, String s, SQLiteQuery sqlitequery);
    }

    private static class SyncUpdateInfo
    {

        String deletedTable;
        String foreignKey;
        String masterTable;

        SyncUpdateInfo(String s, String s1, String s2)
        {
            masterTable = s;
            deletedTable = s1;
            foreignKey = s2;
        }
    }


    private static final String COMMIT_SQL = "COMMIT;";
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    private static final String CONFLICT_VALUES[] = {
        "", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "
    };
    public static final int CREATE_IF_NECESSARY = 0x10000000;
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    private static final int EVENT_DB_CORRUPT = 0x124fc;
    private static final int EVENT_DB_OPERATION = 52000;
    static final String GET_LOCK_LOG_PREFIX = "GETLOCK:";
    private static final int LOCK_ACQUIRED_WARNING_THREAD_TIME_IN_MS = 100;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS = 300;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS_ALWAYS_PRINT = 2000;
    private static final int LOCK_WARNING_WINDOW_IN_MS = 20000;
    private static final String LOG_SLOW_QUERIES_PROPERTY = "db.log.slow_query_threshold";
    public static final int MAX_SQL_CACHE_SIZE = 250;
    private static final int MAX_WARNINGS_ON_CACHESIZE_CONDITION = 1;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    private static final int QUERY_LOG_SQL_LENGTH = 64;
    private static final int SLEEP_AFTER_YIELD_QUANTUM = 1000;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "Database";
    private static int sQueryLogTimeInMillis = 0;
    private int mCacheFullWarnings;
    Map mCompiledQueries;
    private CursorFactory mFactory;
    private int mFlags;
    private boolean mInnerTransactionIsSuccessful;
    private long mLastLockMessageTime;
    private String mLastSqlStatement;
    private final ReentrantLock mLock;
    private long mLockAcquiredThreadTime;
    private long mLockAcquiredWallTime;
    private boolean mLockingEnabled;
    private int mMaxSqlCacheSize;
    int mNativeHandle;
    private int mNumCacheHits;
    private int mNumCacheMisses;
    private String mPath;
    private String mPathForLogs;
    private WeakHashMap mPrograms;
    private final Random mRandom;
    private final int mSlowQueryThreshold;
    private Throwable mStackTrace;
    private final Map mSyncUpdateInfo;
    int mTempTableSequence;
    private String mTimeClosed;
    private String mTimeOpened;
    private boolean mTransactionIsSuccessful;
    private SQLiteTransactionListener mTransactionListener;

    public SQLiteDatabase(String s, String s1, CursorFactory cursorfactory, int i)
    {
        this(s, s1, cursorfactory, i, null);
    }

    public SQLiteDatabase(String s, String s1, CursorFactory cursorfactory, int i, SQLiteDatabaseHook sqlitedatabasehook)
    {
        mLock = new ReentrantLock(true);
        mLockAcquiredWallTime = 0L;
        mLockAcquiredThreadTime = 0L;
        mLastLockMessageTime = 0L;
        mRandom = new Random();
        mLastSqlStatement = null;
        mNativeHandle = 0;
        mTempTableSequence = 0;
        mPathForLogs = null;
        mCompiledQueries = Maps.newHashMap();
        mMaxSqlCacheSize = 250;
        mTimeOpened = null;
        mTimeClosed = null;
        mStackTrace = null;
        mLockingEnabled = true;
        mSyncUpdateInfo = new HashMap();
        if (s == null)
        {
            throw new IllegalArgumentException("path should not be null");
        }
        mFlags = i;
        mPath = s;
        mSlowQueryThreshold = -1;
        mStackTrace = (new DatabaseObjectNotClosedException()).fillInStackTrace();
        mFactory = cursorfactory;
        dbopen(mPath, mFlags);
        if (sqlitedatabasehook != null)
        {
            sqlitedatabasehook.preKey(this);
        }
        native_key(s1.toCharArray());
        if (sqlitedatabasehook != null)
        {
            sqlitedatabasehook.postKey(this);
        }
        if (SQLiteDebug.DEBUG_SQL_CACHE)
        {
            mTimeOpened = getTime();
        }
        mPrograms = new WeakHashMap();
        try
        {
            setLocale(Locale.getDefault());
            return;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            Log.e("Database", "Failed to setLocale() when constructing, closing the database", s);
        }
        dbclose();
        if (SQLiteDebug.DEBUG_SQL_CACHE)
        {
            mTimeClosed = getTime();
        }
        throw s;
    }

    private void checkLockHoldTime()
    {
        long l;
        long l1;
        l = SystemClock.elapsedRealtime();
        l1 = l - mLockAcquiredWallTime;
        break MISSING_BLOCK_LABEL_12;
        if ((l1 >= 2000L || Log.isLoggable("Database", 2) || l - mLastLockMessageTime >= 20000L) && l1 > 300L)
        {
            int i = (int)((Debug.threadCpuTimeNanos() - mLockAcquiredThreadTime) / 0xf4240L);
            if (i > 100 || l1 > 2000L)
            {
                mLastLockMessageTime = l;
                String s = (new StringBuilder()).append("lock held on ").append(mPath).append(" for ").append(l1).append("ms. Thread time was ").append(i).append("ms").toString();
                if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING_STACK_TRACE)
                {
                    Log.d("Database", s, new Exception());
                    return;
                } else
                {
                    Log.d("Database", s);
                    return;
                }
            }
        }
        return;
    }

    private void closeClosable()
    {
        deallocCachedSqlStatements();
        Iterator iterator = mPrograms.entrySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            SQLiteClosable sqliteclosable = (SQLiteClosable)((java.util.Map.Entry)iterator.next()).getKey();
            if (sqliteclosable != null)
            {
                sqliteclosable.onAllReferencesReleasedFromContainer();
            }
        } while (true);
    }

    public static SQLiteDatabase create(CursorFactory cursorfactory, String s)
    {
        return openDatabase(":memory:", s, cursorfactory, 0x10000000);
    }

    private native void dbclose();

    private native void dbopen(String s, int i);

    private void deallocCachedSqlStatements()
    {
        Map map = mCompiledQueries;
        map;
        JVM INSTR monitorenter ;
        for (Iterator iterator = mCompiledQueries.values().iterator(); iterator.hasNext(); ((SQLiteCompiledSql)iterator.next()).releaseSqlStatement()) { }
        break MISSING_BLOCK_LABEL_51;
        Exception exception;
        exception;
        map;
        JVM INSTR monitorexit ;
        throw exception;
        mCompiledQueries.clear();
        map;
        JVM INSTR monitorexit ;
    }

    private native void enableSqlProfiling(String s);

    private native void enableSqlTracing(String s);

    public static String findEditTable(String s)
    {
        int i;
        int j;
        if (TextUtils.isEmpty(s))
        {
            break MISSING_BLOCK_LABEL_67;
        }
        i = s.indexOf(' ');
        j = s.indexOf(',');
        if (i <= 0 || i >= j && j >= 0) goto _L2; else goto _L1
_L1:
        String s1 = s.substring(0, i);
_L4:
        return s1;
_L2:
        s1 = s;
        if (j <= 0) goto _L4; else goto _L3
_L3:
        if (j < i)
        {
            break; /* Loop/switch isn't completed */
        }
        s1 = s;
        if (i >= 0) goto _L4; else goto _L5
_L5:
        return s.substring(0, j);
        throw new IllegalStateException("Invalid tables");
    }

    private static ArrayList getAttachedDbs(SQLiteDatabase sqlitedatabase)
    {
        if (!sqlitedatabase.isOpen())
        {
            return null;
        }
        ArrayList arraylist = new ArrayList();
        for (sqlitedatabase = sqlitedatabase.rawQuery("pragma database_list;", null); sqlitedatabase.moveToNext(); arraylist.add(new Pair(sqlitedatabase.getString(1), sqlitedatabase.getString(2)))) { }
        sqlitedatabase.close();
        return arraylist;
    }

    static ArrayList getDbStats()
    {
        ArrayList arraylist = new ArrayList();
        for (Iterator iterator = ActiveDatabases.getInstance().mActiveDatabases.iterator(); iterator.hasNext();)
        {
            SQLiteDatabase sqlitedatabase = (SQLiteDatabase)((WeakReference)iterator.next()).get();
            if (sqlitedatabase != null && sqlitedatabase.isOpen())
            {
                int j = sqlitedatabase.native_getDbLookaside();
                String s = sqlitedatabase.getPath();
                int i = s.lastIndexOf("/");
                String s1;
                ArrayList arraylist1;
                if (i != -1)
                {
                    i++;
                } else
                {
                    i = 0;
                }
                s1 = s.substring(i);
                arraylist1 = getAttachedDbs(sqlitedatabase);
                if (arraylist1 != null)
                {
                    i = 0;
                    while (i < arraylist1.size()) 
                    {
                        Pair pair = (Pair)arraylist1.get(i);
                        long l = getPragmaVal(sqlitedatabase, (new StringBuilder()).append((String)pair.first).append(".page_count;").toString());
                        Object obj;
                        if (i == 0)
                        {
                            obj = s1;
                        } else
                        {
                            boolean flag = false;
                            String s2 = (new StringBuilder()).append("  (attached) ").append((String)pair.first).toString();
                            obj = s2;
                            j = ((flag) ? 1 : 0);
                            if (((String)pair.second).trim().length() > 0)
                            {
                                j = ((String)pair.second).lastIndexOf("/");
                                obj = (new StringBuilder()).append(s2).append(" : ");
                                s2 = (String)pair.second;
                                if (j != -1)
                                {
                                    j++;
                                } else
                                {
                                    j = 0;
                                }
                                obj = ((StringBuilder) (obj)).append(s2.substring(j)).toString();
                                j = ((flag) ? 1 : 0);
                            }
                        }
                        if (l > 0L)
                        {
                            arraylist.add(new SQLiteDebug.DbStats(((String) (obj)), l, sqlitedatabase.getPageSize(), j));
                        }
                        i++;
                    }
                }
            }
        }

        return arraylist;
    }

    private String getPathForLogs()
    {
        if (mPathForLogs != null)
        {
            return mPathForLogs;
        }
        if (mPath == null)
        {
            return null;
        }
        if (mPath.indexOf('@') == -1)
        {
            mPathForLogs = mPath;
        } else
        {
            mPathForLogs = EMAIL_IN_DB_PATTERN.matcher(mPath).replaceAll("XX@YY");
        }
        return mPathForLogs;
    }

    private static long getPragmaVal(SQLiteDatabase sqlitedatabase, String s)
    {
        if (sqlitedatabase.isOpen()) goto _L2; else goto _L1
_L1:
        long l = 0L;
_L4:
        return l;
_L2:
        Object obj = null;
        sqlitedatabase = new SQLiteStatement(sqlitedatabase, (new StringBuilder()).append("PRAGMA ").append(s).toString());
        long l1 = sqlitedatabase.simpleQueryForLong();
        l = l1;
        if (sqlitedatabase == null) goto _L4; else goto _L3
_L3:
        sqlitedatabase.close();
        return l1;
        s;
        sqlitedatabase = obj;
_L6:
        if (sqlitedatabase != null)
        {
            sqlitedatabase.close();
        }
        throw s;
        s;
        if (true) goto _L6; else goto _L5
_L5:
    }

    private String getTime()
    {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ")).format(Long.valueOf(System.currentTimeMillis()));
    }

    private static void loadICUData(Context context, File file)
    {
        byte abyte0[];
        file = new File(file, "icu");
        if (!file.exists())
        {
            file.mkdirs();
        }
        file = new File(file, "icudt46l.dat");
        if (file.exists())
        {
            break MISSING_BLOCK_LABEL_112;
        }
        context = new ZipInputStream(context.getAssets().open("icudt46l.zip"));
        context.getNextEntry();
        file = new FileOutputStream(file);
        abyte0 = new byte[1024];
_L1:
        int i = context.read(abyte0);
        if (i <= 0)
        {
            break MISSING_BLOCK_LABEL_113;
        }
        file.write(abyte0, 0, i);
          goto _L1
        context;
        Log.e("Database", "Error copying icu data file", context);
        return;
        context.close();
        file.flush();
        file.close();
        return;
    }

    public static void loadLibs(Context context)
    {
        loadLibs(context, context.getFilesDir());
    }

    public static void loadLibs(Context context, File file)
    {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("sqlcipher_android");
        System.loadLibrary("database_sqlcipher");
        boolean flag = (new File("/system/usr/icu/icudt46l.dat")).exists();
        String s;
        if (flag)
        {
            s = "/system/usr";
        } else
        {
            s = file.getAbsolutePath();
        }
        setICURoot(s);
        if (!flag)
        {
            loadICUData(context, file);
        }
    }

    private void lockForced()
    {
        mLock.lock();
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && mLock.getHoldCount() == 1)
        {
            mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
        }
    }

    private void markTableSyncable(String s, String s1, String s2, String s3)
    {
        lock();
        native_execSQL((new StringBuilder()).append("SELECT _sync_dirty FROM ").append(s2).append(" LIMIT 0").toString());
        native_execSQL((new StringBuilder()).append("SELECT ").append(s1).append(" FROM ").append(s).append(" LIMIT 0").toString());
        unlock();
        s2 = new SyncUpdateInfo(s2, s3, s1);
        synchronized (mSyncUpdateInfo)
        {
            mSyncUpdateInfo.put(s, s2);
        }
        return;
        s;
        unlock();
        throw s;
        s;
        s1;
        JVM INSTR monitorexit ;
        throw s;
    }

    private native int native_getDbLookaside();

    private native void native_key(String s)
        throws SQLException;

    private native void native_key(char ac[])
        throws SQLException;

    private native void native_rawExecSQL(String s);

    private native int native_status(int i, boolean flag);

    public static SQLiteDatabase openDatabase(String s, String s1, CursorFactory cursorfactory, int i)
    {
        return openDatabase(s, s1, cursorfactory, 0x10000000, null);
    }

    public static SQLiteDatabase openDatabase(String s, String s1, CursorFactory cursorfactory, int i, SQLiteDatabaseHook sqlitedatabasehook)
    {
        SQLiteDatabase sqlitedatabase = new SQLiteDatabase(s, s1, cursorfactory, i, sqlitedatabasehook);
        if (SQLiteDebug.DEBUG_SQL_STATEMENTS)
        {
            sqlitedatabase.enableSqlTracing(s);
        }
        Object obj = sqlitedatabase;
        if (!SQLiteDebug.DEBUG_SQL_TIME)
        {
            break MISSING_BLOCK_LABEL_47;
        }
        sqlitedatabase.enableSqlProfiling(s);
        obj = sqlitedatabase;
_L2:
        ActiveDatabases.getInstance().mActiveDatabases.add(new WeakReference(obj));
        return ((SQLiteDatabase) (obj));
        SQLiteDatabaseCorruptException sqlitedatabasecorruptexception;
        sqlitedatabasecorruptexception;
_L3:
        Log.e("Database", (new StringBuilder()).append("Deleting and re-creating corrupt database ").append(s).toString(), sqlitedatabasecorruptexception);
        if (!s.equalsIgnoreCase(":memory"))
        {
            (new File(s)).delete();
        }
        sqlitedatabasecorruptexception = new SQLiteDatabase(s, s1, cursorfactory, i, sqlitedatabasehook);
        if (true) goto _L2; else goto _L1
_L1:
        sqlitedatabasecorruptexception;
          goto _L3
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String s, CursorFactory cursorfactory)
    {
        return openOrCreateDatabase(file.getPath(), s, cursorfactory, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String s, CursorFactory cursorfactory, SQLiteDatabaseHook sqlitedatabasehook)
    {
        return openOrCreateDatabase(file.getPath(), s, cursorfactory, sqlitedatabasehook);
    }

    public static SQLiteDatabase openOrCreateDatabase(String s, String s1, CursorFactory cursorfactory)
    {
        return openDatabase(s, s1, cursorfactory, 0x10000000, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String s, String s1, CursorFactory cursorfactory, SQLiteDatabaseHook sqlitedatabasehook)
    {
        return openDatabase(s, s1, cursorfactory, 0x10000000, sqlitedatabasehook);
    }

    public static native int releaseMemory();

    public static native void setICURoot(String s);

    private void unlockForced()
    {
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && mLock.getHoldCount() == 1)
        {
            checkLockHoldTime();
        }
        mLock.unlock();
    }

    public static void upgradeDatabaseFormatFromVersion1To2(File file, String s)
        throws Exception
    {
        SQLiteDatabaseHook sqlitedatabasehook = new SQLiteDatabaseHook() {

            public void postKey(SQLiteDatabase sqlitedatabase)
            {
                sqlitedatabase.execSQL("PRAGMA cipher_default_use_hmac = on");
            }

            public void preKey(SQLiteDatabase sqlitedatabase)
            {
                sqlitedatabase.execSQL("PRAGMA cipher_default_use_hmac = off");
            }

        };
        File file1;
        try
        {
            file1 = File.createTempFile("temp", "db", file.getParentFile());
            s = openOrCreateDatabase(file, s, null, sqlitedatabasehook);
            s.rawExecSQL(String.format("ATTACH DATABASE '%s' as newdb", new Object[] {
                file1.getAbsolutePath()
            }));
            s.rawExecSQL("SELECT sqlcipher_export('newdb')");
            s.rawExecSQL("DETACH DATABASE newdb");
            s.close();
        }
        // Misplaced declaration of an exception variable
        catch (File file)
        {
            throw file;
        }
        if (true)
        {
            file.delete();
            file1.renameTo(file);
        }
    }

    private boolean yieldIfContendedHelper(boolean flag, long l)
    {
        if (mLock.getQueueLength() == 0)
        {
            mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            return false;
        }
        setTransactionSuccessful();
        SQLiteTransactionListener sqlitetransactionlistener = mTransactionListener;
        endTransaction();
        if (flag && isDbLockedByCurrentThread())
        {
            throw new IllegalStateException("Db locked more than once. yielfIfContended cannot yield");
        }
        if (l > 0L)
        {
            do
            {
                if (l <= 0L)
                {
                    break;
                }
                long l1;
                if (l < 1000L)
                {
                    l1 = l;
                } else
                {
                    l1 = 1000L;
                }
                try
                {
                    Thread.sleep(l1);
                }
                catch (InterruptedException interruptedexception)
                {
                    Thread.interrupted();
                }
                l -= 1000L;
            } while (mLock.getQueueLength() != 0);
        }
        beginTransactionWithListener(sqlitetransactionlistener);
        return true;
    }

    void addSQLiteClosable(SQLiteClosable sqliteclosable)
    {
        lock();
        mPrograms.put(sqliteclosable, null);
        unlock();
        return;
        sqliteclosable;
        unlock();
        throw sqliteclosable;
    }

    void addToCompiledQueries(String s, SQLiteCompiledSql sqlitecompiledsql)
    {
        if (mMaxSqlCacheSize == 0)
        {
            if (SQLiteDebug.DEBUG_SQL_CACHE)
            {
                Log.v("Database", (new StringBuilder()).append("|NOT adding_sql_to_cache|").append(getPath()).append("|").append(s).toString());
            }
            return;
        }
        synchronized (mCompiledQueries)
        {
            if ((SQLiteCompiledSql)mCompiledQueries.get(s) == null)
            {
                break MISSING_BLOCK_LABEL_84;
            }
        }
        return;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
        int i;
        if (mCompiledQueries.size() != mMaxSqlCacheSize)
        {
            break MISSING_BLOCK_LABEL_174;
        }
        i = mCacheFullWarnings + 1;
        mCacheFullWarnings = i;
        if (i != 1)
        {
            break MISSING_BLOCK_LABEL_171;
        }
        Log.w("Database", (new StringBuilder()).append("Reached MAX size for compiled-sql statement cache for database ").append(getPath()).append("; i.e., NO space for this sql statement in cache: ").append(s).append(". Please change your sql statements to use '?' for ").append("bindargs, instead of using actual values").toString());
_L2:
        map;
        JVM INSTR monitorexit ;
        return;
        mCompiledQueries.put(s, sqlitecompiledsql);
        if (SQLiteDebug.DEBUG_SQL_CACHE)
        {
            Log.v("Database", (new StringBuilder()).append("|adding_sql_to_cache|").append(getPath()).append("|").append(mCompiledQueries.size()).append("|").append(s).toString());
        }
        if (true) goto _L2; else goto _L1
_L1:
    }

    public void beginTransaction()
    {
        beginTransactionWithListener(null);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener sqlitetransactionlistener)
    {
        lockForced();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        if (mLock.getHoldCount() > 1)
        {
            if (mInnerTransactionIsSuccessful)
            {
                sqlitetransactionlistener = new IllegalStateException("Cannot call beginTransaction between calling setTransactionSuccessful and endTransaction");
                Log.e("Database", "beginTransaction() failed", sqlitetransactionlistener);
                throw sqlitetransactionlistener;
            }
            break MISSING_BLOCK_LABEL_74;
        }
          goto _L1
        sqlitetransactionlistener;
        if (true)
        {
            unlockForced();
        }
        throw sqlitetransactionlistener;
        if (false)
        {
            unlockForced();
        }
_L3:
        return;
_L1:
        execSQL("BEGIN EXCLUSIVE;");
        mTransactionListener = sqlitetransactionlistener;
        mTransactionIsSuccessful = true;
        mInnerTransactionIsSuccessful = false;
        if (sqlitetransactionlistener == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        sqlitetransactionlistener.onBegin();
        if (true) goto _L3; else goto _L2
_L2:
        unlockForced();
        return;
        sqlitetransactionlistener;
        execSQL("ROLLBACK;");
        throw sqlitetransactionlistener;
    }

    public void close()
    {
        if (!isOpen())
        {
            return;
        }
        lock();
        closeClosable();
        onAllReferencesReleased();
        unlock();
        return;
        Exception exception;
        exception;
        unlock();
        throw exception;
    }

    public SQLiteStatement compileStatement(String s)
        throws SQLException
    {
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        s = new SQLiteStatement(this, s);
        unlock();
        return s;
        s;
        unlock();
        throw s;
    }

    public int delete(String s, String s1, String as[])
    {
        String s2;
        String s3;
        Object obj;
        Object obj1;
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        obj1 = null;
        obj = null;
        s3 = obj;
        s2 = obj1;
        StringBuilder stringbuilder = (new StringBuilder()).append("DELETE FROM ").append(s);
        s3 = obj;
        s2 = obj1;
        if (TextUtils.isEmpty(s1)) goto _L2; else goto _L1
_L1:
        s3 = obj;
        s2 = obj1;
        s = (new StringBuilder()).append(" WHERE ").append(s1).toString();
_L7:
        s3 = obj;
        s2 = obj1;
        s = compileStatement(stringbuilder.append(s).toString());
        if (as == null) goto _L4; else goto _L3
_L3:
        s3 = s;
        s2 = s;
        int j = as.length;
        int i = 0;
_L5:
        if (i >= j)
        {
            break; /* Loop/switch isn't completed */
        }
        s3 = s;
        s2 = s;
        DatabaseUtils.bindObjectToProgram(s, i + 1, as[i]);
        i++;
        if (true) goto _L5; else goto _L4
_L4:
        s3 = s;
        s2 = s;
        s.execute();
        s3 = s;
        s2 = s;
        i = lastChangeCount();
        if (s != null)
        {
            s.close();
        }
        unlock();
        return i;
        s;
        s2 = s3;
        onCorruption();
        s2 = s3;
        throw s;
        s;
        if (s2 != null)
        {
            s2.close();
        }
        unlock();
        throw s;
_L2:
        s = "";
        if (true) goto _L7; else goto _L6
_L6:
    }

    public void endTransaction()
    {
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        if (!mLock.isHeldByCurrentThread())
        {
            throw new IllegalStateException("no transaction pending");
        }
        if (!mInnerTransactionIsSuccessful)
        {
            break MISSING_BLOCK_LABEL_76;
        }
        mInnerTransactionIsSuccessful = false;
_L1:
        int i = mLock.getHoldCount();
        if (i != 1)
        {
            mTransactionListener = null;
            unlockForced();
            return;
        }
        break MISSING_BLOCK_LABEL_96;
        Exception exception;
        mTransactionIsSuccessful = false;
          goto _L1
        Object obj = null;
        SQLiteTransactionListener sqlitetransactionlistener = mTransactionListener;
        RuntimeException runtimeexception = obj;
        if (sqlitetransactionlistener == null) goto _L3; else goto _L2
_L2:
        if (!mTransactionIsSuccessful) goto _L5; else goto _L4
_L4:
        mTransactionListener.onCommit();
        runtimeexception = obj;
_L3:
        if (!mTransactionIsSuccessful) goto _L7; else goto _L6
_L6:
        execSQL("COMMIT;");
_L9:
        mTransactionListener = null;
        unlockForced();
        return;
_L5:
        mTransactionListener.onRollback();
        runtimeexception = obj;
          goto _L3
        runtimeexception;
        mTransactionIsSuccessful = false;
          goto _L3
_L7:
        execSQL("ROLLBACK;");
        if (runtimeexception == null) goto _L9; else goto _L8
_L8:
        try
        {
            throw runtimeexception;
        }
        catch (SQLException sqlexception) { }
        finally
        {
            mTransactionListener = null;
            unlockForced();
            throw exception;
        }
        Log.d("Database", "exception during rollback, maybe the DB previously performed an auto-rollback");
          goto _L9
    }

    public void execSQL(String s)
        throws SQLException
    {
        long l;
        l = SystemClock.uptimeMillis();
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        logTimeStat(mLastSqlStatement, l, "GETLOCK:");
        native_execSQL(s);
        unlock();
        if (s == "COMMIT;")
        {
            logTimeStat(mLastSqlStatement, l, "COMMIT;");
            return;
        } else
        {
            logTimeStat(s, l, null);
            return;
        }
        s;
        onCorruption();
        throw s;
        s;
        unlock();
        throw s;
    }

    public void execSQL(String s, Object aobj[])
        throws SQLException
    {
        SQLiteStatement sqlitestatement;
        SQLiteStatement sqlitestatement1;
        long l;
        if (aobj == null)
        {
            throw new IllegalArgumentException("Empty bindArgs");
        }
        l = SystemClock.uptimeMillis();
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        sqlitestatement1 = null;
        sqlitestatement = null;
        SQLiteStatement sqlitestatement2 = compileStatement(s);
        if (aobj == null) goto _L2; else goto _L1
_L1:
        sqlitestatement = sqlitestatement2;
        sqlitestatement1 = sqlitestatement2;
        int j = aobj.length;
        int i = 0;
_L3:
        if (i >= j)
        {
            break; /* Loop/switch isn't completed */
        }
        sqlitestatement = sqlitestatement2;
        sqlitestatement1 = sqlitestatement2;
        DatabaseUtils.bindObjectToProgram(sqlitestatement2, i + 1, aobj[i]);
        i++;
        if (true) goto _L3; else goto _L2
_L2:
        sqlitestatement = sqlitestatement2;
        sqlitestatement1 = sqlitestatement2;
        sqlitestatement2.execute();
        if (sqlitestatement2 != null)
        {
            sqlitestatement2.close();
        }
        unlock();
        logTimeStat(s, l);
        return;
        s;
        sqlitestatement1 = sqlitestatement;
        onCorruption();
        sqlitestatement1 = sqlitestatement;
        throw s;
        s;
        if (sqlitestatement1 != null)
        {
            sqlitestatement1.close();
        }
        unlock();
        throw s;
    }

    protected void finalize()
    {
        if (isOpen())
        {
            Log.e("Database", (new StringBuilder()).append("close() was never explicitly called on database '").append(mPath).append("' ").toString(), mStackTrace);
            closeClosable();
            onAllReferencesReleased();
        }
    }

    SQLiteCompiledSql getCompiledStatementForSql(String s)
    {
label0:
        {
            synchronized (mCompiledQueries)
            {
                if (mMaxSqlCacheSize != 0)
                {
                    break label0;
                }
                if (SQLiteDebug.DEBUG_SQL_CACHE)
                {
                    Log.v("Database", (new StringBuilder()).append("|cache NOT found|").append(getPath()).toString());
                }
            }
            return null;
        }
        SQLiteCompiledSql sqlitecompiledsql = (SQLiteCompiledSql)mCompiledQueries.get(s);
        boolean flag;
        if (sqlitecompiledsql != null)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        map;
        JVM INSTR monitorexit ;
        if (flag)
        {
            mNumCacheHits = mNumCacheHits + 1;
        } else
        {
            mNumCacheMisses = mNumCacheMisses + 1;
        }
        if (SQLiteDebug.DEBUG_SQL_CACHE)
        {
            Log.v("Database", (new StringBuilder()).append("|cache_stats|").append(getPath()).append("|").append(mCompiledQueries.size()).append("|").append(mNumCacheHits).append("|").append(mNumCacheMisses).append("|").append(flag).append("|").append(mTimeOpened).append("|").append(mTimeClosed).append("|").append(s).toString());
        }
        return sqlitecompiledsql;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
    }

    public int getMaxSqlCacheSize()
    {
        this;
        JVM INSTR monitorenter ;
        int i = mMaxSqlCacheSize;
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public long getMaximumSize()
    {
        SQLiteStatement sqlitestatement;
        sqlitestatement = null;
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement sqlitestatement1 = new SQLiteStatement(this, "PRAGMA max_page_count;");
        long l;
        long l1;
        l = sqlitestatement1.simpleQueryForLong();
        l1 = getPageSize();
        if (sqlitestatement1 != null)
        {
            sqlitestatement1.close();
        }
        unlock();
        return l1 * l;
        Exception exception;
        exception;
_L2:
        if (sqlitestatement != null)
        {
            sqlitestatement.close();
        }
        unlock();
        throw exception;
        exception;
        sqlitestatement = sqlitestatement1;
        if (true) goto _L2; else goto _L1
_L1:
    }

    public long getPageSize()
    {
        SQLiteStatement sqlitestatement;
        sqlitestatement = null;
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement sqlitestatement1 = new SQLiteStatement(this, "PRAGMA page_size;");
        long l = sqlitestatement1.simpleQueryForLong();
        if (sqlitestatement1 != null)
        {
            sqlitestatement1.close();
        }
        unlock();
        return l;
        Exception exception;
        exception;
_L2:
        if (sqlitestatement != null)
        {
            sqlitestatement.close();
        }
        unlock();
        throw exception;
        exception;
        sqlitestatement = sqlitestatement1;
        if (true) goto _L2; else goto _L1
_L1:
    }

    public final String getPath()
    {
        return mPath;
    }

    public Map getSyncedTables()
    {
        Map map = mSyncUpdateInfo;
        map;
        JVM INSTR monitorenter ;
        Object obj;
        obj = new HashMap();
        Iterator iterator = mSyncUpdateInfo.keySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            String s = (String)iterator.next();
            SyncUpdateInfo syncupdateinfo = (SyncUpdateInfo)mSyncUpdateInfo.get(s);
            if (syncupdateinfo.deletedTable != null)
            {
                ((HashMap) (obj)).put(s, syncupdateinfo.deletedTable);
            }
        } while (true);
        break MISSING_BLOCK_LABEL_94;
        obj;
        map;
        JVM INSTR monitorexit ;
        throw obj;
        map;
        JVM INSTR monitorexit ;
        return ((Map) (obj));
    }

    public int getVersion()
    {
        SQLiteStatement sqlitestatement;
        sqlitestatement = null;
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement sqlitestatement1 = new SQLiteStatement(this, "PRAGMA user_version;");
        long l = sqlitestatement1.simpleQueryForLong();
        int i = (int)l;
        if (sqlitestatement1 != null)
        {
            sqlitestatement1.close();
        }
        unlock();
        return i;
        Exception exception;
        exception;
_L2:
        if (sqlitestatement != null)
        {
            sqlitestatement.close();
        }
        unlock();
        throw exception;
        exception;
        sqlitestatement = sqlitestatement1;
        if (true) goto _L2; else goto _L1
_L1:
    }

    public boolean inTransaction()
    {
        return mLock.getHoldCount() > 0;
    }

    public long insert(String s, String s1, ContentValues contentvalues)
    {
        long l;
        try
        {
            l = insertWithOnConflict(s, s1, contentvalues, 0);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            Log.e("Database", (new StringBuilder()).append("Error inserting ").append(contentvalues).toString(), s);
            return -1L;
        }
        return l;
    }

    public long insertOrThrow(String s, String s1, ContentValues contentvalues)
        throws SQLException
    {
        return insertWithOnConflict(s, s1, contentvalues, 0);
    }

    public long insertWithOnConflict(String s, String s1, ContentValues contentvalues, int i)
    {
        SQLiteStatement sqlitestatement;
        StringBuilder stringbuilder;
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        stringbuilder = new StringBuilder(152);
        stringbuilder.append("INSERT");
        stringbuilder.append(CONFLICT_VALUES[i]);
        stringbuilder.append(" INTO ");
        stringbuilder.append(s);
        s = new StringBuilder(40);
        Object obj = null;
        int j;
        if (contentvalues != null && contentvalues.size() > 0)
        {
            obj = contentvalues.valueSet();
            s1 = ((Set) (obj)).iterator();
            stringbuilder.append('(');
            i = 0;
            for (; s1.hasNext(); s.append('?'))
            {
                if (i != 0)
                {
                    stringbuilder.append(", ");
                    s.append(", ");
                }
                i = 1;
                stringbuilder.append((String)((java.util.Map.Entry)s1.next()).getKey());
            }

            stringbuilder.append(')');
        } else
        {
            stringbuilder.append((new StringBuilder()).append("(").append(s1).append(") ").toString());
            s.append("NULL");
        }
        stringbuilder.append(" VALUES(");
        stringbuilder.append(s);
        stringbuilder.append(");");
        lock();
        s1 = null;
        s = null;
        sqlitestatement = compileStatement(stringbuilder.toString());
        if (obj == null) goto _L2; else goto _L1
_L1:
        s = sqlitestatement;
        s1 = sqlitestatement;
        j = ((Set) (obj)).size();
        s = sqlitestatement;
        s1 = sqlitestatement;
        obj = ((Set) (obj)).iterator();
        i = 0;
_L3:
        if (i >= j)
        {
            break; /* Loop/switch isn't completed */
        }
        s = sqlitestatement;
        s1 = sqlitestatement;
        DatabaseUtils.bindObjectToProgram(sqlitestatement, i + 1, ((java.util.Map.Entry)((Iterator) (obj)).next()).getValue());
        i++;
        if (true) goto _L3; else goto _L2
_L2:
        s = sqlitestatement;
        s1 = sqlitestatement;
        sqlitestatement.execute();
        s = sqlitestatement;
        s1 = sqlitestatement;
        long l = lastInsertRow();
        if (l != -1L) goto _L5; else goto _L4
_L4:
        s = sqlitestatement;
        s1 = sqlitestatement;
        Log.e("Database", (new StringBuilder()).append("Error inserting ").append(contentvalues).append(" using ").append(stringbuilder).toString());
_L7:
        if (sqlitestatement != null)
        {
            sqlitestatement.close();
        }
        unlock();
        return l;
_L5:
        s = sqlitestatement;
        s1 = sqlitestatement;
        if (!Log.isLoggable("Database", 2)) goto _L7; else goto _L6
_L6:
        s = sqlitestatement;
        s1 = sqlitestatement;
        Log.v("Database", (new StringBuilder()).append("Inserting row ").append(l).append(" from ").append(contentvalues).append(" using ").append(stringbuilder).toString());
          goto _L7
        contentvalues;
        s1 = s;
        onCorruption();
        s1 = s;
        throw contentvalues;
        s;
        if (s1 != null)
        {
            s1.close();
        }
        unlock();
        throw s;
    }

    public boolean isDbLockedByCurrentThread()
    {
        return mLock.isHeldByCurrentThread();
    }

    public boolean isDbLockedByOtherThreads()
    {
        return !mLock.isHeldByCurrentThread() && mLock.isLocked();
    }

    public boolean isInCompiledSqlCache(String s)
    {
        boolean flag;
        synchronized (mCompiledQueries)
        {
            flag = mCompiledQueries.containsKey(s);
        }
        return flag;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
    }

    public boolean isOpen()
    {
        return mNativeHandle != 0;
    }

    public boolean isReadOnly()
    {
        return (mFlags & 1) == 1;
    }

    native int lastChangeCount();

    native long lastInsertRow();

    void lock()
    {
        if (mLockingEnabled)
        {
            mLock.lock();
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && mLock.getHoldCount() == 1)
            {
                mLockAcquiredWallTime = SystemClock.elapsedRealtime();
                mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
                return;
            }
        }
    }

    void logTimeStat(String s, long l)
    {
        logTimeStat(s, l, null);
    }

    void logTimeStat(String s, long l, String s1)
    {
        mLastSqlStatement = s;
        l = SystemClock.uptimeMillis() - l;
        if (l != 0L || s1 != "GETLOCK:")
        {
            if (sQueryLogTimeInMillis == 0)
            {
                sQueryLogTimeInMillis = 500;
            }
            String s2;
            int i;
            if (l < (long)sQueryLogTimeInMillis)
            {
                if (mRandom.nextInt(100) >= (i = (int)((100L * l) / (long)sQueryLogTimeInMillis)) + 1)
                {
                    return;
                }
            }
            s2 = s;
            if (s1 != null)
            {
                s2 = (new StringBuilder()).append(s1).append(s).toString();
            }
            if (s2.length() > 64)
            {
                s2.substring(0, 64);
            }
            if ("unknown" == null)
            {
                return;
            }
        }
    }

    public void markTableSyncable(String s, String s1)
    {
        markTableSyncable(s, "_id", s, s1);
    }

    public void markTableSyncable(String s, String s1, String s2)
    {
        markTableSyncable(s, s1, s2, null);
    }

    native void native_execSQL(String s)
        throws SQLException;

    native void native_setLocale(String s, int i);

    public boolean needUpgrade(int i)
    {
        return i > getVersion();
    }

    protected void onAllReferencesReleased()
    {
        if (isOpen())
        {
            if (SQLiteDebug.DEBUG_SQL_CACHE)
            {
                mTimeClosed = getTime();
            }
            dbclose();
        }
    }

    void onCorruption()
    {
        Log.e("Database", (new StringBuilder()).append("Removing corrupt database: ").append(mPath).toString());
        close();
        if (!mPath.equalsIgnoreCase(":memory"))
        {
            (new File(mPath)).delete();
        }
        return;
        Exception exception;
        exception;
        if (!mPath.equalsIgnoreCase(":memory"))
        {
            (new File(mPath)).delete();
        }
        throw exception;
    }

    public void purgeFromCompiledSqlCache(String s)
    {
        synchronized (mCompiledQueries)
        {
            mCompiledQueries.remove(s);
        }
        return;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
    }

    public Cursor query(String s, String as[], String s1, String as1[], String s2, String s3, String s4)
    {
        return query(false, s, as, s1, as1, s2, s3, s4, null);
    }

    public Cursor query(String s, String as[], String s1, String as1[], String s2, String s3, String s4, 
            String s5)
    {
        return query(false, s, as, s1, as1, s2, s3, s4, s5);
    }

    public Cursor query(boolean flag, String s, String as[], String s1, String as1[], String s2, String s3, 
            String s4, String s5)
    {
        return queryWithFactory(null, flag, s, as, s1, as1, s2, s3, s4, s5);
    }

    public Cursor queryWithFactory(CursorFactory cursorfactory, boolean flag, String s, String as[], String s1, String as1[], String s2, 
            String s3, String s4, String s5)
    {
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        } else
        {
            return rawQueryWithFactory(cursorfactory, SQLiteQueryBuilder.buildQueryString(flag, s, as, s1, s2, s3, s4, s5), as1, findEditTable(s));
        }
    }

    public void rawExecSQL(String s)
    {
        long l;
        l = SystemClock.uptimeMillis();
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        logTimeStat(mLastSqlStatement, l, "GETLOCK:");
        native_rawExecSQL(s);
        unlock();
        if (s == "COMMIT;")
        {
            logTimeStat(mLastSqlStatement, l, "COMMIT;");
            return;
        } else
        {
            logTimeStat(s, l, null);
            return;
        }
        s;
        onCorruption();
        throw s;
        s;
        unlock();
        throw s;
    }

    public Cursor rawQuery(String s, String as[])
    {
        return rawQueryWithFactory(null, s, as, null);
    }

    public Cursor rawQuery(String s, String as[], int i, int j)
    {
        s = (SQLiteCursor)rawQueryWithFactory(null, s, as, null);
        s.setLoadStyle(i, j);
        return s;
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorfactory, String s, String as[], String s1)
    {
        long l;
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        l = 0L;
        if (mSlowQueryThreshold != -1)
        {
            l = System.currentTimeMillis();
        }
        s = new SQLiteDirectCursorDriver(this, s, s1);
        if (cursorfactory == null) goto _L2; else goto _L1
_L1:
        s1 = s.query(cursorfactory, as);
        if (mSlowQueryThreshold != -1)
        {
            int i = -1;
            if (s1 != null)
            {
                i = s1.getCount();
            }
            l = System.currentTimeMillis() - l;
            if (l >= (long)mSlowQueryThreshold)
            {
                s = (new StringBuilder()).append("query (").append(l).append(" ms): ").append(s.toString()).append(", args are ");
                if (as != null)
                {
                    cursorfactory = TextUtils.join(",", as);
                } else
                {
                    cursorfactory = "<null>";
                }
                Log.v("Database", s.append(cursorfactory).append(", count is ").append(i).toString());
            }
        }
        return new CrossProcessCursorWrapper(s1);
_L2:
        cursorfactory = mFactory;
        if (true) goto _L1; else goto _L3
_L3:
        cursorfactory = "<null>";
_L8:
        Log.v("Database", s.append(cursorfactory).append(", count is ").append(-1).toString());
_L5:
        throw s1;
        s1;
        if (mSlowQueryThreshold == -1) goto _L5; else goto _L4
_L4:
        if (false)
        {
            throw new NullPointerException();
        }
        l = System.currentTimeMillis() - l;
        if (l < (long)mSlowQueryThreshold) goto _L5; else goto _L6
_L6:
        s = (new StringBuilder()).append("query (").append(l).append(" ms): ").append(s.toString()).append(", args are ");
        if (as == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        cursorfactory = TextUtils.join(",", as);
        if (true) goto _L8; else goto _L7
_L7:
        if (true) goto _L3; else goto _L9
_L9:
    }

    void removeSQLiteClosable(SQLiteClosable sqliteclosable)
    {
        lock();
        mPrograms.remove(sqliteclosable);
        unlock();
        return;
        sqliteclosable;
        unlock();
        throw sqliteclosable;
    }

    public long replace(String s, String s1, ContentValues contentvalues)
    {
        long l;
        try
        {
            l = insertWithOnConflict(s, s1, contentvalues, 5);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            Log.e("Database", (new StringBuilder()).append("Error inserting ").append(contentvalues).toString(), s);
            return -1L;
        }
        return l;
    }

    public long replaceOrThrow(String s, String s1, ContentValues contentvalues)
        throws SQLException
    {
        return insertWithOnConflict(s, s1, contentvalues, 5);
    }

    public void resetCompiledSqlCache()
    {
        synchronized (mCompiledQueries)
        {
            mCompiledQueries.clear();
        }
        return;
        exception;
        map;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void rowUpdated(String s, long l)
    {
        SyncUpdateInfo syncupdateinfo;
        synchronized (mSyncUpdateInfo)
        {
            syncupdateinfo = (SyncUpdateInfo)mSyncUpdateInfo.get(s);
        }
        if (syncupdateinfo != null)
        {
            execSQL((new StringBuilder()).append("UPDATE ").append(syncupdateinfo.masterTable).append(" SET _sync_dirty=1 WHERE _id=(SELECT ").append(syncupdateinfo.foreignKey).append(" FROM ").append(s).append(" WHERE _id=").append(l).append(")").toString());
        }
        return;
        s;
        map;
        JVM INSTR monitorexit ;
        throw s;
    }

    public void setLocale(Locale locale)
    {
        lock();
        native_setLocale(locale.toString(), mFlags);
        unlock();
        return;
        locale;
        unlock();
        throw locale;
    }

    public void setLockingEnabled(boolean flag)
    {
        mLockingEnabled = flag;
    }

    public void setMaxSqlCacheSize(int i)
    {
        this;
        JVM INSTR monitorenter ;
        if (i <= 250 && i >= 0)
        {
            break MISSING_BLOCK_LABEL_29;
        }
        throw new IllegalStateException("expected value between 0 and 250");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if (i < mMaxSqlCacheSize)
        {
            throw new IllegalStateException("cannot set cacheSize to a value less than the value set with previous setMaxSqlCacheSize() call.");
        }
        mMaxSqlCacheSize = i;
        this;
        JVM INSTR monitorexit ;
    }

    public long setMaximumSize(long l)
    {
        SQLiteStatement sqlitestatement;
        sqlitestatement = null;
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        long l2;
        long l3;
        l3 = getPageSize();
        l2 = l / l3;
        long l1;
        l1 = l2;
        if (l % l3 != 0L)
        {
            l1 = l2 + 1L;
        }
        SQLiteStatement sqlitestatement1 = new SQLiteStatement(this, (new StringBuilder()).append("PRAGMA max_page_count = ").append(l1).toString());
        l = sqlitestatement1.simpleQueryForLong();
        if (sqlitestatement1 != null)
        {
            sqlitestatement1.close();
        }
        unlock();
        return l * l3;
        Exception exception;
        exception;
_L2:
        if (sqlitestatement != null)
        {
            sqlitestatement.close();
        }
        unlock();
        throw exception;
        exception;
        sqlitestatement = sqlitestatement1;
        if (true) goto _L2; else goto _L1
_L1:
    }

    public void setPageSize(long l)
    {
        execSQL((new StringBuilder()).append("PRAGMA page_size = ").append(l).toString());
    }

    public void setTransactionSuccessful()
    {
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        if (!mLock.isHeldByCurrentThread())
        {
            throw new IllegalStateException("no transaction pending");
        }
        if (mInnerTransactionIsSuccessful)
        {
            throw new IllegalStateException("setTransactionSuccessful may only be called once per call to beginTransaction");
        } else
        {
            mInnerTransactionIsSuccessful = true;
            return;
        }
    }

    public void setVersion(int i)
    {
        execSQL((new StringBuilder()).append("PRAGMA user_version = ").append(i).toString());
    }

    public int status(int i, boolean flag)
    {
        return native_status(i, flag);
    }

    void unlock()
    {
        if (!mLockingEnabled)
        {
            return;
        }
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && mLock.getHoldCount() == 1)
        {
            checkLockHoldTime();
        }
        mLock.unlock();
    }

    public int update(String s, ContentValues contentvalues, String s1, String as[])
    {
        return updateWithOnConflict(s, contentvalues, s1, as, 0);
    }

    public int updateWithOnConflict(String s, ContentValues contentvalues, String s1, String as[], int i)
    {
        SQLiteStatement sqlitestatement;
        StringBuilder stringbuilder;
        Object obj;
        if (contentvalues == null || contentvalues.size() == 0)
        {
            throw new IllegalArgumentException("Empty values");
        }
        stringbuilder = new StringBuilder(120);
        stringbuilder.append("UPDATE ");
        stringbuilder.append(CONFLICT_VALUES[i]);
        stringbuilder.append(s);
        stringbuilder.append(" SET ");
        obj = contentvalues.valueSet();
        s = ((Set) (obj)).iterator();
        do
        {
            if (!s.hasNext())
            {
                break;
            }
            stringbuilder.append((String)((java.util.Map.Entry)s.next()).getKey());
            stringbuilder.append("=?");
            if (s.hasNext())
            {
                stringbuilder.append(", ");
            }
        } while (true);
        if (!TextUtils.isEmpty(s1))
        {
            stringbuilder.append(" WHERE ");
            stringbuilder.append(s1);
        }
        lock();
        if (!isOpen())
        {
            throw new IllegalStateException("database not open");
        }
        s = null;
        sqlitestatement = null;
        s1 = null;
        SQLiteStatement sqlitestatement1 = compileStatement(stringbuilder.toString());
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        int k = ((Set) (obj)).size();
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        obj = ((Set) (obj)).iterator();
        int j;
        i = 1;
        j = 0;
_L2:
        if (j >= k)
        {
            break; /* Loop/switch isn't completed */
        }
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        DatabaseUtils.bindObjectToProgram(sqlitestatement1, i, ((java.util.Map.Entry)((Iterator) (obj)).next()).getValue());
        i++;
        j++;
        if (true) goto _L2; else goto _L1
_L1:
        if (as == null) goto _L4; else goto _L3
_L3:
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        k = as.length;
        j = 0;
_L5:
        if (j >= k)
        {
            break; /* Loop/switch isn't completed */
        }
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        sqlitestatement1.bindString(i, as[j]);
        i++;
        j++;
        if (true) goto _L5; else goto _L4
_L4:
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        sqlitestatement1.execute();
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        i = lastChangeCount();
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        if (!Log.isLoggable("Database", 2))
        {
            break MISSING_BLOCK_LABEL_482;
        }
        s1 = sqlitestatement1;
        s = sqlitestatement1;
        sqlitestatement = sqlitestatement1;
        Log.v("Database", (new StringBuilder()).append("Updated ").append(i).append(" using ").append(contentvalues).append(" and ").append(stringbuilder).toString());
        if (sqlitestatement1 != null)
        {
            sqlitestatement1.close();
        }
        unlock();
        return i;
        contentvalues;
        s = s1;
        onCorruption();
        s = s1;
        throw contentvalues;
        contentvalues;
        if (s != null)
        {
            s.close();
        }
        unlock();
        throw contentvalues;
        s1;
        s = sqlitestatement;
        Log.e("Database", (new StringBuilder()).append("Error updating ").append(contentvalues).append(" using ").append(stringbuilder).toString());
        s = sqlitestatement;
        throw s1;
    }

    public boolean yieldIfContended()
    {
        return yieldIfContendedHelper(false, -1L);
    }

    public boolean yieldIfContendedSafely()
    {
        return yieldIfContendedHelper(true, -1L);
    }

    public boolean yieldIfContendedSafely(long l)
    {
        return yieldIfContendedHelper(true, l);
    }

}
