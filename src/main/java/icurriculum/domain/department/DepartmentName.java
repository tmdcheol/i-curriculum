package icurriculum.domain.department;

import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;

public enum DepartmentName {

    컴퓨터공학과,
    전기공학과,
    기계공학과;

    public static DepartmentName to(String value) {
        try {
            return DepartmentName.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus.DEPARTMENT_NAME_INVALID_DATA, value);
        }
    }

}
