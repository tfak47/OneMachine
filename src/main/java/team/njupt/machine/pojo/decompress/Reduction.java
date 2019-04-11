package team.njupt.machine.pojo.decompress;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Reduction {
    private static int vec_size = 1 <<18;
    private static String identifier;

    private static String timeInfo;
    private static String integerLongitude;
    private static String decimalLongitude;
    private static String integerLatitude;
    private static String decimalLatitude;

    private static int time_count_tar;
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

    private static int longitude_difference_len;
    private static int latitude_difference_len;
    private static int[] longitude_difference = new int[vec_size];
    private static int[] latitude_difference = new int[vec_size];

    public static void readCompressedFile(String path) {
        BufferedReader br = null;
        File file = new File(path);

        try {
            br = new BufferedReader(new FileReader(file));
            identifier = br.readLine();
            timeInfo = br.readLine();
            integerLongitude = br.readLine();
            decimalLongitude = br.readLine();
            integerLatitude = br.readLine();
            decimalLatitude = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (file.exists()) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void timeReduction() {
        time_count_tar = 0;

        String[] time = timeInfo.split(",");
        time_info_tar[0] = time[0];
        time_len = Integer.valueOf(time[1]);
        for (int i = 0; i < time_len; i ++) {
            time_start[i] = Integer.valueOf(time[(2 * i + 2)]);
            time_length[i] = Integer.valueOf(time[(2 * i + 3)]);
        }

        //还原line_break_vec和line_break_len
        for (int i = 0; i < time_len; i ++) {
            for (int j = 0; j < time_length[i]; j ++) {
                time_difference[time_count_tar ++] = time_start[i];
            }
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (int i = 1; i < time_count_tar; i ++) {
                time_info_tar[i] = df.format(df.parse(time_info_tar[i - 1]).getTime() + time_difference[i] * 1000);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void longitudeReduction() {
        //经度整数部分
        integer_longitude_tar_len = 0;
        String[] integer = integerLongitude.split(",");
        int integer_len = Integer.valueOf(integer[0]);
        for (int i = 0; i < integer_len; i ++) {
            integer_longitude_tar_start[i] = Integer.valueOf(integer[2 * i + 1]);
            integer_longitude_tar_length[i] = Integer.valueOf(integer[2 * i + 2]);
        }
        for (int i = 0; i < integer_len; i ++) {
            for (int j = 0; j < integer_longitude_tar_length[i]; j ++) {
                integer_longitude_tar[integer_longitude_tar_len ++] = integer_longitude_tar_start[i];
            }
        }
        //经度小数部分
        String[] decimal = decimalLongitude.split(",");
        decimal_longitude_tar[0] = decimal[0];
        String temp = decimal_longitude_tar[0];
        longitude_difference_len = Integer.valueOf(decimal[1]);
        for (int i = 0; i < longitude_difference_len; i ++) {
            longitude_difference[i] = Integer.valueOf(decimal[i + 2]);
        }
        for (int i = 1; i < longitude_difference_len; i ++) {
            temp = String.valueOf(Integer.valueOf(temp) + longitude_difference[i]);
            decimal_longitude_tar[i] = temp;
            while (decimal_longitude_tar[i].length() < 5) {
                decimal_longitude_tar[i] = "0" + decimal_longitude_tar[i];
            }
            while ((decimal_longitude_tar[i].substring(decimal_longitude_tar[i].length() - 1)) .equals("0")) {
                decimal_longitude_tar[i] = decimal_longitude_tar[i].substring(0, decimal_longitude_tar[i].length() - 1);
            }
        }
    }

    public static void latitudeReduction() {
        //纬度整数部分
        integer_latitude_tar_len = 0;
        String[] integer = integerLatitude.split(",");
        int integer_len = Integer.valueOf(integer[0]);
        for (int i = 0; i < integer_len; i ++) {
            integer_latitude_tar_start[i] = Integer.valueOf(integer[2 * i + 1]);
            integer_latitude_tar_length[i] = Integer.valueOf(integer[2 * i + 2]);
        }
        for (int i = 0; i < integer_len; i ++) {
            for (int j = 0; j < integer_latitude_tar_length[i]; j ++) {
                integer_latitude_tar[integer_latitude_tar_len ++] = integer_latitude_tar_start[i];
            }
        }
        //纬度小数部分
        String[] decimal = decimalLatitude.split(",");
        decimal_latitude_tar[0] = decimal[0];
        String temp = decimal_latitude_tar[0];
        latitude_difference_len = Integer.valueOf(decimal[1]);
        for (int i = 0; i < latitude_difference_len; i ++) {
            latitude_difference[i] = Integer.valueOf(decimal[i + 2]);
        }
        for (int i = 1; i < latitude_difference_len; i ++) {
            temp = String.valueOf(Integer.valueOf(temp) + latitude_difference[i]);
            decimal_latitude_tar[i] = temp;
            while (decimal_latitude_tar[i].length() < 5) {
                decimal_latitude_tar[i] = "0" + decimal_latitude_tar[i];
            }
            while ((decimal_latitude_tar[i].substring(decimal_latitude_tar[i].length() - 1)) .equals("0")) {
                decimal_latitude_tar[i] = decimal_latitude_tar[i].substring(0, decimal_latitude_tar[i].length() - 1);
            }
        }
    }

    public static void writeToFile(String path) {
        File file = new File(path);
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(identifier);
            bw.write("\n");
            for (int i = 0; i < time_count_tar; i ++) {
                bw.write(time_info_tar[i] + "," +
                        integer_longitude_tar[i] + "." +
                        decimal_longitude_tar[i] + "," +
                        integer_latitude_tar[i] + "." +
                        decimal_latitude_tar[i] + "\n");
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
