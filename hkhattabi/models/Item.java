package hkhattabi.models;

public class Item extends Model {
    protected String name;
    protected Human usedBy;

    public Item(String name, Human usedBy) {
        this.name = name;
        this.usedBy = usedBy;
    }


    public Human getUsedBy() {
        return usedBy;
    }

    public String getName() {
        return name;
    }
}
