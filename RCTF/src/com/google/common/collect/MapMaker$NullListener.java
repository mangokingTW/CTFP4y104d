// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;


// Referenced classes of package com.google.common.collect:
//            MapEvictionListener, MapMaker

static final class  extends Enum
    implements MapEvictionListener
{

    private static final INSTANCE $VALUES[];
    public static final INSTANCE INSTANCE;

    public static  valueOf(String s)
    {
        return ()Enum.valueOf(com/google/common/collect/MapMaker$NullListener, s);
    }

    public static [] values()
    {
        return ([])$VALUES.clone();
    }

    public void onEviction(Object obj, Object obj1)
    {
    }

    static 
    {
        INSTANCE = new <init>("INSTANCE", 0);
        $VALUES = (new .VALUES[] {
            INSTANCE
        });
    }

    private (String s, int i)
    {
        super(s, i);
    }
}
