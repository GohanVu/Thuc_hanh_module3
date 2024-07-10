package repositories.impl;

import models.Product;
import repositories.IRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static repositories.impl.BaseRepository.getConnection;

public class ProductRepository implements IRepository {
    private static ProductRepository instance;

    private ProductRepository() {
    }

    public synchronized static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select id_san_pham,ten_san_pham,gia, muc_giam_gia, ton_kho from san_pham");
            ResultSet resultSet = preparedStatement.executeQuery();
            int id;
            String name;
            Float price;
            Float discount;
            int quantity;

            while (resultSet.next()) {
                id = resultSet.getInt("id_san_pham");
                name = resultSet.getString("ten_san_pham");
                price = resultSet.getFloat("gia");
                discount = resultSet.getFloat("muc_giam_gia");
                quantity = resultSet.getInt("ton_kho");

                products.add(new Product(id,name,price,discount,quantity));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void save(Product product) {
        try {
            PreparedStatement preparedStatement = getConnection().
                    prepareStatement("insert into san_pham(ten_san_pham,gia,muc_giam_gia,ton_kho) values (?,?,?,?)");
            preparedStatement.setString(1,product.getName());
            preparedStatement.setFloat(2,product.getPrice());
            preparedStatement.setFloat(3, product.getDiscount());
            preparedStatement.setInt(4,product.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return;
    }

    @Override
    public List<Product> findTop(int top) {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select product.*, sum(ton_kho) as sum from san_pham inner join order_detail on san_pham.id_san_pham=order_detail.id_san_pham group by san_pham.id_san_pham order by sum desc limit ?");
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.setInt(1, top);
            int id;
            String name;
            Float price;
            Float discount;
            int quantity;

            while (resultSet.next()) {
                id = resultSet.getInt("id_san_pham");
                name = resultSet.getString("ten_san_pham");
                price = resultSet.getFloat("gia");
                discount = resultSet.getFloat("muc_giam_gia");
                quantity = resultSet.getInt("ton_kho");

                products.add(new Product(id,name,price,discount,quantity));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

}


