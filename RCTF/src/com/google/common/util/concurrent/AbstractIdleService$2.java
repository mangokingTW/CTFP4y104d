// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractIdleService

class val.state
    implements Executor
{

    final AbstractIdleService this$0;
    final val.state val$state;

    public void execute(Runnable runnable)
    {
        (new Thread(runnable, (new StringBuilder()).append(AbstractIdleService.access$000(AbstractIdleService.this)).append(" ").append(val$state).toString())).start();
    }

    ()
    {
        this$0 = final_abstractidleservice;
        val$state = val.state.this;
        super();
    }
}
