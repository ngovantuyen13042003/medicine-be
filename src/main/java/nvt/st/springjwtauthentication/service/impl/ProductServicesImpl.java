package nvt.st.springjwtauthentication.service.impl;

import nvt.st.springjwtauthentication.Repository.ProductRepository;
import nvt.st.springjwtauthentication.entity.Product;
import nvt.st.springjwtauthentication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServicesImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
