package vn.MinhTri.ShopFizz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> GetAllProduct() {
        List<Product> products = this.productRepository.findAll();
        return products;
    }
}
