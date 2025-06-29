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
        setNameAndAmount(extractedText, transaction);
        return transaction;
    }

    private void setNameAndAmount(String extractedText, Transaction transaction) {
        String[] lines = extractedText.split("\n");
        String name;
        BigDecimal amount = BigDecimal.ZERO;

        if (lines.length > 0) {
            String line1 = lines[0].replaceAll("[\\n\\t\\r]", "").trim();
            String line2 = lines.length > 1 ? lines[1].replaceAll("[\\n\\t\\r]", "").trim() : "";

            String amountPattern = ".*[0-9][.,][0-9]{2}.*";

            String possibleAmountLine = "";
            String possibleNameLine = "";

            if (line1.replaceAll("[^0-9.,]", "").matches(amountPattern)) {
                possibleAmountLine = line1;
                possibleNameLine = line2;
            } else if (line2.replaceAll("[^0-9.,]", "").matches(amountPattern)) {
                possibleAmountLine = line2;
                possibleNameLine = line1;
            } else {
                possibleNameLine = line1;
            }

            // Set name
            name = possibleNameLine.replaceAll("[^\\p{L}0-9 ]", "");
            if (name.length() > 100) {
                name = name.substring(0, 100);
            }
            transaction.setName(name);

            // Set amount
            if (!possibleAmountLine.isEmpty()) {
                String amountStr = possibleAmountLine.replaceAll("[^0-9.,]", "");
                if (amountStr.matches(".*\\d{1,3}(\\.\\d{3})+[,.]\\d{2}.*")) {
                    amountStr = amountStr.replaceAll("\\.", "");
                }
                amountStr = amountStr.replace(',', '.');
                try {
                    amount = new BigDecimal(amountStr);
                } catch (NumberFormatException ignored) {
                }
            }
            transaction.setAmount(amount);
        }
    }

}
