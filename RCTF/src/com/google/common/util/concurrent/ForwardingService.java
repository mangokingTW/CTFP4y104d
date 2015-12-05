// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Throwables;
import com.google.common.collect.ForwardingObject;
import java.util.concurrent.ExecutionException;

// Referenced classes of package com.google.common.util.concurrent:
//            Service, Futures, UninterruptibleFuture, ListenableFuture

public abstract class ForwardingService extends ForwardingObject
    implements Service
{

    protected ForwardingService()
    {
    }

    protected abstract Service _mthdelegate();

    protected volatile Object _mthdelegate()
    {
        return _mthdelegate();
    }

    public boolean isRunning()
    {
        return _mthdelegate().isRunning();
    }

    protected Service.State standardStartAndWait()
    {
        Service.State state1;
        try
        {
            state1 = (Service.State)Futures.makeUninterruptible(start()).get();
        }
        catch (ExecutionException executionexception)
        {
            throw Throwables.propagate(executionexception.getCause());
        }
        return state1;
    }

    protected Service.State standardStopAndWait()
    {
        Service.State state1;
        try
        {
            state1 = (Service.State)Futures.makeUninterruptible(stop()).get();
        }
        catch (ExecutionException executionexception)
        {
            throw Throwables.propagate(executionexception.getCause());
        }
        return state1;
    }

    public ListenableFuture start()
    {
        return _mthdelegate().start();
    }

    public Service.State startAndWait()
    {
        return _mthdelegate().startAndWait();
    }

    public Service.State state()
    {
        return _mthdelegate().state();
    }

    public ListenableFuture stop()
    {
        return _mthdelegate().stop();
    }

    public Service.State stopAndWait()
    {
        return _mthdelegate().stopAndWait();
    }
}
