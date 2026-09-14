import entities.User;
import services.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        User found = userService.getUser("amalie@mail.com");
        System.out.println("Fundet bruger: " + (found != null ? found.getEmail() : "ikke fundet"));

        User loginOk = userService.login("amalie@mail.com", "password1");
        System.out.println("Login korrekt: " + (loginOk != null));

        User loginFail = userService.login("amalie@mail.com", "forkertpass");
        System.out.println("Login forkert password: " + (loginFail != null));

        User newUser = userService.createUser("ny@mail.com", "sikkertpass1");
        System.out.println("Ny bruger oprettet: " + "email: "+newUser.getEmail()+" password: "+newUser.getPassword());

        User duplicateUser = userService.createUser("amalie@mail.com", "andetpass1");
        System.out.println("Dublet oprettet : " + (duplicateUser != null));
    }
}