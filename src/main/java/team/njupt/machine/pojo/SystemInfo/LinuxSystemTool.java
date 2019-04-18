package team.njupt.machine.pojo.SystemInfo;

import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.StringTokenizer;

public final class LinuxSystemTool {
    /**
     * get memory by used info
     *
     * @return int[] result
     * result.length==4;int[0]=MemTotal;int[1]=MemFree;int[2]=SwapTotal;int[3]=SwapFree;
     * @throws IOException
     * @throws InterruptedException
     */


    public static int[] getMemInfo() throws IOException, InterruptedException
    {
        File file = new File("/proc/meminfo");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)));
        int [] result=new int[4];
        String str = null ;
        StringTokenizer token = null ;
        //读取到文件末尾结束
        while ((str=br.readLine())!= null )
        {
            //如果str截取后为空，continue，例如“   ”
            token=new StringTokenizer(str);
            if (!token.hasMoreTokens())
                continue ;
            //将截取的第一个字符串赋值给str
            str=token.nextToken();
            //貌似多余，方法开始就排除了截取为空的字符串，避免错误，不做删除处理
            if (!token.hasMoreTokens())
                continue ;
            //匹配截取的首个字符串
            if (str.equalsIgnoreCase("MemTotal:"))
                result[0]=Integer.parseInt(token.nextToken());
            else  if (str.equalsIgnoreCase("MemFree:"))
                result[1]=Integer.parseInt(token.nextToken());
            else  if (str.equalsIgnoreCase("SwapTotal:"))
                result[2]=Integer.parseInt(token.nextToken());
            else  if (str.equalsIgnoreCase("SwapFree:"))
                result[3]=Integer.parseInt(token.nextToken());
            else
                continue;
        }
        br.close();
        return result;
    }

    /**
     * get memory by used info
     *
     * @return float efficiency
     * @throws IOException
     * @throws InterruptedException
     */
    public  static  double getCpuInfo() throws IOException, InterruptedException
    {
        File file = new File( "/proc/stat" );
        BufferedReader br = new BufferedReader( new InputStreamReader(
                new FileInputStream(file)));
//        String str = null ;
        //遍历整个文件
       /* while ((str=br.readLine())!= null){
            //第一步对获取的token进行前三个字符匹配，若不为cpu，跳出内部while
            StringTokenizer token1 = new StringTokenizer(str);
            //如果str截取后为空，continue，例如“   ”
            if (!token1.hasMoreTokens())
                continue ;
            String strSub=token1.nextToken();
            //判断token1截取的第一个字符串是否前三位是否为cpu，是进入while
            while(strSub.substring(0,3).equalsIgnoreCase("cpu")){
                int user1 = Integer.parseInt(token1.nextToken());
                int nice1 = Integer.parseInt(token1.nextToken());
                int sys1 = Integer.parseInt(token1.nextToken());
                int idle1 = Integer.parseInt(token1.nextToken());

                Thread.sleep(1000 );

            }
        }*/


        StringTokenizer token = new StringTokenizer(br.readLine());
        token.nextToken();
        double user1 = Double.parseDouble(token.nextToken());
        double nice1 = Double.parseDouble(token.nextToken());
        double sys1 = Double.parseDouble(token.nextToken());
        double idle1 = Double.parseDouble(token.nextToken());
        double iowait=Double.parseDouble(token.nextToken());
        double irq=Double.parseDouble(token.nextToken());
        double softirq=Double.parseDouble(token.nextToken());
        br.close();
        Thread.sleep(1000 );


        BufferedReader br1 = new BufferedReader(
                new InputStreamReader( new FileInputStream(file)));
        token = new StringTokenizer(br1.readLine());
        token.nextToken();
        double user2 = Double.parseDouble(token.nextToken());
        double nice2 = Double.parseDouble(token.nextToken());
        double sys2 = Double.parseDouble(token.nextToken());
        double idle2 = Double.parseDouble(token.nextToken());
        double iowait2=Double.parseDouble(token.nextToken());
        double irq2=Double.parseDouble(token.nextToken());
        double softirq2=Double.parseDouble(token.nextToken());
        br1.close();
//  cpu usage=[(user_2 +sys_2+nice_2) - (user_1 + sys_1+nice_1)]/(total_2 - total_1)*100
//  total_0=USER[0]+NICE[0]+SYSTEM[0]+IDLE[0]+IOWAIT[0]+IRQ[0]+SOFTIRQ[0]
//  total_1=USER[1]+NICE[1]+SYSTEM[1]+IDLE[1]+IOWAIT[1]+IRQ[1]+SOFTIRQ[1]
//        TODO 保留三位小数
        return ((user2+sys2+nice2)-(user1+sys1+nice1))/((user2+nice2+sys2+idle2+iowait2+irq2+softirq2)-(user1+nice1+sys1+idle1+iowait+irq+softirq))*100;
    }

}
