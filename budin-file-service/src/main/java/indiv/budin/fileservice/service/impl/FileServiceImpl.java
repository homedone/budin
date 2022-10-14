package indiv.budin.fileservice.service.impl;

import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import indiv.budin.entity.po.BudinFile;
import indiv.budin.fileservice.service.api.FileService;
import indiv.budin.mapper.BudinFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author homedone
 * @date 2022/10/8 19 57
 * discription
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private BudinFileMapper budinFileMapper;

    @Override
    public boolean saveFile(BudinFile budinFile) {
        try {
            int res = budinFileMapper.insertSelective(budinFile);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeOneFileById(Integer id) {
        return false;
    }

    @Override
    public boolean removeOneFileByUuid(String uuid) {
        return false;
    }
}
