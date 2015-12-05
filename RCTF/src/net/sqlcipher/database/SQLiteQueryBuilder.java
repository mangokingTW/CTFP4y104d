// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.text.TextUtils;
import android.util.Log;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseUtils;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase

public class SQLiteQueryBuilder
{

    private static final String TAG = "SQLiteQueryBuilder";
    private static final Pattern sLimitPattern = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");
    private boolean mDistinct;
    private SQLiteDatabase.CursorFactory mFactory;
    private Map mProjectionMap;
    private boolean mStrictProjectionMap;
    private String mTables;
    private StringBuilder mWhereClause;

    public SQLiteQueryBuilder()
    {
        mProjectionMap = null;
        mTables = "";
        mWhereClause = null;
        mDistinct = false;
        mFactory = null;
    }

    private static void appendClause(StringBuilder stringbuilder, String s, String s1)
    {
        if (!TextUtils.isEmpty(s1))
        {
            stringbuilder.append(s);
            stringbuilder.append(s1);
        }
    }

    private static void appendClauseEscapeClause(StringBuilder stringbuilder, String s, String s1)
    {
        if (!TextUtils.isEmpty(s1))
        {
            stringbuilder.append(s);
            DatabaseUtils.appendEscapedSQLString(stringbuilder, s1);
        }
    }

    public static void appendColumns(StringBuilder stringbuilder, String as[])
    {
        int j = as.length;
        for (int i = 0; i < j; i++)
        {
            String s = as[i];
            if (s == null)
            {
                continue;
            }
            if (i > 0)
            {
                stringbuilder.append(", ");
            }
            stringbuilder.append(s);
        }

        stringbuilder.append(' ');
    }

    public static String buildQueryString(boolean flag, String s, String as[], String s1, String s2, String s3, String s4, String s5)
    {
        if (TextUtils.isEmpty(s2) && !TextUtils.isEmpty(s3))
        {
            throw new IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
        }
        if (!TextUtils.isEmpty(s5) && !sLimitPattern.matcher(s5).matches())
        {
            throw new IllegalArgumentException((new StringBuilder()).append("invalid LIMIT clauses:").append(s5).toString());
        }
        StringBuilder stringbuilder = new StringBuilder(120);
        stringbuilder.append("SELECT ");
        if (flag)
        {
            stringbuilder.append("DISTINCT ");
        }
        if (as != null && as.length != 0)
        {
            appendColumns(stringbuilder, as);
        } else
        {
            stringbuilder.append("* ");
        }
        stringbuilder.append("FROM ");
        stringbuilder.append(s);
        appendClause(stringbuilder, " WHERE ", s1);
        appendClause(stringbuilder, " GROUP BY ", s2);
        appendClause(stringbuilder, " HAVING ", s3);
        appendClause(stringbuilder, " ORDER BY ", s4);
        appendClause(stringbuilder, " LIMIT ", s5);
        return stringbuilder.toString();
    }

    private String[] computeProjection(String as[])
    {
        if (as == null || as.length <= 0) goto _L2; else goto _L1
_L1:
        Object obj;
        if (mProjectionMap != null)
        {
            String as1[] = new String[as.length];
            int k = as.length;
            int i = 0;
            do
            {
                obj = as1;
                if (i >= k)
                {
                    break;
                }
                obj = as[i];
                String s = (String)mProjectionMap.get(obj);
                if (s != null)
                {
                    as1[i] = s;
                } else
                if (!mStrictProjectionMap && (((String) (obj)).contains(" AS ") || ((String) (obj)).contains(" as ")))
                {
                    as1[i] = ((String) (obj));
                } else
                {
                    throw new IllegalArgumentException((new StringBuilder()).append("Invalid column ").append(as[i]).toString());
                }
                i++;
            } while (true);
        } else
        {
            obj = as;
        }
_L4:
        return ((String []) (obj));
_L2:
label0:
        {
            if (mProjectionMap == null)
            {
                break label0;
            }
            obj = mProjectionMap.entrySet();
            as = new String[((Set) (obj)).size()];
            Iterator iterator = ((Set) (obj)).iterator();
            int j = 0;
            do
            {
                obj = as;
                if (!iterator.hasNext())
                {
                    break;
                }
                obj = (java.util.Map.Entry)iterator.next();
                if (!((String)((java.util.Map.Entry) (obj)).getKey()).equals("_count"))
                {
                    as[j] = (String)((java.util.Map.Entry) (obj)).getValue();
                    j++;
                }
            } while (true);
        }
        if (true) goto _L4; else goto _L3
_L3:
        return null;
    }

