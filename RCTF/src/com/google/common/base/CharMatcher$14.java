// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.base;


// Referenced classes of package com.google.common.base:
//            CharMatcher

class it> extends CharMatcher
{

    final CharMatcher this$0;
    final CharMatcher val$original;

    public volatile boolean apply(Object obj)
    {
        return super.apply((Character)obj);
    }

    public int countIn(CharSequence charsequence)
    {
        return charsequence.length() - val$original.countIn(charsequence);
    }

    public boolean matches(char c)
    {
        return !val$original.matches(c);
    }

    public boolean matchesAllOf(CharSequence charsequence)
    {
        return val$original.matchesNoneOf(charsequence);
    }

    public boolean matchesNoneOf(CharSequence charsequence)
    {
        return val$original.matchesAllOf(charsequence);
    }

    public CharMatcher negate()
    {
        return val$original;
    }

    ()
    {
        this$0 = final_charmatcher;
        val$original = CharMatcher.this;
        super();
    }
}
