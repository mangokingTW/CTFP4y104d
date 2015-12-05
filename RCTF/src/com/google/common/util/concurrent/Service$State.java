// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;


// Referenced classes of package com.google.common.util.concurrent:
//            Service

public static final class  extends Enum
{

    private static final FAILED $VALUES[];
    public static final FAILED FAILED;
    public static final FAILED NEW;
    public static final FAILED RUNNING;
    public static final FAILED STARTING;
    public static final FAILED STOPPING;
    public static final FAILED TERMINATED;

    public static  valueOf(String s)
    {
        return ()Enum.valueOf(com/google/common/util/concurrent/Service$State, s);
    }

    public static [] values()
    {
        return ([])$VALUES.clone();
    }

    static 
    {
        NEW = new <init>("NEW", 0);
        STARTING = new <init>("STARTING", 1);
        RUNNING = new <init>("RUNNING", 2);
        STOPPING = new <init>("STOPPING", 3);
        TERMINATED = new <init>("TERMINATED", 4);
        FAILED = new <init>("FAILED", 5);
        $VALUES = (new .VALUES[] {
            NEW, STARTING, RUNNING, STOPPING, TERMINATED, FAILED
        });
    }

    private (String s, int i)
    {
        super(s, i);
    }
}
