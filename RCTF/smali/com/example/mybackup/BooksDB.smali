.class public Lcom/example/mybackup/BooksDB;
.super Lnet/sqlcipher/database/SQLiteOpenHelper;
.source "BooksDB.java"


# static fields
.field public static final BOOK_AUTHOR:Ljava/lang/String; = "book_author"

.field public static final BOOK_ID:Ljava/lang/String; = "book_id"

.field public static final BOOK_NAME:Ljava/lang/String; = "book_name"

.field private static final DATABASE_NAME:Ljava/lang/String; = "BOOKS.db"

.field private static final DATABASE_VERSION:I = 0x1

.field private static final TABLE_NAME:Ljava/lang/String; = "books_table"


# instance fields
.field private db:Lnet/sqlcipher/database/SQLiteDatabase;

.field private dbr:Lnet/sqlcipher/database/SQLiteDatabase;

.field private k:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 3
    .param p1, "context"    # Landroid/content/Context;

    .prologue
    .line 23
    const-string v0, "BOOKS.db"

    const/4 v1, 0x0

    const/4 v2, 0x1

    invoke-direct {p0, p1, v0, v1, v2}, Lnet/sqlcipher/database/SQLiteOpenHelper;-><init>(Landroid/content/Context;Ljava/lang/String;Lnet/sqlcipher/database/SQLiteDatabase$CursorFactory;I)V

    .line 24
    invoke-static {p1}, Lcom/example/mybackup/Test;->getSign(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/example/mybackup/BooksDB;->k:Ljava/lang/String;

    .line 25
    iget-object v0, p0, Lcom/example/mybackup/BooksDB;->k:Ljava/lang/String;

    invoke-virtual {p0, v0}, Lcom/example/mybackup/BooksDB;->getWritableDatabase(Ljava/lang/String;)Lnet/sqlcipher/database/SQLiteDatabase;

    move-result-object v0

    iput-object v0, p0, Lcom/example/mybackup/BooksDB;->db:Lnet/sqlcipher/database/SQLiteDatabase;

    .line 26
    iget-object v0, p0, Lcom/example/mybackup/BooksDB;->k:Ljava/lang/String;

    invoke-virtual {p0, v0}, Lcom/example/mybackup/BooksDB;->getReadableDatabase(Ljava/lang/String;)Lnet/sqlcipher/database/SQLiteDatabase;

    move-result-object v0

    iput-object v0, p0, Lcom/example/mybackup/BooksDB;->dbr:Lnet/sqlcipher/database/SQLiteDatabase;

    .line 27
    return-void
.end method


# virtual methods
.method public delete(I)V
    .locals 4
    .param p1, "id"    # I

    .prologue
    .line 60
    const-string v0, "book_id = ?"

    .line 61
    .local v0, "where":Ljava/lang/String;
    const/4 v2, 0x1

    new-array v1, v2, [Ljava/lang/String;

    const/4 v2, 0x0

    invoke-static {p1}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v1, v2

    .line 62
    .local v1, "whereValue":[Ljava/lang/String;
    iget-object v2, p0, Lcom/example/mybackup/BooksDB;->db:Lnet/sqlcipher/database/SQLiteDatabase;

    const-string v3, "books_table"

    invoke-virtual {v2, v3, v0, v1}, Lnet/sqlcipher/database/SQLiteDatabase;->delete(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I

    .line 63
    return-void
.end method

.method public insert(Ljava/lang/String;Ljava/lang/String;)J
    .locals 6
    .param p1, "bookname"    # Ljava/lang/String;
    .param p2, "author"    # Ljava/lang/String;

    .prologue
    .line 51
    new-instance v0, Landroid/content/ContentValues;

    invoke-direct {v0}, Landroid/content/ContentValues;-><init>()V

    .line 52
    .local v0, "cv":Landroid/content/ContentValues;
    const-string v3, "book_name"

    invoke-virtual {v0, v3, p1}, Landroid/content/ContentValues;->put(Ljava/lang/String;Ljava/lang/String;)V

    .line 53
    const-string v3, "book_author"

    invoke-virtual {v0, v3, p2}, Landroid/content/ContentValues;->put(Ljava/lang/String;Ljava/lang/String;)V

    .line 54
    iget-object v3, p0, Lcom/example/mybackup/BooksDB;->db:Lnet/sqlcipher/database/SQLiteDatabase;

    const-string v4, "books_table"

    const/4 v5, 0x0

    invoke-virtual {v3, v4, v5, v0}, Lnet/sqlcipher/database/SQLiteDatabase;->insert(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J

    move-result-wide v1

    .line 55
    .local v1, "row":J
    return-wide v1
.end method

.method public onCreate(Lnet/sqlcipher/database/SQLiteDatabase;)V
    .locals 1
    .param p1, "db"    # Lnet/sqlcipher/database/SQLiteDatabase;

    .prologue
    .line 31
    const-string v0, "CREATE TABLE books_table (book_id INTEGER primary key autoincrement, book_name text, book_author text);"

    .line 33
    .local v0, "sql":Ljava/lang/String;
    invoke-virtual {p1, v0}, Lnet/sqlcipher/database/SQLiteDatabase;->execSQL(Ljava/lang/String;)V

    .line 34
    return-void
.end method

.method public onUpgrade(Lnet/sqlcipher/database/SQLiteDatabase;II)V
    .locals 1
    .param p1, "db"    # Lnet/sqlcipher/database/SQLiteDatabase;
    .param p2, "oldVersion"    # I
    .param p3, "newVersion"    # I

    .prologue
    .line 37
    const-string v0, "DROP TABLE IF EXISTS books_table"

    .line 38
    .local v0, "sql":Ljava/lang/String;
    invoke-virtual {p1, v0}, Lnet/sqlcipher/database/SQLiteDatabase;->execSQL(Ljava/lang/String;)V

    .line 39
    invoke-virtual {p0, p1}, Lcom/example/mybackup/BooksDB;->onCreate(Lnet/sqlcipher/database/SQLiteDatabase;)V

    .line 40
    return-void
.end method

.method public select()Lnet/sqlcipher/Cursor;
    .locals 9

    .prologue
    const/4 v2, 0x0

    .line 44
    iget-object v0, p0, Lcom/example/mybackup/BooksDB;->dbr:Lnet/sqlcipher/database/SQLiteDatabase;

    const-string v1, "books_table"

    move-object v3, v2

    move-object v4, v2

    move-object v5, v2

    move-object v6, v2

    move-object v7, v2

    invoke-virtual/range {v0 .. v7}, Lnet/sqlcipher/database/SQLiteDatabase;->query(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sqlcipher/Cursor;

    move-result-object v8

    .line 45
    .local v8, "cursor":Lnet/sqlcipher/Cursor;
    return-object v8
.end method

.method public update(ILjava/lang/String;Ljava/lang/String;)V
    .locals 5
    .param p1, "id"    # I
    .param p2, "bookname"    # Ljava/lang/String;
    .param p3, "author"    # Ljava/lang/String;

    .prologue
    .line 67
    const-string v1, "book_id = ?"

    .line 68
    .local v1, "where":Ljava/lang/String;
    const/4 v3, 0x1

    new-array v2, v3, [Ljava/lang/String;

    const/4 v3, 0x0

    invoke-static {p1}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    .line 70
    .local v2, "whereValue":[Ljava/lang/String;
    new-instance v0, Landroid/content/ContentValues;

    invoke-direct {v0}, Landroid/content/ContentValues;-><init>()V

    .line 71
    .local v0, "cv":Landroid/content/ContentValues;
    const-string v3, "book_name"

    invoke-virtual {v0, v3, p2}, Landroid/content/ContentValues;->put(Ljava/lang/String;Ljava/lang/String;)V

    .line 72
    const-string v3, "book_author"

    invoke-virtual {v0, v3, p3}, Landroid/content/ContentValues;->put(Ljava/lang/String;Ljava/lang/String;)V

    .line 73
    iget-object v3, p0, Lcom/example/mybackup/BooksDB;->db:Lnet/sqlcipher/database/SQLiteDatabase;

    const-string v4, "books_table"

    invoke-virtual {v3, v4, v0, v1, v2}, Lnet/sqlcipher/database/SQLiteDatabase;->update(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I

    .line 74
    return-void
.end method
