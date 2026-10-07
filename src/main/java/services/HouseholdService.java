package services;

import java.util.ArrayList;
import java.util.Random;

public class HouseholdService {

private static ArrayList <String> allCodes = new ArrayList<>();




    public static String code(){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String householdCode=null;

        allCodes.add("AAAAAA");

        Random r= new Random();

        String code = //kan gøres smukkere
        String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())))+
        String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())))+
        String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())))+
        String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())))+
        String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())))+
        String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));

        for (String s:allCodes){
            if(!code.equals(s)){
                householdCode=code;

            } else{
                code();
        }}
        allCodes.add(code);

        return householdCode;
    }







}
