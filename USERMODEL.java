package com.example.ludo3;

public class USERMODEL {
    private int pid;
    private  String username;
    private String  password;
    private int wins;
    private  int totalPlayed;


    //constructor
    public USERMODEL(int pid, String username, String password, int wins, int totalPlayed) {
        this.pid = pid;
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.totalPlayed = totalPlayed;
    }

    @Override
    public String toString() {
        return "USERMODEL{" +
                "pid=" + pid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", wins=" + wins +
                ", totalPlayed=" + totalPlayed +
                '}';
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getTotalPlayed() {
        return totalPlayed;
    }

    public void setTotalPlayed(int totalPlayed) {
        this.totalPlayed = totalPlayed;
    }
}
