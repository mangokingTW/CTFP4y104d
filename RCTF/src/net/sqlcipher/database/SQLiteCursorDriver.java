// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.database.Cursor;

public interface SQLiteCursorDriver
{

    public abstract void cursorClosed();

    public abstract void cursorDeactivated();

    public abstract void cursorRequeried(Cursor cursor);

    public abstract net.sqlcipher.Cursor query(SQLiteDatabase.CursorFactory cursorfactory, String as[]);

    public abstract void setBindArguments(String as[]);
}
