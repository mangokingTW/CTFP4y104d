// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.Map;

// Referenced classes of package net.sqlcipher:
//            CursorWindow, IContentObserver

public interface IBulkCursor
    extends IInterface
{

    public static final int CLOSE_TRANSACTION = 12;
    public static final int COUNT_TRANSACTION = 2;
    public static final int DEACTIVATE_TRANSACTION = 6;
    public static final int DELETE_ROW_TRANSACTION = 5;
    public static final int GET_COLUMN_NAMES_TRANSACTION = 3;
    public static final int GET_CURSOR_WINDOW_TRANSACTION = 1;
    public static final int GET_EXTRAS_TRANSACTION = 10;
    public static final int ON_MOVE_TRANSACTION = 8;
    public static final int REQUERY_TRANSACTION = 7;
    public static final int RESPOND_TRANSACTION = 11;
    public static final int UPDATE_ROWS_TRANSACTION = 4;
    public static final int WANTS_ON_MOVE_TRANSACTION = 9;
    public static final String descriptor = "android.content.IBulkCursor";

    public abstract void close()
        throws RemoteException;

    public abstract int count()
        throws RemoteException;

    public abstract void deactivate()
        throws RemoteException;

    public abstract boolean deleteRow(int i)
        throws RemoteException;

    public abstract String[] getColumnNames()
        throws RemoteException;

    public abstract Bundle getExtras()
        throws RemoteException;

    public abstract boolean getWantsAllOnMoveCalls()
        throws RemoteException;

    public abstract CursorWindow getWindow(int i)
        throws RemoteException;

    public abstract void onMove(int i)
        throws RemoteException;

    public abstract int requery(IContentObserver icontentobserver, CursorWindow cursorwindow)
        throws RemoteException;

    public abstract Bundle respond(Bundle bundle)
        throws RemoteException;

    public abstract boolean updateRows(Map map)
        throws RemoteException;
}
