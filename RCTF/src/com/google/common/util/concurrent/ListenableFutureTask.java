// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

// Referenced classes of package com.google.common.util.concurrent:
//            ListenableFuture, ExecutionList

public class ListenableFutureTask extends FutureTask
    implements ListenableFuture
{

    private final ExecutionList executionList;

    public ListenableFutureTask(Runnable runnable, Object obj)
    {
        super(runnable, obj);
        executionList = new ExecutionList();
    }

    public ListenableFutureTask(Callable callable)
    {
        super(callable);
        executionList = new ExecutionList();
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
