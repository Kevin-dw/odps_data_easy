package com.kevin;

import com.alibaba.fastjson.JSONArray;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author kevin
 * @version 1.0
 * @Email kevin_dw@163.com
 * @date 2022/5/24 20:58
 * @desc
 */
public class Runner {
    public static void main(String[] args) throws IOException, WriteException {
        // 原始数据目录
        List<String> files = getAllFile("D:\\system\\桌面\\车辆\\data");

        int reCnt = 0;
        int fileNum = 1;
        for (String file : files) {
            // 读取文件
            InputStream inputStream = new FileInputStream(file);
            String data = readFromInputStream(inputStream);
            // 解析JSON
            String dataStr = JSONUtils.parseObject(data).get("data").toString();
            // 获取数据
            String bodyStr = JSONUtils.parseObject(dataStr).get("body").toString();
            JSONArray jsonArray = JSONUtils.parseArray(bodyStr);
            jsonArray.stream().forEach(jsonArr -> {
                JSONArray array = JSONUtils.parseArray(jsonArr.toString());
                for (Object o : array) {
                    String value = o.toString().replace("\\N", "");
                    try {
                        System.out.print(new String(value.getBytes(), "utf-8") + "\t");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
            });
            //System.out.println("第" + (fileNum++) + "个文件，    总共" + jsonArray.size() + "条记录。  文件：" + file + "");
        }
        //System.out.println("-----------------------------------------------------");
        //System.out.println("解析完成！！！总共解析" + reCnt + "条记录");
    }

    /**
     * 从输入流中读取
     *
     * @param inputStream 输入流
     * @return {@link String}
     * @throws IOException ioexception
     */
    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }



    /**
     * 获取路径下的所有文件/文件夹
     *
     * @param directoryPath 需要遍历的文件夹路径
     * @return
     */
    public static List<String> getAllFile(String directoryPath) {
        List<String> list = new ArrayList<>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(getAllFile(file.getAbsolutePath()));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

}
