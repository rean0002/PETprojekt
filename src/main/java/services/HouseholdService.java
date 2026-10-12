package services;

import Exceptions.DatabaseException;
import entities.Household;
import mappers.HouseholdMapper;

import java.util.ArrayList;
import java.util.Random;

public class HouseholdService {

    private HouseholdMapper householdMapper;
    private static ArrayList <String> allCodes = new ArrayList<>(); //skal laves om


public HouseholdService(HouseholdMapper householdMapper){
    this.householdMapper=householdMapper;

}


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

    public Household createHousehold (String name){

        Household household= new Household(name, code());

        return household;

    }







}
