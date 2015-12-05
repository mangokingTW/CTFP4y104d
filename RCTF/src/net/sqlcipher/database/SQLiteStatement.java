// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.os.SystemClock;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteProgram, SQLiteDatabase

public class SQLiteStatement extends SQLiteProgram
{

    SQLiteStatement(SQLiteDatabase sqlitedatabase, String s)
    {
        super(sqlitedatabase, s);
    }

    private final native long native_1x1_long();

    private final native String native_1x1_string();

    private final native void native_execute();

    public void execute()
    {
        long l;
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        l = SystemClock.uptimeMillis();
        mDatabase.lock();
        acquireReference();
        native_execute();
        mDatabase.logTimeStat(mSql, l);
        releaseReference();
        mDatabase.unlock();
        return;
        Exception exception;
        exception;
        releaseReference();
        mDatabase.unlock();
        throw exception;
    }

    public long executeInsert()
    {
        long l;
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        l = SystemClock.uptimeMillis();
        mDatabase.lock();
        acquireReference();
        native_execute();
        mDatabase.logTimeStat(mSql, l);
        if (mDatabase.lastChangeCount() <= 0) goto _L2; else goto _L1
_L1:
        l = mDatabase.lastInsertRow();
_L4:
        releaseReference();
        mDatabase.unlock();
        return l;
_L2:
        l = -1L;
        if (true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        releaseReference();
        mDatabase.unlock();
        throw exception;
    }

    public long executeUpdateDelete()
    {
        long l;
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        l = SystemClock.uptimeMillis();
        mDatabase.lock();
        acquireReference();
        int i;
        native_execute();
        mDatabase.logTimeStat(mSql, l);
        i = mDatabase.lastChangeCount();
        long l1 = i;
        releaseReference();
        mDatabase.unlock();
        return l1;
        Exception exception;
        exception;
        releaseReference();
        mDatabase.unlock();
        throw exception;
    }

    public long simpleQueryForLong()
    {
        long l;
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        l = SystemClock.uptimeMillis();
        mDatabase.lock();
        acquireReference();
        long l1;
        l1 = native_1x1_long();
        mDatabase.logTimeStat(mSql, l);
        releaseReference();
        mDatabase.unlock();
        return l1;
        Exception exception;
        exception;
        releaseReference();
        mDatabase.unlock();
        throw exception;
    }

    public String simpleQueryForString()
    {
        long l;
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        l = SystemClock.uptimeMillis();
        mDatabase.lock();
        acquireReference();
        String s;
        s = native_1x1_string();
        mDatabase.logTimeStat(mSql, l);
        releaseReference();
        mDatabase.unlock();
        return s;
        Exception exception;
        exception;
        releaseReference();
        mDatabase.unlock();
        throw exception;
    }
}
