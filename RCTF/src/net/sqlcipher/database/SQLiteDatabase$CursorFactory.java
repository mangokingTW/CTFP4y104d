// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import net.sqlcipher.Cursor;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase, SQLiteCursorDriver, SQLiteQuery

public static interface _cls9
{

    public abstract Cursor newCursor(SQLiteDatabase sqlitedatabase, SQLiteCursorDriver sqlitecursordriver, String s, SQLiteQuery sqlitequery);
}
