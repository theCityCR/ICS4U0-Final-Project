package culminating;

public class Card {
    private String info;
    private Boolean read;
    public Card(String info){
        this.info = info;
        read = false;
    }

    public void hasRead(){
        read = true;
    }

    public Boolean getRead(){
        return read;
    }
    public String getInfo(){
        return info;
    }
}
