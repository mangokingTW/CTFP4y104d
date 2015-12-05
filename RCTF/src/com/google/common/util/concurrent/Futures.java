// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

// Referenced classes of package com.google.common.util.concurrent:
//            MoreExecutors, ListenableFuture, SettableFuture, UninterruptibleFuture, 
//            CheckedFuture, AbstractListenableFuture, ForwardingFuture, ThreadFactoryBuilder, 
//            ExecutionList, AbstractCheckedFuture

public final class Futures
{
    private static class ChainingListenableFuture extends AbstractListenableFuture
        implements Runnable
    {

        private Function function;
        private ListenableFuture inputFuture;
        private final BlockingQueue mayInterruptIfRunningChannel;
        private final CountDownLatch outputCreated;
        private volatile ListenableFuture outputFuture;

        private void cancel(Future future, boolean flag)
        {
            if (future != null)
            {
                future.cancel(flag);
            }
        }

        public boolean cancel(boolean flag)
        {
            if (cancel())
            {
                try
                {
                    mayInterruptIfRunningChannel.put(Boolean.valueOf(flag));
                }
                catch (InterruptedException interruptedexception)
                {
                    Thread.currentThread().interrupt();
                }
                cancel(((Future) (inputFuture)), flag);
                cancel(((Future) (outputFuture)), flag);
                return true;
            } else
            {
                return false;
            }
        }

        public Object get()
            throws InterruptedException, ExecutionException
        {
            if (!isDone())
            {
                ListenableFuture listenablefuture = inputFuture;
                if (listenablefuture != null)
                {
                    listenablefuture.get();
                }
                outputCreated.await();
                listenablefuture = outputFuture;
                if (listenablefuture != null)
                {
                    listenablefuture.get();
                }
            }
            return super.get();
        }

        public Object get(long l, TimeUnit timeunit)
            throws TimeoutException, ExecutionException, InterruptedException
        {
            long l1 = l;
            TimeUnit timeunit2 = timeunit;
            if (!isDone())
            {
                l1 = l;
                TimeUnit timeunit1 = timeunit;
                if (timeunit != TimeUnit.NANOSECONDS)
                {
                    l1 = TimeUnit.NANOSECONDS.convert(l, timeunit);
                    timeunit1 = TimeUnit.NANOSECONDS;
                }
                timeunit = inputFuture;
                l = l1;
                if (timeunit != null)
                {
                    l = System.nanoTime();
                    timeunit.get(l1, timeunit1);
                    l = l1 - Math.max(0L, System.nanoTime() - l);
                }
                l1 = System.nanoTime();
                if (!outputCreated.await(l, timeunit1))
                {
                    throw new TimeoutException();
                }
                l -= Math.max(0L, System.nanoTime() - l1);
                timeunit = outputFuture;
                l1 = l;
                timeunit2 = timeunit1;
                if (timeunit != null)
                {
                    timeunit.get(l, timeunit1);
                    timeunit2 = timeunit1;
                    l1 = l;
                }
            }
            return super.get(l1, timeunit2);
        }

        public void run()
        {
            Object obj = Futures.makeUninterruptible(inputFuture).get();
            boolean flag;
            obj = (ListenableFuture)function.apply(obj);
            outputFuture = ((ListenableFuture) (obj));
            flag = isCancelled();
            if (!flag)
            {
                break MISSING_BLOCK_LABEL_176;
            }
            ((ListenableFuture) (obj)).cancel(((Boolean)mayInterruptIfRunningChannel.take()).booleanValue());
_L1:
            outputFuture = null;
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            return;
            obj;
            cancel();
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            return;
            obj;
            setException(((ExecutionException) (obj)).getCause());
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            return;
            obj;
            Thread.currentThread().interrupt();
              goto _L1
            obj;
            setException(((UndeclaredThrowableException) (obj)).getCause());
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            return;
            ((ListenableFuture) (obj)).addListener(((_cls1) (obj)). new Runnable() {

                final ChainingListenableFuture this$0;
                final ListenableFuture val$outputFuture;

                public void run()
                {
                    set(Futures.makeUninterruptible(outputFuture).get());
                    ChainingListenableFuture.this.outputFuture = null;
                    return;
                    Object obj;
                    obj;
                    cancel();
                    ChainingListenableFuture.this.outputFuture = null;
                    return;
                    obj;
                    setException(((ExecutionException) (obj)).getCause());
                    ChainingListenableFuture.this.outputFuture = null;
                    return;
                    obj;
                    ChainingListenableFuture.this.outputFuture = null;
                    throw obj;
                }

            
            {
                this$0 = final_chaininglistenablefuture;
                outputFuture = ListenableFuture.this;
                super();
            }
            }, MoreExecutors.sameThreadExecutor());
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            return;
            Object obj1;
            obj1;
            setException(((Throwable) (obj1)));
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            return;
            obj1;
            setException(((Throwable) (obj1)));
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            return;
            obj1;
            function = null;
            inputFuture = null;
            outputCreated.countDown();
            throw obj1;
        }


/*
        static ListenableFuture access$102(ChainingListenableFuture chaininglistenablefuture, ListenableFuture listenablefuture)
        {
            chaininglistenablefuture.outputFuture = listenablefuture;
            return listenablefuture;
        }

*/

        private ChainingListenableFuture(Function function1, ListenableFuture listenablefuture)
        {
            mayInterruptIfRunningChannel = new LinkedBlockingQueue(1);
            outputCreated = new CountDownLatch(1);
            function = (Function)Preconditions.checkNotNull(function1);
            inputFuture = (ListenableFuture)Preconditions.checkNotNull(listenablefuture);
        }

    }

