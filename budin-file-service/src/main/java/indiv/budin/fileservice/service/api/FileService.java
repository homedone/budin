package indiv.budin.fileservice.service.api;


import indiv.budin.entity.po.BudinFile;

/**
 * @author homedone
 * @date 2022/10/8 19 56
 * discription
 */
public interface FileService {
    public boolean saveFile(BudinFile budinFile);

    public boolean removeOneFileById(Integer id);

    public boolean removeOneFileByUuid(String uuid);

}
