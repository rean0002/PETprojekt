package services;

import Exceptions.DatabaseException;
import entities.Household;
import mappers.ConnectionPool;
import mappers.HouseholdMapper;

import java.util.ArrayList;
import java.util.Random;

public class HouseholdService {

    private ConnectionPool connectionPool;
    private HouseholdMapper householdMapper;
    private static ArrayList <String> allCodes = new ArrayList<>(); //skal laves om
    //liste af alle koder i households, via mapper Select code from households, løb denne igennem i stedet


public HouseholdService(ConnectionPool connectionPool){
    this.householdMapper= new HouseholdMapper(connectionPool);

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

    private Household saveHousehold (Household household){


        try {
            householdMapper.createHousehold(household);
        } catch (DatabaseException e) {
            e.getMessage();
        }

        return household;

    }

    public Household createHousehold (String name){

        Household household= new Household(name, code());
        saveHousehold(household);

        return household;

    }







}
