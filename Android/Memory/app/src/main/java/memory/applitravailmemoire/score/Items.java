package memory.applitravailmemoire.score;


import memory.applitravailmemoire.memory.MainMemory;

public class Items {

    private int id;
    private String name;
    private int score;
    private String play;
    private String time;

    public Items() {
    }

    public Items(String name, int score, String play) {

        this.name = name;
        this.score = score;
        this.play = play;
    //    this.time = time;

    }

   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

   /* public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/

    @Override
    public String toString() {
        return "Items{" +
                "name='" + name + '\'' +
                ", score='" + score + '\'' +
                ", play='" + play + '\'' +
                '}';
    }

}