// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.database.CrossProcessCursor;
import android.database.CursorWindow;

// Referenced classes of package net.sqlcipher:
//            CursorWrapper, Cursor

public class CrossProcessCursorWrapper extends CursorWrapper
    implements CrossProcessCursor
{

    public CrossProcessCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public void fillWindow(int i, CursorWindow cursorwindow)
    {
        if (i < 0 || i > getCount())
        {
            return;
        }
        cursorwindow.acquireReference();
        int j;
        moveToPosition(i - 1);
        cursorwindow.clear();
        cursorwindow.setStartPosition(i);
        j = getColumnCount();
        cursorwindow.setNumColumns(j);
_L4:
        if (!moveToNext() || !cursorwindow.allocRow()) goto _L2; else goto _L1
_L1:
        i = 0;
_L7:
        if (i >= j) goto _L4; else goto _L3
_L3:
        String s = getString(i);
        if (s == null) goto _L6; else goto _L5
_L5:
        if (cursorwindow.putString(s, getPosition(), i))
        {
            break MISSING_BLOCK_LABEL_131;
        }
        cursorwindow.freeLastRow();
          goto _L4
        Object obj;
        obj;
_L2:
        cursorwindow.releaseReference();
        return;
_L6:
        if (cursorwindow.putNull(getPosition(), i))
        {
            break MISSING_BLOCK_LABEL_131;
        }
        cursorwindow.freeLastRow();
          goto _L4
        obj;
        cursorwindow.releaseReference();
        throw obj;
        i++;
          goto _L7
    }

    public CursorWindow getWindow()
    {
        return null;
    }

    public boolean onMove(int i, int j)
    {
        return true;
    }
}
