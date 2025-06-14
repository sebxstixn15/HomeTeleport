package at.sebdev.homeTeleporter.model;

//model to get the name and the three axis

public class NameCoordinate {
    private String name;
    private int x;
    private int y;
    private int z;

    public NameCoordinate(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
