.class public Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;
.super Landroid/widget/BaseAdapter;
.source "SQLiteDatabaseDemo.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/example/mybackup/SQLiteDatabaseDemo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "BooksListAdapter"
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

.field private mCursor:Lnet/sqlcipher/Cursor;

.field final synthetic this$0:Lcom/example/mybackup/SQLiteDatabaseDemo;


# direct methods
.method public constructor <init>(Lcom/example/mybackup/SQLiteDatabaseDemo;Landroid/content/Context;Lnet/sqlcipher/Cursor;)V
    .locals 0
    .param p2, "context"    # Landroid/content/Context;
    .param p3, "cursor"    # Lnet/sqlcipher/Cursor;

    .prologue
    .line 143
    iput-object p1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->this$0:Lcom/example/mybackup/SQLiteDatabaseDemo;

    invoke-direct {p0}, Landroid/widget/BaseAdapter;-><init>()V

    .line 145
    iput-object p2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mContext:Landroid/content/Context;

    .line 146
    iput-object p3, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    .line 147
    return-void
.end method


# virtual methods
.method public getCount()I
    .locals 1

    .prologue
    .line 150
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    if-nez v0, :cond_0

    .line 151
    const/4 v0, 0x0

    .line 153
    :goto_0
    return v0

    :cond_0
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v0}, Lnet/sqlcipher/Cursor;->getCount()I

    move-result v0

    goto :goto_0
.end method

.method public getItem(I)Ljava/lang/Object;
    .locals 1
    .param p1, "position"    # I

    .prologue
    .line 157
    const/4 v0, 0x0

    return-object v0
.end method

.method public getItemId(I)J
    .locals 2
    .param p1, "position"    # I

    .prologue
    .line 161
    const-wide/16 v0, 0x0

    return-wide v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 4
    .param p1, "position"    # I
    .param p2, "convertView"    # Landroid/view/View;
    .param p3, "parent"    # Landroid/view/ViewGroup;

    .prologue
    const/4 v3, 0x1

    .line 165
    new-instance v0, Landroid/widget/TextView;

    iget-object v1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 166
    .local v0, "mTextView":Landroid/widget/TextView;
    iget-object v1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    if-nez v1, :cond_0

    .line 175
    :goto_0
    return-object v0

    .line 169
    :cond_0
    iget-object v1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v1, p1}, Lnet/sqlcipher/Cursor;->moveToPosition(I)Z

    .line 170
    const-string v1, "Flag"

    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v2, v3}, Lnet/sqlcipher/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 171
    const-string v1, "Flag is here!"

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 174
    :cond_1
    new-instance v1, Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v2, v3}, Lnet/sqlcipher/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "___"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;->mCursor:Lnet/sqlcipher/Cursor;

    const/4 v3, 0x2

    invoke-interface {v2, v3}, Lnet/sqlcipher/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0
.end method
