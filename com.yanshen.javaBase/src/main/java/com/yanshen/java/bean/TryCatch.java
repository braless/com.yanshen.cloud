package com.yanshen.java.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-16 10:05
 **/
public class TryCatch {

    public static void main(String[] args) {

       keyBoard();
    }


    public static int f(int value) {
        try {
            return value * value;
        } finally {
            if (value == 2) {
                return 0;
            }
        }
    }

    /**
     * 虚拟机停止 finally 不会执行
     */
    public static  void stopVm(){
        try {
            System.out.println("Try to do something");
            throw new RuntimeException("RuntimeException");
        } catch (Exception e) {
            System.out.println("Catch Exception -> " + e.getMessage());
            // 终止当前正在运行的Java虚拟机
            System.exit(1);
        } finally {
            System.out.println("Finally");
        }

    }

    /**
     * try catch 执行流程
     */
    public static  void  tryTest(){
        try {
            System.out.println(f(2));
            System.out.println("Try to do something");
            throw new RuntimeException("RuntimeException");
        } catch (Exception e) {
            System.out.println("Catch Exception -> " + e.getMessage());
        } finally {
            System.out.println("Finally");
        }
    }

    /**
     * 读取文件
     */
    private static  void readFile(){
        //读取文本文件的内容
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("F://read.txt"));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

    }

    /**
     * 获取键盘输入
     */
    private static  void keyBoard(){
        while (true){
            Scanner input = new Scanner(System.in);
            //BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            String s  = input.nextLine();
            System.out.println("获取到键盘的输入是: "+s);
            if (s.equals("exit")){
                System.out.println("退出应用");
                break;
            }
        }

    }
}
