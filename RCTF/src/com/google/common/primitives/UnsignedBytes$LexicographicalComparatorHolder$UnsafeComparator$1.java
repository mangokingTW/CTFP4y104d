// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.primitives;

import java.lang.reflect.Field;
import java.security.PrivilegedAction;
import sun.misc.Unsafe;

// Referenced classes of package com.google.common.primitives:
//            UnsignedBytes

static final class 
    implements PrivilegedAction
{

    public Object run()
    {
        Object obj;
        try
        {
            obj = sun/misc/Unsafe.getDeclaredField("theUnsafe");
            ((Field) (obj)).setAccessible(true);
            obj = ((Field) (obj)).get(null);
        }
        catch (NoSuchFieldException nosuchfieldexception)
        {
            throw new Error();
        }
        catch (IllegalAccessException illegalaccessexception)
        {
            throw new Error();
        }
        return obj;
    }

    ()
    {
    }
}
