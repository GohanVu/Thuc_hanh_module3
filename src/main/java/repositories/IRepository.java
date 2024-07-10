package repositories;

import models.Product;

import java.util.List;

public interface IRepository {
    List<Product> findAll();

    void save(Product product);

    List<Product> findTop(int top);
}
