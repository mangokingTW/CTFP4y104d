.class public Lcom/example/mybackup/SQLiteDatabaseDemo;
.super Landroid/app/Activity;
.source "SQLiteDatabaseDemo.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/app/Activity;",
        "Landroid/widget/AdapterView$OnItemClickListener;"
    }
.end annotation


# static fields
.field protected static final MENU_ADD:I = 0x1

.field protected static final MENU_DELETE:I = 0x2

.field protected static final MENU_UPDATE:I = 0x3


# instance fields
.field private BOOK_ID:I

.field private BookAuthor:Landroid/widget/EditText;

.field private BookName:Landroid/widget/EditText;

.field private BooksList:Landroid/widget/ListView;

.field private mBooksDB:Lcom/example/mybackup/BooksDB;

.field private mCursor:Lnet/sqlcipher/Cursor;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 19
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 21
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    .line 26
    const/4 v0, 0x0

    iput v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BOOK_ID:I

    .line 19
    return-void
.end method


# virtual methods
.method public add()V
    .locals 4

    .prologue
    .line 78
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    .line 79
    .local v1, "bookname":Ljava/lang/String;
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v0

    .line 81
    .local v0, "author":Ljava/lang/String;
    const-string v2, ""

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    const-string v2, ""

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 92
    :cond_0
    :goto_0
    return-void

    .line 84
    :cond_1
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mBooksDB:Lcom/example/mybackup/BooksDB;

    invoke-virtual {v2, v1, v0}, Lcom/example/mybackup/BooksDB;->insert(Ljava/lang/String;Ljava/lang/String;)J

    .line 85
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    if-eqz v2, :cond_2

    .line 86
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v2}, Lnet/sqlcipher/Cursor;->requery()Z

    .line 88
    :cond_2
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BooksList:Landroid/widget/ListView;

    invoke-virtual {v2}, Landroid/widget/ListView;->invalidateViews()V

    .line 89
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    const-string v3, ""

    invoke-virtual {v2, v3}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 90
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    const-string v3, ""

    invoke-virtual {v2, v3}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 91
    const-string v2, "Add Successed!"

    const/4 v3, 0x0

    invoke-static {p0, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method

.method public delete()V
    .locals 2

    .prologue
    .line 95
    iget v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BOOK_ID:I

    if-nez v0, :cond_0

    .line 106
    :goto_0
    return-void

    .line 98
    :cond_0
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mBooksDB:Lcom/example/mybackup/BooksDB;

    iget v1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BOOK_ID:I

    invoke-virtual {v0, v1}, Lcom/example/mybackup/BooksDB;->delete(I)V

    .line 99
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    if-eqz v0, :cond_1

    .line 100
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v0}, Lnet/sqlcipher/Cursor;->requery()Z

    .line 102
    :cond_1
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BooksList:Landroid/widget/ListView;

    invoke-virtual {v0}, Landroid/widget/ListView;->invalidateViews()V

    .line 103
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    const-string v1, ""

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 104
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    const-string v1, ""

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 105
    const-string v0, "Delete Successed!"

    const/4 v1, 0x0

    invoke-static {p0, v0, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 32
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 33
    const v0, 0x7f030001

    invoke-virtual {p0, v0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->setContentView(I)V

    .line 34
    invoke-static {p0}, Lnet/sqlcipher/database/SQLiteDatabase;->loadLibs(Landroid/content/Context;)V

    .line 35
    invoke-virtual {p0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->setUpViews()V

    .line 36
    return-void
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 4
    .param p1, "menu"    # Landroid/view/Menu;

    .prologue
    const/4 v3, 0x2

    const/4 v2, 0x1

    const/4 v1, 0x0

    .line 51
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreateOptionsMenu(Landroid/view/Menu;)Z

    .line 53
    const-string v0, "ADD"

    invoke-interface {p1, v1, v2, v1, v0}, Landroid/view/Menu;->add(IIILjava/lang/CharSequence;)Landroid/view/MenuItem;

    .line 54
    const-string v0, "DELETE"

    invoke-interface {p1, v1, v3, v1, v0}, Landroid/view/Menu;->add(IIILjava/lang/CharSequence;)Landroid/view/MenuItem;

    .line 55
    const-string v0, "UPDATE"

    invoke-interface {p1, v1, v3, v1, v0}, Landroid/view/Menu;->add(IIILjava/lang/CharSequence;)Landroid/view/MenuItem;

    .line 56
    return v2
.end method

.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 3
    .param p2, "view"    # Landroid/view/View;
    .param p3, "position"    # I
    .param p4, "id"    # J
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView",
            "<*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .prologue
    .local p1, "parent":Landroid/widget/AdapterView;, "Landroid/widget/AdapterView<*>;"
    const/4 v2, 0x1

    .line 127
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    if-eqz v0, :cond_0

    .line 128
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v0, p3}, Lnet/sqlcipher/Cursor;->moveToPosition(I)Z

    .line 129
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lnet/sqlcipher/Cursor;->getInt(I)I

    move-result v0

    iput v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BOOK_ID:I

    .line 130
    const-string v0, "Flag"

    iget-object v1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v1, v2}, Lnet/sqlcipher/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 131
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    const-string v1, "Guess"

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 132
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    const-string v1, "Flag is here!"

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 138
    :cond_0
    :goto_0
    return-void

    .line 135
    :cond_1
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    iget-object v1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v1, v2}, Lnet/sqlcipher/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 136
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    iget-object v1, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    const/4 v2, 0x2

    invoke-interface {v1, v2}, Lnet/sqlcipher/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0
