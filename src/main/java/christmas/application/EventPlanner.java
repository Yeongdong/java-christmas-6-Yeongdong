package christmas.application;

import christmas.domain.Benefits;
import christmas.domain.Customer;
import christmas.view.InputView;
import christmas.view.OutputView;

public class EventPlanner {
    public static void show() {
        Customer customer = Customer.reserve(InputView.inputVisitDate(), InputView.inputMenuAndOrderCount());
        OutputView.printPreviewBenefit(customer);
        OutputView.printOrderedMenus(customer);
        OutputView.printTotalPriceBeforeDiscount(customer);
        Benefits benefits = Benefits.from(customer);
        OutputView.printGiveawayMenu(benefits);
        OutputView.printBenefits(benefits);
        OutputView.printTotalBenefit(benefits);
        OutputView.printEstimatePrice(benefits, customer);
        OutputView.printEventBadge(customer, benefits);
    }
}
