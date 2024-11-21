package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        try{
            User user = autorizacia ();
            if (user == null){
                return;
            }
            Menu(user);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sc.close();
        }
    }

    private static User autorizacia() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your login:");
        String login = sc.nextLine();

        User user = new User (login);
        int num = user.sendcheckuser();
        if (num != 201) {
            System.out.println("Your login correct!");
        } else {
            System.out.println("input correct login! ");
        }
        return user;
    }

    public static void Menu(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input  1: chat");
        System.out.println("Input 2: private chat");
        int a = scanner.nextInt();
        switch (a) {
            case 1:
                message(user);
                break;
            case 2:
                privateMessages(user);
                break;
            default:
                return;
        }

    }
    public static void message (User user) {
        Scanner scanner = new Scanner(System.in);

        Thread th = new Thread(new GetThread());
        th.setDaemon(true);
        th.start();


        System.out.println("Enter your message: ");
        while (true) {
            String text = scanner.nextLine();
            if (text.isEmpty()) break;

            Message m = new Message (user.getLogin(), text);
            int res = 0;
            try {
                res = m.send(Utils.getURL() + "/add");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (res != 200) { // 200 OK
                System.out.println("HTTP error occured: " + res);
                return;
            }
        }
    }

    public static void privateMessages (User user) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter login person for privat messege: ");
            String to = scanner.nextLine();
            Thread th = new Thread(new GetThreadPrivateM());
            th.setDaemon(true);
            th.start();

            System.out.println("Enter your private message: ");
            while (true) {
                String text = scanner.nextLine();
                if (text.isEmpty()) break;

                PrivateM m = new PrivateM(user.getLogin (), to, text);
                int res = m.send(Utils.getURL() + "/add");

                if (res != 200) {
                    System.out.println("HTTP error occured: " + res);
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
