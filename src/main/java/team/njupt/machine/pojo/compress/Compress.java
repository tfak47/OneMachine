package team.njupt.machine.pojo.compress;

import org.springframework.stereotype.Service;

@Service
public class Compress {
    private double rate;
    private long runTime;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }
}
