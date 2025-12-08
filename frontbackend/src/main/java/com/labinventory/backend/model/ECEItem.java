// =======================================================
// this class represents items specific to the ECE department.
// =======================================================
package com.labinventory.backend.model;

public class ECEItem extends Item {

    public ECEItem(String code, String name, String description) {
        super(code, name, description);
        setName("ECE-" + name);   // ✅ use public setter
    }
}
