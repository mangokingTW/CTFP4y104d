// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Function;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// Referenced classes of package com.google.common.util.concurrent:
//            Futures

static final class exception
    implements Future
{

    private ExecutionException exception;
    private final Object lock = new Object();
    private boolean set;
    final Function val$function;
    final Future val$future;
    private Object value;

    private Object apply(Object obj)
        throws ExecutionException
    {
        Object obj1 = lock;
        obj1;
        JVM INSTR monitorenter ;
        boolean flag = set;
        if (flag) goto _L2; else goto _L1
_L1:
        value = val$function.apply(obj);
_L3:
        set = true;
_L2:
        if (exception != null)
        {
            throw exception;
        }
        break MISSING_BLOCK_LABEL_84;
        obj;
        obj1;
        JVM INSTR monitorexit ;
        throw obj;
        obj;
        exception = new ExecutionException(((Throwable) (obj)));
          goto _L3
        obj;
        exception = new ExecutionException(((Throwable) (obj)));
          goto _L3
        obj = value;
        obj1;
        JVM INSTR monitorexit ;
        return obj;
    }

    public boolean cancel(boolean flag)
    {
        return val$future.cancel(flag);
    }

    public Object get()
        throws InterruptedException, ExecutionException
    {
        return apply(val$future.get());
    }

    public Object get(long l, TimeUnit timeunit)
        throws InterruptedException, ExecutionException, TimeoutException
    {
        return apply(val$future.get(l, timeunit));
    }

    public boolean isCancelled()
    {
        return val$future.isCancelled();
    }

    public boolean isDone()
    {
        return val$future.isDone();
    }

    (Future future1, Function function1)
    {
        val$future = future1;
        val$function = function1;
        super();
        set = false;
        value = null;
        exception = null;
    }
}
