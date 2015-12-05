// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;


public interface SQLiteTransactionListener
{

    public abstract void onBegin();

    public abstract void onCommit();

    public abstract void onRollback();
}
