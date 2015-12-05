// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.Callable;

public final class Callables
{

    private Callables()
    {
    }

    public static Callable returning(Object obj)
    {
        return new Callable(obj) {

            final Object val$value;

            public Object call()
            {
                return value;
            }

            
            {
                value = obj;
                super();
            }
        };
    }
}
