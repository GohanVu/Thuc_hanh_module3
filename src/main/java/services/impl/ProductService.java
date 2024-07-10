package services.impl;

import models.Product;
import repositories.IRepository;
import repositories.impl.ProductRepository;
import services.IService;

import java.util.List;

public class ProductService implements IService {
    private static IRepository productRepository = ProductRepository.getInstance();
    private static IService instance;

    private ProductService() {
    }

    public synchronized static IService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findTop(int top) {
        return productRepository.findTop(top);
    }
}
