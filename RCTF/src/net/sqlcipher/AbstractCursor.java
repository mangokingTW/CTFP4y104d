// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.CrossProcessCursor;
import android.database.CursorWindow;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package net.sqlcipher:
//            Cursor, CursorIndexOutOfBoundsException, CursorWindow

public abstract class AbstractCursor
    implements CrossProcessCursor, Cursor
{
    protected static class SelfContentObserver extends ContentObserver
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

        public SelfContentObserver(AbstractCursor abstractcursor)
        {
            super(null);
            mCursor = new WeakReference(abstractcursor);
        }
    }


    private static final String TAG = "Cursor";
    protected boolean mClosed;
    ContentObservable mContentObservable;
    protected ContentResolver mContentResolver;
    protected Long mCurrentRowID;
    DataSetObservable mDataSetObservable;
    private Uri mNotifyUri;
    protected int mPos;
    protected int mRowIdColumnIndex;
    private ContentObserver mSelfObserver;
    private final Object mSelfObserverLock = new Object();
    private boolean mSelfObserverRegistered;
    protected HashMap mUpdatedRows;

    public AbstractCursor()
    {
        mDataSetObservable = new DataSetObservable();
        mContentObservable = new ContentObservable();
        mClosed = false;
        mPos = -1;
        mRowIdColumnIndex = -1;
        mCurrentRowID = null;
        mUpdatedRows = new HashMap();
    }

    public void abortUpdates()
    {
        synchronized (mUpdatedRows)
        {
            mUpdatedRows.clear();
        }
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    protected void checkPosition()
    {
        if (-1 == mPos || getCount() == mPos)
        {
            throw new CursorIndexOutOfBoundsException(mPos, getCount());
        } else
        {
            return;
        }
    }

    public void close()
    {
        mClosed = true;
        mContentObservable.unregisterAll();
        deactivateInternal();
    }

    public boolean commitUpdates()
    {
        return commitUpdates(null);
    }

    public boolean commitUpdates(Map map)
    {
        return false;
    }

    public void copyStringToBuffer(int i, CharArrayBuffer chararraybuffer)
    {
        String s = getString(i);
        if (s != null)
        {
            char ac[] = chararraybuffer.data;
            if (ac == null || ac.length < s.length())
            {
                chararraybuffer.data = s.toCharArray();
            } else
            {
                s.getChars(0, s.length(), ac, 0);
            }
            chararraybuffer.sizeCopied = s.length();
            return;
        } else
        {
            chararraybuffer.sizeCopied = 0;
            return;
        }
    }

    public void deactivate()
    {
        deactivateInternal();
    }

    public void deactivateInternal()
    {
        if (mSelfObserver != null)
        {
            mContentResolver.unregisterContentObserver(mSelfObserver);
            mSelfObserverRegistered = false;
        }
        mDataSetObservable.notifyInvalidated();
    }

    public boolean deleteRow()
    {
        return false;
    }

    public void fillWindow(int i, CursorWindow cursorwindow)
    {
        if (i < 0 || i >= getCount())
        {
            return;
        }
        cursorwindow.acquireReference();
        int j;
        int k;
        j = mPos;
        mPos = i - 1;
        cursorwindow.clear();
        cursorwindow.setStartPosition(i);
        k = getColumnCount();
        cursorwindow.setNumColumns(k);
_L4:
        if (!moveToNext() || !cursorwindow.allocRow()) goto _L2; else goto _L1
_L1:
        i = 0;
_L5:
        if (i >= k) goto _L4; else goto _L3
_L3:
        Exception exception;
        String s;
        try
        {
            s = getString(i);
        }
        catch (IllegalStateException illegalstateexception)
        {
            cursorwindow.releaseReference();
            return;
        }
        finally
        {
            cursorwindow.releaseReference();
        }
        if (s == null)
        {
            break MISSING_BLOCK_LABEL_110;
        }
        if (cursorwindow.putString(s, mPos, i))
        {
            break MISSING_BLOCK_LABEL_136;
        }
        cursorwindow.freeLastRow();
          goto _L4
        if (cursorwindow.putNull(mPos, i))
        {
            break MISSING_BLOCK_LABEL_136;
        }
        cursorwindow.freeLastRow();
          goto _L4
        throw exception;
        i++;
          goto _L5
_L2:
        mPos = j;
        cursorwindow.releaseReference();
        return;
          goto _L4
    }

    protected void finalize()
    {
        if (mSelfObserver != null && mSelfObserverRegistered)
        {
            mContentResolver.unregisterContentObserver(mSelfObserver);
        }
    }

    public byte[] getBlob(int i)
    {
        throw new UnsupportedOperationException("getBlob is not supported");
    }

    public int getColumnCount()
    {
        return getColumnNames().length;
    }

    public int getColumnIndex(String s)
    {
        int i = s.lastIndexOf('.');
        Object obj = s;
        if (i != -1)
        {
            obj = new Exception();
            Log.e("Cursor", (new StringBuilder()).append("requesting column name with table name -- ").append(s).toString(), ((Throwable) (obj)));
            obj = s.substring(i + 1);
        }
        s = getColumnNames();
        int k = s.length;
        for (int j = 0; j < k; j++)
        {
            if (s[j].equalsIgnoreCase(((String) (obj))))
            {
                return j;
            }
        }

        return -1;
    }

    public int getColumnIndexOrThrow(String s)
    {
        int i = getColumnIndex(s);
        if (i < 0)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("column '").append(s).append("' does not exist").toString());
        } else
        {
            return i;
        }
    }

    public String getColumnName(int i)
    {
        return getColumnNames()[i];
    }

    public abstract String[] getColumnNames();

    public abstract int getCount();

    protected DataSetObservable getDataSetObservable()
    {
        return mDataSetObservable;
    }

    public abstract double getDouble(int i);

    public Bundle getExtras()
    {
        return Bundle.EMPTY;
    }

    public abstract float getFloat(int i);

    public abstract int getInt(int i);

    public abstract long getLong(int i);

    public Uri getNotificationUri()
    {
        return mNotifyUri;
    }

    public final int getPosition()
    {
        return mPos;
    }

    public abstract short getShort(int i);

    public abstract String getString(int i);

    public abstract int getType(int i);

    protected Object getUpdatedField(int i)
    {
        return ((Map)mUpdatedRows.get(mCurrentRowID)).get(getColumnNames()[i]);
    }

    public boolean getWantsAllOnMoveCalls()
    {
        return false;
    }

    public volatile CursorWindow getWindow()
    {
        return getWindow();
    }

    public net.sqlcipher.CursorWindow getWindow()
    {
        return null;
    }

    public boolean hasUpdates()
    {
        HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        Exception exception;
        boolean flag;
        if (mUpdatedRows.size() > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        hashmap;
        JVM INSTR monitorexit ;
        return flag;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public final boolean isAfterLast()
    {
        while (getCount() == 0 || mPos == getCount()) 
        {
            return true;
        }
        return false;
    }

    public final boolean isBeforeFirst()
    {
        while (getCount() == 0 || mPos == -1) 
        {
            return true;
        }
        return false;
    }

    public boolean isClosed()
    {
        return mClosed;
    }

    protected boolean isFieldUpdated(int i)
    {
        if (mRowIdColumnIndex != -1 && mUpdatedRows.size() > 0)
        {
            Map map = (Map)mUpdatedRows.get(mCurrentRowID);
            if (map != null && map.containsKey(getColumnNames()[i]))
            {
                return true;
            }
        }
        return false;
    }

    public final boolean isFirst()
    {
        return mPos == 0 && getCount() != 0;
    }

    public final boolean isLast()
    {
        int i = getCount();
        return mPos == i - 1 && i != 0;
    }

    public abstract boolean isNull(int i);

    public final boolean move(int i)
    {
        return moveToPosition(mPos + i);
    }

    public final boolean moveToFirst()
    {
        return moveToPosition(0);
    }

    public final boolean moveToLast()
    {
        return moveToPosition(getCount() - 1);
    }

    public final boolean moveToNext()
    {
        return moveToPosition(mPos + 1);
    }

    public final boolean moveToPosition(int i)
    {
        boolean flag = false;
        int j = getCount();
        if (i >= j)
        {
            mPos = j;
        } else
        {
            if (i < 0)
            {
                mPos = -1;
                return false;
            }
            if (i == mPos)
            {
                return true;
            }
            boolean flag1 = onMove(mPos, i);
            if (!flag1)
            {
                mPos = -1;
                return flag1;
            }
            mPos = i;
            flag = flag1;
            if (mRowIdColumnIndex != -1)
            {
                mCurrentRowID = Long.valueOf(getLong(mRowIdColumnIndex));
                return flag1;
            }
        }
        return flag;
    }

    public final boolean moveToPrevious()
    {
        return moveToPosition(mPos - 1);
    }

    protected void notifyDataSetChange()
    {
        mDataSetObservable.notifyChanged();
    }

    protected void onChange(boolean flag)
    {
        Object obj = mSelfObserverLock;
        obj;
        JVM INSTR monitorenter ;
        mContentObservable.dispatchChange(flag);
        if (mNotifyUri == null || !flag)
        {
            break MISSING_BLOCK_LABEL_41;
        }
        mContentResolver.notifyChange(mNotifyUri, mSelfObserver);
        obj;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean onMove(int i, int j)
    {
        return true;
    }

    public void registerContentObserver(ContentObserver contentobserver)
    {
        mContentObservable.registerObserver(contentobserver);
    }

    public void registerDataSetObserver(DataSetObserver datasetobserver)
    {
        mDataSetObservable.registerObserver(datasetobserver);
    }

    public boolean requery()
    {
        if (mSelfObserver != null && !mSelfObserverRegistered)
        {
            mContentResolver.registerContentObserver(mNotifyUri, true, mSelfObserver);
            mSelfObserverRegistered = true;
        }
        mDataSetObservable.notifyChanged();
        return true;
    }

    public Bundle respond(Bundle bundle)
    {
        return Bundle.EMPTY;
    }

    public void setNotificationUri(ContentResolver contentresolver, Uri uri)
    {
        synchronized (mSelfObserverLock)
        {
            mNotifyUri = uri;
            mContentResolver = contentresolver;
            if (mSelfObserver != null)
            {
                mContentResolver.unregisterContentObserver(mSelfObserver);
            }
            mSelfObserver = new SelfContentObserver(this);
            mContentResolver.registerContentObserver(mNotifyUri, true, mSelfObserver);
            mSelfObserverRegistered = true;
        }
        return;
        contentresolver;
        obj;
        JVM INSTR monitorexit ;
        throw contentresolver;
    }

    public boolean supportsUpdates()
    {
        return mRowIdColumnIndex != -1;
    }

    public void unregisterContentObserver(ContentObserver contentobserver)
    {
        if (!mClosed)
        {
            mContentObservable.unregisterObserver(contentobserver);
        }
    }

    public void unregisterDataSetObserver(DataSetObserver datasetobserver)
    {
        mDataSetObservable.unregisterObserver(datasetobserver);
    }

    public boolean update(int i, Object obj)
    {
        Long long1;
        if (!supportsUpdates())
        {
            return false;
        }
        long1 = new Long(getLong(mRowIdColumnIndex));
        if (long1 == null)
        {
            throw new IllegalStateException((new StringBuilder()).append("null rowid. mRowIdColumnIndex = ").append(mRowIdColumnIndex).toString());
        }
        HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        Map map = (Map)mUpdatedRows.get(long1);
        Object obj1;
        obj1 = map;
        if (map != null)
        {
            break MISSING_BLOCK_LABEL_112;
        }
        obj1 = new HashMap();
        mUpdatedRows.put(long1, obj1);
        ((Map) (obj1)).put(getColumnNames()[i], obj);
        hashmap;
        JVM INSTR monitorexit ;
        return true;
        obj;
        hashmap;
        JVM INSTR monitorexit ;
        throw obj;
    }

    public boolean updateBlob(int i, byte abyte0[])
    {
        return update(i, abyte0);
    }

    public boolean updateDouble(int i, double d)
    {
        return update(i, Double.valueOf(d));
    }

    public boolean updateFloat(int i, float f)
    {
        return update(i, Float.valueOf(f));
    }

    public boolean updateInt(int i, int j)
    {
        return update(i, Integer.valueOf(j));
    }

    public boolean updateLong(int i, long l)
    {
        return update(i, Long.valueOf(l));
    }

    public boolean updateShort(int i, short word0)
    {
        return update(i, Short.valueOf(word0));
    }

    public boolean updateString(int i, String s)
    {
        return update(i, s);
    }

    public boolean updateToNull(int i)
    {
        return update(i, null);
    }
}
