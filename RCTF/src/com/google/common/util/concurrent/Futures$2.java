// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Function;

// Referenced classes of package com.google.common.util.concurrent:
//            Futures

static final class 
    implements Function
{

    public Exception apply(Exception exception)
    {
        throw new AssertionError("impossible");
    }

    public volatile Object apply(Object obj)
    {
        return apply((Exception)obj);
    }

    ()
    {
    }
}
