package br.com.projeto.ecommerce.service;

import br.com.projeto.ecommerce.dto.ProductDto;
import br.com.projeto.ecommerce.enums.ProductCategory;
import br.com.projeto.ecommerce.mapper.ModelMapper;
import br.com.projeto.ecommerce.models.Product;
import br.com.projeto.ecommerce.repository.ProductRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        try {
            List<Product> products = productRepository.findAll();
            return ModelMapper.parseListObjects(products, ProductDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar todos os produtos!");
        }
    }

    public List<ProductDto> findProductsByCategory(ProductCategory productCategory) {
        try {
            List<Product> products = productRepository.findAll();
            return products.stream()
                    .filter(product -> product.getProductCategory().equals(productCategory))
                    .map(p -> ModelMapper.parseObject(p, ProductDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar os produtos!");
        }
    }

    public ProductDto findById(Long id) {
        if(id != null) {
            Optional<Product> product = productRepository.findById(id);
            return ModelMapper.parseObject(product, ProductDto.class);
        } else {
            throw new RuntimeException("Insira um id para fazer a busca!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(ProductDto dto) {
        try {
            if(dto == null) {
                throw new RuntimeException("Insira os dados do produto!");
            }
            Product product = ModelMapper.parseObject(dto, Product.class);
            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Product updateProduct(ProductDto dto, Long id) {
        if (dto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }

        Optional<Product> productExists = productRepository.findById(id);
        if (productExists.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        try {
            Product product = productExists.get();
            BeanUtils.copyProperties(dto, product);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o produto") ;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void removeProduct(Long id) {
        if(id == null) {
            throw new RuntimeException("Insira um id válido!");
        }
        Optional<Product> product = productRepository.findById(id);
        product.ifPresentOrElse(
                p -> productRepository.deleteById(id),
                () -> {
                    throw new RuntimeException("Produto não encontrado com ID: " + id);
                }
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public void readAndSaveExcel(MultipartFile file) throws IOException {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                ProductDto dto = new ProductDto();
                dto.setName(row.getCell(0).toString());
                dto.setDescription(row.getCell(1).toString());
                dto.setProductCategory(ProductCategory.valueOf(row.getCell(2).getStringCellValue()));
                dto.setAmount((int) row.getCell(3).getNumericCellValue());
                dto.setPrice(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));

                Product product = ModelMapper.parseObject(dto, Product.class);
                productRepository.save(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
