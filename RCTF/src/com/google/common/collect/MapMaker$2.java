// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;

import com.google.common.base.Ticker;

// Referenced classes of package com.google.common.collect:
//            MapMaker

static final class 
    implements Ticker
{

    public long read()
    {
        return System.nanoTime();
    }

    ()
    {
    }
}
