// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.os.Process;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteCursor, SQLiteQuery

private final class mThreadState
    implements Runnable
{

    private final int mThreadState;
    final SQLiteCursor this$0;

    private void sendMessage()
    {
        if (mNotificationHandler != null)
        {
            mNotificationHandler.sendEmptyMessage(1);
            SQLiteCursor.access$002(SQLiteCursor.this, false);
            return;
        } else
        {
            SQLiteCursor.access$002(SQLiteCursor.this, true);
            return;
        }
    }

    public void run()
    {
        net.sqlcipher.CursorWindow cursorwindow;
        cursorwindow = SQLiteCursor.access$100(SQLiteCursor.this);
        Process.setThreadPriority(Process.myTid(), 10);
_L1:
        Exception exception1;
        SQLiteCursor.access$200(SQLiteCursor.this).lock();
        if (SQLiteCursor.access$300(SQLiteCursor.this) != mThreadState)
        {
            SQLiteCursor.access$200(SQLiteCursor.this).unlock();
            return;
        }
        int i;
        try
        {
            i = SQLiteCursor.access$600(SQLiteCursor.this).fillWindow(cursorwindow, SQLiteCursor.access$400(SQLiteCursor.this), SQLiteCursor.access$500(SQLiteCursor.this));
        }
        catch (Exception exception)
        {
            SQLiteCursor.access$200(SQLiteCursor.this).unlock();
            return;
        }
        finally
        {
            SQLiteCursor.access$200(SQLiteCursor.this).unlock();
        }
        if (i == 0)
        {
            break MISSING_BLOCK_LABEL_142;
        }
        if (i != -1)
        {
            break MISSING_BLOCK_LABEL_118;
        }
        SQLiteCursor.access$512(SQLiteCursor.this, SQLiteCursor.access$400(SQLiteCursor.this));
        sendMessage();
        SQLiteCursor.access$200(SQLiteCursor.this).unlock();
          goto _L1
        SQLiteCursor.access$502(SQLiteCursor.this, i);
        sendMessage();
        SQLiteCursor.access$200(SQLiteCursor.this).unlock();
        return;
        SQLiteCursor.access$200(SQLiteCursor.this).unlock();
        return;
        throw exception1;
    }

    tificationHandler(int i)
    {
        this$0 = SQLiteCursor.this;
        super();
        mThreadState = i;
    }
}
