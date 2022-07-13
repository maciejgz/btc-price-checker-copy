package pl.mg.btc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.btc.distributedlock.DistributedLockService;

@RestController
@RequestMapping(value = "/lock")
@Slf4j
public class DistributedLockController {

    private final DistributedLockService lockService;

    public DistributedLockController(DistributedLockService lockService) {
        this.lockService = lockService;
    }

    @PutMapping("/lock")
    public String lock() {
        return lockService.lock();
    }

    @PutMapping("/lock/{callId}")
    public String lockWithParam(@PathVariable String callId) {
        return lockService.lockWithParam(DistributedLockController.isNumeric(callId) ? Integer.parseInt(callId) : 0);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    @PutMapping("/failLock")
    public String failLock() {
        lockService.failLock();
        return "fail lock called, output in logs";
    }

}
