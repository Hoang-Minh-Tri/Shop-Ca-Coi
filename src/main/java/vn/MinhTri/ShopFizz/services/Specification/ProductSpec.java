package vn.MinhTri.ShopFizz.services.Specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.Product_;

public class ProductSpec {
    public static Specification<Product> PriceMinMax(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), min, max);
    }

    public static Specification<Product> checkFactory(List<String> factorys) {
        return (root, query, criteriaBuider) -> criteriaBuider.in(root.get(Product_.FACTORY)).value(factorys);
    }

    public static Specification<Product> checkTaget(List<String> targets) {
        return (root, query, criteriaBuider) -> criteriaBuider.in(root.get(Product_.TARGET)).value(targets);
    }

    public static Specification<Product> checkStatus(String status) {
        return (root, query, criteriaBuider) -> criteriaBuider.in(root.get(Product_.STATUS)).value(status);
    }

    public static Specification<Product> checkSearch(String name) {
        return (root, query, criteriaBuider) -> criteriaBuider.like(root.get(Product_.STATUS), "%" + name + "%");
    }
}
