package stepdefs;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookStepDefinitions {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    @Then("the status code is (\\d+)")
    public void verify_status_code(int statusCode) {
        json = response.then().statusCode(statusCode);
    }

    @And("^I can print the services offered by the retailer$")
    public void iCanPrintTheServicesOfferedByTheRetailer(DataTable retailerId) throws Throwable {
        ArrayList<String> servicesOffered;
        servicesOffered = response.path("services");
        System.out.println("The services offered by the retailer with " + getDataFromTable(retailerId) + " are - \n" + servicesOffered + "\n");
    }

    @Given("^I retrieve the services of a retailer with retailer id$")
    public void theFollowingRetailers(DataTable table) throws Throwable {
        Map<String, String> retailerId = getDataFromTable(table);
        request = given().pathParam("retailerId", retailerId.get("Retailer Id"));
        response = request.when().get("/content/retailerServices/{retailerId}");
    }

    @Given("^the base url$")
    public void theBaseUrl(DataTable table) throws Throwable {
        String baseURL = "";
        for (int j = 0; j < table.cells(0).size(); j++)
            for (int i = 0; i < table.cells(0).get(0).size(); i++)
                baseURL = table.cells(0).get(j).get(i);
        RestAssured.baseURI = baseURL;
    }

    @When("^I retrieve a brief introduction of all the retailers$")
    public void iRetrieveABriefIntroductionOfARetailer() throws Throwable {
        request = given();
        response = request.when().get("/retailer/allLiveRetailersWithShortInfo");
    }

    @And("^I can print a brief introduction of a retailer$")
    public void iCanPrintABriefIntroductionOfARetailer(DataTable table) throws Throwable {
        Map<String, String> retailerName = getDataFromTable(table);
        ArrayList<String> dealerNumber = response.path("dealerNumber");
        ArrayList<Integer> id = response.path("id");
        ArrayList<Float> latitude = response.path("latitude");
        ArrayList<Float> longitude = response.path("longitude");
        ArrayList<String> name = response.path("name");
        ArrayList<String> fcaName = response.path("fcaName");
        for (int i = 0; i < name.size(); i++) {
            if (name.get(i).equals(retailerName.get("Retailer Name"))) {
                System.out.println("The brief information for the request retailer is -");
                System.out.println("dealer number - " + dealerNumber.get(i));
                System.out.println("id - " + id.get(i));
                System.out.println("latitude - " + latitude.get(i));
                System.out.println("longitude - " + longitude.get(i));
                System.out.println("name - " + name.get(i));
                System.out.println("fcaName - " + fcaName.get(i));
            }
        }
    }

    private Map<String, String> getDataFromTable(DataTable table) {
        List<Map<String, String>> tableRow = table.asMaps(String.class, String.class);
        Map<String, String> data = null;
        for (Map<String, String> aTableRow : tableRow) {
            data = aTableRow;
        }
        return data;
    }
}