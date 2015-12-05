// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.google.common.collect:
//            ImmutableMultimap, ListMultimap, Multimap, ImmutableMap, 
//            ImmutableList, EmptyImmutableListMultimap, Serialization, ImmutableCollection

public class ImmutableListMultimap extends ImmutableMultimap
    implements ListMultimap
{
    public static final class Builder extends ImmutableMultimap.Builder
    {

        public ImmutableListMultimap build()
        {
            return (ImmutableListMultimap)super.build();
        }

        public volatile ImmutableMultimap build()
        {
            return build();
        }

        public Builder orderKeysBy(Comparator comparator)
        {
            super.orderKeysBy(comparator);
            return this;
        }

        public volatile ImmutableMultimap.Builder orderKeysBy(Comparator comparator)
        {
            return orderKeysBy(comparator);
        }

        public Builder orderValuesBy(Comparator comparator)
        {
            super.orderValuesBy(comparator);
            return this;
        }

        public volatile ImmutableMultimap.Builder orderValuesBy(Comparator comparator)
        {
            return orderValuesBy(comparator);
        }

        public Builder put(Object obj, Object obj1)
        {
            super.put(obj, obj1);
            return this;
        }

        public volatile ImmutableMultimap.Builder put(Object obj, Object obj1)
        {
            return put(obj, obj1);
        }

        public Builder putAll(Multimap multimap)
        {
            super.putAll(multimap);
            return this;
        }

        public Builder putAll(Object obj, Iterable iterable)
        {
            super.putAll(obj, iterable);
            return this;
        }

        public transient Builder putAll(Object obj, Object aobj[])
        {
            super.putAll(obj, aobj);
            return this;
        }

        public volatile ImmutableMultimap.Builder putAll(Multimap multimap)
        {
            return putAll(multimap);
        }

        public volatile ImmutableMultimap.Builder putAll(Object obj, Iterable iterable)
        {
            return putAll(obj, iterable);
        }

        public volatile ImmutableMultimap.Builder putAll(Object obj, Object aobj[])
        {
            return putAll(obj, aobj);
        }

        public Builder()
        {
        }
    }


    private static final long serialVersionUID = 0L;

    ImmutableListMultimap(ImmutableMap immutablemap, int i)
    {
        super(immutablemap, i);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static ImmutableListMultimap copyOf(Multimap multimap)
    {
        if (!multimap.isEmpty()) goto _L2; else goto _L1
_L1:
        ImmutableListMultimap immutablelistmultimap = of();
_L4:
        return immutablelistmultimap;
_L2:
        ImmutableListMultimap immutablelistmultimap1;
        if (!(multimap instanceof ImmutableListMultimap))
        {
            break; /* Loop/switch isn't completed */
        }
        immutablelistmultimap1 = (ImmutableListMultimap)multimap;
        immutablelistmultimap = immutablelistmultimap1;
        if (!immutablelistmultimap1.isPartialView()) goto _L4; else goto _L3
_L3:
        ImmutableMap.Builder builder1 = ImmutableMap.builder();
        int i = 0;
        multimap = multimap.asMap().entrySet().iterator();
        do
        {
            if (!multimap.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)multimap.next();
            ImmutableList immutablelist = ImmutableList.copyOf((Collection)entry.getValue());
            if (!immutablelist.isEmpty())
            {
                builder1.put(entry.getKey(), immutablelist);
                i += immutablelist.size();
            }
        } while (true);
        return new ImmutableListMultimap(builder1.build(), i);
    }

    public static ImmutableListMultimap of()
    {
        return EmptyImmutableListMultimap.INSTANCE;
    }

    public static ImmutableListMultimap of(Object obj, Object obj1)
    {
        Builder builder1 = builder();
        builder1.put(obj, obj1);
        return builder1.build();
    }

    public static ImmutableListMultimap of(Object obj, Object obj1, Object obj2, Object obj3)
    {
        Builder builder1 = builder();
        builder1.put(obj, obj1);
        builder1.put(obj2, obj3);
        return builder1.build();
    }

    public static ImmutableListMultimap of(Object obj, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5)
    {
        Builder builder1 = builder();
        builder1.put(obj, obj1);
        builder1.put(obj2, obj3);
        builder1.put(obj4, obj5);
        return builder1.build();
    }

    public static ImmutableListMultimap of(Object obj, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7)
    {
        Builder builder1 = builder();
        builder1.put(obj, obj1);
        builder1.put(obj2, obj3);
        builder1.put(obj4, obj5);
        builder1.put(obj6, obj7);
        return builder1.build();
    }

    public static ImmutableListMultimap of(Object obj, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, 
            Object obj8, Object obj9)
    {
        Builder builder1 = builder();
        builder1.put(obj, obj1);
        builder1.put(obj2, obj3);
        builder1.put(obj4, obj5);
        builder1.put(obj6, obj7);
        builder1.put(obj8, obj9);
        return builder1.build();
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        int l = objectinputstream.readInt();
        if (l < 0)
        {
            throw new InvalidObjectException((new StringBuilder()).append("Invalid key count ").append(l).toString());
        }
        ImmutableMap.Builder builder1 = ImmutableMap.builder();
        int j = 0;
        for (int i = 0; i < l; i++)
        {
            Object obj = objectinputstream.readObject();
            int i1 = objectinputstream.readInt();
            if (i1 <= 0)
            {
                throw new InvalidObjectException((new StringBuilder()).append("Invalid value count ").append(i1).toString());
            }
            Object aobj[] = new Object[i1];
            for (int k = 0; k < i1; k++)
            {
                aobj[k] = objectinputstream.readObject();
            }

            builder1.put(obj, ImmutableList.copyOf(aobj));
            j += i1;
        }

        try
        {
            objectinputstream = builder1.build();
        }
        // Misplaced declaration of an exception variable
        catch (ObjectInputStream objectinputstream)
        {
            throw (InvalidObjectException)(new InvalidObjectException(objectinputstream.getMessage())).initCause(objectinputstream);
        }
        ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, objectinputstream);
        ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, j);
    }

    private void writeObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
        objectoutputstream.defaultWriteObject();
        Serialization.writeMultimap(this, objectoutputstream);
    }

    public volatile ImmutableCollection get(Object obj)
    {
        return get(obj);
    }

    public ImmutableList get(Object obj)
    {
        ImmutableList immutablelist = (ImmutableList)map.get(obj);
        obj = immutablelist;
        if (immutablelist == null)
        {
            obj = ImmutableList.of();
        }
        return ((ImmutableList) (obj));
    }

    public volatile Collection get(Object obj)
    {
        return get(obj);
    }

    public volatile List get(Object obj)
    {
        return get(obj);
    }

    public volatile ImmutableCollection removeAll(Object obj)
    {
        return removeAll(obj);
    }

    public ImmutableList removeAll(Object obj)
    {
        throw new UnsupportedOperationException();
    }

    public volatile Collection removeAll(Object obj)
    {
        return removeAll(obj);
    }

    public volatile List removeAll(Object obj)
    {
        return removeAll(obj);
    }

    public volatile ImmutableCollection replaceValues(Object obj, Iterable iterable)
    {
        return replaceValues(obj, iterable);
    }

    public ImmutableList replaceValues(Object obj, Iterable iterable)
    {
        throw new UnsupportedOperationException();
    }

    public volatile Collection replaceValues(Object obj, Iterable iterable)
    {
        return replaceValues(obj, iterable);
    }

    public volatile List replaceValues(Object obj, Iterable iterable)
    {
        return replaceValues(obj, iterable);
    }
}
