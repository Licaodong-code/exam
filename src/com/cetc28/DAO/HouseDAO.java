package com.cetc28.DAO;

import com.cetc28.entity.House;

import java.util.List;

/**
 * @author Licaodong
 * @project exam
 * @date 2023/8/3 16:11
 **/
public interface HouseDAO {
    void save(House house);
    void readFileAndSave(String path);
    List<House> selectByStatus();
    int updatePrice();
    long selectRentDays();
}
