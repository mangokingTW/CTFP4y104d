// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.ListIterator;
import java.util.Map;

// Referenced classes of package com.google.common.collect:
//            LinkedListMultimap, Multiset

private class current
    implements ListIterator
{

    current current;
    final Object key;
    current next;
    int nextIndex;
    current previous;
    final LinkedListMultimap this$0;

    public void add(Object obj)
    {
        previous = LinkedListMultimap.access$700(LinkedListMultimap.this, key, obj, next);
        nextIndex = nextIndex + 1;
        current = null;
    }

    public boolean hasNext()
    {
        return next != null;
    }

    public boolean hasPrevious()
    {
        return previous != null;
    }

    public Object next()
    {
        LinkedListMultimap.access$100(next);
        previous previous1 = next;
        current = previous1;
        previous = previous1;
        next = next.next;
        nextIndex = nextIndex + 1;
        return current.current;
    }

    public int nextIndex()
    {
        return nextIndex;
    }

    public Object previous()
    {
        LinkedListMultimap.access$100(previous);
        nextIndex nextindex = previous;
        current = nextindex;
        next = nextindex;
        previous = previous.;
        nextIndex = nextIndex - 1;
        return current.current;
    }

    public int previousIndex()
    {
        return nextIndex - 1;
    }

    public void remove()
    {
        boolean flag;
        if (current != null)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkState(flag);
        if (current != next)
        {
            previous = current.;
            nextIndex = nextIndex - 1;
        } else
        {
            next = current.current;
        }
        LinkedListMultimap.access$200(LinkedListMultimap.this, current);
        current = null;
    }

    public void set(Object obj)
    {
        boolean flag;
        if (current != null)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkState(flag);
        current.current = obj;
    }

    (Object obj)
    {
        this$0 = LinkedListMultimap.this;
        super();
        key = obj;
        next = (next)LinkedListMultimap.access$400(LinkedListMultimap.this).get(obj);
    }

    public next(Object obj, int i)
    {
        this$0 = LinkedListMultimap.this;
        super();
        int j = LinkedListMultimap.access$500(LinkedListMultimap.this).count(obj);
        Preconditions.checkPositionIndex(i, j);
        if (i >= j / 2)
        {
            previous = (previous)LinkedListMultimap.access$600(LinkedListMultimap.this).get(obj);
            nextIndex = j;
            for (; i < j; i++)
            {
                previous();
            }

        } else
        {
            next = (next)LinkedListMultimap.access$400(LinkedListMultimap.this).get(obj);
            for (; i > 0; i--)
            {
                next();
            }

        }
        key = obj;
        current = null;
    }
}
