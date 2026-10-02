package services;

import entities.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


public class DateService {

    public String getDate(LocalDate dato){
        return Date.date(dato);
    }
    public String getDate(){

       return Date.date();
    }

    public List<LocalDate> getNext7Days(){
        ArrayList <LocalDate> week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            week.add(date);
        } return week;
    }




}
