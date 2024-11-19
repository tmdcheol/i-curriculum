package icurriculum.domain.take;

import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;

public enum Category {

    /*
     * 전공
     */
    전공필수,
    전공선택,

    /*
     * 교양
     */
    교양필수,
    교양선택,

    SW_AI,
    창의,

    /*
     * 핵심 교양
     */
    핵심교양1,
    핵심교양2,
    핵심교양3,
    핵심교양4,
    핵심교양5,
    핵심교양6;

    public static Category to(String value) {
        try {
            return Category.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus.CATEGORY_INVALID_DATA, value);
        }
    }

    public static boolean isValid(String value) {
        for (Category category : Category.values()) {
            if (category.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
