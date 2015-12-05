// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;


// Referenced classes of package net.sqlcipher:
//            MatrixCursor, CursorIndexOutOfBoundsException

public class endIndex
{

    private final int endIndex;
    private int index;
    final MatrixCursor this$0;

    public endIndex add(Object obj)
    {
        if (index == endIndex)
        {
            throw new CursorIndexOutOfBoundsException("No more columns left.");
        } else
        {
            Object aobj[] = MatrixCursor.access$000(MatrixCursor.this);
            int i = index;
            index = i + 1;
            aobj[i] = obj;
            return this;
        }
    }

    ception(int i, int j)
    {
        this$0 = MatrixCursor.this;
        super();
        index = i;
        endIndex = j;
    }
}
