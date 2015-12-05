.class public Lcom/example/mybackup/Test;
.super Ljava/lang/Object;
.source "Test.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 23
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static MD5(Ljava/lang/String;)Ljava/lang/String;
    .locals 8
    .param p0, "input"    # Ljava/lang/String;

    .prologue
    .line 90
    :try_start_0
    const-string v6, "MD5"

    invoke-static {v6}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v4

    .line 92
    .local v4, "mdInst":Ljava/security/MessageDigest;
    invoke-virtual {p0}, Ljava/lang/String;->getBytes()[B

    move-result-object v6

    invoke-virtual {v4, v6}, Ljava/security/MessageDigest;->update([B)V

    .line 94
    invoke-virtual {v4}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v3

    .line 96
    .local v3, "md":[B
    new-instance v1, Ljava/lang/StringBuffer;

    invoke-direct {v1}, Ljava/lang/StringBuffer;-><init>()V

    .line 98
    .local v1, "hexString":Ljava/lang/StringBuffer;
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_0
    array-length v6, v3

    if-lt v2, v6, :cond_0

    .line 105
    invoke-virtual {v1}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v6

    .line 109
    .end local v1    # "hexString":Ljava/lang/StringBuffer;
    .end local v2    # "i":I
    .end local v3    # "md":[B
    .end local v4    # "mdInst":Ljava/security/MessageDigest;
    :goto_1
    return-object v6

    .line 99
    .restart local v1    # "hexString":Ljava/lang/StringBuffer;
    .restart local v2    # "i":I
    .restart local v3    # "md":[B
    .restart local v4    # "mdInst":Ljava/security/MessageDigest;
    :cond_0
    aget-byte v6, v3, v2

    and-int/lit16 v6, v6, 0xff

    invoke-static {v6}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v5

    .line 100
    .local v5, "shaHex":Ljava/lang/String;
    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v6

    const/4 v7, 0x2

    if-ge v6, v7, :cond_1

    .line 101
    const/4 v6, 0x0

    invoke-virtual {v1, v6}, Ljava/lang/StringBuffer;->append(I)Ljava/lang/StringBuffer;

    .line 103
    :cond_1
    invoke-virtual {v1, v5}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0

    .line 98
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 106
    .end local v1    # "hexString":Ljava/lang/StringBuffer;
    .end local v2    # "i":I
    .end local v3    # "md":[B
    .end local v4    # "mdInst":Ljava/security/MessageDigest;
    .end local v5    # "shaHex":Ljava/lang/String;
    :catch_0
    move-exception v0

    .line 107
    .local v0, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v0}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    .line 109
    const-string v6, ""

    goto :goto_1
.end method

.method public static SHA(Ljava/lang/String;)Ljava/lang/String;
    .locals 8
    .param p0, "decript"    # Ljava/lang/String;

    .prologue
    .line 52
    :try_start_0
    const-string v6, "SHA"

    invoke-static {v6}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    .line 53
    .local v0, "digest":Ljava/security/MessageDigest;
    invoke-virtual {p0}, Ljava/lang/String;->getBytes()[B

    move-result-object v6

    invoke-virtual {v0, v6}, Ljava/security/MessageDigest;->update([B)V

    .line 54
    invoke-virtual {v0}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v4

    .line 56
    .local v4, "messageDigest":[B
    new-instance v2, Ljava/lang/StringBuffer;

    invoke-direct {v2}, Ljava/lang/StringBuffer;-><init>()V

    .line 58
    .local v2, "hexString":Ljava/lang/StringBuffer;
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    array-length v6, v4

    if-lt v3, v6, :cond_0

    .line 65
    invoke-virtual {v2}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v6

    .line 70
    .end local v0    # "digest":Ljava/security/MessageDigest;
    .end local v2    # "hexString":Ljava/lang/StringBuffer;
    .end local v3    # "i":I
    .end local v4    # "messageDigest":[B
    :goto_1
    return-object v6

    .line 59
    .restart local v0    # "digest":Ljava/security/MessageDigest;
    .restart local v2    # "hexString":Ljava/lang/StringBuffer;
    .restart local v3    # "i":I
    .restart local v4    # "messageDigest":[B
    :cond_0
    aget-byte v6, v4, v3

    and-int/lit16 v6, v6, 0xff

    invoke-static {v6}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v5

    .line 60
    .local v5, "shaHex":Ljava/lang/String;
    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v6

    const/4 v7, 0x2

    if-ge v6, v7, :cond_1

    .line 61
    const/4 v6, 0x0

    invoke-virtual {v2, v6}, Ljava/lang/StringBuffer;->append(I)Ljava/lang/StringBuffer;

    .line 63
    :cond_1
    invoke-virtual {v2, v5}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0

    .line 58
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 67
    .end local v0    # "digest":Ljava/security/MessageDigest;
    .end local v2    # "hexString":Ljava/lang/StringBuffer;
    .end local v3    # "i":I
    .end local v4    # "messageDigest":[B
    .end local v5    # "shaHex":Ljava/lang/String;
    :catch_0
    move-exception v1

    .line 68
    .local v1, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v1}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    .line 70
    const-string v6, ""

    goto :goto_1
.end method

.method public static SHA1(Ljava/lang/String;)Ljava/lang/String;
    .locals 8
    .param p0, "decript"    # Ljava/lang/String;

    .prologue
    .line 28
    :try_start_0
    const-string v6, "SHA-1"

    invoke-static {v6}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    .line 29
    .local v0, "digest":Ljava/security/MessageDigest;
    invoke-virtual {p0}, Ljava/lang/String;->getBytes()[B

    move-result-object v6

    invoke-virtual {v0, v6}, Ljava/security/MessageDigest;->update([B)V

    .line 30
    invoke-virtual {v0}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v4

    .line 32
    .local v4, "messageDigest":[B
    new-instance v2, Ljava/lang/StringBuffer;

    invoke-direct {v2}, Ljava/lang/StringBuffer;-><init>()V

    .line 34
    .local v2, "hexString":Ljava/lang/StringBuffer;
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    array-length v6, v4

    if-lt v3, v6, :cond_0

    .line 41
    invoke-virtual {v2}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v6

    .line 46
    .end local v0    # "digest":Ljava/security/MessageDigest;
    .end local v2    # "hexString":Ljava/lang/StringBuffer;
    .end local v3    # "i":I
    .end local v4    # "messageDigest":[B
    :goto_1
    return-object v6

    .line 35
    .restart local v0    # "digest":Ljava/security/MessageDigest;
    .restart local v2    # "hexString":Ljava/lang/StringBuffer;
    .restart local v3    # "i":I
    .restart local v4    # "messageDigest":[B
    :cond_0
    aget-byte v6, v4, v3

    and-int/lit16 v6, v6, 0xff

    invoke-static {v6}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v5

    .line 36
    .local v5, "shaHex":Ljava/lang/String;
    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v6

    const/4 v7, 0x2

    if-ge v6, v7, :cond_1

    .line 37
    const/4 v6, 0x0

    invoke-virtual {v2, v6}, Ljava/lang/StringBuffer;->append(I)Ljava/lang/StringBuffer;

    .line 39
    :cond_1
    invoke-virtual {v2, v5}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0

    .line 34
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 43
    .end local v0    # "digest":Ljava/security/MessageDigest;
    .end local v2    # "hexString":Ljava/lang/StringBuffer;
    .end local v3    # "i":I
    .end local v4    # "messageDigest":[B
    .end local v5    # "shaHex":Ljava/lang/String;
    :catch_0
    move-exception v1

    .line 44
    .local v1, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v1}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    .line 46
    const-string v6, ""

    goto :goto_1
.end method

.method public static decryptAES([BLjava/lang/String;)[B
    .locals 10
    .param p0, "content"    # [B
    .param p1, "password"    # Ljava/lang/String;

    .prologue
    .line 160
    :try_start_0
    const-string v7, "AES"

    invoke-static {v7}, Ljavax/crypto/KeyGenerator;->getInstance(Ljava/lang/String;)Ljavax/crypto/KeyGenerator;

    move-result-object v4

    .line 161
    .local v4, "kgen":Ljavax/crypto/KeyGenerator;
    const/16 v7, 0x80

    new-instance v8, Ljava/security/SecureRandom;

    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v9

    invoke-direct {v8, v9}, Ljava/security/SecureRandom;-><init>([B)V

    invoke-virtual {v4, v7, v8}, Ljavax/crypto/KeyGenerator;->init(ILjava/security/SecureRandom;)V

    .line 162
    invoke-virtual {v4}, Ljavax/crypto/KeyGenerator;->generateKey()Ljavax/crypto/SecretKey;

    move-result-object v6

    .line 163
    .local v6, "secretKey":Ljavax/crypto/SecretKey;
    invoke-interface {v6}, Ljavax/crypto/SecretKey;->getEncoded()[B

    move-result-object v2

    .line 164
    .local v2, "enCodeFormat":[B
    new-instance v3, Ljavax/crypto/spec/SecretKeySpec;

    const-string v7, "AES"

    invoke-direct {v3, v2, v7}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 165
    .local v3, "key":Ljavax/crypto/spec/SecretKeySpec;
    const-string v7, "AES"

    invoke-static {v7}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v0

    .line 166
    .local v0, "cipher":Ljavax/crypto/Cipher;
    const/4 v7, 0x2

    invoke-virtual {v0, v7, v3}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;)V

    .line 167
    invoke-virtual {v0, p0}, Ljavax/crypto/Cipher;->doFinal([B)[B
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljavax/crypto/NoSuchPaddingException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/security/InvalidKeyException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljavax/crypto/IllegalBlockSizeException; {:try_start_0 .. :try_end_0} :catch_3
    .catch Ljavax/crypto/BadPaddingException; {:try_start_0 .. :try_end_0} :catch_4

    move-result-object v5

    .line 180
    .end local v0    # "cipher":Ljavax/crypto/Cipher;
    .end local v2    # "enCodeFormat":[B
    .end local v3    # "key":Ljavax/crypto/spec/SecretKeySpec;
    .end local v4    # "kgen":Ljavax/crypto/KeyGenerator;
    .end local v6    # "secretKey":Ljavax/crypto/SecretKey;
    :goto_0
    return-object v5

    .line 169
    :catch_0
    move-exception v1

    .line 170
    .local v1, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v1}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    .line 180
    .end local v1    # "e":Ljava/security/NoSuchAlgorithmException;
    :goto_1
    const/4 v5, 0x0

    goto :goto_0

    .line 171
    :catch_1
    move-exception v1

    .line 172
    .local v1, "e":Ljavax/crypto/NoSuchPaddingException;
    invoke-virtual {v1}, Ljavax/crypto/NoSuchPaddingException;->printStackTrace()V

    goto :goto_1

    .line 173
    .end local v1    # "e":Ljavax/crypto/NoSuchPaddingException;
    :catch_2
    move-exception v1

    .line 174
    .local v1, "e":Ljava/security/InvalidKeyException;
    invoke-virtual {v1}, Ljava/security/InvalidKeyException;->printStackTrace()V

    goto :goto_1

    .line 175
    .end local v1    # "e":Ljava/security/InvalidKeyException;
    :catch_3
    move-exception v1

    .line 176
    .local v1, "e":Ljavax/crypto/IllegalBlockSizeException;
    invoke-virtual {v1}, Ljavax/crypto/IllegalBlockSizeException;->printStackTrace()V

    goto :goto_1

    .line 177
    .end local v1    # "e":Ljavax/crypto/IllegalBlockSizeException;
    :catch_4
    move-exception v1

    .line 178
    .local v1, "e":Ljavax/crypto/BadPaddingException;
    invoke-virtual {v1}, Ljavax/crypto/BadPaddingException;->printStackTrace()V

    goto :goto_1
.end method

.method public static decryptBASE64(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .param p0, "key"    # Ljava/lang/String;

    .prologue
    .line 192
    const-string v0, ""

    return-object v0
.end method

.method public static encryptAES(Ljava/lang/String;Ljava/lang/String;)[B
    .locals 11
    .param p0, "content"    # Ljava/lang/String;
    .param p1, "password"    # Ljava/lang/String;

    .prologue
    .line 123
    :try_start_0
    const-string v8, "AES"

    invoke-static {v8}, Ljavax/crypto/KeyGenerator;->getInstance(Ljava/lang/String;)Ljavax/crypto/KeyGenerator;

    move-result-object v5

    .line 124
    .local v5, "kgen":Ljavax/crypto/KeyGenerator;
    const/16 v8, 0x80

    new-instance v9, Ljava/security/SecureRandom;

    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v10

    invoke-direct {v9, v10}, Ljava/security/SecureRandom;-><init>([B)V

    invoke-virtual {v5, v8, v9}, Ljavax/crypto/KeyGenerator;->init(ILjava/security/SecureRandom;)V

    .line 125
    invoke-virtual {v5}, Ljavax/crypto/KeyGenerator;->generateKey()Ljavax/crypto/SecretKey;

    move-result-object v7

    .line 126
    .local v7, "secretKey":Ljavax/crypto/SecretKey;
    invoke-interface {v7}, Ljavax/crypto/SecretKey;->getEncoded()[B

    move-result-object v3

    .line 127
    .local v3, "enCodeFormat":[B
    new-instance v4, Ljavax/crypto/spec/SecretKeySpec;

    const-string v8, "AES"

    invoke-direct {v4, v3, v8}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 128
    .local v4, "key":Ljavax/crypto/spec/SecretKeySpec;
    const-string v8, "AES"

    invoke-static {v8}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v1

    .line 129
    .local v1, "cipher":Ljavax/crypto/Cipher;
    const-string v8, "utf-8"

    invoke-virtual {p0, v8}, Ljava/lang/String;->getBytes(Ljava/lang/String;)[B

    move-result-object v0

    .line 130
    .local v0, "byteContent":[B
    const/4 v8, 0x1

    invoke-virtual {v1, v8, v4}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;)V

    .line 131
    invoke-virtual {v1, v0}, Ljavax/crypto/Cipher;->doFinal([B)[B
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljavax/crypto/NoSuchPaddingException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/security/InvalidKeyException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/io/UnsupportedEncodingException; {:try_start_0 .. :try_end_0} :catch_3
    .catch Ljavax/crypto/IllegalBlockSizeException; {:try_start_0 .. :try_end_0} :catch_4
    .catch Ljavax/crypto/BadPaddingException; {:try_start_0 .. :try_end_0} :catch_5

    move-result-object v6

    .line 146
    .end local v0    # "byteContent":[B
    .end local v1    # "cipher":Ljavax/crypto/Cipher;
    .end local v3    # "enCodeFormat":[B
    .end local v4    # "key":Ljavax/crypto/spec/SecretKeySpec;
    .end local v5    # "kgen":Ljavax/crypto/KeyGenerator;
    .end local v7    # "secretKey":Ljavax/crypto/SecretKey;
    :goto_0
    return-object v6

    .line 133
    :catch_0
    move-exception v2

    .line 134
    .local v2, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v2}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    .line 146
    .end local v2    # "e":Ljava/security/NoSuchAlgorithmException;
    :goto_1
    const/4 v6, 0x0

    goto :goto_0

    .line 135
    :catch_1
    move-exception v2

    .line 136
    .local v2, "e":Ljavax/crypto/NoSuchPaddingException;
    invoke-virtual {v2}, Ljavax/crypto/NoSuchPaddingException;->printStackTrace()V

    goto :goto_1

    .line 137
    .end local v2    # "e":Ljavax/crypto/NoSuchPaddingException;
    :catch_2
    move-exception v2

    .line 138
    .local v2, "e":Ljava/security/InvalidKeyException;
    invoke-virtual {v2}, Ljava/security/InvalidKeyException;->printStackTrace()V

    goto :goto_1

    .line 139
    .end local v2    # "e":Ljava/security/InvalidKeyException;
    :catch_3
    move-exception v2

    .line 140
    .local v2, "e":Ljava/io/UnsupportedEncodingException;
    invoke-virtual {v2}, Ljava/io/UnsupportedEncodingException;->printStackTrace()V

    goto :goto_1

    .line 141
    .end local v2    # "e":Ljava/io/UnsupportedEncodingException;
    :catch_4
    move-exception v2

    .line 142
    .local v2, "e":Ljavax/crypto/IllegalBlockSizeException;
    invoke-virtual {v2}, Ljavax/crypto/IllegalBlockSizeException;->printStackTrace()V

    goto :goto_1

    .line 143
    .end local v2    # "e":Ljavax/crypto/IllegalBlockSizeException;
    :catch_5
    move-exception v2

    .line 144
    .local v2, "e":Ljavax/crypto/BadPaddingException;
    invoke-virtual {v2}, Ljavax/crypto/BadPaddingException;->printStackTrace()V

    goto :goto_1
.end method

.method public static encryptBASE64(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .param p0, "key"    # Ljava/lang/String;

    .prologue
    .line 204
    const-string v0, ""

    return-object v0
.end method

.method public static getSign(Landroid/content/Context;)Ljava/lang/String;
    .locals 7
    .param p0, "context"    # Landroid/content/Context;

    .prologue
    .line 74
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v4

    .line 75
    .local v4, "pm":Landroid/content/pm/PackageManager;
    const/16 v5, 0x40

    invoke-virtual {v4, v5}, Landroid/content/pm/PackageManager;->getInstalledPackages(I)Ljava/util/List;

    move-result-object v0

    .line 76
    .local v0, "apps":Ljava/util/List;, "Ljava/util/List<Landroid/content/pm/PackageInfo;>;"
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .line 77
    .local v1, "iter":Ljava/util/Iterator;, "Ljava/util/Iterator<Landroid/content/pm/PackageInfo;>;"
    :cond_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-nez v5, :cond_1

    .line 84
    const-string v5, ""

    :goto_0
    return-object v5

    .line 78
    :cond_1
    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/content/pm/PackageInfo;

    .line 79
    .local v3, "packageinfo":Landroid/content/pm/PackageInfo;
    iget-object v2, v3, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    .line 80
    .local v2, "packageName":Ljava/lang/String;
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v2, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_0

    .line 81
    iget-object v5, v3, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    const/4 v6, 0x0

    aget-object v5, v5, v6

    invoke-virtual {v5}, Landroid/content/pm/Signature;->toCharsString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v5}, Lcom/example/mybackup/Test;->SHA1(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    goto :goto_0
.end method
