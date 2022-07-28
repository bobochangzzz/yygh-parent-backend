package com.bobochang.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bobochang.yygh.cmn.listener.DictListener;
import com.bobochang.yygh.cmn.mapper.DictMapper;
import com.bobochang.yygh.cmn.service.DictService;
import com.bobochang.yygh.model.cmn.Dict;
import com.bobochang.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bobochang
 * @description
 * @created 2022/7/1-14:56
 **/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    //根据数据id查询子数据列表
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(queryWrapper);
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean hasChild = this.hasChild(dictId);
            dict.setHasChildren(hasChild);
        }
        return dictList;
    }

    //导出数据字典表
    @Override
    public void exportDictData(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<Dict> dictList = baseMapper.selectList(null);
        List<DictEeVo> dictVoList = new ArrayList<>();
        for (Dict dict : dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            //dictEeVo.setId(dict.getId)
            BeanUtils.copyProperties(dict, dictEeVo);
            dictVoList.add(dictEeVo);
        }
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName(String dictCode, String value) {
        if (StringUtils.isEmpty(dictCode)) {
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(queryWrapper);
            return dict.getName();
        } else {
            QueryWrapper<Dict> parentIdQueryWrapper = new QueryWrapper<>();
            parentIdQueryWrapper.eq("dict_code", dictCode);
            Long parentId = baseMapper.selectOne(parentIdQueryWrapper).getId();
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>()
                    .eq("parent_id", parentId)
                    .eq("value", value));
            return finalDict.getName();
        }
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        Long parentId = baseMapper.selectOne(queryWrapper).getId();
        List<Dict> list = findChildData(parentId);
        return list;
    }

    //判断当前id是否有子节点

    private boolean hasChild(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
