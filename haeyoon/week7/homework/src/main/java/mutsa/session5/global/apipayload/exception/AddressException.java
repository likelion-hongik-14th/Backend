package mutsa.session5.global.apipayload.exception;

import mutsa.session5.global.apipayload.code.BaseErrorCode;

public class AddressException extends ProjectException {
    public AddressException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
