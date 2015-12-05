// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// Referenced classes of package com.google.common.util.concurrent:
//            AbstractListenableFuture, AbstractService

private class <init> extends AbstractListenableFuture
{

    final AbstractService this$0;

    public <init> get(long l, TimeUnit timeunit)
        throws InterruptedException, TimeoutException, ExecutionException
    {
        try
        {
            timeunit = (<init>)super.get(l, timeunit);
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

    private ()
    {
        this$0 = AbstractService.this;
        super();
    }

    nit>(nit> nit>)
    {
        this();
    }
}
