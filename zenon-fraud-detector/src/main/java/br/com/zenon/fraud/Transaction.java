package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(int step, TipoOperacao type, BigDecimal amount, TransactionCustomer origin,
                          TransactionCustomer recipient, boolean isFraud, boolean isFlaggedFraud) {

}