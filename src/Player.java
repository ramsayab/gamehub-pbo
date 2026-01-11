public class Player {
    private String id;
    private int totalScore;

    public Player() { this.totalScore = 0; }

    public void setID(String id) { this.id = id; }
    public String getID() { return id; }
    
    public void addScore(int score) { this.totalScore += score; }
    public int getScore() { return totalScore; }
}