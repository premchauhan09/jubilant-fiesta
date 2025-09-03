package pages;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.commons.io.FileUtils;

public class substore_page extends StartupPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public substore_page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ========== Locators ==========
    public By xyz = By.id("username_id");
    public By getPasswordTextboxLocator = By.xpath("//input[@id='password']");
    public By getSignInButtonLocator = By.xpath("//button[@id='login']");
    public By getDropDownLocater = By.xpath("//a[@href='#/WardSupply']");

    public By getCounterButtonFourth = By.xpath("//a[@class='report_list']");
    public By getAnchorTagLocatorInventory = By.xpath("//a[contains(text(),'Inventory')]");
    public By getModuleSignoutLocator = By.xpath("//i[contains(@class,'sign-out')]");
    public By getHoverText = By.xpath("//h6[contains(text(),'To change, you can always click here.')]");
    public By getAnchorTagLocatorPharmacy = By.xpath("//a[contains(text(),'Pharmacy')]");
    public By getSubModuleLocator = By.xpath("//ul[contains(@class,'nav-tabs')]//li//a");
    public By getAnchorTagLocatorStock = By.xpath("//a[contains(text(),'Stock')]");
    public By getAnchorTagLocatorByTextInventoryRequisition = By.xpath("//a[contains(text(),'Inventory Requisition')]");
    public By getAnchorTagLocatorConsumption = By.xpath("//a[contains(text(),'Consumption')]");
    public By getAnchorTagLocatorReports = By.xpath("//a[contains(text(),'Reports')]");
    public By getAnchorTagLocatorPatientConsumption = By.xpath("//a[contains(text(),'Patient Consumption')]");
    public By getAnchorTagLocatorReturn = By.xpath("//a[contains(text(),'Return')]");

    public By getCreateRequisitionButton = By.xpath("//button/span[text()='Create Requisition']");
    public By getStarIconLocator = By.xpath("//i[contains(@class,'icon-favourite')]/..");
    public By getButtonLocatorFirst = By.xpath("//button[contains(text(),'First')]");
    public By getButtonLocatorPrevious = By.xpath("//button[contains(text(),'Previous')]");
    public By getButtonLocatorNext = By.xpath("//button[contains(text(),'Next')]");
    public By getButtonLocatorLast = By.xpath("//button[contains(text(),'Last')]");
    public By getButtonLocatorOK = By.xpath("//button[contains(text(),'OK')]");
    public By getRadioButtonLocatorPending = By.xpath("//label[contains(text(),'Pending')]/span");
    public By getRadioButtonLocatorComplete = By.xpath("//label[contains(text(),'Complete')]/span");
    public By getRadioButtonLocatorCancelled = By.xpath("//label[contains(text(),'Cancelled')]/span");
    public By getRadioButtonLocatorWithdrawn = By.xpath("//label[contains(text(),'Withdrawn')]/span");
    public By getRadioButtonLocatorAll = By.xpath("//label[contains(text(),'All')]/span");

    public By getRequestButton = By.cssSelector("input#save_requisition");
    public By getTargetInventory = By.xpath("//input[@id='activeInventory']");
    public By getItemName = By.xpath("//input[@id='itemName0']");
    public By getRequiredQuantity = By.xpath("//input[@id='qtyip0']");
    public By popupCloseButton = By.cssSelector("a.close-btn");
    public By getCloseModalLocator = By.cssSelector("a[title='Cancel']");

    public By searchBarId() {
        return By.id("quickFilterInput");
    }

    public By getAnchorTagLocatorByText(String anchorTagName) {
        return By.xpath("//a[contains(text(),'" + anchorTagName + "')]");
    }

    public By getPopUpMessageText(String msgStatus, String messageText) {
        return By.xpath("//p[contains(text(),' " + msgStatus + " ')]/../p[contains(text(),'" + messageText + "')]");
    }

    // ========== Methods ==========

    public boolean loginToHealthAppByGivenValidCredetial(Map<String, String> expectedData) throws Exception {
        try {
            WebElement username = driver.findElement(xyz);
            username.clear();
            username.sendKeys(expectedData.get("username"));

            WebElement password = driver.findElement(getPasswordTextboxLocator);
            password.clear();
            password.sendKeys(expectedData.get("password"));

            driver.findElement(getSignInButtonLocator).click();
            return true;
        } catch (Exception e) {
            throw new Exception("Login failed: " + e.getMessage(), e);
        }
    }

    public boolean scrollDownAndClickSubstoreTab() throws Exception {
        try {
            WebElement substoreTab = driver.findElement(getDropDownLocater);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", substoreTab);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -50)");
            substoreTab.click();
            wait.until(ExpectedConditions.urlContains("WardSupply"));
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to click Substore tab: " + e.getMessage(), e);
        }
    }

    public String verifySubstorePageUrl() throws Exception {
        return driver.getCurrentUrl();
    }

    public boolean clickFourthCounterIfAvailable() throws Exception {
        try {
            List<WebElement> counters = driver.findElements(getCounterButtonFourth);
            if (!counters.isEmpty()) {
                counters.get(0).click(); // currently clicking the first element
            }
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to click counter: " + e.getMessage(), e);
        }
    }

    public boolean verifyModuleSignoutHoverText(Map<String, String> substoreExpectedData) throws Exception {
        try {
            driver.findElement(getAnchorTagLocatorInventory).click();
            WebElement elementToHover = driver.findElement(getModuleSignoutLocator);
            Actions actions = new Actions(driver);
            actions.moveToElement(elementToHover).perform();
            String actualText = driver.findElement(getHoverText).getText();
            return actualText.contains(substoreExpectedData.get("moduleSignOutHoverText"));
        } catch (Exception e) {
            throw new Exception("Failed to verify hover text: " + e.getMessage(), e);
        }
    }

    public boolean verifySubstoreSubModule(Map<String, String> substoreExpectedData) throws Exception {
        try {
            WebElement inventory = driver.findElement(getAnchorTagLocatorInventory);
            WebElement pharmacy = driver.findElement(getAnchorTagLocatorPharmacy);
            inventory.click();
            pharmacy.click();
            return true;
        } catch (Exception e) {
            throw new Exception("Substore sub-modules verification failed: " + e.getMessage(), e);
        }
    }

    public boolean subModulePresentInventory() throws Exception {
        try {
            driver.findElement(getAnchorTagLocatorInventory).click();
            List<WebElement> subModules = driver.findElements(getSubModuleLocator);
            for (WebElement sub : subModules) {
                if (sub.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new Exception("Failed to verify inventory sub-modules: " + e.getMessage(), e);
        }
    }

    public boolean verifyNavigationBetweenSubmodules() throws Exception {
        try {
            driver.findElement(getAnchorTagLocatorInventory).click();

            driver.findElement(getAnchorTagLocatorStock).click();
            wait.until(ExpectedConditions.urlContains("Inventory/Stock"));

            driver.findElement(getAnchorTagLocatorByTextInventoryRequisition).click();
            wait.until(ExpectedConditions.urlContains("Inventory/InventoryRequisitionList"));

            driver.findElement(getAnchorTagLocatorConsumption).click();
            wait.until(ExpectedConditions.urlContains("Inventory/Consumption/ConsumptionList"));

            driver.findElement(getAnchorTagLocatorReports).click();
            wait.until(ExpectedConditions.urlContains("Inventory/Reports"));

            driver.findElement(getAnchorTagLocatorPatientConsumption).click();
            wait.until(ExpectedConditions.urlContains("Inventory/PatientConsumption/PatientConsumptionList"));

            driver.findElement(getAnchorTagLocatorReturn).click();
            wait.until(ExpectedConditions.urlContains("Inventory/Return"));

            driver.findElement(getAnchorTagLocatorStock).click();
            return true;
        } catch (Exception e) {
            throw new Exception("Navigation failed: " + e.getMessage(), e);
        }
    }

    public Boolean takingScreenshotOfTheCurrentPage() throws Exception {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File("SubStore.png"));
            return true;
        } catch (Exception e) {
            throw new Exception("Screenshot failed: " + e.getMessage(), e);
        }
    }

    public boolean verifyIfInventoryReqInputFieldsDropdownsAndCheckboxesAreVisibleOrNot() throws Exception {
        try {
            driver.findElement(getAnchorTagLocatorByText("Inventory Requisition")).click();
            wait.until(ExpectedConditions.urlContains("Inventory/InventoryRequisitionList"));

            List<WebElement> elements = Arrays.asList(
                driver.findElement(getButtonLocatorFirst),
                driver.findElement(getButtonLocatorPrevious),
                driver.findElement(getButtonLocatorNext),
                driver.findElement(getButtonLocatorLast),
                driver.findElement(getButtonLocatorOK),
                driver.findElement(getCreateRequisitionButton),
                driver.findElement(searchBarId()),
                driver.findElement(getStarIconLocator),
                driver.findElement(getRadioButtonLocatorPending),
                driver.findElement(getRadioButtonLocatorComplete),
                driver.findElement(getRadioButtonLocatorCancelled),
                driver.findElement(getRadioButtonLocatorWithdrawn),
                driver.findElement(getRadioButtonLocatorAll)
            );

            for (WebElement el : elements) {
                if (!el.isDisplayed()) {
                    throw new Exception("Element not visible: " + el.toString());
                }
            }
            return true;
        } catch (Exception e) {
            throw new Exception("Field verification failed: " + e.getMessage(), e);
        }
    }

    public String verifyCreateRequisitionButton() throws Exception {
        try {
            driver.findElement(getCreateRequisitionButton).click();
            wait.until(ExpectedConditions.urlContains("Inventory/InventoryRequisitionItem"));

            WebElement requestBtn = driver.findElement(getRequestButton);
            wait.until(ExpectedConditions.visibilityOf(requestBtn));

            WebElement inventoryField = driver.findElement(getTargetInventory);
            inventoryField.sendKeys("General-Inventory", Keys.TAB, Keys.TAB);

            WebElement itemField = driver.findElement(getItemName);
            itemField.sendKeys("tissue", Keys.ENTER);

            WebElement qtyField = driver.findElement(getRequiredQuantity);
            qtyField.sendKeys("5");

            requestBtn.click();

            WebElement successMsg = driver.findElement(
                getPopUpMessageText("success", "Requisition is Generated and Saved")
            );
            String message = successMsg.getText();

            driver.findElement(popupCloseButton).click();
            driver.findElement(getCloseModalLocator).click();

            return message;
        } catch (Exception e) {
            throw new Exception("Requisition creation failed: " + e.getMessage(), e);
        }
    }
}