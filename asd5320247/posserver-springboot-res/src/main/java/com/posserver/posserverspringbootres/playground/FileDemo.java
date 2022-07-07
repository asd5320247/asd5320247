package com.posserver.posserverspringbootres.playground;


import javafx.scene.image.Image;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileDemo {

    private static int width = 960;
    private static int line_height = 24;
    private static Font font = new Font("宋体",Font.PLAIN,14);

    public static void main(String[] args) throws Exception {
        FileDemo fileDemo = new FileDemo();
        String str = fileDemo.strArr();
        fileDemo.createImage(str);
//        fileDemo.FileTest1();
//        fileDemo.FileTest2();
//        fileDemo.FileTest3();
//        fileDemo.FileTest4();
//        fileDemo.FileTest5();
    }




    //创建一个txt文件
    public void FileTest1() throws IOException {
        File f1 = new File("src\\aa.txt");
        if (f1.exists()) {
            System.out.println("文件已存在");
        } else {
            f1.createNewFile();
            System.out.println("文件创建成功");
        }
        System.out.println("文件已经存在" + f1.exists());
        System.out.println("文件的名字" + f1.getName());
        System.out.println("文件路径" + f1.getPath());
        System.out.println("文件绝对路径" + f1.getAbsolutePath());
    }

    //在txt文件中写入内容
    public void FileTest2() throws FileNotFoundException {
        File file = new File("src\\aa.txt");
        FileOutputStream f1 = new FileOutputStream(file);
        String str = "I Love Java";
        byte[] buff = str.getBytes();
        try {
            f1.write(buff);
        } catch (Exception e) {

        } finally {
            try {
                f1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取txt文件内容,注意这里的fileinputstream方法只能读取英文，无法独去中文内容。
    public void FileTest3() throws IOException {
        File file = new File("src\\aa.txt");
        FileInputStream f1 = new FileInputStream(file);
        for (int i = 0; i < file.length(); i++) {
            char ch = (char) f1.read();
            System.out.print(ch);
        }
        f1.close();
    }

    //使用FileWrite类和BufferedWriter类写入内容，就可以写入中文内容。
    public void FileTest4() throws IOException {
        String[] str = {"爱你孤身走暗巷，", "爱你不贵的摸样，", "爱你和我那么像，", "缺口都一样，"};
        File file = new File("src\\bb.txt");
        FileWriter f1 = null;
        BufferedWriter f2 = null;

        f1 = new FileWriter(file);
        f2 = new BufferedWriter(f1);

        for (int i = 0; i < str.length; i++) {
            f2.write(str[i]);
            f2.newLine();
        }
        f2.close();
        f1.close();
    }

    //使用FileReader和BufferedReader类进行读取就可以读取中文类
    public void FileTest5() throws IOException {
        File file = new File("src\\bb.txt");
        FileReader f1 = null;
        BufferedReader b1 = null;

        f1 = new FileReader(file);
        b1 = new BufferedReader(f1);
        String str = null;
        while ((str = b1.readLine()) != null) {
            System.out.println(str);
        }
        b1.close();
        f1.close();
    }

    public String strArr() throws IOException {

        File file = new File("src\\bb.txt");
        FileReader f1 = null;
        BufferedReader b1 = null;

        f1 = new FileReader(file);
        b1 = new BufferedReader(f1);
        String str = null;
        String str1 = null;
        while ((str = b1.readLine()) != null) {
            str1 = str1+str;
        }
        b1.close();
        f1.close();
        return str1;
    }


    public static void createImage(String strArr) throws Exception {


        FontMetrics fm = FontDesignMetrics.getMetrics(font);
        int stringWidth = fm.charWidth('字');// 标点符号也算一个字
        //计算每行多少字 = 宽/每个字占用的宽度
        int line_string_num = width % stringWidth == 0 ? (width / stringWidth) : (width / stringWidth) + 1;

        System.out.println("每行字数=" + line_string_num);
        //将数组转为list
        List<String> strList = new ArrayList<>(Arrays.asList(strArr));

        //按照每行多少个字进行分割
        for (int j = 0; j < strList.size(); j++) {
            //当字数超过限制，就进行分割
            if (strList.get(j).length() > line_string_num) {
                //将多的那一端放入本行下一行，等待下一个循环处理
                strList.add(j + 1, strList.get(j).substring(line_string_num));
                //更新本行的内容
                strList.set(j, strList.get(j).substring(0, line_string_num));
            }
        }

        //计算图片的高度，多预留一行
        int image_height = strList.size() * line_height + line_height;

        //每张图片有多少行文字
        int every_line = image_height / line_height;


        for (int m = 0; m < 1; m++) {
            String filePath = "E:\\新建文件夹\\" + m + ".jpg";
            File outFile = new File(filePath);
            // 创建图片  宽度多预留一点
            BufferedImage image = new BufferedImage(width + 20, image_height,
                    BufferedImage.TYPE_INT_BGR);
            Graphics g = image.getGraphics();
            g.setClip(0, 0, width + 20, image_height);
            g.setColor(Color.white); // 背景色白色
            g.fillRect(0, 0, width + 20, image_height);

            g.setColor(Color.black);//  字体颜色黑色
            g.setFont(font);// 设置画笔字体

            // 每张多少行，当到最后一张时判断是否填充满
            for (int i = 0; i < every_line; i++) {
                int index = i + m * every_line;
                if (strList.size() - 1 >= index) {
//                    System.out.println("每行实际=" + newList.get(index).length());
                    g.drawString(strList.get(index), 0, line_height * (i + 1));
                }
            }
            g.dispose();
            ImageIO.write(image, "jpg", outFile);// 输出png图片
        }
    }
}
