package com.example.admin.myapplication;

public class Abc {
    private  static int count = 0;
    public static void main(String args[]){
//        System.out.println("hello world one!");
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                System.out.println("hello world two!");
//            }
//        };
//        thread.start();
//
//        for (int i = 0; i < 10 ;i++) {
//            final int j = i;
//            new Thread(){
//                @Override
//                public void run() {
//                    System.out.println("  "+ j);
//                }
//            }.start();
//        }

        (new Thread(){
           @Override
           public void run(){
               System.out.println("hello world1");
               while (true){
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   countCar();
               }
           }
        }).start();

        (new Thread(){
            @Override
            public void run() {
                while (true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    printCar();
                }

            }
        }).start();
    }
    synchronized static void printCar(){
        if (count != 0){
            System.out.println("过去的车辆数=" + count);
            count = 0;
        }

    }
    synchronized static void countCar(){
        count++;
        System.out.println("又过了一辆车");
    }
}
