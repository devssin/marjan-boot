package ma.yc.marjane;
import ma.yc.marjane.DTO.ProductDTO;
import ma.yc.marjane.DTO.PromotionDTO;
import ma.yc.marjane.Models.ProductModel;
import ma.yc.marjane.Models.PromotionModel;
import ma.yc.marjane.Mappers.ProductMapper;
import ma.yc.marjane.Repositories.PromotionRepository;
import ma.yc.marjane.Services.ProductService;
import ma.yc.marjane.Services.PromotionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest {
    @Mock
    private ProductService productService;

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionService promotionService;

    @Test
    public void testAcceptPromotion() {
        // Arrange
        int promotionId = 1;

        // Mocking the PromotionModel and ProductDTO
        PromotionModel promotionModel = new PromotionModel();
        promotionModel.setId(promotionId);
        promotionModel.setPromotionPercentage(10.0F);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2);
        productDTO.setPrice(100.0);

        // Mocking the ProductService methods
        Mockito.when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotionModel));
        Mockito.when(productService.read(productDTO.getId())).thenReturn(productDTO);

        // Mocking the ProductService update method
        Mockito.when(productService.update(Mockito.any())).thenReturn(new ProductDTO());

        // Act
        ProductDTO updatedProduct = promotionService.acceptPromotion(promotionId);

        // Assert
        // Verify that the ProductService methods were called
        Mockito.verify(productService, Mockito.times(1)).read(productDTO.getId());
        Mockito.verify(productService, Mockito.times(1)).update(Mockito.any());

        // Add assertions based on the expected behavior of the acceptPromotion method
        assertEquals(90.0, updatedProduct.getPrice(), 0.01);
    }

}
