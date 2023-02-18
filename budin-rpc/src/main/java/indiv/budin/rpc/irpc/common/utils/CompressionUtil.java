package indiv.budin.rpc.irpc.common.utils;
import java.io.*;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


/**
 * util for compress/decompress data
 * Java压缩/解压缩二进制文件
 * 在Java中提供Deflater和Inflater工具类来压缩/解压缩数据。 这两个工具类采用zlib算法
 */
public class CompressionUtil {

    /**
     * Compression level
     */
    public enum Level {

        /**
         * Compression level for no compression.
         */
        NO_COMPRESSION(0),

        /**
         * Compression level for fastest compression.
         */
        BEST_SPEED(1),

        /**
         * Compression level for best compression.
         */
        BEST_COMPRESSION(9),

        /**
         * Default compression level.
         */
        DEFAULT_COMPRESSION(-1);

        private int level;

        Level(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    private static final int BUFFER_SIZE = 4 * 1024;
    public static byte[] compress(byte[] data) throws IOException {
        return compress(data,Level.DEFAULT_COMPRESSION);
    }


    public static byte[] compress(byte[] data, Level level) throws IOException {

        Deflater deflater = new Deflater();
        // set compression level
        deflater.setLevel(level.getLevel());
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        byte[] output = outputStream.toByteArray();
        outputStream.close();
        return output;
    }

    public static byte[] decompress(byte[] data) throws IOException,
            DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
                data.length);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        byte[] output = outputStream.toByteArray();
        outputStream.close();
        return output;
    }

    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
        String s = "this is a test";
        byte[] bytes = s.getBytes();
        System.out.println("压缩前: "+bytesToHexString(bytes));
        byte[] compress = compress(bytes, Level.DEFAULT_COMPRESSION);
        System.out.println("压缩后: "+bytesToHexString(compress));
        byte[] decompress = decompress(compress);
        System.out.println("解压缩后: "+bytesToHexString(decompress));
    }
}
