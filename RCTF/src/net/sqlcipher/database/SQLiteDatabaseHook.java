// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;


// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase

public interface SQLiteDatabaseHook
{

    public abstract void postKey(SQLiteDatabase sqlitedatabase);

    public abstract void preKey(SQLiteDatabase sqlitedatabase);
}