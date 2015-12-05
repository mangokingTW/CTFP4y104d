// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractFuture, ListenableFuture, ExecutionList

public abstract class AbstractListenableFuture extends AbstractFuture
    implements ListenableFuture
{

    private final ExecutionList executionList = new ExecutionList();

    public AbstractListenableFuture()
    {
    }

    public void addListener(Runnable runnable, Executor executor)
    {
        executionList.add(runnable, executor);
    }

    protected void done()
    {
        executionList.run();
    }
}
