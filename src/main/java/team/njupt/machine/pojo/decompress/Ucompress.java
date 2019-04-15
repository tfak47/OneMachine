package team.njupt.machine.pojo.decompress;

import org.springframework.stereotype.Service;

@Service
public class Ucompress {
    private String msg;
    private String printSrc;
    private long runTime;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPrintSrc() {
        return printSrc;
    }

    public void setPrintSrc(String printSrc) {
        this.printSrc = printSrc;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }
}
