package models;

/**
 * Created by Szilvi on 2017. 10. 04..
 */
public class Author {
    /**
     * Itt igazából az egész getter setter dolog ködös nekem. Ahhoz hogy ez valid legyen ahhoz ugye private -ra kell
     * a name és date properyket állítani. Viszont mivel nincs semmi állítgatás a set és a get metódusnál se ezért sztem ezeket elhagyhatod
     * és csak simán public String name -ként venném fel.
     * A másik dolog ez már csak szép érzek, nem tudom hogy van pontosan a Javaban hogy először az összes setter metódust felsorold vagy
     * inkább propertynként csinálod a setet és a getet (én a c# miatt ezt preferálom) de ezt próbáld egy féleképpen csinálni.
     * Láttam hogy az egyik osztályod így van, a másik úgy.
     */
    String name;
    String date;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "models.Author{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
