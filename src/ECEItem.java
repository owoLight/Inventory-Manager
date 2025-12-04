// =======================================================
// this class represents items specific to the ECE department.
// =======================================================

public class ECEItem extends Item {

    // constructor
    public ECEItem(String id, String name, String description) {
        super(id, name, description);
        this.name = "ECE-" + name;
    }

}
