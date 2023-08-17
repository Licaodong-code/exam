package com.cetc28.DAO.Impl;

import com.cetc28.DAO.HouseDAO;
import com.cetc28.DbUtils.DbObject;
import com.cetc28.DbUtils.DbUtil;
import com.cetc28.entity.House;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Licaodong
 * @project exam
 * @date 2023/8/3 16:13
 **/
public class HouseDAOImpl implements HouseDAO {

    @Override
    public void save(House house) {
        String sql = "insert into houses values(?,?,?,?,?,?)";
        int i = DbUtil.executeSql(DbUtil.getPoolConnection(), sql, house.getId(), house.getAddress(), house.getArea(), house.getPrice(), house.getStatus(),house.getRent_date());
        System.out.println(i>0?"插入成功":"插入失败");
    }

    @Override
    public void readFileAndSave(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String str;
            House house = new House();
            while ((str = bufferedReader.readLine()) != null){
                String[] strings = str.split(",");
                System.out.println(strings.length);
                house.setId(DbUtil.getId());
                house.setAddress(strings[0]);
                house.setArea(Double.parseDouble(strings[1]));
                house.setPrice(Double.parseDouble(strings[2]));
                house.setStatus(Integer.parseInt(strings[3]));
                house.setRent_date(Date.valueOf(strings[4]));
                save(house);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<House> selectByStatus() {
        try {
            String sql = "select * from houses where status = 2 order by rent_date desc";
            DbObject dbObject = DbUtil.executeQuery(DbUtil.getPoolConnection(), sql);
            ResultSet resultSet = dbObject.getResultSet();
            List<House> list = new ArrayList<>();

            while (resultSet.next()){
                House house = new House();
                house.setId(resultSet.getString(1));
                house.setAddress(resultSet.getString(2));
                house.setArea(resultSet.getDouble(3));
                house.setPrice(resultSet.getDouble(4));
                house.setStatus(resultSet.getInt(5));
                house.setRent_date(resultSet.getDate(6));
                list.add(house);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int updatePrice() {
        String sql = "UPDATE HOUSES\n" +
                "SET price = price * 0.9\n" +
                "WHERE status = 2";

        int i = DbUtil.executeSql(DbUtil.getPoolConnection(), sql);
        return i;
    }

    @Override
    public long selectRentDays() {
        try {
            String sql = "SELECT AVG(TO_NUMBER(SYSDATE - rent_date)) AS average_rental_days\n" +
                        "FROM HOUSES\n" +
                        "WHERE status = 1";
            QueryRunner queryRunner = new QueryRunner(DbUtil.getPoolDataSource());
            ScalarHandler<Long> handler=new ScalarHandler<>();
            Long query = queryRunner.query(sql, handler);
            return query;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
