// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package net.sqlcipher:
//            AbstractWindowedCursor, IBulkCursor, CursorWindow, IContentObserver

public final class BulkCursorToCursorAdaptor extends AbstractWindowedCursor
{

    private static final String TAG = "BulkCursor";
    private IBulkCursor mBulkCursor;
    private String mColumns[];
    private int mCount;
    private AbstractCursor.SelfContentObserver mObserverBridge;
    private boolean mWantsAllOnMoveCalls;

    public BulkCursorToCursorAdaptor()
    {
    }

    public static int findRowIdColumnIndex(String as[])
    {
        int j = as.length;
        for (int i = 0; i < j; i++)
        {
            if (as[i].equals("_id"))
            {
                return i;
            }
        }

        return -1;
    }

    public void close()
    {
        super.close();
        try
        {
            mBulkCursor.close();
        }
        catch (RemoteException remoteexception)
        {
            Log.w("BulkCursor", "Remote process exception when closing");
        }
        mWindow = null;
    }

    public boolean commitUpdates(Map map)
    {
        if (!supportsUpdates())
        {
            Log.e("BulkCursor", "commitUpdates not supported on this cursor, did you include the _id column?");
            return false;
        }
        HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (map == null)
        {
            break MISSING_BLOCK_LABEL_36;
        }
        mUpdatedRows.putAll(map);
        if (mUpdatedRows.size() > 0)
        {
            break MISSING_BLOCK_LABEL_50;
        }
        hashmap;
        JVM INSTR monitorexit ;
        return false;
        boolean flag = mBulkCursor.updateRows(mUpdatedRows);
        if (!flag)
        {
            break MISSING_BLOCK_LABEL_81;
        }
        mUpdatedRows.clear();
        onChange(true);
        hashmap;
        JVM INSTR monitorexit ;
        return flag;
        map;
        hashmap;
        JVM INSTR monitorexit ;
        throw map;
        map;
        Log.e("BulkCursor", "Unable to commit updates because the remote process is dead");
        hashmap;
        JVM INSTR monitorexit ;
        return false;
    }

    public void copyStringToBuffer(int i, CharArrayBuffer chararraybuffer)
    {
    }

    public void deactivate()
    {
        super.deactivate();
        try
        {
            mBulkCursor.deactivate();
        }
        catch (RemoteException remoteexception)
        {
            Log.w("BulkCursor", "Remote process exception when deactivating");
        }
        mWindow = null;
    }

    public boolean deleteRow()
    {
        boolean flag1 = mBulkCursor.deleteRow(mPos);
        boolean flag;
        flag = flag1;
        if (!flag1)
        {
            break MISSING_BLOCK_LABEL_98;
        }
        mWindow = null;
        mCount = mBulkCursor.count();
        if (mPos >= mCount)
        {
            break MISSING_BLOCK_LABEL_76;
        }
        int i = mPos;
        mPos = -1;
        moveToPosition(i);
_L1:
        onChange(true);
        return flag1;
        mPos = mCount;
          goto _L1
        RemoteException remoteexception;
        remoteexception;
        Log.e("BulkCursor", "Unable to delete row because the remote process is dead");
        flag = false;
        return flag;
    }

    public String[] getColumnNames()
    {
        if (mColumns == null)
        {
            try
            {
                mColumns = mBulkCursor.getColumnNames();
            }
            catch (RemoteException remoteexception)
            {
                Log.e("BulkCursor", "Unable to fetch column names because the remote process is dead");
                return null;
            }
        }
        return mColumns;
    }

    public int getCount()
    {
        return mCount;
    }

    public Bundle getExtras()
    {
        Bundle bundle;
        try
        {
            bundle = mBulkCursor.getExtras();
        }
        catch (RemoteException remoteexception)
        {
            throw new RuntimeException(remoteexception);
        }
        return bundle;
    }

    public IContentObserver getObserver()
    {
        this;
        JVM INSTR monitorenter ;
        if (mObserverBridge == null)
        {
            mObserverBridge = new AbstractCursor.SelfContentObserver(this);
        }
        this;
        JVM INSTR monitorexit ;
        return null;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean onMove(int i, int j)
    {
        if (mWindow == null)
        {
            break MISSING_BLOCK_LABEL_91;
        }
        if (j >= mWindow.getStartPosition() && j < mWindow.getStartPosition() + mWindow.getNumRows()) goto _L2; else goto _L1
_L1:
        mWindow = mBulkCursor.getWindow(j);
_L4:
        RemoteException remoteexception;
        return mWindow != null;
_L2:
        if (!mWantsAllOnMoveCalls) goto _L4; else goto _L3
_L3:
        mBulkCursor.onMove(j);
          goto _L4
        try
        {
            mWindow = mBulkCursor.getWindow(j);
        }
        // Misplaced declaration of an exception variable
        catch (RemoteException remoteexception)
        {
            Log.e("BulkCursor", "Unable to get window because the remote process is dead");
            return false;
        }
          goto _L4
    }

    public void registerContentObserver(ContentObserver contentobserver)
    {
    }

    public void registerDataSetObserver(DataSetObserver datasetobserver)
    {
    }

    public boolean requery()
    {
        int i = mCount;
        mCount = mBulkCursor.requery(getObserver(), new CursorWindow(false));
        if (mCount == -1)
        {
            break MISSING_BLOCK_LABEL_55;
        }
        mPos = -1;
        mWindow = null;
        super.requery();
        return true;
        try
        {
            deactivate();
        }
        catch (Exception exception)
        {
            Log.e("BulkCursor", (new StringBuilder()).append("Unable to requery because the remote process exception ").append(exception.getMessage()).toString());
            deactivate();
            return false;
        }
        return false;
    }

    public Bundle respond(Bundle bundle)
    {
        try
        {
            bundle = mBulkCursor.respond(bundle);
        }
        // Misplaced declaration of an exception variable
        catch (Bundle bundle)
        {
            Log.w("BulkCursor", "respond() threw RemoteException, returning an empty bundle.", bundle);
            return Bundle.EMPTY;
        }
        return bundle;
    }

    public void set(IBulkCursor ibulkcursor)
    {
        mBulkCursor = ibulkcursor;
        try
        {
            mCount = mBulkCursor.count();
            mWantsAllOnMoveCalls = mBulkCursor.getWantsAllOnMoveCalls();
            mColumns = mBulkCursor.getColumnNames();
            mRowIdColumnIndex = findRowIdColumnIndex(mColumns);
            return;
        }
        // Misplaced declaration of an exception variable
        catch (IBulkCursor ibulkcursor)
        {
            Log.e("BulkCursor", "Setup failed because the remote process is dead");
        }
    }

    public void set(IBulkCursor ibulkcursor, int i, int j)
    {
        mBulkCursor = ibulkcursor;
        mColumns = null;
        mCount = i;
        mRowIdColumnIndex = j;
    }

    public void unregisterContentObserver(ContentObserver contentobserver)
    {
    }

    public void unregisterDataSetObserver(DataSetObserver datasetobserver)
    {
    }
}
