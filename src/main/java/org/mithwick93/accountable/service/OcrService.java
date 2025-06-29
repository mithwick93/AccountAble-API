package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.gateway.OcrGateway;
import org.mithwick93.accountable.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OcrService {

    private final OcrGateway ocrGateway;

    public Transaction extractTransactionFromImage(MultipartFile imageFile) {
        String extractedText = ocrGateway.extractText(imageFile);

        Transaction transaction = new Transaction();
        setName(extractedText, transaction);
        setAmount(extractedText, transaction);

        return transaction;
    }

    private void setName(String extractedText, Transaction transaction) {
        String[] lines = extractedText.split("\n");
        if (lines.length > 1) {
            String name = lines[0].trim();
            transaction.setName(name);
        }
    }

    private void setAmount(String extractedText, Transaction transaction) {
        String[] lines = extractedText.split("\n");
        if (lines.length > 1) {
            String amountLine = lines[1].trim();

            String amountStr = amountLine.replaceAll("[^0-9.,]", "");

            if (amountStr.matches(".*\\d{1,3}(\\.\\d{3})+[,\\.]\\d{2}.*")) {
                amountStr = amountStr.replaceAll("\\.", "");
            }
            amountStr = amountStr.replace(',', '.');
            try {
                BigDecimal amount = new BigDecimal(amountStr);
                transaction.setAmount(amount);
            } catch (NumberFormatException e) {
                transaction.setAmount(BigDecimal.ZERO);
            }
        } else {
            transaction.setAmount(BigDecimal.ZERO);
        }
    }

}
