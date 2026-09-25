package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Date {


    public static String date (){

        LocalDate myDateObj = LocalDate.now();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("EEEE dd. MMMM", new Locale("da"));

        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate;
    }

}
