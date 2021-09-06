package br.com.bluesoft.desafio.exception;

public class FornecedorException extends RuntimeException {

    private static final long serialVersionUID = 4991045715494917777L;

    public FornecedorException(String msg) {
        super(msg);
    }

    public FornecedorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
