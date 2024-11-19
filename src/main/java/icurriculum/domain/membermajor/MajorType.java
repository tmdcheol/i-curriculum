package icurriculum.domain.membermajor;

import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;

public enum MajorType {

    주전공;

    public static MajorType to(String value) {
        try {
            return MajorType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus.MAJOR_TYPE_INVALID_DATA, value);
        }
    }

}
