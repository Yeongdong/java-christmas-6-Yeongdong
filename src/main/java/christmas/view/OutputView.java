package christmas.view;

import christmas.domain.*;

import java.util.ArrayList;
import java.util.List;

public class OutputView {
    private static final String PREVIEW_BENEFIT_MESSAGE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n";
    private static final String ORDERED_MENU_MESSAGE = "\n<주문 메뉴>";
    private static final String MENU_AND_ORDER_COUNT_FORMAT = "%s %d개";
    private static final String TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE = "\n<할인 전 총주문 금액>";
    private static final String PRICE_FORMAT = "%,d원\n";
    private static final String DISCOUNT_FORMAT = "-%,d원\n";
    private static final String GIVEAWAY_MENU_MESSAGE = "\n<증정 메뉴>";
    private static final String BENEFITS_MESSAGE = "\n<혜택 내역>";
    private static final String TOTAL_BENEFITS_MESSAGE = "<총혜택 금액>";
    private static final String ESTIMATED_PRICE_AFTER_DISCOUNT_MESSAGE = "\n<할인 후 예상 결제 금액>";
    private static final String DECEMBER_EVENT_BADGE_MESSAGE = "\n<12월 이벤트 배지>";
    private static final String NOT_APPLICABLE = "없음";
    private static final String BENEFIT_FORMAT = "%s 할인: ";
    private static final String GIVEAWAY_EVENT = "증정 이벤트: -%,d원\n";

    public static void printPreviewBenefit(Customer customer) {
        System.out.printf(PREVIEW_BENEFIT_MESSAGE, customer.getVisitDate());
    }

    public static void printOrderedMenus(Customer customer) {
        System.out.println(ORDERED_MENU_MESSAGE);
        customer.getOrderedMenusList().forEach(orderedMenu -> {
            System.out.printf(MENU_AND_ORDER_COUNT_FORMAT, orderedMenu.getMenuName(), orderedMenu.getOrderCount());
            System.out.println();
        });
    }

    public static void printTotalPriceBeforeDiscount(Customer customer) {
        System.out.println(TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE);
        System.out.printf(PRICE_FORMAT, customer.getTotalPriceBeforeDiscount());
    }

    public static void printGiveawayMenu(Benefits benefits) {
        System.out.println(GIVEAWAY_MENU_MESSAGE);
        System.out.println(checkGiveaway(benefits));
    }

    public static void printBenefits(Benefits benefits) {
        System.out.println(BENEFITS_MESSAGE);
        System.out.println(checkBenefits(benefits));
    }

    public static void printTotalBenefit(Benefits benefits) {
        System.out.println(TOTAL_BENEFITS_MESSAGE);
        if (benefits.isEmpty()) {
            System.out.printf(PRICE_FORMAT, 0);
        }
        if (!benefits.isEmpty()) {
            System.out.printf(DISCOUNT_FORMAT, benefits.getTotalDiscount());
        }
    }

    public static void printEstimatePrice(Benefits benefits, Customer customer) {
        System.out.println(ESTIMATED_PRICE_AFTER_DISCOUNT_MESSAGE);
        System.out.printf(PRICE_FORMAT, benefits.calculateEstimatePrice(customer));
    }

    public static void printEventBadge(Customer customer, Benefits benefits) {
        System.out.println(DECEMBER_EVENT_BADGE_MESSAGE);
        System.out.println(customer.getBadgeStatus(benefits));
    }

    private static String checkGiveaway(Benefits benefits) {
        if (benefits.containsKey(Menu.CHAMPAIGN.getName())) {
            int giveawayCount = (int) benefits.getBenefits().keySet().stream()
                    .filter(key -> key.equals(Menu.CHAMPAIGN.getName()))
                    .count();
            return String.format(MENU_AND_ORDER_COUNT_FORMAT, Menu.CHAMPAIGN.getName(), giveawayCount);
        }
        return NOT_APPLICABLE;
    }

    private static String checkBenefits(Benefits benefits) {
        List<String> result = new ArrayList<>();
        if (benefits.isEmpty()) {
            return NOT_APPLICABLE;
        }
        benefits.getBenefits().forEach((key, discount) -> {
            if (discount > 0)
                result.add(String.format(BENEFIT_FORMAT, key) + String.format(DISCOUNT_FORMAT, discount));
        });

        if (benefits.containsKey(Menu.CHAMPAIGN.getName())) {
            result.add(String.format(GIVEAWAY_EVENT, Menu.CHAMPAIGN.getPrice()));
            result.remove(String.format(BENEFIT_FORMAT, Menu.CHAMPAIGN.getName()) + String.format(DISCOUNT_FORMAT, Menu.CHAMPAIGN.getPrice()));
        }
        return String.join("", result);
    }
}