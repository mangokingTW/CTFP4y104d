// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractExecutionThreadService

class this._cls0
    implements Executor
{

    final AbstractExecutionThreadService this$0;

    public void execute(Runnable runnable)
    {
        (new Thread(runnable, AbstractExecutionThreadService.access$000(AbstractExecutionThreadService.this))).start();
    }

    ()
    {
        this$0 = AbstractExecutionThreadService.this;
        super();
    }
}
