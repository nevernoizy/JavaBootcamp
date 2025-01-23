package edu.school21.GameProject;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import edu.school21.chaselogic.ChaseLogic;
import edu.school21.chaselogic.PositionFinder;
import edu.school21.chaselogic.Position;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class FieldClass {
    private char[][] table;
    private int enemiesCount;
    private int wallsCount;
    private int size;
    private int playerICoord;
    private int playerJCoord;
    private PropertiesClass propertiesClass;

    private ChaseLogic chaseLogic;

    FieldClass(int enemiesCount, int wallsCount, int size, PropertiesClass propertiesClass) {
        this.table = new char[size][size];
        this.enemiesCount = enemiesCount;
        this.wallsCount = wallsCount;
        this.size = size;
        this.propertiesClass = propertiesClass;

        this.chaseLogic =
                new ChaseLogic(
                        propertiesClass.getEnemy(),
                        propertiesClass.getPlayer(),
                        propertiesClass.getWall(),
                        propertiesClass.getGoal(),
                        propertiesClass.getEmpty());
    }

    private void fillTable() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                table[i][j] = propertiesClass.getEmpty();
            }
        }
        for (int i = 0; i < wallsCount; i++) {
            takePlace(propertiesClass.getWall());
        }
        for (int i = 0; i < enemiesCount; i++) {
            takePlace(propertiesClass.getEnemy());
        }
        takePlace(propertiesClass.getPlayer());
        takePlace(propertiesClass.getGoal());
    }

    private void takePlace(char symbol) {
        int i = ThreadLocalRandom.current().nextInt(0, size);
        int j = ThreadLocalRandom.current().nextInt(0, size);
        while (table[i][j] != propertiesClass.getEmpty()) {
            i = ThreadLocalRandom.current().nextInt(0, size);
            j = ThreadLocalRandom.current().nextInt(0, size);
        }
        table[i][j] = symbol;
        if (symbol == propertiesClass.getPlayer()) {
            playerICoord = i;
            playerJCoord = j;
        }
    }

    private void print() {
        ColoredPrinter printer =
                new ColoredPrinter.Builder(1, false)
                        .foreground(Ansi.FColor.WHITE)
                        .background(Ansi.BColor.BLUE)
                        .build();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                printPixel(table[i][j], printer);
            }
            System.out.println();
        }
    }

    private void printPixel(char symbol, ColoredPrinter printer) {

        if (propertiesClass.getEmpty() == symbol) {
            printer.print(
                    symbol,
                    Ansi.Attribute.NONE,
                    Ansi.FColor.NONE,
                    Ansi.BColor.valueOf(propertiesClass.getEmptyColor()));
        } else if (propertiesClass.getEnemy() == symbol) {
            printer.print(
                    symbol,
                    Ansi.Attribute.NONE,
                    Ansi.FColor.valueOf("BLACK"),
                    Ansi.BColor.valueOf(propertiesClass.getEnemyColor()));
        } else if (propertiesClass.getWall() == symbol) {
            printer.print(
                    symbol,
                    Ansi.Attribute.NONE,
                    Ansi.FColor.valueOf("BLACK"),
                    Ansi.BColor.valueOf(propertiesClass.getWallColor()));
        } else if (propertiesClass.getPlayer() == symbol) {
            printer.print(
                    symbol,
                    Ansi.Attribute.NONE,
                    Ansi.FColor.valueOf("BLACK"),
                    Ansi.BColor.valueOf(propertiesClass.getPlayerColor()));
        } else if (propertiesClass.getGoal() == symbol) {
            printer.print(
                    symbol,
                    Ansi.Attribute.NONE,
                    Ansi.FColor.valueOf("BLACK"),
                    Ansi.BColor.valueOf(propertiesClass.getGoalColor()));
        }
    }

    private int movePlayer(int inputParam) {
        int res = 0;
        switch (inputParam) {
            case (1):
                res = moveLeft();
                break;
            case (2):
                res = moveRight();
                break;
            case (3):
                res = moveTop();
                break;
            case (4):
                res = moveBottom();
                break;
        }
        return res;
    }

    private int moveLeft() {
        int res = 0;
        if (playerJCoord == 0) {
            res = 1;
        } else if (table[playerICoord][playerJCoord - 1] == propertiesClass.getEmpty()) {
            table[playerICoord][playerJCoord - 1] = propertiesClass.getPlayer();
            table[playerICoord][playerJCoord] = propertiesClass.getEmpty();
            playerJCoord = playerJCoord - 1;
            if (checkAround()) {
                res = 0;
            } else {
                res = 1;
            }
        } else if (table[playerICoord][playerJCoord - 1] == propertiesClass.getGoal()) {
            res = 2;
        } else if (table[playerICoord][playerJCoord - 1] == propertiesClass.getEnemy()) {
            res = 1;
        }
        return res;
    }

    private int moveRight() {
        int res = 0;
        if (playerJCoord == size - 1) {
            res = 1;
        } else if (table[playerICoord][playerJCoord + 1] == propertiesClass.getEmpty()) {
            table[playerICoord][playerJCoord + 1] = propertiesClass.getPlayer();
            table[playerICoord][playerJCoord] = propertiesClass.getEmpty();
            playerJCoord = playerJCoord + 1;
            if (checkAround()) {
                res = 0;
            } else {
                res = 1;
            }
        } else if (table[playerICoord][playerJCoord + 1] == propertiesClass.getGoal()) {
            res = 2;
        } else if (table[playerICoord][playerJCoord + 1] == propertiesClass.getEnemy()) {
            res = 1;
        }
        return res;
    }

    private int moveTop() {
        int res = 0;
        if (playerICoord == 0) {
            res = 1;
        } else if (table[playerICoord - 1][playerJCoord] == propertiesClass.getEmpty()) {
            table[playerICoord - 1][playerJCoord] = propertiesClass.getPlayer();
            table[playerICoord][playerJCoord] = propertiesClass.getEmpty();
            playerICoord = playerICoord - 1;
            if (checkAround()) {
                res = 0;
            } else {
                res = 1;
            }
        } else if (table[playerICoord - 1][playerJCoord] == propertiesClass.getGoal()) {
            res = 2;
        } else if (table[playerICoord - 1][playerJCoord] == propertiesClass.getEnemy()) {
            res = 1;
        }
        return res;
    }

    private int moveBottom() {
        int res = 0;
        if (playerICoord == size - 1) {
            res = 1;
        } else if (table[playerICoord + 1][playerJCoord] == propertiesClass.getEmpty()) {
            table[playerICoord + 1][playerJCoord] = propertiesClass.getPlayer();
            table[playerICoord][playerJCoord] = propertiesClass.getEmpty();
            playerICoord = playerICoord + 1;
            if (checkAround()) {
                res = 0;
            } else {
                res = 1;
            }
        } else if (table[playerICoord + 1][playerJCoord] == propertiesClass.getGoal()) {
            res = 2;
        } else if (table[playerICoord + 1][playerJCoord] == propertiesClass.getEnemy()) {
            res = 1;
        }
        return res;
    }

    private boolean checkAround() {
        boolean res = true;
        if (checkEnemy(playerICoord - 1, playerJCoord)) {
            res = false;
        } else if (checkEnemy(playerICoord, playerJCoord - 1)) {
            res = false;
        } else if (checkEnemy(playerICoord, playerJCoord + 1)) {
            res = false;
        } else if (checkEnemy(playerICoord + 1, playerJCoord)) {
            res = false;
        }
        return res;
    }

    private boolean checkEnemy(int i, int j) {
        boolean res = false;
        if (i >= 0 && i <= size - 1 && j >= 0 && j <= size - 1) {
            if (table[i][j] == propertiesClass.getEnemy()) {
                res = true;
            }
        } else {
            res = false;
        }
        return res;
    }



    public boolean generateTable() {
        fillTable();
        for (int i = 0; i < 15 && chaseLogic.noPathToGoal(table); ++i) {
            fillTable();
        }

        if (chaseLogic.noPathToGoal(table)) {
            System.out.println("Could not generate proper table with given parameters. Please try decreasing the number of walls and/or enemies");
            return false;
        }

        return true;
    }
    private void printWithClear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        print();
    }

    public void mainFunc() {
        if (!generateTable()) {
            return;
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        print();
        int inputParam = 0;
        int result = 0;
        Scanner in = new Scanner(System.in);
        while (inputParam != 9) {
            inputParam = in.nextInt();
            result = movePlayer(inputParam);
            if (result == 1) {
                chaseLogic.updateTable(table);
                printWithClear();
                System.out.println("You lose");
                inputParam = 9;
            } else if (result == 2) {
                chaseLogic.updateTable(table);
                printWithClear();
                System.out.println("You win");
                inputParam = 9;
            } else {
                boolean enemyWon = chaseLogic.updateTable(table);
                if (enemyWon) {
                    chaseLogic.updateTable(table);
                    printWithClear();
                    System.out.println("You lose. Enemy will catch you");
                    inputParam = 9;
                }
            }

            if (inputParam != 9) {
                printWithClear();
            }
        }

        in.close();
    }

    public void devMainFunc() {
        if (!generateTable()) {
            return;
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        print();
        int inputParam = 0;
        int result = 0;
        Scanner in = new Scanner(System.in);
        while (inputParam != 9) {
            inputParam = in.nextInt();
            result = movePlayer(inputParam);
            if (result == 1) {
                chaseLogic.updateTable(table);
                printWithClear();
                System.out.println("You lose");
                inputParam = 9;
            } else if (result == 2) {
                chaseLogic.updateTable(table);
                printWithClear();
                System.out.println("You win");
                inputParam = 9;
            } else if (inputParam == 8) {
                boolean enemyWon = chaseLogic.updateTable(table);
                if (enemyWon) {
                    chaseLogic.updateTable(table);
                    printWithClear();
                    System.out.println("You lose. Enemy will catch you.");
                    inputParam = 9;
                }
            }
            if (inputParam != 9) {
                printWithClear();
            }
        }

        in.close();
    }
}
