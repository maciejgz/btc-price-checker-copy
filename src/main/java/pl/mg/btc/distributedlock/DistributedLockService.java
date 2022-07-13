package pl.mg.btc.distributedlock;

public interface DistributedLockService {

    String lock();

    String lockWithParam(int callId);

    void failLock();
}
