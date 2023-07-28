package jp.co.sss.shop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.sss.shop.controller.item.ItemShowCustomerController;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(MockitoJUnitRunner.class)
public class ItemShowCustomerControllerTest {
//
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private ItemShowCustomerController itemShowCustomerController;
//
//    @Mock
//    private ItemRepository itemRepository;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(itemShowCustomerController).build();
//    }
//
//    @Test
//    public void testShowItem() throws Exception {
//        // Given
//        Item item = new Item(1, "Test Item", "Test Description", "Test Image", "Test categoryName");
//        when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(item));
//
//        // When and Then
//        mockMvc.perform(get("/customer/item/1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("itemShowCustomerView"))
//                .andExpect(model().attributeExists("item"))
//                .andExpect(model().attribute("item", item));
//    }
}