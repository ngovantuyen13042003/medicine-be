package nvt.st.springjwtauthentication.service;

import nvt.st.springjwtauthentication.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
}
