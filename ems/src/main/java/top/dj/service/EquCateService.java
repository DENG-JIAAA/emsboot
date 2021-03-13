package top.dj.service;

import top.dj.POJO.DO.EquCate;

import java.util.Map;

public interface EquCateService extends MyIService<EquCate> {

    Map<Integer, Integer> getTheMapOfCate();

    Map<Integer, String> getTheMapWithNameOfCate();
}
