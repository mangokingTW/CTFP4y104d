// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.mybackup;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

// Referenced classes of package com.example.mybackup:
//            Test

public class BooksDB extends SQLiteOpenHelper
{

    public static final String BOOK_AUTHOR = "book_author";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_NAME = "book_name";
    private static final String DATABASE_NAME = "BOOKS.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "books_table";
    private SQLiteDatabase db;
    private SQLiteDatabase dbr;
    private String k;

    public BooksDB(Context context)
    {
        super(context, "BOOKS.db", null, 1);
        k = Test.getSign(context);
        db = getWritableDatabase(k);
        dbr = getReadableDatabase(k);
    }

    public void delete(int i)
    {
        String s = Integer.toString(i);
        db.delete("books_table", "book_id = ?", new String[] {
            s
        });
    }

    public long insert(String s, String s1)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("book_name", s);
        contentvalues.put("book_author", s1);
        return db.insert("books_table", null, contentvalues);
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        sqlitedatabase.execSQL("CREATE TABLE books_table (book_id INTEGER primary key autoincrement, book_name text, book_author text);");
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
    {
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS books_table");
        onCreate(sqlitedatabase);
    }

    public Cursor select()
    {
        return dbr.query("books_table", null, null, null, null, null, null);
    }

    public void update(int i, String s, String s1)
    {
        String s2 = Integer.toString(i);
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("book_name", s);
        contentvalues.put("book_author", s1);
        db.update("books_table", contentvalues, "book_id = ?", new String[] {
            s2
        });
    }
}
