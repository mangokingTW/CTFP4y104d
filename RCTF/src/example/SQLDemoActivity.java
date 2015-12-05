// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package example;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import net.sqlcipher.database.SQLiteDatabase;

// Referenced classes of package example:
//            EventDataSQLHelper

public class SQLDemoActivity extends Activity
{

    EventDataSQLHelper eventsData;

    public SQLDemoActivity()
    {
    }

    private void addEvent(String s, SQLiteDatabase sqlitedatabase)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("time", Long.valueOf(System.currentTimeMillis()));
        contentvalues.put("title", s);
        sqlitedatabase.insert("events", null, contentvalues);
    }

    private Cursor getEvents(SQLiteDatabase sqlitedatabase)
    {
        sqlitedatabase = sqlitedatabase.query("events", null, null, null, null, null, null);
        startManagingCursor(sqlitedatabase);
        return sqlitedatabase;
    }

    private void showEvents(Cursor cursor)
    {
        StringBuilder stringbuilder = new StringBuilder("Saved Events:\n\n");
        String s;
        long l;
        long l1;
        for (; cursor.moveToNext(); stringbuilder.append((new StringBuilder()).append(l).append(": ").append(l1).append(": ").append(s).append("\n").toString()))
        {
            l = cursor.getLong(0);
            l1 = cursor.getLong(1);
            s = cursor.getString(2);
        }

        Log.i("sqldemo", stringbuilder.toString());
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        SQLiteDatabase.loadLibs(this);
        eventsData = new EventDataSQLHelper(this);
        bundle = eventsData.getWritableDatabase("foo123");
        for (int i = 1; i < 100; i++)
        {
            addEvent((new StringBuilder()).append("Hello Android Event: ").append(i).toString(), bundle);
        }

        bundle.close();
        bundle = eventsData.getReadableDatabase("foo123");
        showEvents(getEvents(bundle));
        bundle.close();
    }

    public void onDestroy()
    {
        eventsData.close();
    }
}
