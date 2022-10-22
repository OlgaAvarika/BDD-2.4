package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
        Configuration.holdBrowserOpen = true;
    }

    @Test
    public void shouldTransferFromSecondToFirst() {
        var dashboardPage = new DashboardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1")+2000;
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2")-2000;
        dashboardPage.getMoneyTransferFromSecondToFirstCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("2"), "2000");
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldTransferMoneyFromFirstToSecond() {
        var dashboardPage = new DashboardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1")-4000;
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2")+4000;
        dashboardPage.getMoneyTransferFromFirstToSecondCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("1"), "4000");
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldReloadBalance() {
        var dashboardPage = new DashboardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1");
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2");
        dashboardPage.reloadBalance();
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldCancelMoneyTransfer() {
        var dashboardPage = new DashboardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1");
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferFromSecondToFirstCard();
        dashboardPage.cancelMoneyTransfer();
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldNotTransferMoneyIfCardNotSelected() {
        var dashboardPage = new DashboardPage();
        dashboardPage.getMoneyTransferFromSecondToFirstCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo(""), "2000");
        moneyTransferPage.getError();
    }

    @Test
    public void shouldNotTransferMoneyAboveExistingValue() {
        var dashboardPage = new DashboardPage();
        String aboveBalance = String.valueOf(dashboardPage.getCardBalance("1") + 1000);
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1");
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferFromFirstToSecondCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("1"), aboveBalance);
        moneyTransferPage.getError();
    }
}
