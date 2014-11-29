package model;

public class Site {

    private final String id;
    private final String name;

    public Site(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
