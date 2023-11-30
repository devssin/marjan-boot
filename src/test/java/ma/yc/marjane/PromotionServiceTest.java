package ma.yc.marjane;

import ma.yc.marjane.DTO.CategoryDTO;
import ma.yc.marjane.Models.ProductModel;
import ma.yc.marjane.Models.PromotionModel;
import ma.yc.marjane.Services.CategoryService;
import ma.yc.marjane.Services.PromotionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PromotionServiceTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private PromotionService promotionService;

    @Test
    public void testReadByCategory() {
        // Arrange
        int categoryId = 1;

        // Mocking CategoryDTO and ProductModel
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setName("Electronics");

        List<ProductModel> productModelList = new ArrayList<>();
        ProductModel productModel = new ProductModel();
        productModel.setId(1);
        productModelList.add(productModel);

        List<PromotionModel> promotionModelList = new ArrayList<>();
        PromotionModel promotionModel = new PromotionModel();
        promotionModel.setProduct(productModel);
        promotionModelList.add(promotionModel);

        // Mocking CategoryService read method
        when(categoryService.read(categoryId)).thenReturn(categoryDTO);

        // Mocking PromotionService readAll method
        when(promotionService.readAll()).thenReturn(promotionModelList);

        // Act
        List<PromotionModel> matchingPromotions = promotionService.readByCategory(categoryId);

        // Assert
        assertNotNull(matchingPromotions);
        assertEquals(1, matchingPromotions.size());
        assertEquals(promotionModel, matchingPromotions.get(0));

        // Verify that setPromotion and setCategory methods were called on the matching promotion's product
        Mockito.verify(promotionModel.getProduct(), Mockito.times(1)).setPromotion(null);
        Mockito.verify(promotionModel.getProduct(), Mockito.times(1)).setCategory(null);
    }
}
