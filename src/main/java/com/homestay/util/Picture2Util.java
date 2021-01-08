package com.homestay.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Picture2Util {
    // 将图片插入数据库
    public static void readImage2DB() {
        String path = "F:/1.jpg";
        FileInputStream in = null;
        try {
            in = PictureUtil.readImage(path);
            System.out.println(in.available());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // 读取数据库中图片
    public static void readDB2Image() {
        String targetPath = "D:/image/1.png";
        ResultSet rs = null;
        try {
            while (rs.next()) {
                InputStream in = rs.getBinaryStream("photo");
                PictureUtil.readBin2Image(in, targetPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //测试
    public static void main(String[] args) {
        readImage2DB();
        //readDB2Image();
    }
}
