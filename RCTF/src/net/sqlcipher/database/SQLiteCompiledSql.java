// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.util.Log;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase, DatabaseObjectNotClosedException, SQLiteDebug

class SQLiteCompiledSql
{

    private static final String TAG = "SQLiteCompiledSql";
    SQLiteDatabase mDatabase;
    private boolean mInUse;
    private String mSqlStmt;
    private Throwable mStackTrace;
    int nHandle;
    int nStatement;

    SQLiteCompiledSql(SQLiteDatabase sqlitedatabase, String s)
    {
        nHandle = 0;
        nStatement = 0;
        mSqlStmt = null;
        mStackTrace = null;
        mInUse = false;
        if (!sqlitedatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(sqlitedatabase.getPath()).append(" already closed").toString());
        } else
        {
            mDatabase = sqlitedatabase;
            mSqlStmt = s;
            mStackTrace = (new DatabaseObjectNotClosedException()).fillInStackTrace();
            nHandle = sqlitedatabase.mNativeHandle;
            compile(s, true);
            return;
        }
    }

    private void compile(String s, boolean flag)
    {
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        if (!flag)
        {
            break MISSING_BLOCK_LABEL_71;
        }
        mDatabase.lock();
        native_compile(s);
        mDatabase.unlock();
        return;
        s;
        mDatabase.unlock();
        throw s;
    }

    private final native void native_compile(String s);

    private final native void native_finalize();

    boolean acquire()
    {
        boolean flag1 = true;
        this;
        JVM INSTR monitorenter ;
        boolean flag = mInUse;
        if (!flag) goto _L2; else goto _L1
_L1:
        flag = false;
_L4:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        mInUse = true;
        flag = flag1;
        if (!SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION)
        {
            continue; /* Loop/switch isn't completed */
        }
        Log.v("SQLiteCompiledSql", (new StringBuilder()).append("Acquired DbObj (id#").append(nStatement).append(") from DB cache").toString());
        flag = flag1;
        if (true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    protected void finalize()
        throws Throwable
    {
        int i = nStatement;
        if (i == 0)
        {
            super.finalize();
            return;
        }
        StringBuilder stringbuilder;
        String s;
        int j;
        if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION)
        {
            Log.v("SQLiteCompiledSql", (new StringBuilder()).append("** warning ** Finalized DbObj (id#").append(nStatement).append(")").toString());
        }
        j = mSqlStmt.length();
        stringbuilder = (new StringBuilder()).append("Releasing statement in a finalizer. Please ensure that you explicitly call close() on your cursor: ");
        s = mSqlStmt;
        i = j;
        if (j > 100)
        {
            i = 100;
        }
        Log.w("SQLiteCompiledSql", stringbuilder.append(s.substring(0, i)).toString(), mStackTrace);
        releaseSqlStatement();
        super.finalize();
        return;
        Exception exception;
        exception;
        super.finalize();
        throw exception;
    }

    void release()
    {
        this;
        JVM INSTR monitorenter ;
        if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION)
        {
            Log.v("SQLiteCompiledSql", (new StringBuilder()).append("Released DbObj (id#").append(nStatement).append(") back to DB cache").toString());
        }
        mInUse = false;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    void releaseSqlStatement()
    {
        if (nStatement == 0)
        {
            break MISSING_BLOCK_LABEL_69;
        }
        if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION)
        {
            Log.v("SQLiteCompiledSql", (new StringBuilder()).append("closed and deallocated DbObj (id#").append(nStatement).append(")").toString());
        }
        mDatabase.lock();
        native_finalize();
        nStatement = 0;
        mDatabase.unlock();
        return;
        Exception exception;
        exception;
        mDatabase.unlock();
        throw exception;
    }
}
