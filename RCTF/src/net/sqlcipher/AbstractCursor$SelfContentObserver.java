// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.database.ContentObserver;
import java.lang.ref.WeakReference;

// Referenced classes of package net.sqlcipher:
//            AbstractCursor

protected static class mCursor extends ContentObserver
{

    WeakReference mCursor;

    public boolean deliverSelfNotifications()
    {
        return false;
    }

    public void onChange(boolean flag)
    {
        AbstractCursor abstractcursor = (AbstractCursor)mCursor.get();
        if (abstractcursor != null)
        {
            abstractcursor.onChange(false);
        }
    }

    public (AbstractCursor abstractcursor)
    {
        super(null);
        mCursor = new WeakReference(abstractcursor);
    }
}
