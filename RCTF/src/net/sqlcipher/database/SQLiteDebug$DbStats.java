// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;


// Referenced classes of package net.sqlcipher.database:
//            SQLiteDebug

public static class lookaside
{

    public String dbName;
    public long dbSize;
    public int lookaside;
    public long pageSize;

    public (String s, long l, long l1, int i)
    {
        dbName = s;
        pageSize = l1;
        dbSize = (l * l1) / 1024L;
        lookaside = i;
    }
}
