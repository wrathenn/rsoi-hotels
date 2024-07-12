import com.wrathenn.loyalty.service.services.getLoyaltyStatus
import com.wrathenn.util.models.loyalty.LoyaltyStatus
import org.junit.jupiter.api.Test

class LoyaltyDiscountTest {
    @Test
    fun testBronzeLoyaltyStatus() {
        assert(LoyaltyStatus.BRONZE.discountPercentage == 5) {
            "Loyalty discount for status BRONZE should be 5, current is ${LoyaltyStatus.BRONZE.discountPercentage}"
        }

        (0..9).forEach {  reservationCount ->
            val loyaltyStatus = getLoyaltyStatus(reservationCount)
            assert(loyaltyStatus == LoyaltyStatus.BRONZE) {
                "Loyalty status should be BRONZE for reservationCount = $loyaltyStatus"
            }
        }
    }

    @Test
    fun testSilverLoyaltyStatus() {
        assert(LoyaltyStatus.SILVER.discountPercentage == 7) {
            "Loyalty discount for status SILVER should be 7, current is ${LoyaltyStatus.SILVER.discountPercentage}"
        }

        (10..19).forEach {  reservationCount ->
            val loyaltyStatus = getLoyaltyStatus(reservationCount)
            assert(loyaltyStatus == LoyaltyStatus.SILVER) {
                "Loyalty status should be SILVER for reservationCount = $loyaltyStatus"
            }
        }
    }

    @Test
    fun testGoldLoyaltyStatus() {
        assert(LoyaltyStatus.GOLD.discountPercentage == 10) {
            "Loyalty discount for status GOLD should be 10, current is ${LoyaltyStatus.GOLD.discountPercentage}"
        }

        (20..1000).forEach {  reservationCount ->
            val loyaltyStatus = getLoyaltyStatus(reservationCount)
            assert(loyaltyStatus == LoyaltyStatus.GOLD) {
                "Loyalty status should be BRONZE for reservationCount = $loyaltyStatus"
            }
        }
    }
}