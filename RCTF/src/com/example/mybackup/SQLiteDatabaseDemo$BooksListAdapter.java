// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.mybackup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.sqlcipher.Cursor;

// Referenced classes of package com.example.mybackup:
//            SQLiteDatabaseDemo

public class mCursor extends BaseAdapter
{

    private Context mContext;
    private Cursor mCursor;
    final SQLiteDatabaseDemo this$0;

    public int getCount()
    {
        if (mCursor == null)
        {
            return 0;
        } else
        {
            return mCursor.getCount();
        }
    }

    public Object getItem(int i)
    {
        return null;
    }

    public long getItemId(int i)
    {
        return 0L;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        view = new TextView(mContext);
        if (mCursor == null)
        {
            return view;
        }
        mCursor.moveToPosition(i);
        if ("Flag".equals(mCursor.getString(1)))
        {
            view.setText("Flag is here!");
            return view;
        } else
        {
            view.setText((new StringBuilder(String.valueOf(mCursor.getString(1)))).append("___").append(mCursor.getString(2)).toString());
            return view;
        }
    }

    public (Context context, Cursor cursor)
    {
        this$0 = SQLiteDatabaseDemo.this;
        super();
        mContext = context;
        mCursor = cursor;
    }
}
