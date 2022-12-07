package day7;

public abstract class DiskEntity {
    protected String name;

    public DiskEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract long getSize();
}
