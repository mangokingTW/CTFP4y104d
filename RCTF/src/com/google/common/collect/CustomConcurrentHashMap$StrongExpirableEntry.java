// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;


// Referenced classes of package com.google.common.collect:
//            CustomConcurrentHashMap

private static class previousExpirable extends previousExpirable
    implements previousExpirable
{

    previousExpirable nextExpirable;
    previousExpirable previousExpirable;
    volatile long time;

    public long getExpirationTime()
    {
        return time;
    }

    public time getNextExpirable()
    {
        return nextExpirable;
    }

    public nextExpirable getPreviousExpirable()
    {
        return previousExpirable;
    }

    public void setExpirationTime(long l)
    {
        time = l;
    }

    public void setNextExpirable(time time1)
    {
        nextExpirable = time1;
    }

    public void setPreviousExpirable(nextExpirable nextexpirable)
    {
        previousExpirable = nextexpirable;
    }

    (CustomConcurrentHashMap customconcurrenthashmap, Object obj, int i,  )
    {
        super(customconcurrenthashmap, obj, i, );
        time = 0x7fffffffffffffffL;
        nextExpirable = CustomConcurrentHashMap.nullEntry();
        previousExpirable = CustomConcurrentHashMap.nullEntry();
    }
}
