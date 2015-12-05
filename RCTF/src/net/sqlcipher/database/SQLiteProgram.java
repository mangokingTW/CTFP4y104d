// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.util.Log;
import java.util.Map;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteClosable, SQLiteDatabase, SQLiteCompiledSql, SQLiteDebug

public abstract class SQLiteProgram extends SQLiteClosable
{

    private static final String TAG = "SQLiteProgram";
    private SQLiteCompiledSql mCompiledSql;
    protected SQLiteDatabase mDatabase;
    final String mSql;
    protected int nHandle;
    protected int nStatement;

    SQLiteProgram(SQLiteDatabase sqlitedatabase, String s)
    {
        nHandle = 0;
        nStatement = 0;
        mDatabase = sqlitedatabase;
        mSql = s.trim();
        sqlitedatabase.acquireReference();
        sqlitedatabase.addSQLiteClosable(this);
        nHandle = sqlitedatabase.mNativeHandle;
        String s1 = mSql.substring(0, 6);
        if (!s1.equalsIgnoreCase("INSERT") && !s1.equalsIgnoreCase("UPDATE") && !s1.equalsIgnoreCase("REPLAC") && !s1.equalsIgnoreCase("DELETE") && !s1.equalsIgnoreCase("SELECT"))
        {
            mCompiledSql = new SQLiteCompiledSql(sqlitedatabase, s);
            nStatement = mCompiledSql.nStatement;
            return;
        }
        mCompiledSql = sqlitedatabase.getCompiledStatementForSql(s);
        if (mCompiledSql != null) goto _L2; else goto _L1
_L1:
        mCompiledSql = new SQLiteCompiledSql(sqlitedatabase, s);
        mCompiledSql.acquire();
        sqlitedatabase.addToCompiledQueries(s, mCompiledSql);
        if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION)
        {
            Log.v("SQLiteProgram", (new StringBuilder()).append("Created DbObj (id#").append(mCompiledSql.nStatement).append(") for sql: ").append(s).toString());
        }
_L4:
        nStatement = mCompiledSql.nStatement;
        return;
_L2:
        if (!mCompiledSql.acquire())
        {
            int i = mCompiledSql.nStatement;
            mCompiledSql = new SQLiteCompiledSql(sqlitedatabase, s);
            if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION)
            {
                Log.v("SQLiteProgram", (new StringBuilder()).append("** possible bug ** Created NEW DbObj (id#").append(mCompiledSql.nStatement).append(") because the previously created DbObj (id#").append(i).append(") was not released for sql:").append(s).toString());
            }
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    private final native void native_clear_bindings();

    private void releaseCompiledSqlIfNotInCache()
    {
        if (mCompiledSql == null)
        {
            return;
        }
        Map map = mDatabase.mCompiledQueries;
        map;
        JVM INSTR monitorenter ;
        if (mDatabase.mCompiledQueries.containsValue(mCompiledSql))
        {
            break MISSING_BLOCK_LABEL_62;
        }
        mCompiledSql.releaseSqlStatement();
        mCompiledSql = null;
        nStatement = 0;
_L1:
        return;
        Exception exception;
        exception;
        map;
        JVM INSTR monitorexit ;
        throw exception;
        mCompiledSql.release();
          goto _L1
    }

    public void bindBlob(int i, byte abyte0[])
    {
        if (abyte0 == null)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("the bind value at index ").append(i).append(" is null").toString());
        }
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        acquireReference();
        native_bind_blob(i, abyte0);
        releaseReference();
        return;
        abyte0;
        releaseReference();
        throw abyte0;
    }

    public void bindDouble(int i, double d)
    {
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        acquireReference();
        native_bind_double(i, d);
        releaseReference();
        return;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public void bindLong(int i, long l)
    {
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        acquireReference();
        native_bind_long(i, l);
        releaseReference();
        return;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public void bindNull(int i)
    {
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        acquireReference();
        native_bind_null(i);
        releaseReference();
        return;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public void bindString(int i, String s)
    {
        if (s == null)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("the bind value at index ").append(i).append(" is null").toString());
        }
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        acquireReference();
        native_bind_string(i, s);
        releaseReference();
        return;
        s;
        releaseReference();
        throw s;
    }

    public void clearBindings()
    {
        if (!mDatabase.isOpen())
        {
            throw new IllegalStateException((new StringBuilder()).append("database ").append(mDatabase.getPath()).append(" already closed").toString());
        }
        acquireReference();
        native_clear_bindings();
        releaseReference();
        return;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public void close()
    {
        if (!mDatabase.isOpen())
        {
            return;
        }
        mDatabase.lock();
        releaseReference();
        mDatabase.unlock();
        return;
        Exception exception;
        exception;
        mDatabase.unlock();
        throw exception;
    }

    protected void compile(String s, boolean flag)
    {
    }

    String getSqlString()
    {
        return mSql;
    }

    public final int getUniqueId()
    {
        return nStatement;
    }

    protected final native void native_bind_blob(int i, byte abyte0[]);

    protected final native void native_bind_double(int i, double d);

    protected final native void native_bind_long(int i, long l);

    protected final native void native_bind_null(int i);

    protected final native void native_bind_string(int i, String s);

    protected final native void native_compile(String s);

    protected final native void native_finalize();

    protected void onAllReferencesReleased()
    {
        releaseCompiledSqlIfNotInCache();
        mDatabase.releaseReference();
        mDatabase.removeSQLiteClosable(this);
    }

    protected void onAllReferencesReleasedFromContainer()
    {
        releaseCompiledSqlIfNotInCache();
        mDatabase.releaseReference();
    }
}
