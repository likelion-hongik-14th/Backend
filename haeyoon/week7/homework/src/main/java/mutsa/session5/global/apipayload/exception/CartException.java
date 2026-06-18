package mutsa.session5.global.apipayload.exception;

import mutsa.session5.global.apipayload.code.BaseErrorCode;

public class CartException extends ProjectException {
    public CartException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
