import java.io.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.*;

public class PDFBoxReceiptReader {

    public static void main(String[] args){
        PDDocument pd;
        BufferedWriter wr;
        try {
            File input = new File("C:\\Users\\jshar\\Ideaproject2\\recieptReaderMaven\\src\\main\\java\\ikeareceipt.jpg");  // The PDF file from where you would like to extract
            File output = new File("C:\\Users\\jshar\\Ideaproject2\\recieptReaderMaven\\src\\main\\java\\SampleText.txt"); // The text file where you are going to store the extracted data
            pd = PDDocument.load(input);
            System.out.println(pd.getNumberOfPages());
            System.out.println(pd.isEncrypted());
            pd.save("CopyOfBill.pdf"); // Creates a copy called "CopyOfInvoice.pdf"
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(1); //Start extracting from page 3
            stripper.setEndPage(1); //Extract till page 5
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
            stripper.writeText(pd, wr);
            if (pd != null) {
                pd.close();
            }
            // I use close() to flush the stream.
            wr.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}