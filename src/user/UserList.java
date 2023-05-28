package user;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static user.UserFrame.user1;
import static user.UserFrame.user2;

public class UserList {
    public static ArrayList<User> users = new ArrayList<>();

    public UserList() {}

    public void addUser(User user) {
        users.add(user);
    }

    public boolean signup(String name,String password) throws IOException {
        if (users.size() >= 9000) {return false;}
        Random rd= new Random();
        int id = rd.nextInt(1003, 9999);
        for (int i = 0; i < users.size(); i++) {
            if (id == users.get(i).userID) {
                id = rd.nextInt(1003, 9999);
                i = 0;
            }
        }
        User user = new User(name, id, password);
        for (User user1 : users) {
            if (user.userName.equals(user1.userName)) {
                return false;
            }
        }
        users.add(user);
        String message =String.format("%s %d %s 0 0 0 0#",name,user.userID,password);
        rankFile(message);

        return true;
    }

    public static void toSave()
    {
        List<String> forWrite =new ArrayList<>();
        for (User user : users) {
            String w = String.format("%s %d %s %d %d %d %d#", user.userName, user.userID, user.password, user.rank, user.win, user.lose, user.score);
            if(String.format("%s %d %s", user.userName, user.userID, user.password).equals(String.format("%s %d %s", user1.userName, user1.userID, user1.password)))
            {
                w = String.format("%s %d %s %d %d %d %d#", user.userName, user.userID, user.password, user1.rank, user1.win, user1.lose, user1.score);
            }
            if(String.format("%s %d %s", user.userName, user.userID, user.password).equals(String.format("%s %d %s", user2.userName, user2.userID, user2.password)))
            {
                w = String.format("%s %d %s %d %d %d %d#", user.userName, user.userID, user.password, user2.rank, user2.win, user2.lose, user2.score);
            }
            forWrite.add(w);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resource/userListMessage"));
            for (String m : forWrite)
            {
                writer.write(m);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void rankFile(String s) throws IOException {
        List<String> messages = Files.readAllLines(Path.of("resource/userListMessage"));
        ArrayList<User> forRead = new ArrayList<>();
        for (String message : messages) {
            String[] m = new String[]{"", "", "", "", "", "", ""};
            int loc = 0;
            for (int j = 0; j < message.length(); j++) {
                if (message.charAt(j) == ' ' || message.charAt(j) == '#') {
                    loc++;
                    continue;
                }
                m[loc] += message.charAt(j);
            }
            User user = new User(m[0], Integer.parseInt(m[1]), m[2], Integer.parseInt(m[3]), Integer.parseInt(m[4]), Integer.parseInt(m[5]), Integer.parseInt(m[6]));
            forRead.add(user);
        }
        for (int i = 0; i < forRead.size(); i++) {
            for (int j = 0; j < forRead.size() - i - 1; j++) {
                if (forRead.get(j).score <= forRead.get(j + 1).score) {
                    User user = forRead.get(j);
                    forRead.set(j, forRead.get(j + 1));
                    forRead.set(j + 1, user);
                }
            }
        }
        for (int i = 0; i < forRead.size(); i++) {
            for (int j = 0; j < forRead.size() - i - 1; j++) {
                if (forRead.get(j).userID >= forRead.get(j + 1).userID && forRead.get(j).score == forRead.get(j + 1).score) {
                    User user = forRead.get(j);
                    forRead.set(j, forRead.get(j + 1));
                    forRead.set(j + 1, user);
                }
            }
        }
        List<String> forWrite =new ArrayList<>();
        for (User user : forRead) {
            String w = String.format("%s %d %s %d %d %d %d#", user.userName, user.userID, user.password, user.rank, user.win, user.lose, user.score);
            forWrite.add(w);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resource/userListMessage"));
            for (String m : forWrite) {
                writer.write(m);
                writer.newLine();
            }
            if (!s.equals("")) {
                writer.write(s);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> rankUsers() throws IOException
    {
        List<String> rankUser=new ArrayList<>();
        rankFile("");
//        LineNumberReader reader=new LineNumberReader(new FileReader("resource/userListMessage"));
//        System.out.println(reader);
        Scanner reader = new Scanner(new File("resource/userListMessage"));
        for (int i = 0; i < users.size() && i <= 4&&reader.hasNextLine(); i++) {
//            reader.setLineNumber(i + 1);
            String origin = reader.nextLine();
            String m="";
            int loc = 0;
            for (int j = 0; j < origin.length(); j++) {
                if (origin.charAt(j) == ' ' || origin.charAt(j) == '#') {
                    loc++;
                }
                if (loc == 0 || loc == 4 || loc == 5 || loc == 6) {
                    m += origin.charAt(j);
                }
            }
            rankUser.add(m);
        }
        return rankUser;
    }
}
