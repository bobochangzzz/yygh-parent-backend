package com.bobochang.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bobochang.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author bobochang
 * @description
 * @created 2022/7/1-14:56
 **/
public interface DictService extends IService<Dict> {

    //根据数据id查询子数据列表
    List<Dict> findChildData(Long id);

    //导出数据字典表
    void exportDictData(HttpServletResponse response);

    //导入数据字典表
    void importDictData(MultipartFile file);

    String getName(String dictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
