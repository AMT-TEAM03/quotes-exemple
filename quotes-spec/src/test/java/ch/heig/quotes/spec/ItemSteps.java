package ch.heig.quotes.spec;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.api.ItemsEndPointApi;
import org.openapitools.client.model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ItemSteps {

    private final ItemsEndPointApi api = new ItemsEndPointApi();
    private Item item;
    private int statusCode;

    @Given("I have an item without sounds")
    public void i_have_an_item_without_sounds() throws Throwable{
        item = new Item();
        item.setName("Marteau");
    }

    @When("I POST it to the \\/items endpoint")
    public void i_POST_it_to_the_items_endpoint() throws Throwable{
        try{
            ApiResponse response = api.addItemWithHttpInfo(item);
            statusCode = response.getStatusCode();
            System.out.println(response.getData());
        } catch (ApiException e) {
            statusCode = e.getCode();
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, statusCode);
    }

    //Exemple venu du projet de départ

    /*private final QuotesEndPointApi api = new QuotesEndPointApi();
    private Quote quote;
    private int statusCode;
    @Given("I have an quote payload")
    public void i_have_an_quote_payload() throws Throwable {
        quote = new Quote();
        quote.setAuthor("Coluche");
        quote.setCitation("Se pencher sur son passé, c'est risquer de tomber dans l'oubli.");
    }
    @When("I POST it to the \\/quotes endpoint")
    public void i_POST_it_to_the_quotes_endpoint() throws Throwable {
        try {
            ApiResponse response = api.addQuoteWithHttpInfo(quote);
            statusCode = response.getStatusCode();
        } catch (ApiException e) {
            statusCode = e.getCode();
        }
    }
    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, statusCode);
    }*/
}