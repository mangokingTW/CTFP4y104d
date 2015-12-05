// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package net.sqlcipher:
//            AbstractCursor, CursorIndexOutOfBoundsException, DatabaseUtils

public class MatrixCursor extends AbstractCursor
{
    public class RowBuilder
    {

        private final int endIndex;
        private int index;
        final MatrixCursor this$0;

        public RowBuilder add(Object obj)
        {
            if (index == endIndex)
            {
                throw new CursorIndexOutOfBoundsException("No more columns left.");
            } else
            {
                Object aobj[] = data;
                int i = index;
                index = i + 1;
                aobj[i] = obj;
                return this;
            }
        }

        RowBuilder(int i, int j)
        {
            this$0 = MatrixCursor.this;
            super();
            index = i;
            endIndex = j;
        }
    }


    private final int columnCount;
    private final String columnNames[];
    private Object data[];
    private int rowCount;

    public MatrixCursor(String as[])
    {
        this(as, 16);
    }

    public MatrixCursor(String as[], int i)
    {
        rowCount = 0;
        columnNames = as;
        columnCount = as.length;
        int j = i;
        if (i < 1)
        {
            j = 1;
        }
        data = new Object[columnCount * j];
    }

    private void addRow(ArrayList arraylist, int i)
    {
        int k = arraylist.size();
        if (k != columnCount)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("columnNames.length = ").append(columnCount).append(", columnValues.size() = ").append(k).toString());
        }
        rowCount = rowCount + 1;
        Object aobj[] = data;
        for (int j = 0; j < k; j++)
        {
            aobj[i + j] = arraylist.get(j);
        }

    }

    private void ensureCapacity(int i)
    {
        if (i > data.length)
        {
            Object aobj[] = data;
            int k = data.length * 2;
            int j = k;
            if (k < i)
            {
                j = i;
            }
            data = new Object[j];
            System.arraycopy(((Object) (aobj)), 0, ((Object) (data)), 0, aobj.length);
        }
    }

    private Object get(int i)
    {
        if (i < 0 || i >= columnCount)
        {
            throw new CursorIndexOutOfBoundsException((new StringBuilder()).append("Requested column: ").append(i).append(", # of columns: ").append(columnCount).toString());
        }
        if (mPos < 0)
        {
            throw new CursorIndexOutOfBoundsException("Before first row.");
        }
        if (mPos >= rowCount)
        {
            throw new CursorIndexOutOfBoundsException("After last row.");
        } else
        {
            return data[mPos * columnCount + i];
        }
    }

    public void addRow(Iterable iterable)
    {
        int i = rowCount * columnCount;
        int j = i + columnCount;
        ensureCapacity(j);
        if (iterable instanceof ArrayList)
        {
            addRow((ArrayList)iterable, i);
            return;
        }
        Object aobj[] = data;
        for (iterable = iterable.iterator(); iterable.hasNext();)
        {
            Object obj = iterable.next();
            if (i == j)
            {
                throw new IllegalArgumentException("columnValues.size() > columnNames.length");
            }
            aobj[i] = obj;
            i++;
        }

        if (i != j)
        {
            throw new IllegalArgumentException("columnValues.size() < columnNames.length");
        } else
        {
            rowCount = rowCount + 1;
            return;
        }
    }

    public void addRow(Object aobj[])
    {
        if (aobj.length != columnCount)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("columnNames.length = ").append(columnCount).append(", columnValues.length = ").append(aobj.length).toString());
        } else
        {
            int i = rowCount;
            rowCount = i + 1;
            i *= columnCount;
            ensureCapacity(columnCount + i);
            System.arraycopy(((Object) (aobj)), 0, ((Object) (data)), i, columnCount);
            return;
        }
    }

    public String[] getColumnNames()
    {
        return columnNames;
    }

    public int getCount()
    {
        return rowCount;
    }

    public double getDouble(int i)
    {
        Object obj = get(i);
        if (obj == null)
        {
            return 0.0D;
        }
        if (obj instanceof Number)
        {
            return ((Number)obj).doubleValue();
        } else
        {
            return Double.parseDouble(obj.toString());
        }
    }

    public float getFloat(int i)
    {
        Object obj = get(i);
        if (obj == null)
        {
            return 0.0F;
        }
        if (obj instanceof Number)
        {
            return ((Number)obj).floatValue();
        } else
        {
            return Float.parseFloat(obj.toString());
        }
    }

    public int getInt(int i)
    {
        Object obj = get(i);
        if (obj == null)
        {
            return 0;
        }
        if (obj instanceof Number)
        {
            return ((Number)obj).intValue();
        } else
        {
            return Integer.parseInt(obj.toString());
        }
    }

    public long getLong(int i)
    {
        Object obj = get(i);
        if (obj == null)
        {
            return 0L;
        }
        if (obj instanceof Number)
        {
            return ((Number)obj).longValue();
        } else
        {
            return Long.parseLong(obj.toString());
        }
    }

    public short getShort(int i)
    {
        Object obj = get(i);
        if (obj == null)
        {
            return 0;
        }
        if (obj instanceof Number)
        {
            return ((Number)obj).shortValue();
        } else
        {
            return Short.parseShort(obj.toString());
        }
    }

    public String getString(int i)
    {
        Object obj = get(i);
        if (obj == null)
        {
            return null;
        } else
        {
            return obj.toString();
        }
    }

    public int getType(int i)
    {
        return DatabaseUtils.getTypeOfObject(get(i));
    }

    public boolean isNull(int i)
    {
        return get(i) == null;
    }

    public RowBuilder newRow()
    {
        rowCount = rowCount + 1;
        int i = rowCount * columnCount;
        ensureCapacity(i);
        return new RowBuilder(i - columnCount, i);
    }

}
