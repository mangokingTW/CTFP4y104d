// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

// Referenced classes of package com.google.common.collect:
//            StandardTable, RowSortedTable

class StandardRowSortedTable extends StandardTable
    implements RowSortedTable
{
    private class RowKeySortedSet extends StandardTable.RowKeySet
        implements SortedSet
    {

        final StandardRowSortedTable this$0;

        public Comparator comparator()
        {
            return sortedBackingMap().comparator();
        }

        public Object first()
        {
            return sortedBackingMap().firstKey();
        }

        public SortedSet headSet(Object obj)
        {
            Preconditions.checkNotNull(obj);
            return (new StandardRowSortedTable(sortedBackingMap().headMap(obj), factory)).rowKeySet();
        }

        public Object last()
        {
            return sortedBackingMap().lastKey();
        }

        public SortedSet subSet(Object obj, Object obj1)
        {
            Preconditions.checkNotNull(obj);
            Preconditions.checkNotNull(obj1);
            return (new StandardRowSortedTable(sortedBackingMap().subMap(obj, obj1), factory)).rowKeySet();
        }

        public SortedSet tailSet(Object obj)
        {
            Preconditions.checkNotNull(obj);
            return (new StandardRowSortedTable(sortedBackingMap().tailMap(obj), factory)).rowKeySet();
        }

        private RowKeySortedSet()
        {
            this$0 = StandardRowSortedTable.this;
            super(StandardRowSortedTable.this);
        }

    }

    private class RowSortedMap extends StandardTable.RowMap
        implements SortedMap
    {

        final StandardRowSortedTable this$0;

        public Comparator comparator()
        {
            return sortedBackingMap().comparator();
        }

        public Object firstKey()
        {
            return sortedBackingMap().firstKey();
        }

        public SortedMap headMap(Object obj)
        {
            Preconditions.checkNotNull(obj);
            return (new StandardRowSortedTable(sortedBackingMap().headMap(obj), factory)).rowMap();
        }

        public Object lastKey()
        {
            return sortedBackingMap().lastKey();
        }

        public SortedMap subMap(Object obj, Object obj1)
        {
            Preconditions.checkNotNull(obj);
            Preconditions.checkNotNull(obj1);
            return (new StandardRowSortedTable(sortedBackingMap().subMap(obj, obj1), factory)).rowMap();
        }

        public SortedMap tailMap(Object obj)
        {
            Preconditions.checkNotNull(obj);
            return (new StandardRowSortedTable(sortedBackingMap().tailMap(obj), factory)).rowMap();
        }

        private RowSortedMap()
        {
            this$0 = StandardRowSortedTable.this;
            super(StandardRowSortedTable.this);
        }

    }


    private static final long serialVersionUID = 0L;
    private transient SortedSet rowKeySet;
    private transient RowSortedMap rowMap;

    StandardRowSortedTable(SortedMap sortedmap, Supplier supplier)
    {
        super(sortedmap, supplier);
    }

    private SortedMap sortedBackingMap()
    {
        return (SortedMap)backingMap;
    }

    public volatile Set rowKeySet()
    {
        return rowKeySet();
    }

    public SortedSet rowKeySet()
    {
        SortedSet sortedset = rowKeySet;
        Object obj = sortedset;
        if (sortedset == null)
        {
            obj = new RowKeySortedSet();
            rowKeySet = ((SortedSet) (obj));
        }
        return ((SortedSet) (obj));
    }

    public volatile Map rowMap()
    {
        return rowMap();
    }

    public SortedMap rowMap()
    {
        RowSortedMap rowsortedmap1 = rowMap;
        RowSortedMap rowsortedmap = rowsortedmap1;
        if (rowsortedmap1 == null)
        {
            rowsortedmap = new RowSortedMap();
            rowMap = rowsortedmap;
        }
        return rowsortedmap;
    }

}
