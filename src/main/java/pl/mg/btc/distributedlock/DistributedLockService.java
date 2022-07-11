package pl.mg.btc.distributedlock;

public interface DistributedLockService {

    String lock();

    void failLock();
}
