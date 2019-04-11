package team.njupt.machine.pojo.compress;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataExtract {
    private static int vec_size = 1 <<18;
    private static int COMMA = 44;

    private static String identifier;

    private static int count_tar;
    private static String[] time_info_tar = new String[vec_size];
    private static int[] integer_longitude_tar = new int[vec_size];
    private static String[] decimal_longitude_tar = new String[vec_size];
    private static int[] integer_latitude_tar = new int[vec_size];
    private static String[] decimal_latitude_tar = new String[vec_size];

    private static int time_len;
    private static int[] time_difference = new int[vec_size];
    private static int[] time_start = new int[vec_size];
    private static int[] time_length = new int[vec_size];

    private static int integer_longitude_tar_len;
    private static int[] integer_longitude_tar_start = new int[vec_size];
    private static int[] integer_longitude_tar_length = new int[vec_size];

    private static int integer_latitude_tar_len;
    private static int[] integer_latitude_tar_start = new int[vec_size];
    private static int[] integer_latitude_tar_length = new int[vec_size];

    private static int[] longitude_difference = new int[vec_size];
    private static int[] latitude_difference = new int[vec_size];

    public static void readFile(File file) {
        BufferedReader br = null;
        String info;
        String[] temp_info;
        count_tar = 0;
        String temp;

        try {
            br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file))));
            identifier = br.readLine();
            while ((info = br.readLine()) != null) {
                temp_info = info.split(",");

                time_info_tar[count_tar] = temp_info[0];

                integer_longitude_tar[count_tar] = Integer.parseInt(temp_info[1].split("\\.")[0]);
                temp = temp_info[1].split("\\.")[1];
                while (temp.length() < 5) {
                    temp = temp + "0";
                }
                decimal_longitude_tar[count_tar] = temp;

                integer_latitude_tar[count_tar] = Integer.parseInt(temp_info[2].split("\\.")[0]);
                temp = temp_info[2].split("\\.")[1];
                while (temp.length() < 5) {
                    temp = temp + "0";
                }
                decimal_latitude_tar[count_tar ++] = temp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void extractTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time_len = 0;

        //计算存储时间差
        time_difference[0] = 0;
        try {
            for (int i = 1; i < count_tar; i ++) {
                time_difference[i] = (int)(df.parse(time_info_tar[i]).getTime() - df.parse(time_info_tar[i -1]).getTime()) / 1000;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (count_tar > 0) {
            int cnt = 1;
            time_start[time_len] = time_difference[0];
            for (int i = 1; i < count_tar; i ++) {
                if (time_start[time_len] == time_difference[i]) {
                    cnt ++;
                } else {
                    time_length[time_len ++] = cnt;
                    time_start[time_len] = time_difference[i];
                    cnt = 1;
                }
            }
            time_length[time_len ++] = cnt;
        }
    }

    public static void extractLongitude() {
        //生成整数部分integer_longitude[start, length]，长度为integer_longitude_len
        integer_longitude_tar_len = 0;
        int cnt = 1;
        integer_longitude_tar_start[integer_longitude_tar_len] = integer_longitude_tar[0];
        for (int i = 1; i < count_tar; i ++) {
            if (integer_longitude_tar_start[integer_longitude_tar_len] == integer_longitude_tar[i]) {
                cnt ++;
            } else {
                integer_longitude_tar_length[integer_longitude_tar_len ++] = cnt;
                integer_longitude_tar_start[integer_longitude_tar_len] = integer_longitude_tar[i];
                cnt = 1;
            }
        }
        integer_longitude_tar_length[integer_longitude_tar_len ++] = cnt;

        //小数部分记录相邻的差值
        longitude_difference[0] = 0;
        for (int i = 1; i < count_tar; i ++) {
            longitude_difference[i] = Integer.valueOf(decimal_longitude_tar[i]) - Integer.valueOf(decimal_longitude_tar[i -1]);
        }
    }

    public static void extractLatitude() {
        //生成整数部分integer_latitude[start, length]，长度为integer_latitude_len
        integer_latitude_tar_len = 0;
        int cnt = 1;
        integer_latitude_tar_start[integer_latitude_tar_len] = integer_latitude_tar[0];
        for (int i = 1; i < count_tar; i ++) {
            if (integer_latitude_tar_start[integer_latitude_tar_len] == integer_latitude_tar[i]) {
                cnt ++;
            } else {
                integer_latitude_tar_length[integer_latitude_tar_len ++] = cnt;
                integer_latitude_tar_start[integer_latitude_tar_len] = integer_latitude_tar[i];
                cnt = 1;
            }
        }
        integer_latitude_tar_length[integer_latitude_tar_len ++] = cnt;

        //小数部分记录相邻的差值
        latitude_difference[0] = 0;
        for (int i = 1; i < count_tar; i ++) {
            latitude_difference[i] = Integer.valueOf(decimal_latitude_tar[i]) - Integer.valueOf(decimal_latitude_tar[i -1]);
        }
    }

    public static void saveData(File file) {
        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(file, true));

            //标识文件
            bw.write(String.valueOf(identifier));
            bw.write("\n");

            //时间信息
            bw.write(String.valueOf(time_info_tar[0]));
            bw.write(COMMA);
            bw.write(String.valueOf(time_len));
            bw.write(COMMA);
            for (int i = 0; i < time_len; i ++) {
                bw.write(String.valueOf(time_start[i]));
                bw.write(COMMA);
                bw.write(String.valueOf(time_length[i]));
                bw.write(COMMA);
            }
            bw.write("\n");

            //经度整数部分
            bw.write(String.valueOf(integer_longitude_tar_len));
            bw.write(COMMA);
            for (int i = 0; i < integer_longitude_tar_len; i ++) {
                bw.write(String.valueOf(integer_longitude_tar_start[i]));
                bw.write(COMMA);
                bw.write(String.valueOf(integer_longitude_tar_length[i]));
                bw.write(COMMA);
            }
            bw.write("\n");

            //经度小数部分
            bw.write(String.valueOf(decimal_longitude_tar[0]));
            bw.write(COMMA);
            bw.write(String.valueOf(count_tar));
            bw.write(COMMA);
            for (int i = 0; i < count_tar; i ++) {
                bw.write(String.valueOf(longitude_difference[i]));
                bw.write(COMMA);
            }
            bw.write("\n");

            //纬度整数部分
            bw.write(String.valueOf(integer_latitude_tar_len));
            bw.write(COMMA);
            for (int i = 0; i < integer_latitude_tar_len; i ++) {
                bw.write(String.valueOf(integer_latitude_tar_start[i]));
                bw.write(COMMA);
                bw.write(String.valueOf(integer_latitude_tar_length[i]));
                bw.write(COMMA);
            }
            bw.write("\n");

            //纬度小数部分
            bw.write(String.valueOf(decimal_latitude_tar[0]));
            bw.write(COMMA);
            bw.write(String.valueOf(count_tar));
            bw.write(COMMA);
            for (int i = 0; i < count_tar; i ++) {
                bw.write(String.valueOf(latitude_difference[i]));
                bw.write(COMMA);
            }
            bw.write("\n");

            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
