package guru.springframework.spring6restmvc;

import guru.springframework.spring6restmvc.controller.BeerController;
import guru.springframework.spring6restmvc.controller.CustomerController;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.service.BeerService;
import guru.springframework.spring6restmvc.service.BeerServiceImpl;
import guru.springframework.spring6restmvc.service.CustomerService;
import guru.springframework.spring6restmvc.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({BeerController.class, CustomerController.class})
class Spring6RestMvcApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;
    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @MockBean
    CustomerService customerService;
    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get("/api/v1/beers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beers/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void testListCustomers() throws Exception {
        given(customerService.getCustomers()).willReturn(customerServiceImpl.getCustomers());

        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getCustomers().get(0);
        given(customerService.getCustomerById(customer.getId())).willReturn(customer);

        mockMvc.perform(get("/api/v1/customers/"+customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(customer.getCustomerName())));
    }

}
