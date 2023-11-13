package christmas.domain;

import christmas.utils.PromotionRules;
import christmas.utils.Promotions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BenefitsTest {

    @DisplayName("음료 메뉴만 주문할 경우 혜택이 적용되지 않는다.")
    @Test
    void calculateBenefitsWithOnlyBeverage() {
        List<String> orderedMenus = Arrays.asList(Menu.RED_WINE.getName(), Menu.COKE_ZERO.getName());
        List<Integer> orderCounts = Arrays.asList(1, 2);
        OrderedMenus orderedMenuList = OrderedMenus.from(orderedMenus, orderCounts);
        Customer customer = Customer.reserve(EventDate.from(5), orderedMenuList);
        Benefits benefits = Benefits.from(customer);

        assertThat(benefits.isEmpty()).isTrue();
    }

    @DisplayName("혜택이 있을시 이벤트 혜택을 반환한다.")
    @Test
    void calculateBenefits_WithSpecialEvent() {
        List<String> orderedMenus = Arrays.asList(Menu.CAESAR_SALAD.getName(), Menu.RED_WINE.getName(), Menu.ICECREAM.getName());
        List<Integer> orderCounts = Arrays.asList(3, 2, 1);
        OrderedMenus orderedMenuList = OrderedMenus.from(orderedMenus, orderCounts);
        Customer customer = Customer.reserve(EventDate.from(26), orderedMenuList);
        Benefits benefits = Benefits.from(customer);

        assertThat(benefits.isEmpty()).isFalse();
        assertThat(benefits.getTotalDiscount()).isEqualTo(Menu.CHAMPAIGN.getPrice() + PromotionRules.EVENT_PERIOD_DISCOUNT.getValue());
        assertThat(benefits.getBenefits()).containsKey(Promotions.WEEKDAY.getPromotionName());
    }
}