    private static class ListenableFutureAdapter extends ForwardingFuture
        implements ListenableFuture
    {

        private static final Executor defaultAdapterExecutor;
        private static final ThreadFactory threadFactory;
        private final Executor adapterExecutor;
        private final Future _flddelegate;
        private final ExecutionList executionList;
        private final AtomicBoolean hasListeners;

        public void addListener(Runnable runnable, Executor executor)
        {
label0:
            {
                executionList.add(runnable, executor);
                if (hasListeners.compareAndSet(false, true))
                {
                    if (!_flddelegate.isDone())
                    {
                        break label0;
                    }
                    executionList.run();
                }
                return;
            }
            adapterExecutor.execute(new Runnable() {

                final ListenableFutureAdapter this$0;

                public void run()
                {
                    try
                    {
                        _flddelegate.get();
                    }
                    catch (Error error)
                    {
                        throw error;
                    }
                    catch (InterruptedException interruptedexception)
                    {
                        Thread.currentThread().interrupt();
                        throw new IllegalStateException("Adapter thread interrupted!", interruptedexception);
                    }
                    catch (Throwable throwable) { }
                    executionList.run();
                }

            
            {
                this$0 = ListenableFutureAdapter.this;
                super();
            }
            });
        }

        protected volatile Object _mthdelegate()
        {
            return _mthdelegate();
        }

        protected Future _mthdelegate()
        {
            return _flddelegate;
        }

        static 
        {
            threadFactory = (new ThreadFactoryBuilder()).setNameFormat("ListenableFutureAdapter-thread-%d").build();
            defaultAdapterExecutor = Executors.newCachedThreadPool(threadFactory);
        }



        ListenableFutureAdapter(Future future)
        {
            this(future, defaultAdapterExecutor);
        }

        ListenableFutureAdapter(Future future, Executor executor)
        {
            executionList = new ExecutionList();
            hasListeners = new AtomicBoolean(false);
            _flddelegate = (Future)Preconditions.checkNotNull(future);
            adapterExecutor = (Executor)Preconditions.checkNotNull(executor);
        }
    }

    private static class MappingCheckedFuture extends AbstractCheckedFuture
    {

        final Function mapper;

        protected Exception mapException(Exception exception)
        {
            return (Exception)mapper.apply(exception);
        }

        MappingCheckedFuture(ListenableFuture listenablefuture, Function function)
        {
            super(listenablefuture);
            mapper = (Function)Preconditions.checkNotNull(function);
        }
    }


    private Futures()
    {
    }

    public static ListenableFuture chain(ListenableFuture listenablefuture, Function function)
    {
        return chain(listenablefuture, function, ((Executor) (MoreExecutors.sameThreadExecutor())));
    }

    public static ListenableFuture chain(ListenableFuture listenablefuture, Function function, Executor executor)
    {
        function = new ChainingListenableFuture(function, listenablefuture);
        listenablefuture.addListener(function, executor);
        return function;
    }

    public static CheckedFuture immediateCheckedFuture(Object obj)
    {
        SettableFuture settablefuture = SettableFuture.create();
        settablefuture.set(obj);
        return makeChecked(settablefuture, new Function() {

            public Exception apply(Exception exception)
            {
                throw new AssertionError("impossible");
            }

            public volatile Object apply(Object obj1)
            {
                return apply((Exception)obj1);
            }

        });
    }

    public static CheckedFuture immediateFailedCheckedFuture(Exception exception)
    {
        Preconditions.checkNotNull(exception);
        return makeChecked(immediateFailedFuture(exception), new Function(exception) {

            final Exception val$exception;

            public Exception apply(Exception exception1)
            {
                return exception;
            }

            public volatile Object apply(Object obj)
            {
                return apply((Exception)obj);
            }

            
            {
                exception = exception1;
                super();
            }
        });
    }

    public static ListenableFuture immediateFailedFuture(Throwable throwable)
    {
        Preconditions.checkNotNull(throwable);
        SettableFuture settablefuture = SettableFuture.create();
        settablefuture.setException(throwable);
        return settablefuture;
    }

    public static ListenableFuture immediateFuture(Object obj)
    {
        SettableFuture settablefuture = SettableFuture.create();
        settablefuture.set(obj);
        return settablefuture;
    }

    public static CheckedFuture makeChecked(ListenableFuture listenablefuture, Function function)
    {
        return new MappingCheckedFuture((ListenableFuture)Preconditions.checkNotNull(listenablefuture), function);
    }

    public static CheckedFuture makeChecked(Future future, Function function)
    {
        return new MappingCheckedFuture(makeListenable(future), function);
    }

