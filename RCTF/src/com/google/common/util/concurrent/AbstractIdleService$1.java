// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Throwables;
import java.util.concurrent.Executor;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractService, AbstractIdleService

class  extends AbstractService
{

    final AbstractIdleService this$0;

    protected final void doStart()
    {
        executor().execute(new Runnable() {

            final AbstractIdleService._cls1 this$1;

            public void run()
            {
                try
                {
                    startUp();
                    notifyStarted();
                    return;
                }
                catch (Throwable throwable)
                {
                    notifyFailed(throwable);
                    throw Throwables.propagate(throwable);
                }
            }

            
            {
                this$1 = AbstractIdleService._cls1.this;
                super();
            }
        });
    }

    protected final void doStop()
    {
        executor().execute(new Runnable() {

            final AbstractIdleService._cls1 this$1;

            public void run()
            {
                try
                {
                    shutDown();
                    notifyStopped();
                    return;
                }
                catch (Throwable throwable)
                {
                    notifyFailed(throwable);
                    throw Throwables.propagate(throwable);
                }
            }

            
            {
                this$1 = AbstractIdleService._cls1.this;
                super();
            }
        });
    }

    _cls2.this._cls1()
    {
        this$0 = AbstractIdleService.this;
        super();
    }
}
