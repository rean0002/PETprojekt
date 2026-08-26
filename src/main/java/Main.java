public class Main {


    public static void main (String [] args){

        User Olga = new User(1, "mail@mail.dk","kode","Olga W", 0, Role.ADMIN );

        Task cleaning = new Task(1, "vacuum", "all floors", Olga, TaskCategory.CLEANING, TaskFrequency.BI_WEEKLY);

        System.out.println(cleaning.getTaskCategory());


    }


    

}
