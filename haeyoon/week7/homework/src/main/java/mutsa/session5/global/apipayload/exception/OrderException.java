package mutsa.session5.global.apipayload.exception;

import mutsa.session5.global.apipayload.code.BaseErrorCode;

public class OrderException extends ProjectException {
    public OrderException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
