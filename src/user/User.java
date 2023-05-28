package user;

public class User {
    public String userName;
    int userID;
    String password;
    //win +5 score, lose -3 score, rank in score
    int rank,win,lose,score;

    public User(String userName, int userID, String password, int rank, int win, int lose, int score) {
        this.userName=userName;
        this.userID=userID;
        this.password=password;
        this.rank=rank;
        this.win=win;
        this.lose=lose;
        this.score=score;
    }

    public User(String userName, int id,String password) {
        this.userName=userName;
        this.userID = id;
        this.password=password;
    }
}
