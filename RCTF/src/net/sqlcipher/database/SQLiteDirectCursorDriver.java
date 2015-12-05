// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import net.sqlcipher.Cursor;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteCursorDriver, SQLiteQuery, SQLiteCursor, SQLiteDatabase

public class SQLiteDirectCursorDriver
    implements SQLiteCursorDriver
{

    private Cursor mCursor;
    private SQLiteDatabase mDatabase;
    private String mEditTable;
    private SQLiteQuery mQuery;
    private String mSql;

    public SQLiteDirectCursorDriver(SQLiteDatabase sqlitedatabase, String s, String s1)
    {
        mDatabase = sqlitedatabase;
        mEditTable = s1;
        mSql = s;
    }

    public void cursorClosed()
    {
        mCursor = null;
    }

    public void cursorDeactivated()
    {
    }

    public void cursorRequeried(android.database.Cursor cursor)
    {
    }

    public Cursor query(SQLiteDatabase.CursorFactory cursorfactory, String as[])
    {
        SQLiteQuery sqlitequery1;
        int i;
        i = 0;
        sqlitequery1 = new SQLiteQuery(mDatabase, mSql, 0, as);
        if (as != null) goto _L2; else goto _L1
_L1:
        int j = 0;
_L4:
        SQLiteQuery sqlitequery;
        if (j >= i)
        {
            break; /* Loop/switch isn't completed */
        }
        sqlitequery = sqlitequery1;
        sqlitequery1.bindString(j + 1, as[j]);
        j++;
        if (true) goto _L4; else goto _L3
_L3:
        break; /* Loop/switch isn't completed */
_L2:
        sqlitequery = sqlitequery1;
        i = as.length;
        if (true) goto _L1; else goto _L5
_L5:
        if (cursorfactory != null) goto _L7; else goto _L6
_L6:
        sqlitequery = sqlitequery1;
        mCursor = new SQLiteCursor(mDatabase, this, mEditTable, sqlitequery1);
_L9:
        sqlitequery = sqlitequery1;
        mQuery = sqlitequery1;
        sqlitequery = null;
        cursorfactory = mCursor;
        if (false)
        {
            throw new NullPointerException();
        } else
        {
            return cursorfactory;
        }
_L7:
        sqlitequery = sqlitequery1;
        mCursor = cursorfactory.newCursor(mDatabase, this, mEditTable, sqlitequery1);
        if (true) goto _L9; else goto _L8
_L8:
        cursorfactory;
        if (sqlitequery != null)
        {
            sqlitequery.close();
        }
        throw cursorfactory;
    }

    public void setBindArguments(String as[])
    {
        int j = as.length;
        for (int i = 0; i < j; i++)
        {
            mQuery.bindString(i + 1, as[i]);
        }

    }

    public String toString()
    {
        return (new StringBuilder()).append("SQLiteDirectCursorDriver: ").append(mSql).toString();
    }
}
