package day07;

public final class DiskFile extends DiskEntity {
    private String extension;
    private long size;

    public DiskFile(String name, String extension, long size) {
        super(name);
        this.extension = extension;
        this.size = size;
    }

    public String getFullName() {
        return this.extension.equals("") ? this.name : this.name + "." + this.extension;
    }

    public long getSize() {
        return this.size;
    }
}
