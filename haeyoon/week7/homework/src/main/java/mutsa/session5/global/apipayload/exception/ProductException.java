package mutsa.session5.global.apipayload.exception;

import mutsa.session5.global.apipayload.code.BaseErrorCode;

public class ProductException extends ProjectException {
    public ProductException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
