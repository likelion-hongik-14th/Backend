package mutsa.mutsa_week5_hw.global.exception;

import lombok.Getter;
import mutsa.mutsa_week5_hw.global.code.GeneralCode;

@Getter
public class GeneralException extends RuntimeException {

    private final GeneralCode code;

    public GeneralException(GeneralCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
