// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;


// Referenced classes of package com.google.common.util.concurrent:
//            AbstractListenableFuture

public final class SettableFuture extends AbstractListenableFuture
{

    private SettableFuture()
    {
    }

    public static SettableFuture create()
    {
        return new SettableFuture();
    }

    public boolean cancel(boolean flag)
    {
        return super.cancel();
    }

    public boolean set(Object obj)
    {
        return super.set(obj);
    }

    public boolean setException(Throwable throwable)
    {
        return super.setException(throwable);
    }
}
