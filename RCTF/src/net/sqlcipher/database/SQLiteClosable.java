// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;


// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase, SQLiteProgram, SQLiteStatement, SQLiteQuery

public abstract class SQLiteClosable
{

    private Object mLock;
    private int mReferenceCount;

    public SQLiteClosable()
    {
        mReferenceCount = 1;
        mLock = new Object();
    }

    private String getObjInfo()
    {
        StringBuilder stringbuilder;
        stringbuilder = new StringBuilder();
        stringbuilder.append(getClass().getName());
        stringbuilder.append(" (");
        if (!(this instanceof SQLiteDatabase)) goto _L2; else goto _L1
_L1:
        stringbuilder.append("database = ");
        stringbuilder.append(((SQLiteDatabase)this).getPath());
_L4:
        stringbuilder.append(") ");
        return stringbuilder.toString();
_L2:
        if ((this instanceof SQLiteProgram) || (this instanceof SQLiteStatement) || (this instanceof SQLiteQuery))
        {
            stringbuilder.append("mSql = ");
            stringbuilder.append(((SQLiteProgram)this).mSql);
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void acquireReference()
    {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if (mReferenceCount <= 0)
        {
            throw new IllegalStateException((new StringBuilder()).append("attempt to re-open an already-closed object: ").append(getObjInfo()).toString());
        }
        break MISSING_BLOCK_LABEL_49;
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        mReferenceCount = mReferenceCount + 1;
        obj;
        JVM INSTR monitorexit ;
    }

    protected abstract void onAllReferencesReleased();

    protected void onAllReferencesReleasedFromContainer()
    {
    }

    public void releaseReference()
    {
        synchronized (mLock)
        {
            mReferenceCount = mReferenceCount - 1;
            if (mReferenceCount == 0)
            {
                onAllReferencesReleased();
            }
        }
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void releaseReferenceFromContainer()
    {
        synchronized (mLock)
        {
            mReferenceCount = mReferenceCount - 1;
            if (mReferenceCount == 0)
            {
                onAllReferencesReleasedFromContainer();
            }
        }
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }
}
