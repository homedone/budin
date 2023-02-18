package indiv.budin.rpc.irpc.commu.nio.code;

public class MessageCode {
    /**
     * MAX_LENGTH = 4Mb, 4*1024*1024
     */
    public static final int MAGIC_LEN=4;
    public static final int VERSION_LEN=1;
    public static final int FIELD_LENGTH=4;
    public static final int MESSAGE_TYPE_LEN=1;
    public static final int SERIALIZER_TYPE_LEN=1;
    public static final int MESSAGE_ID=4;
    public static final int HEAD_LEN=15;
    public static final int MAX_LENGTH=4*1024*1024;

    public static final byte[] MAGIC=new byte[]{67,44,35,42};

    public static final byte VERSION=1;



}
