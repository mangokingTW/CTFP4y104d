// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;

import java.util.Comparator;
import java.util.List;

// Referenced classes of package com.google.common.collect:
//            SortedLists

static final class nit> extends nit>
{

    int exactMatchFound(List list, Object obj, int i, int j, int k, Comparator comparator, boolean flag)
    {
        if (!flag)
        {
            return j;
        }
        while (i < j) 
        {
            k = i + (j - i) / 2;
            if (comparator.compare(list.get(k), obj) < 0)
            {
                i = k + 1;
            } else
            {
                j = k;
            }
        }
        return i;
    }

    int exactMatchNotFound(List list, Object obj, int i, Comparator comparator)
    {
        return i - 1;
    }

    nit> reverse()
    {
        return CEILING;
    }

    (String s, int i)
    {
        super(s, i, null);
    }
}
