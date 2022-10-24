package indiv.budin.entity.vo;

import java.io.Serializable;

/**
 * @author
 * @date 2022/10/24 17 25
 * discription
 */
public class BudinFolderVO implements Serializable {
    private String folderOriginalName;

    private String folderPath;

    public BudinFolderVO(String folderOriginalName, String folderPath) {
        this.folderOriginalName = folderOriginalName;
        this.folderPath = folderPath;
    }

    public String getFolderOriginalName() {
        return folderOriginalName;
    }

    public void setFolderOriginalName(String folderOriginalName) {
        this.folderOriginalName = folderOriginalName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public String toString() {
        return "BudinFolderVO{" +
                "folderOriginalName='" + folderOriginalName + '\'' +
                ", folderPath='" + folderPath + '\'' +
                '}';
    }
}
