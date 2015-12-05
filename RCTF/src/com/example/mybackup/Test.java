// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.mybackup;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Test
{

    public Test()
    {
    }

    public static String MD5(String s)
    {
        Object obj;
        String s1;
        int i;
        try
        {
            obj = MessageDigest.getInstance("MD5");
            ((MessageDigest) (obj)).update(s.getBytes());
            s = ((MessageDigest) (obj)).digest();
            obj = new StringBuffer();
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return "";
        }
        i = 0;
        if (i >= s.length)
        {
            return ((StringBuffer) (obj)).toString();
        }
        s1 = Integer.toHexString(s[i] & 0xff);
        if (s1.length() < 2)
        {
            ((StringBuffer) (obj)).append(0);
        }
        ((StringBuffer) (obj)).append(s1);
        i++;
        if (false)
        {
        } else
        {
            break MISSING_BLOCK_LABEL_29;
        }
    }

    public static String SHA(String s)
    {
        Object obj;
        String s1;
        int i;
        try
        {
            obj = MessageDigest.getInstance("SHA");
            ((MessageDigest) (obj)).update(s.getBytes());
            s = ((MessageDigest) (obj)).digest();
            obj = new StringBuffer();
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return "";
        }
        i = 0;
        if (i >= s.length)
        {
            return ((StringBuffer) (obj)).toString();
        }
        s1 = Integer.toHexString(s[i] & 0xff);
        if (s1.length() < 2)
        {
            ((StringBuffer) (obj)).append(0);
        }
        ((StringBuffer) (obj)).append(s1);
        i++;
        if (false)
        {
        } else
        {
            break MISSING_BLOCK_LABEL_29;
        }
    }

    public static String SHA1(String s)
    {
        Object obj;
        String s1;
        int i;
        try
        {
            obj = MessageDigest.getInstance("SHA-1");
            ((MessageDigest) (obj)).update(s.getBytes());
            s = ((MessageDigest) (obj)).digest();
            obj = new StringBuffer();
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return "";
        }
        i = 0;
        if (i >= s.length)
        {
            return ((StringBuffer) (obj)).toString();
        }
        s1 = Integer.toHexString(s[i] & 0xff);
        if (s1.length() < 2)
        {
            ((StringBuffer) (obj)).append(0);
        }
        ((StringBuffer) (obj)).append(s1);
        i++;
        if (false)
        {
        } else
        {
            break MISSING_BLOCK_LABEL_29;
        }
    }

    public static byte[] decryptAES(byte abyte0[], String s)
    {
        Object obj = KeyGenerator.getInstance("AES");
        ((KeyGenerator) (obj)).init(128, new SecureRandom(s.getBytes()));
        s = new SecretKeySpec(((KeyGenerator) (obj)).generateKey().getEncoded(), "AES");
        obj = Cipher.getInstance("AES");
        ((Cipher) (obj)).init(2, s);
        abyte0 = ((Cipher) (obj)).doFinal(abyte0);
        return abyte0;
        abyte0;
        abyte0.printStackTrace();
_L2:
        return null;
        abyte0;
        abyte0.printStackTrace();
        continue; /* Loop/switch isn't completed */
        abyte0;
        abyte0.printStackTrace();
        continue; /* Loop/switch isn't completed */
        abyte0;
        abyte0.printStackTrace();
        continue; /* Loop/switch isn't completed */
        abyte0;
        abyte0.printStackTrace();
        if (true) goto _L2; else goto _L1
_L1:
    }

    public static String decryptBASE64(String s)
    {
        return "";
    }

    public static byte[] encryptAES(String s, String s1)
    {
        Object obj = KeyGenerator.getInstance("AES");
        ((KeyGenerator) (obj)).init(128, new SecureRandom(s1.getBytes()));
        s1 = new SecretKeySpec(((KeyGenerator) (obj)).generateKey().getEncoded(), "AES");
        obj = Cipher.getInstance("AES");
        s = s.getBytes("utf-8");
        ((Cipher) (obj)).init(1, s1);
        s = ((Cipher) (obj)).doFinal(s);
        return s;
        s;
        s.printStackTrace();
_L2:
        return null;
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        if (true) goto _L2; else goto _L1
_L1:
    }

    public static String encryptBASE64(String s)
    {
        return "";
    }

    public static String getSign(Context context)
    {
        Iterator iterator = context.getPackageManager().getInstalledPackages(64).iterator();
        PackageInfo packageinfo;
        do
        {
            if (!iterator.hasNext())
            {
                return "";
            }
            packageinfo = (PackageInfo)iterator.next();
        } while (!packageinfo.packageName.equals(context.getPackageName()));
        return SHA1(packageinfo.signatures[0].toCharsString());
    }
}
