// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.google.common.util.concurrent:
//            Service, Futures, UninterruptibleFuture, ListenableFuture, 
//            AbstractListenableFuture

public abstract class AbstractService
    implements Service
{
    private class Transition extends AbstractListenableFuture
    {

        final AbstractService this$0;

        public Service.State get(long l, TimeUnit timeunit)
            throws InterruptedException, TimeoutException, ExecutionException
        {
            try
            {
                timeunit = (Service.State)super.get(l, timeunit);
            }
            // Misplaced declaration of an exception variable
            catch (TimeUnit timeunit)
            {
                throw new TimeoutException(toString());
            }
            return timeunit;
        }

        public volatile Object get(long l, TimeUnit timeunit)
            throws InterruptedException, ExecutionException, TimeoutException
        {
            return get(l, timeunit);
        }

        private Transition()
        {
            this$0 = AbstractService.this;
            super();
        }

    }


    private final ReentrantLock lock = new ReentrantLock();
    private final Transition shutdown = new Transition();
    private boolean shutdownWhenStartupFinishes;
    private final Transition startup = new Transition();
    private Service.State state;

    public AbstractService()
    {
        state = Service.State.NEW;
        shutdownWhenStartupFinishes = false;
    }

    protected abstract void doStart();

    protected abstract void doStop();

    public final boolean isRunning()
    {
        return state() == Service.State.RUNNING;
    }

    protected final void notifyFailed(Throwable throwable)
    {
        Preconditions.checkNotNull(throwable);
        lock.lock();
        if (state != Service.State.STARTING) goto _L2; else goto _L1
_L1:
        startup.setException(throwable);
        shutdown.setException(new Exception("Service failed to start.", throwable));
_L4:
        state = Service.State.FAILED;
        lock.unlock();
        return;
_L2:
        if (state != Service.State.STOPPING) goto _L4; else goto _L3
_L3:
        shutdown.setException(throwable);
          goto _L4
        throwable;
        lock.unlock();
        throw throwable;
    }

    protected final void notifyStarted()
    {
        lock.lock();
        if (state != Service.State.STARTING)
        {
            IllegalStateException illegalstateexception = new IllegalStateException((new StringBuilder()).append("Cannot notifyStarted() when the service is ").append(state).toString());
            notifyFailed(illegalstateexception);
            throw illegalstateexception;
        }
        break MISSING_BLOCK_LABEL_64;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        state = Service.State.RUNNING;
        if (!shutdownWhenStartupFinishes)
        {
            break MISSING_BLOCK_LABEL_91;
        }
        stop();
_L1:
        lock.unlock();
        return;
        startup.set(Service.State.RUNNING);
          goto _L1
    }

    protected final void notifyStopped()
    {
        lock.lock();
        if (state != Service.State.STOPPING && state != Service.State.RUNNING)
        {
            IllegalStateException illegalstateexception = new IllegalStateException((new StringBuilder()).append("Cannot notifyStopped() when the service is ").append(state).toString());
            notifyFailed(illegalstateexception);
            throw illegalstateexception;
        }
        break MISSING_BLOCK_LABEL_74;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        state = Service.State.TERMINATED;
        shutdown.set(Service.State.TERMINATED);
        lock.unlock();
        return;
    }

    public final ListenableFuture start()
    {
        lock.lock();
        if (state == Service.State.NEW)
        {
            state = Service.State.STARTING;
            doStart();
        }
        lock.unlock();
_L2:
        return startup;
        Object obj;
        obj;
        notifyFailed(((Throwable) (obj)));
        lock.unlock();
        if (true) goto _L2; else goto _L1
_L1:
        obj;
        lock.unlock();
        throw obj;
    }

    public Service.State startAndWait()
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

    public final Service.State state()
    {
        lock.lock();
        Service.State state1;
        if (!shutdownWhenStartupFinishes || state != Service.State.STARTING)
        {
            break MISSING_BLOCK_LABEL_37;
        }
        state1 = Service.State.STOPPING;
        lock.unlock();
        return state1;
        state1 = state;
        lock.unlock();
        return state1;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public final ListenableFuture stop()
    {
        lock.lock();
        if (state != Service.State.NEW) goto _L2; else goto _L1
_L1:
        state = Service.State.TERMINATED;
        startup.set(Service.State.TERMINATED);
        shutdown.set(Service.State.TERMINATED);
_L5:
        lock.unlock();
_L6:
        return shutdown;
_L2:
        if (state != Service.State.STARTING) goto _L4; else goto _L3
_L3:
        shutdownWhenStartupFinishes = true;
        startup.set(Service.State.STOPPING);
          goto _L5
        Object obj;
        obj;
        notifyFailed(((Throwable) (obj)));
        lock.unlock();
          goto _L6
_L4:
        if (state != Service.State.RUNNING) goto _L5; else goto _L7
_L7:
        state = Service.State.STOPPING;
        doStop();
          goto _L5
        obj;
        lock.unlock();
        throw obj;
    }

    public Service.State stopAndWait()
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

    public String toString()
    {
        return (new StringBuilder()).append(getClass().getSimpleName()).append(" [").append(state()).append("]").toString();
    }
}
