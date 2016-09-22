package derry.englishpopqueez;

public class Level {
    String levelName;
    int stars = 0;
    boolean isAnswered = false;
    String correctAnswer;
    String[] possibleAnswers;

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(String[] possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String level) {
        this.levelName = level;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