    public static ListenableFuture makeListenable(Future future)
    {
        if (future instanceof ListenableFuture)
        {
            return (ListenableFuture)future;
        } else
        {
            return new ListenableFutureAdapter(future);
        }
    }

    static ListenableFuture makeListenable(Future future, Executor executor)
    {
        Preconditions.checkNotNull(executor);
        if (future instanceof ListenableFuture)
        {
            return (ListenableFuture)future;
        } else
        {
            return new ListenableFutureAdapter(future, executor);
        }
    }

    public static UninterruptibleFuture makeUninterruptible(Future future)
    {
        Preconditions.checkNotNull(future);
        if (future instanceof UninterruptibleFuture)
        {
            return (UninterruptibleFuture)future;
        } else
        {
            return new UninterruptibleFuture(future) {

                final Future val$future;

                public boolean cancel(boolean flag)
                {
                    return future.cancel(flag);
                }

                public Object get()
                    throws ExecutionException
                {
                    boolean flag = false;
_L2:
                    Object obj = future.get();
                    if (flag)
                    {
                        Thread.currentThread().interrupt();
                    }
                    return obj;
                    Object obj1;
                    obj1;
                    flag = true;
                    if (true) goto _L2; else goto _L1
_L1:
                    obj1;
                    if (flag)
                    {
                        Thread.currentThread().interrupt();
                    }
                    throw obj1;
                }

                public Object get(long l, TimeUnit timeunit)
                    throws TimeoutException, ExecutionException
                {
                    boolean flag;
                    boolean flag1;
                    boolean flag2;
                    flag2 = false;
                    flag = false;
                    flag1 = flag2;
                    long l1 = System.nanoTime();
                    flag1 = flag2;
                    l = timeunit.toNanos(l);
_L2:
                    flag1 = flag;
                    timeunit = ((TimeUnit) (future.get((l1 + l) - System.nanoTime(), TimeUnit.NANOSECONDS)));
                    if (flag)
                    {
                        Thread.currentThread().interrupt();
                    }
                    return timeunit;
                    timeunit;
                    flag = true;
                    if (true) goto _L2; else goto _L1
_L1:
                    timeunit;
                    if (flag1)
                    {
                        Thread.currentThread().interrupt();
                    }
                    throw timeunit;
                }

                public boolean isCancelled()
                {
                    return future.isCancelled();
                }

                public boolean isDone()
                {
                    return future.isDone();
                }

            
            {
                future = future1;
                super();
            }
            };
        }
    }

    public static ListenableFuture transform(ListenableFuture listenablefuture, Function function)
    {
        return transform(listenablefuture, function, ((Executor) (MoreExecutors.sameThreadExecutor())));
    }

    public static ListenableFuture transform(ListenableFuture listenablefuture, Function function, Executor executor)
    {
        Preconditions.checkNotNull(function);
        return chain(listenablefuture, new Function(function) {

            final Function val$function;

            public ListenableFuture apply(Object obj)
            {
                return Futures.immediateFuture(function.apply(obj));
            }

            public volatile Object apply(Object obj)
            {
                return apply(obj);
            }

            
            {
                function = function1;
                super();
            }
        }, executor);
    }

    public static Future transform(Future future, Function function)
    {
        if (future instanceof ListenableFuture)
        {
            return transform((ListenableFuture)future, function);
        } else
        {
            Preconditions.checkNotNull(future);
            Preconditions.checkNotNull(function);
            return new Future(future, function) {

                private ExecutionException exception;
                private final Object lock = new Object();
                private boolean set;
                final Function val$function;
                final Future val$future;
                private Object value;

                private Object apply(Object obj)
                    throws ExecutionException
                {
                    Object obj1 = lock;
                    obj1;
                    JVM INSTR monitorenter ;
                    boolean flag = set;
                    if (flag) goto _L2; else goto _L1
_L1:
                    value = function.apply(obj);
_L3:
                    set = true;
_L2:
                    if (exception != null)
                    {
                        throw exception;
                    }
                    break MISSING_BLOCK_LABEL_84;
                    obj;
                    obj1;
                    JVM INSTR monitorexit ;
                    throw obj;
                    obj;
                    exception = new ExecutionException(((Throwable) (obj)));
                      goto _L3
                    obj;
                    exception = new ExecutionException(((Throwable) (obj)));
                      goto _L3
                    obj = value;
                    obj1;
                    JVM INSTR monitorexit ;
                    return obj;
                }

                public boolean cancel(boolean flag)
                {
                    return future.cancel(flag);
                }

                public Object get()
                    throws InterruptedException, ExecutionException
                {
                    return apply(future.get());
                }

                public Object get(long l, TimeUnit timeunit)
                    throws InterruptedException, ExecutionException, TimeoutException
                {
                    return apply(future.get(l, timeunit));
                }

                public boolean isCancelled()
                {
                    return future.isCancelled();
                }

                public boolean isDone()
                {
                    return future.isDone();
                }

            
            {
                future = future1;
                function = function1;
                super();
                set = false;
                value = null;
                exception = null;
            }
            };
        }
    }
}
