// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Throwables;
import java.util.concurrent.Executor;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractService, AbstractExecutionThreadService

class this._cls0 extends AbstractService
{

    final AbstractExecutionThreadService this$0;

    protected final void doStart()
    {
        executor().execute(new Runnable() {

            final AbstractExecutionThreadService._cls1 this$1;

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
                this$1 = AbstractExecutionThreadService._cls1.this;
                super();
            }
        });
    }

    protected void doStop()
    {
        triggerShutdown();
    }

    _cls1.this._cls1()
    {
        this$0 = AbstractExecutionThreadService.this;
        super();
    }
}
