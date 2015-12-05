// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// Referenced classes of package com.google.common.util.concurrent:
//            CheckedFuture, ListenableFuture

public abstract class AbstractCheckedFuture
    implements CheckedFuture
{

    protected final ListenableFuture _flddelegate;

    protected AbstractCheckedFuture(ListenableFuture listenablefuture)
    {
        _flddelegate = listenablefuture;
    }

    public void addListener(Runnable runnable, Executor executor)
    {
        _flddelegate.addListener(runnable, executor);
    }

    public boolean cancel(boolean flag)
    {
        return _flddelegate.cancel(flag);
    }

    public Object checkedGet()
        throws Exception
    {
        Object obj;
        try
        {
            obj = get();
        }
        catch (InterruptedException interruptedexception)
        {
            Thread.currentThread().interrupt();
            throw mapException(interruptedexception);
        }
        catch (CancellationException cancellationexception)
        {
            throw mapException(cancellationexception);
        }
        catch (ExecutionException executionexception)
        {
            throw mapException(executionexception);
        }
        return obj;
    }

    public Object checkedGet(long l, TimeUnit timeunit)
        throws TimeoutException, Exception
    {
        try
        {
            timeunit = ((TimeUnit) (get(l, timeunit)));
        }
        // Misplaced declaration of an exception variable
        catch (TimeUnit timeunit)
        {
            Thread.currentThread().interrupt();
            throw mapException(timeunit);
        }
        // Misplaced declaration of an exception variable
        catch (TimeUnit timeunit)
        {
            throw mapException(timeunit);
        }
        // Misplaced declaration of an exception variable
        catch (TimeUnit timeunit)
        {
            throw mapException(timeunit);
        }
        return timeunit;
    }

    public Object get()
        throws InterruptedException, ExecutionException
    {
        return _flddelegate.get();
    }

    public Object get(long l, TimeUnit timeunit)
        throws InterruptedException, ExecutionException, TimeoutException
    {
        return _flddelegate.get(l, timeunit);
    }

    public boolean isCancelled()
    {
        return _flddelegate.isCancelled();
    }

    public boolean isDone()
    {
        return _flddelegate.isDone();
    }

    protected abstract Exception mapException(Exception exception);
}
