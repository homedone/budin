package indiv.budin.entity.po;

public class RelationBetweenFolder {
    private Integer id;

    private Integer folderParentId;

    private Integer folderChildId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFolderParentId() {
        return folderParentId;
    }

    public void setFolderParentId(Integer folderParentId) {
        this.folderParentId = folderParentId;
    }

    public Integer getFolderChildId() {
        return folderChildId;
    }

    public void setFolderChildId(Integer folderChildId) {
        this.folderChildId = folderChildId;
    }
}