package edu.school21.chaselogic;

public class EnemyMove {
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    public EnemyMove() {
        this.oldX = 0;
        this.oldY = 0;
        this.newX = 0;
        this.newY = 0;
    }

    public EnemyMove(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }
}
