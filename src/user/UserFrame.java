package user;

import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static user.UserList.toSave;
import static user.UserList.users;

public class UserFrame extends JFrame {
    JButton logInButton = new JButton("登录");
    JButton signUpButton = new JButton("注册");
    JButton userListButton = new JButton("用户排名");
    JButton logOutButton = new JButton("退出账号");
    private static UserList userList=new UserList();
    public static User user1,user2;
    public ChessboardComponent chessboardComponent;
    public UserFrame(ChessboardComponent chessboardComponent) {
        this.chessboardComponent =chessboardComponent;
        setTitle("用户");
        setSize(310, 350);
        setLocationRelativeTo(null);
        setLayout(null);
        initUserMessage();
        addButton();
    }
    private void initUserMessage()  {
        List<String> userData ;
        try
        {
            userData = Files.readAllLines(Path.of("resource/userListMessage"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        users.clear();
        for (String userDatum : userData)
        {
            String[] messages = new String[7];
            int dataLoc = 0;
            String message = new String("");
            for (int j = 0; j < userDatum.length(); j++)
            {
                if (userDatum.charAt(j) == ' ' || userDatum.charAt(j) == '#')
                {
                    messages[dataLoc] = message;
                    dataLoc++;
                    message = "";
                    if (userDatum.charAt(j) == '#') break;
                    j++;
                }
                message += userDatum.charAt(j);
            }
            User user = new User(messages[0], Integer.parseInt(messages[1]), messages[2], Integer.parseInt(messages[3]), Integer.parseInt(messages[4]), Integer.parseInt(messages[5]), Integer.parseInt(messages[6]));
            userList.addUser(user);
        }
    }
    private void addButton()
    {
        logInButton.setLocation(20, 30);
        logInButton.setSize(260, 50);
        logInButton.setFont(new Font("宋体", Font.BOLD, 20));
        logInButton.setBackground(new Color(245, 226, 178));
        add(logInButton);
        logInButton.addActionListener(e ->
        {
            if (users.size() == 0)
            {
                Object[] sameName = {"确定", "取消"};
                JOptionPane.showOptionDialog(null, "暂无注册用户", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, sameName, sameName[0]);
            }
            else
            {
                logIn();
            }
        });

        signUpButton.setLocation(20, 90);
        signUpButton.setSize(260, 50);
        signUpButton.setFont(new Font("宋体", Font.BOLD, 20));
        signUpButton.setBackground(new Color(245, 226, 178));
        add(signUpButton);
        signUpButton.addActionListener(e -> signUp());

        userListButton.setLocation(20, 150);
        userListButton.setSize(260, 50);
        userListButton.setFont(new Font("宋体", Font.BOLD, 20));
        userListButton.setBackground(new Color(245, 226, 178));
        add(userListButton);
        userListButton.addActionListener(e -> {
            if (users.size() == 0) {
                Object[] sameName = {"确定", "取消"};
                JOptionPane.showOptionDialog(null, "暂无注册用户", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, sameName, sameName[0]);
            } else {
                try {
                    showUserList();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        logOutButton.setLocation(20, 210);
        logOutButton.setSize(260, 50);
        logOutButton.setFont(new Font("宋体", Font.BOLD, 20));
        logOutButton.setBackground(new Color(245, 226, 178));
        add(logOutButton);
        logOutButton.addActionListener(e -> {
            if (user1 == null && user2 == null) {
                JOptionPane.showMessageDialog(this, "暂无用户登录");
            } else {
                assert user1 != null;
                JOptionPane.showMessageDialog(this, user1.userName + " , " + user2.userName + " 已退出登录");
                user1 = null;
                user2 = null;
//                changeUserLabel(' ', ' ', false);
            }
        });
    }
    private void logIn() {
        this.setSize(620, 350);
        this.getContentPane().removeAll();
        repaint();
        JLabel userNameLabel1 = new JLabel("蓝方");
        userNameLabel1.setLocation(15, 60);
        userNameLabel1.setSize(80, 30);
        userNameLabel1.setFont(new Font("宋体", Font.BOLD, 20));
        JLabel passwordLabel1 = new JLabel("蓝方密码");
        passwordLabel1.setLocation(15, 130);
        passwordLabel1.setSize(80, 30);
        passwordLabel1.setFont(new Font("宋体", Font.BOLD, 15));
        JTextField userNameText1 = new JTextField(16);
        userNameText1.setLocation(100, 60);
        userNameText1.setSize(170, 30);
        userNameText1.setEditable(true);
        JTextField passwordText1 = new JTextField(16);
        passwordText1.setLocation(100, 130);
        passwordText1.setSize(170, 30);
        passwordText1.setEditable(true);

        JLabel userNameLabel2 = new JLabel("红方");
        userNameLabel2.setLocation(300, 60);
        userNameLabel2.setSize(80, 30);
        userNameLabel2.setFont(new Font("宋体", Font.BOLD, 20));
        JLabel passwordLabel2 = new JLabel("红方密码");
        passwordLabel2.setLocation(300, 130);
        passwordLabel2.setSize(80, 30);
        passwordLabel2.setFont(new Font("宋体", Font.BOLD, 15));
        JTextField userNameText2 = new JTextField(16);
        userNameText2.setLocation(385, 60);
        userNameText2.setSize(170, 30);
        userNameText2.setEditable(true);
        JTextField passwordText2 = new JTextField(16);
        passwordText2.setLocation(385, 130);
        passwordText2.setSize(170, 30);
        passwordText2.setEditable(true);

        JButton confirmButton = new JButton("确定");
        confirmButton.setLocation(250, 200);
        confirmButton.setSize(80, 30);
        confirmButton.setFont(new Font("宋体", Font.BOLD, 20));
        confirmButton.setBackground(new Color(245, 226, 178));

        add(userNameLabel1);
        add(passwordLabel1);
        add(userNameText1);
        add(passwordText1);
        add(userNameLabel2);
        add(passwordLabel2);
        add(userNameText2);
        add(passwordText2);
        add(confirmButton);

        confirmButton.addActionListener(e -> {
            String userName1 = userNameText1.getText();
            String password1 = passwordText1.getText();
            String userName2 = userNameText2.getText();
            String password2 = passwordText2.getText();
            boolean valid1 = false, valid2 = false;
            if (userName1.equals("") || userName2.equals("") || password1.equals("") || password2.equals("")) {
                Object[] sameName = {"确定", "取消"};
                JOptionPane.showOptionDialog(null, "请输入双方用户名和密码", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, sameName, sameName[0]);
            } else if (userName1.equals(userName2)) {
                Object[] sameName = {"确定", "取消"};
                JOptionPane.showOptionDialog(null, "输入了相同用户名", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, sameName, sameName[0]);
            } else {
                for (User user : users) {
                    if (userName1.equals(user.userName)) {
                        if (password1.equals(user.password)) {
                            valid1 = true;
                            user1 = user;
                        }
                    }
                    if (userName2.equals(user.userName)) {
                        if (password2.equals(user.password)) {
                            valid2 = true;
                            user2 = user;
                        }
                    }
                }
                if (valid1 && valid2) {
                    JOptionPane.showMessageDialog(this, "欢迎 " + userName1 + " , " + userName2 + " 登录游戏");
                    dispose();
//                    changeUserLabel(userName1.charAt(0), userName2.charAt(0), true);
                } else {
                    user1 = null;
                    user2 = null;
                    Object[] sameName = {"确定", "取消"};
                    JOptionPane.showOptionDialog(null, "密码错误或用户不存在", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, sameName, sameName[0]);
                }
            }


        });
    }
    private void signUp() {
        this.getContentPane().removeAll();
        repaint();
        JLabel userNameLabel = new JLabel("用户名");
        userNameLabel.setLocation(15, 60);
        userNameLabel.setSize(80, 30);
        userNameLabel.setFont(new Font("宋体", Font.BOLD, 20));
        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setLocation(15, 130);
        passwordLabel.setSize(80, 30);
        passwordLabel.setFont(new Font("宋体", Font.BOLD, 20));
        JTextField userNameText = new JTextField(16);
        userNameText.setLocation(100, 60);
        userNameText.setSize(170, 30);
        userNameText.setEditable(true);
        JTextField passwordText = new JTextField(16);
        passwordText.setLocation(100, 130);
        passwordText.setSize(170, 30);
        passwordText.setEditable(true);
        JButton confirmButton = new JButton("确定");
        confirmButton.setLocation(110, 200);
        confirmButton.setSize(80, 30);
        confirmButton.setFont(new Font("宋体", Font.BOLD, 20));
        confirmButton.setBackground(new Color(245, 226, 178));

        add(userNameLabel);
        add(passwordLabel);
        add(userNameText);
        add(passwordText);
        add(confirmButton);

        confirmButton.addActionListener(e -> {
            String userName = userNameText.getText();
            String password = passwordText.getText();
            System.out.println(userName);
            System.out.println(password);
            try
            {
                if (userName.equals("") || password.equals(""))
                {
                    JOptionPane.showMessageDialog(this,"请输入用户名或密码");
                }
                else if (userList.signup(userName, password))
                {
                    JOptionPane.showMessageDialog(this,userName+" 注册成功");
                    dispose();
                    UserFrame userFrame = new UserFrame(chessboardComponent);
                    userFrame.setVisible(true);
                }
                else
                {
                    Object[] sameName = {"确定", "取消"};
                    JOptionPane.showOptionDialog(null, "该用户已存在或用户数已满", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, sameName, sameName[0]);
                }
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        });
    }
    private void showUserList() throws IOException {
        this.setSize(500, 350);
        this.getContentPane().removeAll();
        repaint();
        List<String> userRank = userList.rankUsers();

        JLabel title=new JLabel("名次  用户名   胜场   败场   分数");
        title.setLocation(30,30);
        title.setSize(400,30);
        title.setFont(new Font("华文行楷", Font.BOLD, 25));
        add(title);
        for (int i = 0; i < userRank.size(); i++) {
            String[] m=new String[]{"","","",""} ; int loc = 0;
            for (int j = 0; j < userRank.get(i).length(); j++) {
                if (userRank.get(i).charAt(j) == ' ') {
                    loc++;
                    continue;
                }
                m[loc] += userRank.get(i).charAt(j);
            }
            JLabel userName = new JLabel("No." + (i + 1) + "    " + m[0]);
            userName.setSize(250, 20);
            userName.setLocation(30, 50 * (i + 1) + 30);
            userName.setFont(new Font("Rockwell", Font.BOLD, 20));
            add(userName);
            JLabel userWin = new JLabel(m[1]);
            userWin.setSize(250, 20);
            userWin.setLocation(210, 50 * (i + 1) + 30);
            userWin.setFont(new Font("Rockwell", Font.BOLD, 20));
            add(userWin);
            JLabel userLoss = new JLabel(m[2]);
            userLoss.setSize(250, 20);
            userLoss.setLocation(280, 50 * (i + 1) + 30);
            userLoss.setFont(new Font("Rockwell", Font.BOLD, 20));
            add(userLoss);
            JLabel userScore = new JLabel(m[3]);
            userScore.setSize(250, 20);
            userScore.setLocation(350, 50 * (i + 1) + 30);
            userScore.setFont(new Font("Rockwell", Font.BOLD, 20));
            add(userScore);
        }
    }
    public static void gameEndUser(int who) {
        if (who==1) {
            user1.win++;
            user1.score += 5;
            user2.lose++;
            user2.score -= 3;
        } else {
            user2.win++;
            user2.score += 5;
            user1.lose++;
            user1.score -= 3;
        }
        toSave();
    }
}
