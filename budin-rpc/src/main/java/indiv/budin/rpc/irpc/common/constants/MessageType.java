package indiv.budin.rpc.irpc.common.constants;

public enum MessageType {

    REQUEST((byte) 1,"REQUEST"),
    RESPONSE((byte) 2,"RESPONSE"),
    BOUNCE((byte) 3,"BOUSE");
    byte type;

    String description;

    MessageType(byte type, String description) {
        this.type = type;
        this.description = description;
    }

    public byte getType() {
        return type;
    }
}
