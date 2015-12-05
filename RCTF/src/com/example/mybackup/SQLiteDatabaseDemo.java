// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.mybackup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

// Referenced classes of package com.example.mybackup:
//            BooksDB

public class SQLiteDatabaseDemo extends Activity
    implements android.widget.AdapterView.OnItemClickListener
{
    public class BooksListAdapter extends BaseAdapter
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

        public BooksListAdapter(Context context, Cursor cursor)
        {
            this$0 = SQLiteDatabaseDemo.this;
            super();
            mContext = context;
            mCursor = cursor;
        }
    }


    protected static final int MENU_ADD = 1;
    protected static final int MENU_DELETE = 2;
    protected static final int MENU_UPDATE = 3;
    private int BOOK_ID;
    private EditText BookAuthor;
    private EditText BookName;
    private ListView BooksList;
    private BooksDB mBooksDB;
    private Cursor mCursor;

    public SQLiteDatabaseDemo()
    {
        mCursor = null;
        BOOK_ID = 0;
    }

    public void add()
    {
        String s = BookName.getText().toString();
        String s1 = BookAuthor.getText().toString();
        if (s.equals("") || s1.equals(""))
        {
            return;
        }
        mBooksDB.insert(s, s1);
        if (mCursor != null)
        {
            mCursor.requery();
        }
        BooksList.invalidateViews();
        BookName.setText("");
        BookAuthor.setText("");
        Toast.makeText(this, "Add Successed!", 0).show();
    }

    public void delete()
    {
        if (BOOK_ID == 0)
        {
            return;
        }
        mBooksDB.delete(BOOK_ID);
        if (mCursor != null)
        {
            mCursor.requery();
        }
        BooksList.invalidateViews();
        BookName.setText("");
        BookAuthor.setText("");
        Toast.makeText(this, "Delete Successed!", 0).show();
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030001);
        SQLiteDatabase.loadLibs(this);
        setUpViews();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "ADD");
        menu.add(0, 2, 0, "DELETE");
        menu.add(0, 2, 0, "UPDATE");
        return true;
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
label0:
        {
            if (mCursor != null)
            {
                mCursor.moveToPosition(i);
                BOOK_ID = mCursor.getInt(0);
                if (!"Flag".equals(mCursor.getString(1)))
                {
                    break label0;
                }
                BookName.setText("Guess");
                BookAuthor.setText("Flag is here!");
            }
            return;
        }
        BookName.setText(mCursor.getString(1));
        BookAuthor.setText(mCursor.getString(2));
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        super.onOptionsItemSelected(menuitem);
        menuitem.getItemId();
        JVM INSTR tableswitch 1 3: default 40
    //                   1 42
    //                   2 49
    //                   3 56;
           goto _L1 _L2 _L3 _L4
_L1:
        return true;
_L2:
        add();
        continue; /* Loop/switch isn't completed */
_L3:
        delete();
        continue; /* Loop/switch isn't completed */
_L4:
        update();
        if (true) goto _L1; else goto _L5
_L5:
    }

    public void setUpViews()
    {
        mBooksDB = new BooksDB(this);
        BookName = (EditText)findViewById(0x7f070000);
        BookAuthor = (EditText)findViewById(0x7f070001);
        BooksList = (ListView)findViewById(0x7f070002);
        BooksList.setAdapter(new BooksListAdapter(this, mCursor));
        BooksList.setOnItemClickListener(this);
    }

    public void update()
    {
        String s = BookName.getText().toString();
        String s1 = BookAuthor.getText().toString();
        if (s.equals("") || s1.equals(""))
        {
            return;
        }
        mBooksDB.update(BOOK_ID, s, s1);
        if (mCursor != null)
        {
            mCursor.requery();
        }
        BooksList.invalidateViews();
        BookName.setText("");
        BookAuthor.setText("");
        Toast.makeText(this, "Update Successed!", 0).show();
    }
}
