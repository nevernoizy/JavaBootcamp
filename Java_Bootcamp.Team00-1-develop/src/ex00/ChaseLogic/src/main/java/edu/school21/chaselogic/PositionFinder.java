package edu.school21.chaselogic;


import java.util.ArrayList;
import java.util.List;

public class PositionFinder {
    public static List<Position> getEnemyPositions(char[][] table, char enemy) {
        List<Position> positions = new ArrayList<Position>();

        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[0].length; ++j) {
                if (table[i][j] == enemy) {
                    positions.add(new Position(i, j));
                }
            }
        }

        return positions;
    }

    public static Position getPlayerPosition(char[][] table, char enemy) {
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[0].length; ++j) {
                if (table[i][j] == enemy) {
                    return new Position(i, j);
                }
            }
        }

        return new Position(0, 0);
    }

    public static Position getGoalPosition(char[][] table, char goal)
    {
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[0].length; ++j) {
                if (table[i][j] == goal) {
                    return new Position(i, j);
                }
            }
        }

        return new Position(0, 0);
    }

    public static List<Position> getPlayerWallPositions(char[][] table, char wall, char enemy)
    {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[0].length; ++j) {
                if (table[i][j] == wall || table[i][j] == enemy) {
                    positions.add(new Position(i, j));
                }
            }
        }

        return positions;
    }

    public static List<Position> getEnemyWallPositions(
            char[][] table, char wall, char goal, char enemy) {
        List<Position> positions = new ArrayList<Position>();

        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[0].length; ++j) {
                if (table[i][j] == wall || table[i][j] == goal || table[i][j] == enemy) {
                    positions.add(new Position(i, j));
                }
            }
        }

        return positions;
    }

    public static List<Position> getAdjacentPositions(Position position, int tableSize) {
        List<Position> positions = new ArrayList<Position>();

        int positionX = position.getX();
        int positionY = position.getY();

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int adjacentPosX = positionX + dx[i];
            int adjacentPosY = positionY + dy[i];

            if (adjacentPosX >= 0
                    && adjacentPosX < tableSize
                    && adjacentPosY >= 0
                    && adjacentPosY < tableSize) {
                positions.add(new Position(adjacentPosX, adjacentPosY));
            }
        }

        return positions;
    }
}
