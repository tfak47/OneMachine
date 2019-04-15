package team.njupt.machine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.njupt.machine.pojo.compress.Compress;
import team.njupt.machine.pojo.decompress.Ucompress;

import java.io.File;

import static team.njupt.machine.pojo.compress.DataExtract.*;
import static team.njupt.machine.pojo.compress.ZipCompress.compress;
import static team.njupt.machine.pojo.decompress.Reduction.*;
import static team.njupt.machine.pojo.decompress.Reduction.writeToFile;
import static team.njupt.machine.pojo.decompress.UnZip.unZip;

@CrossOrigin()
//支持跨域请求
@Controller
@RequestMapping("/Compress/")
/**定义了一个Compress类（springMVC无法返回基本数据类型）
 * 压缩
 */
public class CompressController {
    @Autowired
    private Compress cmp;

    /**
     * 压缩
     * @param 带压缩文件路径
     * @return compress类
     */
    @RequestMapping(value = "Start",method = RequestMethod.POST)
    @ResponseBody
    public Compress dataCompress(@RequestParam("src")String srcFilePath){
        String src=srcFilePath.trim();
        String middleFilePath = "/software/MachineData/中间结果.txt";
        //       输出压缩结果
        String resultFilePath = "/software/MachineData/压缩结果.zip";
        double rate;
        long startTime=System.currentTimeMillis();
        File tarFile = new File(src);
        File middleFile = new File(middleFilePath);
        File resultFile = new File(resultFilePath);
        readFile(tarFile);
        extractTime();
        extractLongitude();
        extractLatitude();
        saveData(middleFile);
        compress(middleFilePath, resultFilePath);
        middleFile.delete();
        rate = tarFile.length() / resultFile.length();
        long endTime=System.currentTimeMillis();
        cmp.setRate(rate);
//        压缩运行的毫秒数
        cmp.setRunTime((endTime-startTime));
        return cmp;
    }

    /**
     *解压缩
     * @param 带解压文件路径
     * @return 解压成功
     */
    @Autowired
    private Ucompress ucompress;
    @RequestMapping(value = "rollback",method = RequestMethod.POST)
    @ResponseBody

    public Ucompress dataUnCompress(@RequestParam("src")String compressedPath){

//        String compressedPath = "E:/DataSet/压缩结果.zip";
        String comdPath=compressedPath.trim();
        String middleFilePath = "/software/MachineData";
        String resultFilePath = "/software/MachineData/还原数据.txt";
        long startTime=System.currentTimeMillis();
        unZip(comdPath, middleFilePath);

       readCompressedFile("/software/MachineData/中间结果.txt");
       timeReduction();
       longitudeReduction();
       latitudeReduction();
       writeToFile(resultFilePath);
       long endTime=System.currentTimeMillis();
       //解压时间，单位ms
       ucompress.setRunTime(endTime-startTime);
       ucompress.setMsg("解压成功");
       return ucompress;
    }
}
