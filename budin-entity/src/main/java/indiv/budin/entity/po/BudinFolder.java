package indiv.budin.entity.po;

import java.util.Date;

public class BudinFolder {
    private Integer folderId;

    private String folderOriginalName;

    private String folderName;

    private String folderPath;

    private String folderUrl;

    private String folderUuid;

    private Long folderSize;

    private Date folderBuildTime;

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderOriginalName() {
        return folderOriginalName;
    }

    public void setFolderOriginalName(String folderOriginalName) {
        this.folderOriginalName = folderOriginalName == null ? null : folderOriginalName.trim();
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath == null ? null : folderPath.trim();
    }

    public String getFolderUrl() {
        return folderUrl;
    }

    public void setFolderUrl(String folderUrl) {
        this.folderUrl = folderUrl == null ? null : folderUrl.trim();
    }

    public String getFolderUuid() {
        return folderUuid;
    }

    public void setFolderUuid(String folderUuid) {
        this.folderUuid = folderUuid == null ? null : folderUuid.trim();
    }

    public Long getFolderSize() {
        return folderSize;
    }

    public void setFolderSize(Long folderSize) {
        this.folderSize = folderSize;
    }

    public Date getFolderBuildTime() {
        return folderBuildTime;
    }

    public void setFolderBuildTime(Date folderBuildTime) {
        this.folderBuildTime = folderBuildTime;
    }
}