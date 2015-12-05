// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import java.util.HashSet;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase

static class mActiveDatabases
{

    private static final activeDatabases activeDatabases = new <init>();
    private HashSet mActiveDatabases;

    static mActiveDatabases getInstance()
    {
        return activeDatabases;
    }



    private ()
    {
        mActiveDatabases = new HashSet();
    }
}
