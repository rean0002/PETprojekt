import java.util.Random;

public class code {


public static void main (){


        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String [] code = new String[6];
        Random r= new Random();

        code[0]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[1]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[2]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[3]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[4]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[5]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));


    System.out.println(code[0]+code[1]+code[2]+code[3]+code[4]+code[5]);

}


}
