package Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package test;


/**
 *
 * @author swiedm1
 */
public class Hero {
    public int exp = 0;
    public String name = "temp";
    
    
   
    public Hero(String temp){
        name = temp;
    }
    
    public String getName(){
        return name;
    }   
    

    public int setExp(int total){
        exp=total+exp;
        return exp;
    }    
    public int getExp(){
        return exp;
    }
}
