package testing;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebStaurantStore {
	static WebDriver driver;
	static WebDriverWait wait;
	public static void main(String[] args) throws InterruptedException {

		try {
		    WebDriverManager.chromedriver().setup();
		    driver = new ChromeDriver();
		
		// 1. Go to https://www.webstaurantstore.com/
		
		driver.get("https://www.webstaurantstore.com/");
        driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
		
		// 2. Search for 'stainless work table'.
        
		WebElement searchInput = driver.findElement(By.id("searchval"));
        searchInput.sendKeys("stainless work table");
        searchInput.submit();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        
        // 3. Check the search result ensuring every product has the word 'Table' in its title.
        
        List<WebElement> productElements = driver.findElements(By.xpath("//div[@class='description']/a"));
        boolean allProductsContainTable = true;
        for (WebElement product : productElements) {
            if (!product.getText().contains("Table")) {
                allProductsContainTable = false;
                break;
            }
        }
        System.out.println("All products contain the word 'Table': " + allProductsContainTable);
         // ScrollDown to the bottom of the page
        JavascriptExecutor jes = (JavascriptExecutor) driver;
        jes.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        // Locate the pagination component
        WebElement paginationContainer = driver.findElement(By.xpath("//div[@id='paging']"));
        // Get all the page number elements
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        List<WebElement> pageNumbers = paginationContainer.findElements(By.tagName("a"));
        // Find the last page element
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement lastPage = pageNumbers.get(pageNumbers.size() - 2);
       // Click the last page element
        lastPage.click();
        //wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        Thread.sleep(100);
        // Scroll down
        jes.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        // 4.found the last item and Add it to the cart
        // Find all elements with the class attribute value "add-to-cart"
        wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='add-to-cart']"));
        // Check if the elements list is not empty
        if (!elements.isEmpty()) {
            // Get the last element from the list
            WebElement lastElement = elements.get(elements.size() - 1);
            // Interact with the last element as needed
            lastElement.click(); // Example: Click on the last element
        } else {
            System.out.println("No elements found with the class 'add-to-cart'.");
        }
        // Click on View cart button
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement clickViewCartButton = driver.findElement(By.xpath("//a[normalize-space()='View Cart']"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        clickViewCartButton.click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        // View Cart
        WebElement viewCart = driver.findElement(By.xpath("//span[@id='cartItemCountSpan']"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        viewCart.click();
        
        // 5. Empty cart
        
        WebElement emptyCart = driver.findElement(By.xpath("//button[contains(text(),'Empty Cart')]"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        emptyCart.click();
        // Assertion to check if the Cart page is displayed
        WebElement cartHeader = driver.findElement(By.xpath("//h1[normalize-space()='Cart']"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        Assert.assertEquals(cartHeader.getText(), "Cart", "Cart page is not displayed.");
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        // Assertion to check if the Empty Cart Title is displayed
        WebElement emptyCartTitle = driver.findElement(By.xpath("//h2[@id='empty-cart-title']"));
        String actualEmptyCartTitle = emptyCartTitle.getText();
        String expectedEmptyCartTitle = "Empty Cart";
        Assert.assertEquals(actualEmptyCartTitle, expectedEmptyCartTitle, "Empty Cart Title is not displayed correctly.");
        // Click on Empty Cart button
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement emptyCartButton = driver.findElement(By.xpath("/html[1]/body[1]/div[11]/div[1]/div[1]/div[1]/footer[1]/button[1]"));
        emptyCartButton.click();
        // Assertion to check if the Cart is Empty Cart
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement emptyCartElement = driver.findElement(By.xpath("//p[contains(text(),'Your cart is empty.')]"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        String actualEmptyCartText = emptyCartElement.getText();
        String expectedEmptyCartText = "Your cart is empty.";
        Assert.assertEquals(actualEmptyCartText, expectedEmptyCartText, "The cart is not empty.");
        
        // Close the browser
        driver.quit();
        
		} catch (Exception e) {
		    e.printStackTrace();
		    // Handle exception or display error message
		} finally {
		    // Ensure browser is closed even if an exception occurred
		    if (driver != null) {
		        driver.quit();
		 }
	}
 }
}
	
	
  




        
        
        
	


    