    public void appendWhere(CharSequence charsequence)
    {
        if (mWhereClause == null)
        {
            mWhereClause = new StringBuilder(charsequence.length() + 16);
        }
        if (mWhereClause.length() == 0)
        {
            mWhereClause.append('(');
        }
        mWhereClause.append(charsequence);
    }

    public void appendWhereEscapeString(String s)
    {
        if (mWhereClause == null)
        {
            mWhereClause = new StringBuilder(s.length() + 16);
        }
        if (mWhereClause.length() == 0)
        {
            mWhereClause.append('(');
        }
        DatabaseUtils.appendEscapedSQLString(mWhereClause, s);
    }

    public String buildQuery(String as[], String s, String as1[], String s1, String s2, String s3, String s4)
    {
        as = computeProjection(as);
        as1 = new StringBuilder();
        boolean flag;
        if (mWhereClause != null && mWhereClause.length() > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (flag)
        {
            as1.append(mWhereClause.toString());
            as1.append(')');
        }
        if (s != null && s.length() > 0)
        {
            if (flag)
            {
                as1.append(" AND ");
            }
            as1.append('(');
            as1.append(s);
            as1.append(')');
        }
        return buildQueryString(mDistinct, mTables, as, as1.toString(), s1, s2, s3, s4);
    }

    public String buildUnionQuery(String as[], String s, String s1)
    {
        StringBuilder stringbuilder = new StringBuilder(128);
        int j = as.length;
        String s2;
        int i;
        if (mDistinct)
        {
            s2 = " UNION ";
        } else
        {
            s2 = " UNION ALL ";
        }
        for (i = 0; i < j; i++)
        {
            if (i > 0)
            {
                stringbuilder.append(s2);
            }
            stringbuilder.append(as[i]);
        }

        appendClause(stringbuilder, " ORDER BY ", s);
        appendClause(stringbuilder, " LIMIT ", s1);
        return stringbuilder.toString();
    }

    public String buildUnionSubQuery(String s, String as[], Set set, int i, String s1, String s2, String as1[], 
            String s3, String s4)
    {
        int k = as.length;
        String as2[] = new String[k];
        int j = 0;
        while (j < k) 
        {
            String s5 = as[j];
            if (s5.equals(s))
            {
                as2[j] = (new StringBuilder()).append("'").append(s1).append("' AS ").append(s).toString();
            } else
            if (j <= i || set.contains(s5))
            {
                as2[j] = s5;
            } else
            {
                as2[j] = (new StringBuilder()).append("NULL AS ").append(s5).toString();
            }
            j++;
        }
        return buildQuery(as2, s2, as1, s3, s4, null, null);
    }

    public String getTables()
    {
        return mTables;
    }

    public Cursor query(SQLiteDatabase sqlitedatabase, String as[], String s, String as1[], String s1, String s2, String s3)
    {
        return query(sqlitedatabase, as, s, as1, s1, s2, s3, null);
    }

    public Cursor query(SQLiteDatabase sqlitedatabase, String as[], String s, String as1[], String s1, String s2, String s3, 
            String s4)
    {
        if (mTables == null)
        {
            return null;
        }
        as = buildQuery(as, s, as1, s1, s2, s3, s4);
        if (Log.isLoggable("SQLiteQueryBuilder", 3))
        {
            Log.d("SQLiteQueryBuilder", (new StringBuilder()).append("Performing query: ").append(as).toString());
        }
        return sqlitedatabase.rawQueryWithFactory(mFactory, as, as1, SQLiteDatabase.findEditTable(mTables));
    }

    public void setCursorFactory(SQLiteDatabase.CursorFactory cursorfactory)
    {
        mFactory = cursorfactory;
    }

    public void setDistinct(boolean flag)
    {
        mDistinct = flag;
    }

    public void setProjectionMap(Map map)
    {
        mProjectionMap = map;
    }

    public void setStrictProjectionMap(boolean flag)
    {
        mStrictProjectionMap = flag;
    }

    public void setTables(String s)
    {
        mTables = s;
    }

}
