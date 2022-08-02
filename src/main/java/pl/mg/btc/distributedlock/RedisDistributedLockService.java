package pl.mg.btc.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
@Slf4j
public class RedisDistributedLockService implements DistributedLockService {

    private static final String MY_LOCK_KEY = "btc_lock_key";
    private static final String PARAMETER_LOCK_KEY = "call_";

    private final LockRegistry lockRegistry;

    public RedisDistributedLockService(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }

    @Override
    public String lock() {
        Lock lock;
        try {
            lock = lockRegistry.obtain(MY_LOCK_KEY);
        } catch (Exception e) {
            // in a production environment this should be a log statement
            System.out.printf("Unable to obtain lock: %s%n", MY_LOCK_KEY);
            return "invalid lock object";
        }
        String returnVal = null;
        try {
            if (lock.tryLock()) {
                returnVal = "jdbc lock successful";
                Thread.sleep(10000);
            } else {
                returnVal = "jdbc lock unsuccessful";
            }
        } catch (Exception e) {
            // in a production environment this should log and do something else
            e.printStackTrace();
        } finally {
            // always have this in a `finally` block in case anything goes wrong
            try {
                lock.unlock();
            } catch (IllegalStateException e) {
                System.out.println("lock not owned");
            }
        }
        return returnVal;
    }

    @Override
    public String lockWithParam(int callId) {
        Lock lock;
        try {
            lock = lockRegistry.obtain(PARAMETER_LOCK_KEY + callId);
        } catch (Exception e) {
            System.out.printf("Unable to obtain lock: %s%n", PARAMETER_LOCK_KEY + callId);
            return "invalid lock object";
        }
        String returnVal = null;
        try {
            if (lock.tryLock()) {
                returnVal = "distributed lock successful";
                Thread.sleep(10000);
            } else {
                returnVal = "distributed lock unsuccessful";
            }
        } catch (Exception e) {
            // in a production environment this should log and do something else
            e.printStackTrace();
        } finally {
            // always have this in a `finally` block in case anything goes wrong
            try {
                lock.unlock();
            } catch (IllegalStateException e) {
                System.out.println("lock not owned");
            }
        }
        return returnVal;
    }

    @Override
    public void failLock() {
        var executor = Executors.newFixedThreadPool(2);
        Runnable lockThreadOne = new LockRunner();

        Runnable lockThreadTwo = new LockRunner();
        executor.submit(lockThreadOne);
        executor.submit(lockThreadTwo);
        executor.shutdown();
    }

    private class LockRunner implements Runnable {

        @Override
        public void run() {
            UUID uuid = UUID.randomUUID();
            var lock = lockRegistry.obtain(MY_LOCK_KEY);
            try {
                System.out.println("Attempting to lock with thread: " + uuid);
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("Locked with thread: " + uuid);
                    Thread.sleep(5000);
                } else {
                    System.out.println("failed to lock with thread: " + uuid);
                }
            } catch (Exception e0) {
                System.out.println("exception thrown with thread: " + uuid);
            } finally {
                lock.unlock();
                System.out.println("unlocked with thread: " + uuid);
            }
        }
    }

}
