package edu.school21.chaselogic;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TrackingAlgorithm {
    public static EnemyMove getNextMove(
            char[][] table,
            Position enemyPosition,
            Position playerPosition,
            List<Position> enemyWallPositions) {
        List<Position> pathToPlayer =
                performBreadthFirstSearch(
                        enemyPosition, playerPosition, enemyWallPositions, table.length);

        Position nextPosition = pathToPlayer.get(1);

        EnemyMove nextMove =
                new EnemyMove(
                        enemyPosition.getX(), enemyPosition.getY(),
                        nextPosition.getX(), nextPosition.getY());

        return nextMove;
    }

    public static List<Position> performBreadthFirstSearch(
            Position start, Position target, List<Position> walls, int tableSize) {
        List<Position> path = new ArrayList<Position>();

        Map<Position, Position> previousPosition = new HashMap<Position, Position>();

        ArrayDeque<Position> queue = new ArrayDeque<Position>();
        queue.add(start);

        HashSet<Position> visited = new HashSet<Position>();
        visited.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.remove();

            if (current.equals(target)) {
                while (current != null) {
                    path.add(0, current);
                    current = previousPosition.get(current);
                }
                return path;
            }

            List<Position> adjacentPositions =
                    PositionFinder.getAdjacentPositions(current, tableSize);

            for (Position adjacent : adjacentPositions) {
                if (!visited.contains(adjacent) && !walls.contains(adjacent)) {
                    queue.add(adjacent);
                    visited.add(adjacent);
                    previousPosition.put(adjacent, current);
                }
            }
        }

        return path;
    }
}
