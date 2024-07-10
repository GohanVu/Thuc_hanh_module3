package services;

import models.Product;

import java.util.List;

public interface IService {
    List<Product> findAll();

    void save(Product product);

    List<Product> findTop(int top);

}
