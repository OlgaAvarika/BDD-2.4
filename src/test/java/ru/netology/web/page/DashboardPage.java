package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement firstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement secondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private ElementsCollection depositButton = $$("[data-test-id='action-deposit']");
    private SelenideElement reloadButton = $("[data-test-id='action-reload']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");

    private static final ElementsCollection cards = $$(".list__item div");
    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public int getCardBalance(String id) {
        String cardBalance = cards.findBy(text(DataHelper.getCardInfo(id).getNumber().substring(16))).getText();
        return extractBalance(cardBalance);
    }

    private int extractBalance(String cardBalance) {
        val start = cardBalance.indexOf(balanceStart);
        val finish = cardBalance.indexOf(balanceFinish);
        val value = cardBalance.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void getMoneyTransferFromSecondToFirstCard() {
        depositButton.first().click();
    }

    public void getMoneyTransferFromFirstToSecondCard() {
        depositButton.last().click();
    }

    public void reloadBalance() {
        reloadButton.click();
    }

    public void cancelMoneyTransfer() {
        cancelButton.click();
    }
}
