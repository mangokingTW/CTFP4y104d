// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.os.SystemClock;
import android.util.Log;
import net.sqlcipher.CursorWindow;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteProgram, SQLiteDatabaseCorruptException, SQLiteDatabase, SQLiteDebug, 
//            SQLiteMisuseException

public class SQLiteQuery extends SQLiteProgram
{

    private static final String TAG = "Cursor";
    private String mBindArgs[];
    private boolean mClosed;
    private int mOffsetIndex;

    SQLiteQuery(SQLiteDatabase sqlitedatabase, String s, int i, String as[])
    {
        super(sqlitedatabase, s);
        mClosed = false;
        mOffsetIndex = i;
        mBindArgs = as;
    }

    private final native int native_column_count();

    private final native String native_column_name(int i);

    private final native int native_fill_window(CursorWindow cursorwindow, int i, int j, int k, int l);

    public void bindDouble(int i, double d)
    {
        mBindArgs[i - 1] = Double.toString(d);
        if (!mClosed)
        {
            super.bindDouble(i, d);
        }
    }

    public void bindLong(int i, long l)
    {
        mBindArgs[i - 1] = Long.toString(l);
        if (!mClosed)
        {
            super.bindLong(i, l);
        }
    }

    public void bindNull(int i)
    {
        mBindArgs[i - 1] = null;
        if (!mClosed)
        {
            super.bindNull(i);
        }
    }

    public void bindString(int i, String s)
    {
        mBindArgs[i - 1] = s;
        if (!mClosed)
        {
            super.bindString(i, s);
        }
    }

    public void close()
    {
        super.close();
        mClosed = true;
    }

    int columnCountLocked()
    {
        acquireReference();
        int i = native_column_count();
        releaseReference();
        return i;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    String columnNameLocked(int i)
    {
        acquireReference();
        String s = native_column_name(i);
        releaseReference();
        return s;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    int fillWindow(CursorWindow cursorwindow, int i, int j)
    {
        long l;
        l = SystemClock.uptimeMillis();
        mDatabase.lock();
        mDatabase.logTimeStat(mSql, l, "GETLOCK:");
        acquireReference();
        cursorwindow.acquireReference();
        i = native_fill_window(cursorwindow, cursorwindow.getStartPosition(), mOffsetIndex, i, j);
        if (SQLiteDebug.DEBUG_SQL_STATEMENTS)
        {
            Log.d("Cursor", (new StringBuilder()).append("fillWindow(): ").append(mSql).toString());
        }
        mDatabase.logTimeStat(mSql, l);
        cursorwindow.releaseReference();
        releaseReference();
        mDatabase.unlock();
        return i;
        Object obj;
        obj;
        cursorwindow.releaseReference();
        releaseReference();
        mDatabase.unlock();
        return 0;
        obj;
        mDatabase.onCorruption();
        throw obj;
        obj;
        cursorwindow.releaseReference();
        throw obj;
        cursorwindow;
        releaseReference();
        mDatabase.unlock();
        throw cursorwindow;
    }

    void requery()
    {
        if (mBindArgs != null)
        {
            int k = mBindArgs.length;
            int i = 0;
            do
            {
                if (i >= k)
                {
                    break;
                }
                try
                {
                    super.bindString(i + 1, mBindArgs[i]);
                }
                catch (SQLiteMisuseException sqlitemisuseexception)
                {
                    StringBuilder stringbuilder = new StringBuilder((new StringBuilder()).append("mSql ").append(mSql).toString());
                    for (int j = 0; j < k; j++)
                    {
                        stringbuilder.append(" ");
                        stringbuilder.append(mBindArgs[j]);
                    }

                    stringbuilder.append(" ");
                    throw new IllegalStateException(stringbuilder.toString(), sqlitemisuseexception);
                }
                i++;
            } while (true);
        }
    }

    public String toString()
    {
        return (new StringBuilder()).append("SQLiteQuery: ").append(mSql).toString();
    }
}
