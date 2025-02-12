package br.com.projeto.ecommerce.service;

import br.com.projeto.ecommerce.dto.ProductDto;
import br.com.projeto.ecommerce.enums.ProductCategory;
import br.com.projeto.ecommerce.mapper.ProductMapper;
import br.com.projeto.ecommerce.models.Product;
import br.com.projeto.ecommerce.repository.ProductRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
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
            return ProductMapper.INSTANCE.toDtoList(products);
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
                    .map(ProductMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar os produtos!");
        }
    }

    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper.INSTANCE::toDto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(ProductDto dto) {
        try {
            if(dto == null) {
                throw new RuntimeException("Insira os dados do produto!");
            }
            Product product = ProductMapper.INSTANCE.toEntity(dto);
            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDto updateProduct(ProductDto dto, Long id) {
        if (dto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID " + id));

        try {
            ProductMapper.INSTANCE.updateEntityFromDto(dto, product);
            Product updateProduct = productRepository.save(product);
            return ProductMapper.INSTANCE.toDto(updateProduct);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o produto") ;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void removeProduct(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Insira um id válido!");
        }

        Product product = productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Produto não encontrado com ID: " + id));

        productRepository.delete(product);
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

                Product product = ProductMapper.INSTANCE.toEntity(dto);
                productRepository.save(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
