package team.njupt.machine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.njupt.machine.pojo.SystemInfo.LinuxCpuInfo;
import team.njupt.machine.pojo.SystemInfo.LinuxMerroyInfo;
import team.njupt.machine.pojo.SystemInfo.LinuxSystemTool;

import java.io.*;
import java.util.ArrayList;

@CrossOrigin()
//支持跨域请求
@Controller
@RequestMapping("/SysInfo/")
public class SystemInfoController {

    @Autowired
    private LinuxMerroyInfo linuxMerroyInfo;


    /**
     * 获取服务器内存基本信息
     * @return useRate内存使用率
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "merryInfo")
    @ResponseBody
    public LinuxMerroyInfo merroyMsg()throws IOException, InterruptedException{
        int[] result=new int[4];
        double useRate;
        for(int i=0;i<4;i++){
            result[i]=LinuxSystemTool.getMemInfo()[i];
        }

        linuxMerroyInfo.setMemTotal(result[0]);
        linuxMerroyInfo.setMemFree(result[1]);
        linuxMerroyInfo.setSwapTotal(result[2]);
        linuxMerroyInfo.setSwapFree(result[3]);
//        保留两位小数
        useRate=1-(double)result[1]/(double)result[0];
        useRate=(double)Math.round(useRate*100)/100;
        linuxMerroyInfo.setUseRate(useRate);
        return linuxMerroyInfo;
    }

    @Autowired
    private LinuxCpuInfo linuxCpuInfo;

    @RequestMapping(value = "cpuInfo")
    @ResponseBody
    public LinuxCpuInfo cpuMsg()throws IOException, InterruptedException{
        float[] cpuIdi={1,2,3};
//        System.out.println(LinuxSystemTool.getCpuInfo());
        linuxCpuInfo.setCpuId(cpuIdi);
        return linuxCpuInfo;
    }
}
