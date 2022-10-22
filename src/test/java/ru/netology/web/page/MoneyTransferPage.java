package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement transferAmount = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public DashboardPage moneyTransfer(DataHelper.CardInfo from, String amountToTransfer) {
        transferAmount.setValue(amountToTransfer);
        fromField.setValue(from.getNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void getError() {
        errorMessage.shouldBe(Condition.visible);
    }
}
