package ch.heig.quotes.spec;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.api.ItemsEndPointApi;
import org.openapitools.client.model.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ItemSteps {

    private final ItemsEndPointApi api = new ItemsEndPointApi();
    private Item item;
    private int statusCode;
    private List<Item> items;

    @Given("I have an item with the id:{int}")
    public void i_have_an_item_without_sounds(int idItem) throws Throwable{
        item = new Item();
        item.setId(idItem);
        item.setName("Marteau");
    }

    @When("I POST it to the items endpoint")
    public void i_POST_it_to_the_items_endpoint() throws Throwable{
        try{
            ApiResponse response = api.addItemWithHttpInfo(item);
            statusCode = response.getStatusCode();
        } catch (ApiException e) {
            statusCode = e.getCode();
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, statusCode);
    }

    @When("I fetch all the items")
    public void i_fetch_all_the_items() throws Throwable {
        try{
            ApiResponse response = api.getItemsWithHttpInfo();
            statusCode = response.getStatusCode();
            items = (List<Item>) response.getData();
        }catch(ApiException e){
            statusCode = e.getCode();
        }
    }

    @Then("I expect id:{int} to be part of the reponse")
    public void i_expect_id_in_response(int id){
        boolean found = false;
        for(Item item : items){
            if(item.getId() == id){
                found = true;
            }
        }
        assert found;
    }
}