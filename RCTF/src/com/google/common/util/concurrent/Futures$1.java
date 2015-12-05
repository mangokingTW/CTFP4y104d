// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// Referenced classes of package com.google.common.util.concurrent:
//            UninterruptibleFuture, Futures

static final class val.future
    implements UninterruptibleFuture
{

    final Future val$future;

    public boolean cancel(boolean flag)
    {
        return val$future.cancel(flag);
    }

    public Object get()
        throws ExecutionException
    {
        boolean flag = false;
_L2:
        Object obj = val$future.get();
        if (flag)
        {
            Thread.currentThread().interrupt();
        }
        return obj;
        Object obj1;
        obj1;
        flag = true;
        if (true) goto _L2; else goto _L1
_L1:
        obj1;
        if (flag)
        {
            Thread.currentThread().interrupt();
        }
        throw obj1;
    }

    public Object get(long l, TimeUnit timeunit)
        throws TimeoutException, ExecutionException
    {
        boolean flag;
        boolean flag1;
        boolean flag2;
        flag2 = false;
        flag = false;
        flag1 = flag2;
        long l1 = System.nanoTime();
        flag1 = flag2;
        l = timeunit.toNanos(l);
_L2:
        flag1 = flag;
        timeunit = ((TimeUnit) (val$future.get((l1 + l) - System.nanoTime(), TimeUnit.NANOSECONDS)));
        if (flag)
        {
            Thread.currentThread().interrupt();
        }
        return timeunit;
        timeunit;
        flag = true;
        if (true) goto _L2; else goto _L1
_L1:
        timeunit;
        if (flag1)
        {
            Thread.currentThread().interrupt();
        }
        throw timeunit;
    }

    public boolean isCancelled()
    {
        return val$future.isCancelled();
    }

    public boolean isDone()
    {
        return val$future.isDone();
    }

    tibleFuture(Future future1)
    {
        val$future = future1;
        super();
    }
}
