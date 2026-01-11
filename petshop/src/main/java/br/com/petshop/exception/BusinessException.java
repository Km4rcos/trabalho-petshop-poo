package br.com.petshop.exception;

public class BusinessException extends RuntimeException { // Melhor usar RuntimeException
    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
