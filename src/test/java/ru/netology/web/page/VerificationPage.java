package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement verifyCode = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public VerificationPage() {
        verifyCode.shouldBe(visible);
    }

    public DashboardPage validVerify (DataHelper.VerificationCode verificationCode) {
        verifyCode.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}
