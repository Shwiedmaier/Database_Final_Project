package Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package test;

import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author swiedm1
 */
public final class BossTest {
     private static final int MAX_NUMBER = 10;
     private static final Scanner in = new Scanner(System.in);
     private static final Random random = new Random();
     public int p1 =  random.nextInt(50) + 51;
     public int p2 = random.nextInt(50) + 1;
      public int p3 =  random.nextInt(10) + 1;
     public int p4 = random.nextInt(50) + 1;
     public int p5 =  random.nextInt(100) + 71;
     public int p6 = random.nextInt(30) + 1;
      public int p7 =  random.nextInt(30) + 1;
     public int p8 = random.nextInt(10) + 1;
     

     public String getQuestion1(){
       return "Arthur baked " + p1 + " muffins, which was " + p2 + " more muffins than Ann." + "\n" +  "How many muffins did Ann bake?";
     }
     public String getQuestion2(){
         while(p4%p3 != 0){  //makes sure 2 numbers are divisible by each other
        p3 =  random.nextInt(50) + 1;
        p4 = random.nextInt(5000) + 1;
    }
         return "In the summertime, you can earn $" + p3 +  " a day by cutting the grass." + "\n" + "How many days will it take you to earn $" + p4 + "?";
     }
     public String getQuestion3(){
         
         return "Michelle has $" + p5 + " to buy a new outfit. She found a skirt for $" + p6 + ", a blouse for $" + p7 + ", and a belt for $" + p8 + "." + "\n" + "How much does she have left to buy shoes?";
     }
     
     
public int getAnswer1(){
        int answer = p1 - p2;
        return answer; 
}
public int getAnswer2(){
        int answer = p4/p3;
        return answer; 
}
public int getAnswer3(){
        int answer = p5 - (p6+p7+p8);
        return answer; 
}
public int getP1(){
    return p1;

}
public int getP2(){
    return p2;

}

}