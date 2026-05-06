package mutsa.week2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class Week2ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private static final String AUTHORIZATION = "Authorization";
	private static final String TOKEN = "Bearer test-token";

	@Test
	void contextLoads() {
	}

	@Test
	void week5ApiSpecFlow() throws Exception {
		String createProductRequest = """
				{
				  "name":"맥북 프로",
				  "price":2000000,
				  "stock":3
				}
				""";

		mockMvc.perform(post("/products")
						.header(AUTHORIZATION, TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(createProductRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("맥북 프로"));

		mockMvc.perform(get("/products")
						.header(AUTHORIZATION, TOKEN))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.products[0].id").value(1));

		String addCartItemRequest = """
				{
				  "productId": 1,
				  "quantity": 2
				}
				""";

		mockMvc.perform(post("/cart/items")
						.header(AUTHORIZATION, TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(addCartItemRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.productId").value(1))
				.andExpect(jsonPath("$.quantity").value(2));

		mockMvc.perform(get("/cart")
						.header(AUTHORIZATION, TOKEN))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cartItems[0].id").value(1))
				.andExpect(jsonPath("$.totalPrice").value(4000000));

		String updateQuantityRequest = """
				{
				  "quantity": 3
				}
				""";

		mockMvc.perform(patch("/cart/items/{itemId}", 1L)
						.header(AUTHORIZATION, TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(updateQuantityRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.quantity").value(3));

		String updateImageRequest = """
				{
				  "imageUrl":"https://cdn.hongik.com/img/new.png"
				}
				""";

		mockMvc.perform(patch("/cart/items/{itemId}/image", 1L)
						.header(AUTHORIZATION, TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(updateImageRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.imageUrl").value("https://cdn.hongik.com/img/new.png"));

		mockMvc.perform(get("/search")
						.header(AUTHORIZATION, TOKEN)
						.param("keyword", "맥북"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalCount").value(1))
				.andExpect(jsonPath("$.items[0].name").value("맥북 프로"));

		mockMvc.perform(get("/search/popular")
						.header(AUTHORIZATION, TOKEN))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items[0].rank").value(1));

		mockMvc.perform(delete("/cart/items/{itemId}", 1L)
						.header(AUTHORIZATION, TOKEN))
				.andExpect(status().isNoContent());
	}

}
