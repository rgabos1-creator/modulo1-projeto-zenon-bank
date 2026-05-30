package br.com.zenon.fraud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionIngestor {
    public List<Transaction> read(String filename) throws FileNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filename);
            Scanner scanner = new Scanner(fis)) {

            int lineCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineCount++;

                if (lineCount == 1) {
                    continue;
                } else if (lineCount > 1001) {
                    break;
                }

                String[] chuncks = line.split(",");
                int step = Integer.parseInt(chuncks[0]);
                TipoOperacao type = TipoOperacao.valueOf(chuncks[1]);
                BigDecimal amount = new BigDecimal(chuncks[2]);
                TransactionCustomer origin = new TransactionCustomer(chuncks[3], new BigDecimal(chuncks[4]), new BigDecimal(chuncks[5]));
                TransactionCustomer recipient = new TransactionCustomer(chuncks[6], new BigDecimal(chuncks[7]), new BigDecimal(chuncks[8]));
                boolean isFraud = Boolean.parseBoolean(chuncks[9]);
                boolean isFlaggedFraud = Boolean.parseBoolean(chuncks[10]);

                Transaction transacao = new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
                transactions.add(transacao);
            }

            } catch (Exception e) {
            throw new RuntimeException("Não conseguiu ler o arquivo: " + filename + " Erro: ", e);
        };
        return transactions;
    }
}
