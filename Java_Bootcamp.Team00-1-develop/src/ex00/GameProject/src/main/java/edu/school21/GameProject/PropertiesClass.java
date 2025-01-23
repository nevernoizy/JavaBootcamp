package edu.school21.GameProject;


import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PropertiesClass {
    private char enemy;
    private char player;
    private char wall;
    private char goal;
    private char empty;
    private String enemyColor;
    private String playerColor;
    private String wallColor;
    private String goalColor;
    private String emptyColor;

    public String getEmptyColor() {
        return emptyColor;
    }

    public char getEnemy() {
        return enemy;
    }

    public char getPlayer() {
        return player;
    }

    public char getWall() {
        return wall;
    }

    public char getGoal() {
        return goal;
    }

    public char getEmpty() {
        return empty;
    }

    public String getEnemyColor() {
        return enemyColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getWallColor() {
        return wallColor;
    }

    public String getGoalColor() {
        return goalColor;
    }

    public PropertiesClass() {
        try {
            InputStream input = GameProject.class.getClassLoader().getResourceAsStream("application-production.properties");

            if (input == null) {
                throw new IllegalArgumentException("Properties file not found in classpath");
            }

            Scanner in = new Scanner(input);
            String buff = in.nextLine();
            this.enemy = buff.charAt(buff.length() - 1);
            buff = in.nextLine();
            this.player = buff.charAt(buff.length() - 1);
            buff = in.nextLine();
            this.wall = buff.charAt(buff.length() - 1);
            buff = in.nextLine();
            this.goal = buff.charAt(buff.length() - 1);
            buff = in.nextLine();
            this.empty = ' ';
            String[] tokens = in.nextLine().split(" ");
            this.enemyColor = tokens[tokens.length - 1];
            tokens = in.nextLine().split(" ");
            this.playerColor = tokens[tokens.length - 1];
            tokens = in.nextLine().split(" ");
            this.wallColor = tokens[tokens.length - 1];
            tokens = in.nextLine().split(" ");
            this.goalColor = tokens[tokens.length - 1];
            tokens = in.nextLine().split(" ");
            this.emptyColor = tokens[tokens.length - 1];

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
