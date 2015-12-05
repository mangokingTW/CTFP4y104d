// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractFuture

static final class  extends AbstractQueuedSynchronizer
{

    static final int CANCELLED = 4;
    static final int COMPLETED = 2;
    static final int COMPLETING = 1;
    static final int RUNNING = 0;
    private static final long serialVersionUID = 0L;
    private Throwable exception;
    private Object value;

    private boolean complete(Object obj, Throwable throwable, int i)
    {
        if (compareAndSetState(0, 1))
        {
            value = obj;
            exception = throwable;
            releaseShared(i);
            return true;
        } else
        {
            return false;
        }
    }

    private Object getValue()
        throws CancellationException, ExecutionException
    {
        int i = getState();
        switch (i)
        {
        case 3: // '\003'
        default:
            throw new IllegalStateException((new StringBuilder()).append("Error, synchronizer in invalid state: ").append(i).toString());

        case 2: // '\002'
            if (exception != null)
            {
                throw new ExecutionException(exception);
            } else
            {
                return value;
            }

        case 4: // '\004'
            throw new CancellationException("Task was cancelled.");
        }
    }

    boolean cancel()
    {
        return complete(null, null, 4);
    }

    Object get()
        throws CancellationException, ExecutionException, InterruptedException
    {
        acquireSharedInterruptibly(-1);
        return getValue();
    }

    Object get(long l)
        throws TimeoutException, CancellationException, ExecutionException, InterruptedException
    {
        if (!tryAcquireSharedNanos(-1, l))
        {
            throw new TimeoutException("Timeout waiting for task.");
        } else
        {
            return getValue();
        }
    }

    boolean isCancelled()
    {
        return getState() == 4;
    }

    boolean isDone()
    {
        return (getState() & 6) != 0;
    }

    boolean set(Object obj)
    {
        return complete(obj, null, 2);
    }

    boolean setException(Throwable throwable)
    {
        return complete(null, throwable, 2);
    }

    protected int tryAcquireShared(int i)
    {
        return !isDone() ? -1 : 1;
    }

    protected boolean tryReleaseShared(int i)
    {
        setState(i);
        return true;
    }

    ()
    {
    }
}
