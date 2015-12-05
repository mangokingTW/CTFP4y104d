// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.google.common.util.concurrent:
//            ThreadFactoryBuilder

public final class MoreExecutors
{
    private static class SameThreadExecutorService extends AbstractExecutorService
    {

        private final Lock lock;
        private int runningTasks;
        private boolean shutdown;
        private final Condition termination;

        private void endTask()
        {
            lock.lock();
            runningTasks = runningTasks - 1;
            if (isTerminated())
            {
                termination.signalAll();
            }
            lock.unlock();
            return;
            Exception exception;
            exception;
            lock.unlock();
            throw exception;
        }

        private void startTask()
        {
            lock.lock();
            if (isShutdown())
            {
                throw new RejectedExecutionException("Executor already shutdown");
            }
            break MISSING_BLOCK_LABEL_38;
            Exception exception;
            exception;
            lock.unlock();
            throw exception;
            runningTasks = runningTasks + 1;
            lock.unlock();
            return;
        }

        public boolean awaitTermination(long l, TimeUnit timeunit)
            throws InterruptedException
        {
            l = timeunit.toNanos(l);
            lock.lock();
_L1:
            boolean flag = isTerminated();
            if (flag)
            {
                lock.unlock();
                return true;
            }
            if (l <= 0L)
            {
                lock.unlock();
                return false;
            }
            l = termination.awaitNanos(l);
              goto _L1
            timeunit;
            lock.unlock();
            throw timeunit;
        }

        public void execute(Runnable runnable)
        {
            startTask();
            runnable.run();
            endTask();
            return;
            runnable;
            endTask();
            throw runnable;
        }

        public boolean isShutdown()
        {
            lock.lock();
            boolean flag = shutdown;
            lock.unlock();
            return flag;
            Exception exception;
            exception;
            lock.unlock();
            throw exception;
        }

        public boolean isTerminated()
        {
            lock.lock();
            if (!shutdown) goto _L2; else goto _L1
_L1:
            int i = runningTasks;
            if (i != 0) goto _L2; else goto _L3
_L3:
            boolean flag = true;
_L5:
            lock.unlock();
            return flag;
_L2:
            flag = false;
            if (true) goto _L5; else goto _L4
_L4:
            Exception exception;
            exception;
            lock.unlock();
            throw exception;
        }

        public void shutdown()
        {
            lock.lock();
            shutdown = true;
            lock.unlock();
            return;
            Exception exception;
            exception;
            lock.unlock();
            throw exception;
        }

        public List shutdownNow()
        {
            shutdown();
            return Collections.emptyList();
        }

        private SameThreadExecutorService()
        {
            lock = new ReentrantLock();
            termination = lock.newCondition();
            runningTasks = 0;
            shutdown = false;
        }

    }


    private MoreExecutors()
    {
    }

    public static void addDelayedShutdownHook(ExecutorService executorservice, long l, TimeUnit timeunit)
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(executorservice, l, timeunit) {

            final ExecutorService val$service;
            final long val$terminationTimeout;
            final TimeUnit val$timeUnit;

            public void run()
            {
                try
                {
                    service.shutdown();
                    service.awaitTermination(terminationTimeout, timeUnit);
                    return;
                }
                catch (InterruptedException interruptedexception)
                {
                    return;
                }
            }

            
            {
                service = executorservice;
                terminationTimeout = l;
                timeUnit = timeunit;
                super();
            }
        }));
    }

    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadpoolexecutor)
    {
        return getExitingExecutorService(threadpoolexecutor, 120L, TimeUnit.SECONDS);
    }

    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadpoolexecutor, long l, TimeUnit timeunit)
    {
        threadpoolexecutor.setThreadFactory((new ThreadFactoryBuilder()).setDaemon(true).setThreadFactory(threadpoolexecutor.getThreadFactory()).build());
        threadpoolexecutor = Executors.unconfigurableExecutorService(threadpoolexecutor);
        addDelayedShutdownHook(threadpoolexecutor, l, timeunit);
        return threadpoolexecutor;
    }

    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledthreadpoolexecutor)
    {
        return getExitingScheduledExecutorService(scheduledthreadpoolexecutor, 120L, TimeUnit.SECONDS);
    }

    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledthreadpoolexecutor, long l, TimeUnit timeunit)
    {
        scheduledthreadpoolexecutor.setThreadFactory((new ThreadFactoryBuilder()).setDaemon(true).setThreadFactory(scheduledthreadpoolexecutor.getThreadFactory()).build());
        scheduledthreadpoolexecutor = Executors.unconfigurableScheduledExecutorService(scheduledthreadpoolexecutor);
        addDelayedShutdownHook(scheduledthreadpoolexecutor, l, timeunit);
        return scheduledthreadpoolexecutor;
    }

    public static ExecutorService sameThreadExecutor()
    {
        return new SameThreadExecutorService();
    }
}
