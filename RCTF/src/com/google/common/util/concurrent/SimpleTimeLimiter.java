// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// Referenced classes of package com.google.common.util.concurrent:
//            TimeLimiter, Futures, UncheckedTimeoutException

public final class SimpleTimeLimiter
    implements TimeLimiter
{

    private final ExecutorService executor;

    public SimpleTimeLimiter()
    {
        this(Executors.newCachedThreadPool());
    }

    public SimpleTimeLimiter(ExecutorService executorservice)
    {
        executor = (ExecutorService)Preconditions.checkNotNull(executorservice);
    }

    private static boolean declaresInterruptedEx(Method method)
    {
        method = method.getExceptionTypes();
        int j = method.length;
        for (int i = 0; i < j; i++)
        {
            if (method[i] == java/lang/InterruptedException)
            {
                return true;
            }
        }

        return false;
    }

    private static Set findInterruptibleMethods(Class class1)
    {
        java.util.HashSet hashset = Sets.newHashSet();
        class1 = class1.getMethods();
        int j = class1.length;
        for (int i = 0; i < j; i++)
        {
            Method method = class1[i];
            if (declaresInterruptedEx(method))
            {
                hashset.add(method);
            }
        }

        return hashset;
    }

    private static Object newProxy(Class class1, InvocationHandler invocationhandler)
    {
        return class1.cast(Proxy.newProxyInstance(class1.getClassLoader(), new Class[] {
            class1
        }, invocationhandler));
    }

    public Object callWithTimeout(Callable callable, long l, TimeUnit timeunit, boolean flag)
        throws Exception
    {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeunit);
        boolean flag1;
        if (l > 0L)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        Preconditions.checkArgument(flag1, "timeout must be positive: %s", new Object[] {
            Long.valueOf(l)
        });
        callable = executor.submit(callable);
        if (!flag)
        {
            break MISSING_BLOCK_LABEL_94;
        }
        timeunit = ((TimeUnit) (callable.get(l, timeunit)));
        return timeunit;
        timeunit;
        try
        {
            callable.cancel(true);
            throw timeunit;
        }
        // Misplaced declaration of an exception variable
        catch (Callable callable)
        {
            throw Throwables.throwCause(callable, true);
        }
        // Misplaced declaration of an exception variable
        catch (TimeUnit timeunit)
        {
            callable.cancel(true);
        }
        break MISSING_BLOCK_LABEL_121;
        timeunit = ((TimeUnit) (Futures.makeUninterruptible(callable).get(l, timeunit)));
        return timeunit;
        throw new UncheckedTimeoutException(timeunit);
    }

    public Object newProxy(final Object target, Class class1, final long timeoutDuration, final TimeUnit timeoutUnit)
    {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(class1);
        Preconditions.checkNotNull(timeoutUnit);
        boolean flag;
        if (timeoutDuration > 0L)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkArgument(flag, (new StringBuilder()).append("bad timeout: ").append(timeoutDuration).toString());
        Preconditions.checkArgument(class1.isInterface(), "interfaceType must be an interface type");
        return newProxy(class1, new InvocationHandler() {

            final SimpleTimeLimiter this$0;
            final Set val$interruptibleMethods;
            final Object val$target;
            final long val$timeoutDuration;
            final TimeUnit val$timeoutUnit;

            public Object invoke(Object obj, final Method method, Object aobj[])
                throws Throwable
            {
                obj = ((_cls1) (aobj)). new Callable() {

                    final _cls1 this$1;
                    final Object val$args[];
                    final Method val$method;

                    public Object call()
                        throws Exception
                    {
                        Object obj;
                        try
                        {
                            obj = method.invoke(target, args);
                        }
                        catch (InvocationTargetException invocationtargetexception)
                        {
                            Throwables.throwCause(invocationtargetexception, false);
                            throw new AssertionError("can't get here");
                        }
                        return obj;
                    }

            
            {
                this$1 = final__pcls1;
                method = method1;
                args = _5B_Ljava.lang.Object_3B_.this;
                super();
            }
                };
                return callWithTimeout(((Callable) (obj)), timeoutDuration, timeoutUnit, interruptibleMethods.contains(method));
            }

            
            {
                this$0 = SimpleTimeLimiter.this;
                target = obj;
                timeoutDuration = l;
                timeoutUnit = timeunit;
                interruptibleMethods = set;
                super();
            }
        });
    }
}
