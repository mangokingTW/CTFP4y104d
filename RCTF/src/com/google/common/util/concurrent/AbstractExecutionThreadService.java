// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Throwables;
import java.util.concurrent.Executor;

// Referenced classes of package com.google.common.util.concurrent:
//            Service, ListenableFuture, AbstractService

public abstract class AbstractExecutionThreadService
    implements Service
{

    private final Service _flddelegate = new AbstractService() {

        final AbstractExecutionThreadService this$0;

        protected final void doStart()
        {
            executor().execute(new Runnable() {

                final _cls1 this$1;

                public void run()
                {
                    Throwable throwable;
                    boolean flag;
                    try
                    {
                        startUp();
                        notifyStarted();
                        flag = isRunning();
                    }
                    catch (Throwable throwable1)
                    {
                        notifyFailed(throwable1);
                        throw Throwables.propagate(throwable1);
                    }
                    if (!flag)
                    {
                        break MISSING_BLOCK_LABEL_39;
                    }
                    AbstractExecutionThreadService.this.run();
                    shutDown();
                    notifyStopped();
                    return;
                    throwable;
                    try
                    {
                        shutDown();
                    }
                    catch (Exception exception) { }
                    throw throwable;
                }

            
            {
                this$1 = _cls1.this;
                super();
            }
            });
        }

        protected void doStop()
        {
            triggerShutdown();
        }

            
            {
                this$0 = AbstractExecutionThreadService.this;
                super();
            }
    };

    public AbstractExecutionThreadService()
    {
    }

    private String getServiceName()
    {
        return getClass().getSimpleName();
    }

    protected Executor executor()
    {
        return new Executor() {

            final AbstractExecutionThreadService this$0;

            public void execute(Runnable runnable)
            {
                (new Thread(runnable, getServiceName())).start();
            }

            
            {
                this$0 = AbstractExecutionThreadService.this;
                super();
            }
        };
    }

    public final boolean isRunning()
    {
        return _flddelegate.isRunning();
    }

    protected abstract void run()
        throws Exception;

    protected void shutDown()
        throws Exception
    {
    }

    public final ListenableFuture start()
    {
        return _flddelegate.start();
    }

    public final Service.State startAndWait()
    {
        return _flddelegate.startAndWait();
    }

    protected void startUp()
        throws Exception
    {
    }

    public final Service.State state()
    {
        return _flddelegate.state();
    }

    public final ListenableFuture stop()
    {
        return _flddelegate.stop();
    }

    public final Service.State stopAndWait()
    {
        return _flddelegate.stopAndWait();
    }

    public String toString()
    {
        return (new StringBuilder()).append(getServiceName()).append(" [").append(state()).append("]").toString();
    }

    protected void triggerShutdown()
    {
    }

}
