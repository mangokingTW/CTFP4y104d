// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;


// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabaseHook, SQLiteDatabase

static final class k
    implements SQLiteDatabaseHook
{

    public void postKey(SQLiteDatabase sqlitedatabase)
    {
        sqlitedatabase.execSQL("PRAGMA cipher_default_use_hmac = on");
    }

    public void preKey(SQLiteDatabase sqlitedatabase)
    {
        sqlitedatabase.execSQL("PRAGMA cipher_default_use_hmac = off");
    }

    k()
    {
    }
}
