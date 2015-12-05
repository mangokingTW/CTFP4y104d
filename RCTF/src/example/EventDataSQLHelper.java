// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package example;

import android.content.Context;
import android.util.Log;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class EventDataSQLHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE = "events";
    public static final String TIME = "time";
    public static final String TITLE = "title";

    public EventDataSQLHelper(Context context)
    {
        super(context, "events.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        Log.d("EventsData", (new StringBuilder()).append("onCreate: ").append("create table events( _id integer primary key autoincrement, time integer, title text not null);").toString());
        sqlitedatabase.execSQL("create table events( _id integer primary key autoincrement, time integer, title text not null);");
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
    {
        if (i < j)
        {
            String s = null;
            if (i == 1)
            {
                s = "alter table events add note text;";
            }
            if (i == 2)
            {
                s = "";
            }
            Log.d("EventsData", (new StringBuilder()).append("onUpgrade\t: ").append(s).toString());
            if (s != null)
            {
                sqlitedatabase.execSQL(s);
                return;
            }
        }
    }
}
