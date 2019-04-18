package team.njupt.machine.pojo.SystemInfo;

import org.springframework.stereotype.Service;

@Service
public class LinuxCpuInfo {
    private float[] cpuId;

    public float[] getCpuId() {
        return cpuId;
    }

    public void setCpuId(float[] cpuId) {
        this.cpuId=cpuId;
    }
}





