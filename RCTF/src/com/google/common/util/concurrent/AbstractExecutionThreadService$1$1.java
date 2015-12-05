// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Throwables;
import java.util.concurrent.Executor;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractExecutionThreadService, AbstractService

class this._cls1
    implements Runnable
{

    final Down this$1;

    public void run()
    {
        Throwable throwable;
        boolean flag;
        try
        {
            startUp();
            tifyStarted();
            flag = Running();
        }
        catch (Throwable throwable1)
        {
            tifyFailed(throwable1);
            throw Throwables.propagate(throwable1);
        }
        if (!flag)
        {
            break MISSING_BLOCK_LABEL_39;
        }
        AbstractExecutionThreadService.this.run();
        shutDown();
        tifyStopped();
        return;
        throwable;
        try
        {
            shutDown();
        }
        catch (Exception exception) { }
        throw throwable;
    }

    is._cls0()
    {
        this$1 = this._cls1.this;
        super();
    }

    // Unreferenced inner class com/google/common/util/concurrent/AbstractExecutionThreadService$1

/* anonymous class */
    class AbstractExecutionThreadService._cls1 extends AbstractService
    {

        final AbstractExecutionThreadService this$0;

        protected final void doStart()
        {
            executor().execute(new AbstractExecutionThreadService._cls1._cls1());
        }

        protected void doStop()
        {
            triggerShutdown();
        }

            
            {
                this$0 = AbstractExecutionThreadService.this;
                super();
            }
    }

}
