package de.z1up.supercloud.core.id;

public class UID extends StringGenerator ***REMOVED***

    private String tag;
    private UIDType type;

    public UID(String tag, UIDType type) ***REMOVED***
        this.tag = tag;
        this.type = type;
    ***REMOVED***

    public static UID randomUID(UIDType type) ***REMOVED***
        String tag = generateRandomTag(16);
        return new UID(tag, type);
    ***REMOVED***

    public String getTag() ***REMOVED***
        return tag;
    ***REMOVED***

    public UIDType getType() ***REMOVED***
        return type;
    ***REMOVED***

    public void setType(UIDType type) ***REMOVED***
        this.type = type;
    ***REMOVED***
***REMOVED***
