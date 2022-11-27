package com.hongsheng.common;

public class MathRandom {
    /**
     * Math.random()是令系统随机选取大于等于 0.0 且小于 1.0 的伪随机 double 值
     * 公式：Math.random()*(n-m)+m，生成大于等于m小于n的随机数；
     *
     * 例如：定义一个随机1到5(取不到5)的变量 [1,5)
     *
     * int number=(int)(Math.random()*(5-1)+1)；
     * int number = (int)(Math.random()*4+1)；取值正好是[1,5)
     */

    public static void main(String[] args) {
        int number = (int)(Math.random()*4+1);
        System.out.println(number);
        System.out.println(generateVerificationCode());
        System.out.println(Math.random()*9+1);
    }

    public static String generateVerificationCode() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }
}
