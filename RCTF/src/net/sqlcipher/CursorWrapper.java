// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;


// Referenced classes of package net.sqlcipher:
//            Cursor

public class CursorWrapper extends android.database.CursorWrapper
    implements Cursor
{

    private final Cursor mCursor;

    public CursorWrapper(Cursor cursor)
    {
        super(cursor);
        mCursor = cursor;
    }

    public int getType(int i)
    {
        return mCursor.getType(i);
    }
}