.end method

.method public onOptionsItemSelected(Landroid/view/MenuItem;)Z
    .locals 1
    .param p1, "item"    # Landroid/view/MenuItem;

    .prologue
    .line 61
    invoke-super {p0, p1}, Landroid/app/Activity;->onOptionsItemSelected(Landroid/view/MenuItem;)Z

    .line 62
    invoke-interface {p1}, Landroid/view/MenuItem;->getItemId()I

    move-result v0

    packed-switch v0, :pswitch_data_0

    .line 74
    :goto_0
    const/4 v0, 0x1

    return v0

    .line 65
    :pswitch_0
    invoke-virtual {p0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->add()V

    goto :goto_0

    .line 68
    :pswitch_1
    invoke-virtual {p0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->delete()V

    goto :goto_0

    .line 71
    :pswitch_2
    invoke-virtual {p0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->update()V

    goto :goto_0

    .line 62
    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_1
        :pswitch_2
    .end packed-switch
.end method

.method public setUpViews()V
    .locals 3

    .prologue
    .line 39
    new-instance v0, Lcom/example/mybackup/BooksDB;

    invoke-direct {v0, p0}, Lcom/example/mybackup/BooksDB;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mBooksDB:Lcom/example/mybackup/BooksDB;

    .line 41
    const/high16 v0, 0x7f070000

    invoke-virtual {p0, v0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    .line 42
    const v0, 0x7f070001

    invoke-virtual {p0, v0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    .line 43
    const v0, 0x7f070002

    invoke-virtual {p0, v0}, Lcom/example/mybackup/SQLiteDatabaseDemo;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ListView;

    iput-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BooksList:Landroid/widget/ListView;

    .line 45
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BooksList:Landroid/widget/ListView;

    new-instance v1, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;

    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-direct {v1, p0, p0, v2}, Lcom/example/mybackup/SQLiteDatabaseDemo$BooksListAdapter;-><init>(Lcom/example/mybackup/SQLiteDatabaseDemo;Landroid/content/Context;Lnet/sqlcipher/Cursor;)V

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 46
    iget-object v0, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BooksList:Landroid/widget/ListView;

    invoke-virtual {v0, p0}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 47
    return-void
.end method

.method public update()V
    .locals 4

    .prologue
    .line 109
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    .line 110
    .local v1, "bookname":Ljava/lang/String;
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v0

    .line 112
    .local v0, "author":Ljava/lang/String;
    const-string v2, ""

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    const-string v2, ""

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 123
    :cond_0
    :goto_0
    return-void

    .line 115
    :cond_1
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mBooksDB:Lcom/example/mybackup/BooksDB;

    iget v3, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BOOK_ID:I

    invoke-virtual {v2, v3, v1, v0}, Lcom/example/mybackup/BooksDB;->update(ILjava/lang/String;Ljava/lang/String;)V

    .line 116
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    if-eqz v2, :cond_2

    .line 117
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->mCursor:Lnet/sqlcipher/Cursor;

    invoke-interface {v2}, Lnet/sqlcipher/Cursor;->requery()Z

    .line 119
    :cond_2
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BooksList:Landroid/widget/ListView;

    invoke-virtual {v2}, Landroid/widget/ListView;->invalidateViews()V

    .line 120
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookName:Landroid/widget/EditText;

    const-string v3, ""

    invoke-virtual {v2, v3}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 121
    iget-object v2, p0, Lcom/example/mybackup/SQLiteDatabaseDemo;->BookAuthor:Landroid/widget/EditText;

    const-string v3, ""

    invoke-virtual {v2, v3}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 122
    const-string v2, "Update Successed!"

    const/4 v3, 0x0

    invoke-static {p0, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method
