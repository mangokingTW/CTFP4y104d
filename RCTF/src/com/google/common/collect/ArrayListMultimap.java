// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.google.common.collect:
//            AbstractListMultimap, Maps, Multimap, Serialization, 
//            Multiset

public final class ArrayListMultimap extends AbstractListMultimap
{

    private static final int DEFAULT_VALUES_PER_KEY = 10;
    private static final long serialVersionUID = 0L;
    transient int expectedValuesPerKey;

    private ArrayListMultimap()
    {
        super(new HashMap());
        expectedValuesPerKey = 10;
    }

    private ArrayListMultimap(int i, int j)
    {
        super(Maps.newHashMapWithExpectedSize(i));
        boolean flag;
        if (j >= 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkArgument(flag);
        expectedValuesPerKey = j;
    }

    private ArrayListMultimap(Multimap multimap)
    {
        int j = multimap.keySet().size();
        int i;
        if (multimap instanceof ArrayListMultimap)
        {
            i = ((ArrayListMultimap)multimap).expectedValuesPerKey;
        } else
        {
            i = 10;
        }
        this(j, i);
        putAll(multimap);
    }

    public static ArrayListMultimap create()
    {
        return new ArrayListMultimap();
    }

    public static ArrayListMultimap create(int i, int j)
    {
        return new ArrayListMultimap(i, j);
    }

    public static ArrayListMultimap create(Multimap multimap)
    {
        return new ArrayListMultimap(multimap);
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        expectedValuesPerKey = objectinputstream.readInt();
        int i = Serialization.readCount(objectinputstream);
        setMap(Maps.newHashMapWithExpectedSize(i));
        Serialization.populateMultimap(this, objectinputstream, i);
    }

    private void writeObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
        objectoutputstream.defaultWriteObject();
        objectoutputstream.writeInt(expectedValuesPerKey);
        Serialization.writeMultimap(this, objectoutputstream);
    }

    public volatile Map asMap()
    {
        return super.asMap();
    }

    public volatile void clear()
    {
        super.clear();
    }

    public volatile boolean containsEntry(Object obj, Object obj1)
    {
        return super.containsEntry(obj, obj1);
    }

    public volatile boolean containsKey(Object obj)
    {
        return super.containsKey(obj);
    }

    public volatile boolean containsValue(Object obj)
    {
        return super.containsValue(obj);
    }

    volatile Collection createCollection()
    {
        return createCollection();
    }

    List createCollection()
    {
        return new ArrayList(expectedValuesPerKey);
    }

    public volatile Collection entries()
    {
        return super.entries();
    }

    public volatile boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    public volatile List get(Object obj)
    {
        return super.get(obj);
    }

    public volatile int hashCode()
    {
        return super.hashCode();
    }

    public volatile boolean isEmpty()
    {
        return super.isEmpty();
    }

    public volatile Set keySet()
    {
        return super.keySet();
    }

    public volatile Multiset keys()
    {
        return super.keys();
    }

    public volatile boolean put(Object obj, Object obj1)
    {
        return super.put(obj, obj1);
    }

    public volatile boolean putAll(Multimap multimap)
    {
        return super.putAll(multimap);
    }

    public volatile boolean putAll(Object obj, Iterable iterable)
    {
        return super.putAll(obj, iterable);
    }

    public volatile boolean remove(Object obj, Object obj1)
    {
        return super.remove(obj, obj1);
    }

    public volatile List removeAll(Object obj)
    {
        return super.removeAll(obj);
    }

    public volatile List replaceValues(Object obj, Iterable iterable)
    {
        return super.replaceValues(obj, iterable);
    }

    public volatile int size()
    {
        return super.size();
    }

    public volatile String toString()
    {
        return super.toString();
    }

    public void trimToSize()
    {
        for (Iterator iterator = backingMap().values().iterator(); iterator.hasNext(); ((ArrayList)(Collection)iterator.next()).trimToSize()) { }
    }

    public volatile Collection values()
    {
        return super.values();
    }
}
