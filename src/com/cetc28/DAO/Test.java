package com.cetc28.DAO;

import com.cetc28.DAO.Impl.HouseDAOImpl;
import com.cetc28.entity.House;

import java.util.List;

/**
 * @author Licaodong
 * @project exam
 * @date 2023/8/2 21:59
 **/
public class Test {
    public static void main(String[] args) {
        HouseDAO houseDAO = new HouseDAOImpl();
//        houseDAO.readFileAndSave("d:\\aaa\\house.txt");
//        List<House> list = houseDAO.selectByStatus();
//        list.forEach(System.out::println);
//        System.out.println(houseDAO.updatePrice());
//        System.out.println(houseDAO.selectRentDays());
        System.out.println(houseDAO.selectRentDays());
    }
}
