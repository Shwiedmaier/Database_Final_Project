package Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package game;

/**
 *
 * @author malory
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author swiedm1
 */
public final class Addition {
     private static final int MAX_NUMBER = 10;
     private static final Scanner in = new Scanner(System.in);
     private static final Random random = new Random();
     public int p1 =  random.nextInt(50) + 1;
     public int p2 = random.nextInt(50) + 1;
public int getAnswer(){
        int answer = p1 + p2;
        return answer; 
}
public int getP1(){
    return p1;
    
}
public int getP2(){
    return p2;
    
}
}
