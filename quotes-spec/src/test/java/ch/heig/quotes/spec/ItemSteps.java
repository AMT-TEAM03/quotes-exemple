package ch.heig.quotes.spec;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.api.ItemsEndPointApi;
import org.openapitools.client.api.SoundsEndPointApi;
import org.openapitools.client.model.Item;
import org.openapitools.client.model.Sound;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ItemSteps {

    private final ItemsEndPointApi itemApi = new ItemsEndPointApi();
    private final SoundsEndPointApi soundApi = new SoundsEndPointApi();
    private Item item;
    private int statusCode;
    private List<Item> items;

    @Given("I have an item with the id:{int} and the name:{word}")
    public void i_have_an_item(int idItem, String nameItem) throws Throwable{
        item = new Item();
        item.setId(idItem);
        item.setName(nameItem);
        // Set random sounds
        ApiResponse response = soundApi.getSoundsWithHttpInfo();
        List<Sound> existingSounds = (List<Sound>) response.getData();
        int randomIndex = (int) (Math.random() * existingSounds.size());
        item.setSoundTape(existingSounds.get(randomIndex));
        List<Sound> soundsTombe = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            int newRandomIndex = randomIndex;
            while(randomIndex == newRandomIndex){
                newRandomIndex = (int) (Math.random() * existingSounds.size());
            }
            randomIndex = newRandomIndex;
            soundsTombe.add(existingSounds.get(randomIndex));
        }
        item.setSoundsTombe(soundsTombe);
    }

    @When("I POST it to the items endpoint")
    public void i_POST_it_to_the_items_endpoint() throws Throwable{
        try{
            ApiResponse response = itemApi.addItemWithHttpInfo(item);
            statusCode = response.getStatusCode();
        } catch (ApiException e) {
            statusCode = e.getCode();
        }
    }

    @When("I PUT it to the items endpoint")
    public void i_PUT_it_to_the_items_endpoint() throws Throwable{
        try{
            ApiResponse response = itemApi.putItemWithHttpInfo(item);
            statusCode = response.getStatusCode();
        } catch (ApiException e) {
            statusCode = e.getCode();
        }
    }

    @When("I PATCH it to the items endpoint")
    public void i_PATCH_it_to_the_items_endpoint() throws Throwable{
        try{
            ApiResponse response = itemApi.patchItemWithHttpInfo(item);
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
            ApiResponse response = itemApi.getItemsWithHttpInfo();
            statusCode = response.getStatusCode();
            items = (List<Item>) response.getData();
        }catch(ApiException e){
            statusCode = e.getCode();
        }
    }

    @Then("I expect id:{int} name:{word} to be part of the response")
    public void i_expect_id_in_response(int id, String name){
        boolean found = false;
        for(Item currentItem : items){
            if(currentItem.getId() == id){
                if(currentItem.getName().equals(name)){
                    found = true;
                }
                break;
            }
        }
        assertEquals(found, true);
    }
}