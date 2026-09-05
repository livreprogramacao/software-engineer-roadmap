package com.petshop.company.adapter.in.web.controller;

import com.petshop.company.domain.dto.CreateProductRequest;
import com.petshop.company.domain.dto.ProductDTO;
import com.petshop.company.domain.port.in.ProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "CRUD de produtos")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar um novo produto", description = "Cria um novo produto no catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ProductDTO> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do produto a ser criado")
            @Valid @RequestBody CreateProductRequest request) {
        log.info("POST /api/products - name: {}", request.getName());
        ProductDTO created = productUseCase.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna a lista de todos os produtos do catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    })
    public ResponseEntity<Page<ProductDTO>> getAllProducts(int page , int size) {
        log.info("GET /api/products");

        return ResponseEntity.ok(productUseCase.getAllProducts(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        log.info("GET /api/products/{}", id);
        return ResponseEntity.ok(productUseCase.getProductById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "ID do produto") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do produto")
            @Valid @RequestBody CreateProductRequest request) {
        log.info("PUT /api/products/{}", id);
        return ResponseEntity.ok(productUseCase.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Remove um produto do catálogo pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        log.info("DELETE /api/products/{}", id);
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
