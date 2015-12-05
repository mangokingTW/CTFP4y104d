// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

// Referenced classes of package net.sqlcipher:
//            IContentObserver

public static abstract class attachInterface extends Binder
    implements IContentObserver
{
    private static class Proxy
        implements IContentObserver
    {

        private IBinder mRemote;

        public IBinder asBinder()
        {
            return mRemote;
        }

        public String getInterfaceDescriptor()
        {
            return "net.sqlcipher.IContentObserver";
        }

        public void onChange(boolean flag)
            throws RemoteException
        {
            Parcel parcel;
            int i;
            i = 1;
            parcel = Parcel.obtain();
            parcel.writeInterfaceToken("net.sqlcipher.IContentObserver");
            if (!flag)
            {
                i = 0;
            }
            parcel.writeInt(i);
            mRemote.transact(1, parcel, null, 1);
            parcel.recycle();
            return;
            Exception exception;
            exception;
            parcel.recycle();
            throw exception;
        }

        Proxy(IBinder ibinder)
        {
            mRemote = ibinder;
        }
    }


    private static final String DESCRIPTOR = "net.sqlcipher.IContentObserver";
    static final int TRANSACTION_onChange = 1;

    public static IContentObserver asInterface(IBinder ibinder)
    {
        if (ibinder == null)
        {
            return null;
        }
        android.os.IInterface iinterface = ibinder.queryLocalInterface("net.sqlcipher.IContentObserver");
        if (iinterface != null && (iinterface instanceof IContentObserver))
        {
            return (IContentObserver)iinterface;
        } else
        {
            return new Proxy(ibinder);
        }
    }

    public IBinder asBinder()
    {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
        throws RemoteException
    {
        switch (i)
        {
        default:
            return super.onTransact(i, parcel, parcel1, j);

        case 1598968902: 
            parcel1.writeString("net.sqlcipher.IContentObserver");
            return true;

        case 1: // '\001'
            parcel.enforceInterface("net.sqlcipher.IContentObserver");
            break;
        }
        boolean flag;
        if (parcel.readInt() != 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        onChange(flag);
        return true;
    }

    public Proxy.mRemote()
    {
        attachInterface(this, "net.sqlcipher.IContentObserver");
    }
}
