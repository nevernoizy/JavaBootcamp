package edu.school21.chaselogic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChaseLogic {
    private char enemy;
    private char player;
    private char wall;
    private char goal;
    private char empty;

    public ChaseLogic(char enemy, char player, char wall, char goal, char empty) {
        this.enemy = enemy;
        this.player = player;
        this.wall = wall;
        this.goal = goal;
        this.empty = empty;
    }

    public boolean updateTable(char[][] table) {
        Position playerPosition = PositionFinder.getPlayerPosition(table, player);
        List<Position> enemyPositions = PositionFinder.getEnemyPositions(table, enemy);
        List<Position> enemyWallPositions =
                PositionFinder.getEnemyWallPositions(table, wall, goal, enemy);

        boolean enemyWon = enemyHasWon(table);

        moveEnemies(table, enemyPositions, playerPosition, enemyWallPositions);

        return enemyWon;
    }

    public boolean noPathToGoal(char[][] table) {
        Position playerPosition = PositionFinder.getPlayerPosition(table, player);
        Position goalPosition = PositionFinder.getGoalPosition(table, goal);

        return (TrackingAlgorithm.performBreadthFirstSearch(playerPosition,
                    goalPosition,
                    PositionFinder.getPlayerWallPositions(table, wall, enemy),
                    table.length).isEmpty());
    }

    public boolean isGoalUnreachableInFuture(char[][] table, Position playerPosition, Position goalPosition, int depth) {
        if (depth == 0) {
            return false;
        }

        List<Position> playerMoves = PositionFinder.getAdjacentPositions(playerPosition, table.length);

        for (Position playerMove : playerMoves) {
            char[][] simulatedTable = copyTable(table);
            simulatedTable[playerPosition.getX()][playerPosition.getY()] = empty;
            simulatedTable[playerMove.getX()][playerMove.getY()] = player;

            List<Position> enemyPositions = PositionFinder.getEnemyPositions(simulatedTable, enemy);
            List<Position> enemyWallPositions = PositionFinder.getEnemyWallPositions(simulatedTable, wall, goal, enemy);

            moveEnemies(simulatedTable, enemyPositions, playerMove, enemyWallPositions);

            if (isPlayerCaught(simulatedTable, playerMove)) {
                return true;
            }

            if (isGoalUnreachableInFuture(simulatedTable, playerMove, goalPosition, depth - 1)) {
                return true;
            }
        }

        return false;
    }

    private char[][] copyTable(char[][] original) {
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; ++i) {
            copy[i] = original[i].clone();
        }

        return copy;
    }

    private boolean isPlayerCaught(char[][] table, Position playerPosition) {
        List<Position> enemyPositions = PositionFinder.getEnemyPositions(table, enemy);
        for (Position enemyPosition : enemyPositions) {
            if (enemyPosition.equals(playerPosition)) {
                return true;
            }
        }
        return false;
    }

    private void moveEnemies(
            char[][] table,
            List<Position> enemyPositions,
            Position playerPosition,
            List<Position> enemyWallPositions) {
        Map<Position, List<Position>> intendedMoves = new HashMap<Position, List<Position>>();

        for (Position enemy : enemyPositions) {
            List<Position> path =
                    TrackingAlgorithm.performBreadthFirstSearch(
                            enemy, playerPosition, enemyWallPositions, table.length);
            if (path.size() > 1) {
                Position nextMove = path.get(1);

                intendedMoves.computeIfAbsent(nextMove, k -> new ArrayList<>()).add(enemy);
            }
        }

        for (Map.Entry<Position, List<Position>> entry : intendedMoves.entrySet()) {
            Position targetPosition = entry.getKey();
            List<Position> enemiesMovingHere = entry.getValue();

            if (enemiesMovingHere.size() == 1) {
                Position enemy = enemiesMovingHere.get(0);
                moveEnemy(table, enemy, targetPosition);
            }
        }
    }

    private void moveEnemy(char[][] table, Position oldPosition, Position newPosition) {
        table[oldPosition.getX()][oldPosition.getY()] = empty;
        table[newPosition.getX()][newPosition.getY()] = enemy;
    }

    private boolean enemyHasWon(char[][] table) {
        List<Position> enemyPositions = PositionFinder.getEnemyPositions(table, enemy);

        Position playerPosition = PositionFinder.getPlayerPosition(table, player);

        for (Position enemyPosition : enemyPositions) {
            if (enemyPosition.isAdjacentTo(playerPosition)) {
                return true;
            }
        }

        return false;
    }
}
