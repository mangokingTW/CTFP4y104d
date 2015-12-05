// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

// Referenced classes of package com.google.common.util.concurrent:
//            ExecutionList

private static class executor
{

    final Executor executor;
    final Runnable runnable;

    void execute()
    {
        try
        {
            executor.execute(runnable);
            return;
        }
        catch (RuntimeException runtimeexception)
        {
            ExecutionList.access$000().log(Level.SEVERE, (new StringBuilder()).append("RuntimeException while executing runnable ").append(runnable).append(" with executor ").append(executor).toString(), runtimeexception);
        }
    }

    (Runnable runnable1, Executor executor1)
    {
        runnable = runnable1;
        executor = executor1;
    }
}
