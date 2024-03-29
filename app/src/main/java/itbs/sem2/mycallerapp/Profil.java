package itbs.sem2.mycallerapp;

public class Profil {
    String name, lastanme, number ;

    public Profil(String name, String lastanme, String number) {
        this.name = name;
        this.lastanme = lastanme;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Profil{" +
                "name='" + name + '\'' +
                ", lastanme='" + lastanme + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
