package team.njupt.machine.pojo.SystemInfo;

import org.springframework.stereotype.Service;

@Service
public class LinuxMerroyInfo {
 private int MemTotal;
 private int MemFree;
 private int SwapTotal;
 private int SwapFree;
 private double useRate;
    public int getMemTotal() {
        return MemTotal;
    }

    public void setMemTotal(int memTotal) {
        MemTotal = memTotal;
    }

    public int getMemFree() {
        return MemFree;
    }

    public void setMemFree(int memFree) {
        MemFree = memFree;
    }

    public int getSwapTotal() {
        return SwapTotal;
    }

    public void setSwapTotal(int swapTotal) {
        SwapTotal = swapTotal;
    }

    public int getSwapFree() {
        return SwapFree;
    }

    public void setSwapFree(int swapFree) {
        SwapFree = swapFree;
    }

    public double getUseRate() {
        return useRate;
    }

    public void setUseRate(double useRate) {
        this.useRate = useRate;
    }
}
