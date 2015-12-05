// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;


// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase

private static class foreignKey
{

    String deletedTable;
    String foreignKey;
    String masterTable;

    (String s, String s1, String s2)
    {
        masterTable = s;
        deletedTable = s1;
        foreignKey = s2;
    }
}
