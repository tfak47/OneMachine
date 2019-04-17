package team.njupt.machine.pojo.SystemInfo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class LinuxCpuInfo {
    private ArrayList LinuxCpuInfo;

    public ArrayList getLinuxCpuInfo() {
        return LinuxCpuInfo;
    }

    public void setLinuxCpuInfo(ArrayList linuxCpuInfo) {
        LinuxCpuInfo = linuxCpuInfo;
    }
}
