package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.EventDate;
import christmas.domain.Menu;
import christmas.domain.OrderedMenus;
import christmas.utils.PromotionRules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputView {
    private static final String GREETING_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String ESTIMATE_DATE_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_AND_ORDER_COUNT = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String ORDER_SPLITTER = ",";
    private static final String MENU_AMOUNT_SPLITTER = "-";
    private static final String INVALID_DATE_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final String INVALID_ORDER_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";

    public static EventDate inputVisitDate() {
        try {
            System.out.println(GREETING_MESSAGE);
            System.out.println(ESTIMATE_DATE_MESSAGE);
            return EventDate.from(toInt(Console.readLine()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputVisitDate();
        }
    }

    private static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    public static OrderedMenus inputMenuAndOrderCount() {
        try {
            System.out.println(MENU_AND_ORDER_COUNT);
            String menuAndOrderCount = Console.readLine();
            List<String> orderInputs = Arrays.stream(menuAndOrderCount.split(ORDER_SPLITTER))
                    .toList();
            return makeOrder(orderInputs);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputMenuAndOrderCount();
        }
    }

    private static OrderedMenus makeOrder(List<String> orderInputs) {
        try {
            List<String> menuNames = new ArrayList<>();
            List<Integer> orderCount = new ArrayList<>();
            for (String order : orderInputs) {
                String[] menus = order.split(MENU_AMOUNT_SPLITTER);
                validateInputForm(menus);
                menuNames.add(menus[PromotionRules.MENU_ORDER.getValue()]);
                orderCount.add(Integer.parseInt(menus[PromotionRules.COUNT_ORDER.getValue()]));
            }
            return OrderedMenus.from(toMenuList(menuNames), orderCount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static List<Menu> toMenuList(List<String> menuNames) {
        return menuNames.stream()
                .map(Menu::findByName)
                .toList();
    }

    private static void validateInputForm(String[] menus) {
        if (menus.length != PromotionRules.VALID_MENU_LENGTH.getValue()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }
}
