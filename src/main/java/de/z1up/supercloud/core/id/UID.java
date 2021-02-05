package de.z1up.supercloud.core.id;

public class UID extends StringGenerator {

    private String tag;
    private UIDType type;

    public UID(String tag, UIDType type) {
        this.tag = tag;
        this.type = type;
    }

    public static UID randomUID(UIDType type) {
        String tag = generateRandomTag(16);
        return new UID(tag, type);
    }

    public String getTag() {
        return tag;
    }

    public UIDType getType() {
        return type;
    }

    public void setType(UIDType type) {
        this.type = type;
    }
}
