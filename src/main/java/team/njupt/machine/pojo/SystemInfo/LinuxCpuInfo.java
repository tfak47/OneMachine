package team.njupt.machine.pojo.SystemInfo;

import org.springframework.stereotype.Service;

@Service
public class LinuxCpuInfo {
 private double usedCpu;

    public double getUsedCpu() {
        return usedCpu;
    }

    public void setUsedCpu(double usedCpu) {
        this.usedCpu = usedCpu;
    }
}





