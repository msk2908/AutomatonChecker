public class PlayerStats {

    private int points = 0;
    private int level = 1;
    private int failures = 0;



    public void win() {
        points++;
        checkLevel();
    }

    public void lose() {
        failures++;
        checkLevel();
    }

    private void checkLevel() {
        if (points > 2) {
            increaseLevel();
            resetPoints();
        }
        if (failures > 2) {
            decreaseLevel();
            resetPoints();
            resetFailures();
        }
    }




    public void setLevel(int level) {
        this.level = level;
    }

    private void resetPoints() {
        points = 0;
    }

    private void increaseLevel() {
        level++;
    }

    private void decreaseLevel() {
        if (level > 1) {
            level--;
        }
    }

    public int getLevel() {
        return level;
    }

    private void resetFailures() {
        failures = 0;
    }



}
