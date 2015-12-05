// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.util.Log;
import java.util.ArrayList;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase

public final class SQLiteDebug
{
    public static class DbStats
    {

        public String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(String s, long l, long l1, int i)
        {
            dbName = s;
            pageSize = l1;
            dbSize = (l * l1) / 1024L;
            lookaside = i;
        }
    }

    public static class PagerStats
    {

        public long databaseBytes;
        public ArrayList dbStats;
        public int largestMemAlloc;
        public int memoryUsed;
        public int numPagers;
        public int pageCacheOverflo;
        public long referencedBytes;
        public long totalBytes;

        public PagerStats()
        {
        }
    }


    public static final boolean DEBUG_ACTIVE_CURSOR_FINALIZATION = Log.isLoggable("SQLiteCursorClosing", 2);
    public static final boolean DEBUG_LOCK_TIME_TRACKING = Log.isLoggable("SQLiteLockTime", 2);
    public static final boolean DEBUG_LOCK_TIME_TRACKING_STACK_TRACE = Log.isLoggable("SQLiteLockStackTrace", 2);
    public static final boolean DEBUG_SQL_CACHE = Log.isLoggable("SQLiteCompiledSql", 2);
    public static final boolean DEBUG_SQL_STATEMENTS = Log.isLoggable("SQLiteStatements", 2);
    public static final boolean DEBUG_SQL_TIME = Log.isLoggable("SQLiteTime", 2);
    private static int sNumActiveCursorsFinalized = 0;

    public SQLiteDebug()
    {
    }

    public static PagerStats getDatabaseInfo()
    {
        PagerStats pagerstats = new PagerStats();
        getPagerStats(pagerstats);
        pagerstats.dbStats = SQLiteDatabase.getDbStats();
        return pagerstats;
    }

    public static native long getHeapAllocatedSize();

    public static native void getHeapDirtyPages(int ai[]);

    public static native long getHeapFreeSize();

    public static native long getHeapSize();

    public static int getNumActiveCursorsFinalized()
    {
        return sNumActiveCursorsFinalized;
    }

    public static native void getPagerStats(PagerStats pagerstats);

    static void notifyActiveCursorFinalized()
    {
        net/sqlcipher/database/SQLiteDebug;
        JVM INSTR monitorenter ;
        sNumActiveCursorsFinalized++;
        net/sqlcipher/database/SQLiteDebug;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

}
