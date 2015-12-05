// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface UninterruptibleFuture
    extends Future
{

    public abstract Object get()
        throws ExecutionException;

    public abstract Object get(long l, TimeUnit timeunit)
        throws ExecutionException, TimeoutException;
}
