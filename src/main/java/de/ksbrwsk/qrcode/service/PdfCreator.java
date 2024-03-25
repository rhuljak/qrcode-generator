package de.ksbrwsk.qrcode.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.*;
import de.ksbrwsk.qrcode.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PdfCreator {

    @Value("${app.my.pdf.repo}")
    private String repoPath;

    @Value("${app.my.pdf.font}")
    private String fontPath;

    public String getPdfStickSheet(List<AddressStamp> request, Integer startIndex) {

        String fileName = "stitky-adobe-crack.pdf";
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + ".pdf";

        try {
            //Create Document instance.
            /*Document document = new Document();


            //Create OutputStream instance.
            //OutputStream outputStream = new FileOutputStream(new File("/app/generate/"+fileName));
            OutputStream outputStream = new FileOutputStream(new File("/Users/roberthuljak/Downloads/"+fileName));

            //Create PDFWriter instance.
            PdfWriter.getInstance(document, outputStream);

            //Open the document.
            document.open();

            //Create Table object, Here 4 specify the no. of columns
            PdfPTable pdfPTable = new PdfPTable(4);

            //Create cells
            PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Cell 1"));
            PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("Cell 2"));
            PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Cell 3"));
            PdfPCell pdfPCell4 = new PdfPCell(new Paragraph("Cell 4"));

            //Add cells to table
            pdfPTable.addCell(pdfPCell1);
            pdfPTable.addCell(pdfPCell2);
            pdfPTable.addCell(pdfPCell3);
            pdfPTable.addCell(pdfPCell4);

            //Add content to the document using Table objects.
            document.add(pdfPTable);
            document.add(new Pag)

            //Close document and outputStream.
            document.close();
            outputStream.close();

            System.out.println("Pdf created successfully.");

            PdfReader reader = new PdfReader("/Users/roberthuljak/Downloads/area_test.pdf");
            AcroFields fields = reader.getAcroFields();
            String name = fields.getField("area_0");
            String age = fields.getField("area_1");
            System.out.println(name);
            System.out.println(age);
            //fields.setField("area_0", "ty vole");

            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/Users/roberthuljak/Downloads/area_test_fill.pdf"));
            AcroFields form = stamper.getAcroFields();
            form.setField("area_0", "1");
            form.setField("area_1", Calendar.getInstance().toString());
            //form.  setFieldProperty("area_0", )


            stamper.setFormFlattening(true);

            stamper.close();
            reader.close();*/

            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));
                /*int pagesa = (35 / 16);
                int zvysok = (35 % 16);
                System.out.println("pagesa "+pagesa);
                System.out.println("zvysok "+zvysok);*/

                if (null != startIndex && startIndex > 1 && startIndex <= 16) {
                    //pridaj uvodne preskakovane pozicie
                    for (int i = 0; i < (startIndex - 1); i++) {
                        request.add(i, new AddressStamp());
                    }
                }

                int pages = request.size() / 16;
                int zvysokk = request.size() % 16;
                if (zvysokk > 0) {
                    pages++;
                }
                System.out.println("pages " + pages);

                for (int i = 0; i < pages; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    renameFields(pdf, "_text");

                }

                renameFields(pdf, "__text");

                /*for (int i = 0; i < pages; i++) {



                    System.out.println("stranka " + i);
                    for (int j = 1; j < 17; j++) {
                        String aa = "text" + j;
                        String bb = "text" + (j + (i * 16));
                        System.out.println(aa);
                        System.out.println(bb);
                        form.getField(aa).setFieldName(bb).setValue(bb);
                    }
                }*/
                //if (request.size() < 17) {
                //Document doc = new Document(pdf);
                // PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
                //form.getField("text1");


                final String REGULAR = fontPath + "arial.ttf";
                /*FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
                PdfFont font = PdfFontFactory.createFont();*/
                PdfFont font = PdfFontFactory.createFont(REGULAR);


                int j = 1;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
                for (AddressStamp value : request) {
                    String aa = "__text" + j;
                    //System.out.println("--> "+aa);
                    form.getField(aa)
                            .setValue(getStampValue(value))
                            .setReadOnly(Boolean.TRUE)
                            .setJustification(TextAlignment.LEFT)
                            .setFontAndSize(font, 12f)
                    ;
                    getStampValue(value);
                    j++;
                }


                    /*form.getField("text1")
                            .setValue(getStampValue(request.get(0)))
                            .setReadOnly(Boolean.TRUE)
                            .setJustification(HorizontalAlignment.LEFT.ordinal())
                    ;
                    form.getField("text2").setValue("aa").setReadOnly(Boolean.TRUE);
*/


                //}
                pdf.close();
                origPdf.close();
            }


            //Original page size
            ////PdfPage origPage = origPdf.getPage(1);
            //Add page with original size
            //pdf.addPage(origPage.copyTo(pdf));
            //pdf.addPage(origPage.copyTo(pdf));

            //origPdf.removePage(1);

            ////
            /*origPdf.copyPagesTo(
                    1, origPdf.getNumberOfPages(),
                    pdf, new PdfPageFormCopier());
            origPdf.copyPagesTo(
                    1, origPdf.getNumberOfPages(),
                    pdf, new PdfPageFormCopier());

            pdf.close();
            origPdf.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    public String getPdfStickFolderSheet(List<FolderStamp> request, Integer startIndex) {

        String fileName = "stitky-adobe-crack.pdf";
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + ".pdf";

        try {

            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));
                /*int pagesa = (35 / 16);
                int zvysok = (35 % 16);
                System.out.println("pagesa "+pagesa);
                System.out.println("zvysok "+zvysok);*/

                if (null != startIndex && startIndex > 1 && startIndex <= 16) {
                    //pridaj uvodne preskakovane pozicie
                    for (int i = 0; i < (startIndex - 1); i++) {
                        request.add(i, new FolderStamp());
                    }
                }

                int pages = request.size() / 16;
                int zvysokk = request.size() % 16;
                if (zvysokk > 0) {
                    pages++;
                }
                System.out.println("pages " + pages);

                for (int i = 0; i < pages; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    renameFields(pdf, "_text");

                }

                renameFields(pdf, "__text");
                final String REGULAR = fontPath + "arial.ttf";
                /*FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
                PdfFont font = PdfFontFactory.createFont();*/
                PdfFont font = PdfFontFactory.createFont(REGULAR);


                int j = 1;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
                for (FolderStamp value : request) {
                    String aa = "__text" + j;
                    //System.out.println("--> "+aa);
                    form.getField(aa)
                            .setValue(getStampValue(value))
                            .setReadOnly(Boolean.TRUE)
                            .setJustification(TextAlignment.LEFT)
                            .setFontAndSize(font, 12f)
                    ;
                    getStampValue(value);
                    j++;
                }
                pdf.close();
                origPdf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    public String getPdfStickAssetsSheet(List<AssetsStamp> request, Integer startIndex) {

        String fileName = "stitky-adobe-crack.pdf";
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + ".pdf";

        try {

            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));
                /*int pagesa = (35 / 16);
                int zvysok = (35 % 16);
                System.out.println("pagesa "+pagesa);
                System.out.println("zvysok "+zvysok);*/

                if (null != startIndex && startIndex > 1 && startIndex <= 16) {
                    //pridaj uvodne preskakovane pozicie
                    for (int i = 0; i < (startIndex - 1); i++) {
                        request.add(i, new AssetsStamp());
                    }
                }

                int pages = request.size() / 16;
                int zvysokk = request.size() % 16;
                if (zvysokk > 0) {
                    pages++;
                }
                System.out.println("pages " + pages);

                for (int i = 0; i < pages; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    renameFields(pdf, "_text");

                }

                renameFields(pdf, "__text");




                final String REGULAR = fontPath + "arial.ttf";
                /*FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
                PdfFont font = PdfFontFactory.createFont();*/
                PdfFont font = PdfFontFactory.createFont(REGULAR);


                int j = 1;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
                for (AssetsStamp value : request) {
                    String aa = "__text" + j;
                    //System.out.println("--> "+aa);
                    form.getField(aa)
                            .setValue(getStampValue(value))
                            .setReadOnly(Boolean.TRUE)
                            .setJustification(TextAlignment.LEFT)
                            .setFontAndSize(font, 14f)
                    ;
                    getStampValue(value);
                    j++;
                }



                pdf.close();
                origPdf.close();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    public String getEPA(List<AddressStamp> request) {

        final String resultFileName = "__" + Calendar.getInstance().getTimeInMillis() + "_epa.csv";

        try {
            if (request != null) {
                ObjectMapper mapper = new ObjectMapper();

                StringBuilder content = new StringBuilder();
                content.append(";E L E K T R O N I C K Ý   P O Š T O V N Í   P O D A C Í   A R C H;;;;;;;;;;;;;Legenda:;;;;;;;;;;;;;\n");
                content.append(";Příjmení a jméno odesílatele (název firmy);PSČ;Obec;Část obce;Ulice;Č. p.;Č. o.;Země;Telefonní číslo;E-mail;Č. Zákaznické karty;;;xxx;sloupce s povinnými údaji;;;;;;;;;;;;\n");
                content.append("Odesílatel:;Gymnázium Paměti národa, s. r. o.;11000;Praha;Staré Město;Opatovická;160;18;CZ;;;;;;;;;;;;;;;;;;;\n");
                content.append(";;;;;;;;;* Získání zvýhodněné ceny pro balíkové služby za podání dat elektronicky je podmíněno předáním jednoho z kontaktních údajů adresáta.;;;;;;;;;Vyplňuje se u zásilek do zahraničí;;Vícekusové zásilky ;;;;;;;\n");
                content.append("Id. číslo zásilky;Příjmení a jméno adresáta (název firmy);PSČ;Obec;Část obce;Ulice;Č. p.;Č. o.;Země;Telefonní číslo* ;E-mail*;Hmotnost v kg;Požadované služby;Udaná cena v Kč;Částka dobírky v Kč;VS dobírkové poukázky;VS zásilky;Poznámka;Kód MRN;Celní obsah zásilky;Id. číslo hlavní zásilky;Pořadové číslo zásilky;Celkový počet zásilek;Částka výplatného v Kč;Datum podání;Čas podání;;\n");

                //;Bednár Petra;10000;Praha;Vršovice;Litevská;1206;11;CZ;;;;50;;;;;;;;;;;;;;1;Všechny povinné údaje jsou vyplněné.
                for (AddressStamp value : request) {
                    content.append(";"+value.getLastName()+" "+value.getFirstName()+";"+value.getZip()+";"+value.getCity()+";");
                    if (null != value.getCity() && null != value.getCityPart() && !value.getCityPart().equals(value.getCity())) {
                        content.append(value.getCityPart());
                    }
                    content.append(";"+value.getStreet()+";"+value.getNumber1()+";"+value.getNumber2()+";CZ;;;;50;;;;;;;;;;;;;;1;Všechny povinné údaje jsou vyplněné.\n");
                }

                final Path resultFilePath = Paths.get(repoPath + resultFileName);
                Files.writeString(resultFilePath, content.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFileName;
    }

    public String getPdfResultsAgenda(List<StudentResult> request) {

        String fileName = "user-export-agenda.pdf";
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + ".pdf";

        try {


            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));

                int pages = request.size();
                System.out.println("pages " + pages);

                for (int i = 0; i < pages; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    getAllFieldNames(pdf);
                    renameFields2(pdf, "_f");
                }
                renameFields3(pdf, "_");
                getAllFieldNames(pdf);

                final String REGULAR = fontPath + "arial.ttf";
                PdfFont font = PdfFontFactory.createFont(REGULAR);


                int j = 0;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);

                for (StudentResult value : request) {
                    fillResultAgenda(pdf, font, value, j);
                    j++;
                }

                pdf.close();
                origPdf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    public String getPdfResultsConfirmation(List<StudentConfirmationResult> request) {

        // prazdny pdf subor, do ktorehu budem mergovat
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + "_confirmation.pdf";
        final String pdfFullName = repoPath + pdfName;
        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<StudentConfirmationResult> request1 = request.stream().filter(a -> a.getAdmitted() == 1)
                .collect(Collectors.toList());
        List<StudentConfirmationResult> request2 = request.stream().filter(a -> a.getAdmitted() == 2)
                .collect(Collectors.toList());
        List<StudentConfirmationResult> request3 = request.stream().filter(a -> a.getAdmitted() == 3 && !a.getP4())
                .collect(Collectors.toList());
        List<StudentConfirmationResult> request4 = request.stream().filter(a -> a.getAdmitted() == 3 && a.getP4())
                .collect(Collectors.toList());

        System.out.println("docNumber type 1: " + request1.size());
        System.out.println("docNumber type 2: " + request2.size());
        System.out.println("docNumber type 3: " + request3.size());
        System.out.println("docNumber type 4: " + request4.size());

        String filename1 = getPdfResultsConfirmation1(request1);
        String filename2 = getPdfResultsConfirmation2(request2);
        String filename3 = getPdfResultsConfirmation3(request3);
        String filename4 = getPdfResultsConfirmation4(request4);

        // merging
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFullName));
            PdfMerger merger = new PdfMerger(pdfDocument);
            if (filename1 != null) {
                PdfDocument pdfDocument1 = new PdfDocument(new PdfReader(repoPath+filename1));
                merger.merge(pdfDocument1, 1, pdfDocument1.getNumberOfPages());
                pdfDocument1.close();
            }
            if (filename2 != null) {
                PdfDocument pdfDocument2 = new PdfDocument(new PdfReader(repoPath+filename2));
                merger.merge(pdfDocument2, 1, pdfDocument2.getNumberOfPages());
                pdfDocument2.close();
            }
            if (filename3 != null) {
                PdfDocument pdfDocument3 = new PdfDocument(new PdfReader(repoPath+filename3));
                merger.merge(pdfDocument3, 1, pdfDocument3.getNumberOfPages());
                pdfDocument3.close();
            }
            if (filename4 != null) {
                PdfDocument pdfDocument4 = new PdfDocument(new PdfReader(repoPath+filename4));
                merger.merge(pdfDocument4, 1, pdfDocument4.getNumberOfPages());
                pdfDocument4.close();
            }
            pdfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }



    public String getPdfResultsConfirmation1(List<StudentConfirmationResult> request) {

        if (request == null || request.size()==0) {
            return null;
        }

        String fileName = resolveConfirmationFileName(1);
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + "_X.pdf";

        try {


            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));

                int docNumber = request.size();
                System.out.println("docNumber " + docNumber);

                for (int i = 0; i < docNumber; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    //getAllFieldNames(pdf);
                    renameFields2(pdf, "_f");
                }
                renameFields33(pdf, "_", 16);
                //getAllFieldNames(pdf);

                final String REGULAR = fontPath + "times.ttf";
                PdfFont font = PdfFontFactory.createFont(REGULAR);

                final String BOLD = fontPath + "timesbd.ttf";
                PdfFont fontBold = PdfFontFactory.createFont(BOLD);


                int j = 0;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);

                for (StudentConfirmationResult value : request) {
                    fillResultConfirmation(pdf, font, fontBold, value, j);
                    j++;
                }
                renameFields4("X", pdf);

                pdf.close();
                origPdf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    public String getPdfResultsConfirmation2(List<StudentConfirmationResult> request) {

        if (request == null || request.size()==0) {
            return null;
        }

        String fileName = resolveConfirmationFileName(2);
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + "_Y.pdf";

        try {


            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));

                int docNumber = request.size();
                System.out.println("docNumber " + docNumber);

                for (int i = 0; i < docNumber; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    //getAllFieldNames(pdf);
                    renameFields2(pdf, "_f");
                }
                renameFields33(pdf, "_", 15);
                //getAllFieldNames(origPdf);

                final String REGULAR = fontPath + "times.ttf";
                PdfFont font = PdfFontFactory.createFont(REGULAR);

                final String BOLD = fontPath + "timesbd.ttf";
                PdfFont fontBold = PdfFontFactory.createFont(BOLD);


                int j = 0;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);

                for (StudentConfirmationResult value : request) {
                    fillResultConfirmation(pdf, font, fontBold, value, j);
                    j++;
                }
                renameFields4("Y", pdf);

                pdf.close();
                origPdf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    public String getPdfResultsConfirmation3(List<StudentConfirmationResult> request) {
        if (request == null || request.size()==0) {
            return null;
        }
        String fileName = resolveConfirmationFileName(3);
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + "_Z.pdf";

        try {


            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));

                int docNumber = request.size();
                System.out.println("docNumber " + docNumber);

                for (int i = 0; i < docNumber; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    //getAllFieldNames(pdf);
                    renameFields2(pdf, "_f");
                }
                renameFields33(pdf, "_", 17);
                getAllFieldNames(pdf);

                final String REGULAR = fontPath + "times.ttf";
                PdfFont font = PdfFontFactory.createFont(REGULAR);

                final String BOLD = fontPath + "timesbd.ttf";
                PdfFont fontBold = PdfFontFactory.createFont(BOLD);


                int j = 0;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);

                for (StudentConfirmationResult value : request) {
                    fillResultConfirmation(pdf, font, fontBold, value, j);
                    j++;
                }
                renameFields4("Z", pdf);

                pdf.close();
                origPdf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    public String getPdfResultsConfirmation4(List<StudentConfirmationResult> request) {
        if (request == null || request.size()==0) {
            return null;
        }
        String fileName = resolveConfirmationFileName(4);
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + "_Q.pdf";

        try {


            if (request != null) {
                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                PdfDocument origPdf = new PdfDocument(new PdfReader(repoPath + fileName));

                int docNumber = request.size();
                System.out.println("docNumber " + docNumber);

                for (int i = 0; i < docNumber; i++) {
                    origPdf.copyPagesTo(
                            1, origPdf.getNumberOfPages(),
                            pdf, new PdfPageFormCopier());
                    //getAllFieldNames(pdf);
                    renameFields2(pdf, "_f");
                }
                renameFields33(pdf, "_", 16);
                //getAllFieldNames(pdf);

                final String REGULAR = fontPath + "times.ttf";
                PdfFont font = PdfFontFactory.createFont(REGULAR);

                final String BOLD = fontPath + "timesbd.ttf";
                PdfFont fontBold = PdfFontFactory.createFont(BOLD);


                int j = 0;
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);

                for (StudentConfirmationResult value : request) {
                    fillResultConfirmation(pdf, font, fontBold, value, j);
                    j++;
                }
                renameFields4("Q", pdf);

                pdf.close();
                origPdf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }


    public String getPdfResultsBoard(List<StudentResult> request) {

        String fileName = "user-export-agenda.pdf";
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + ".pdf";

        try {


            if (request != null) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                //String dateTimeString = formatter.format(now);

                /*TimeZone zone = TimeZone.getTimeZone("Europe/Prague");
                DateFormat dtFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Calendar cal = Calendar.getInstance(zone);
                Date date = cal.getTime();
                String strFormat = dtFormat.format(date);

                System.out.println("LocalDateTime : " + dateTimeString);*/

                ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
                String dateTimeString2 = formatter.format(zonedDateTime);

                System.out.println("LocalDateTime : " + dateTimeString2);
                //UTC+8
                ZonedDateTime prgDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Prague"));
                System.out.println("Depart : " + formatter.format(prgDateTime));

                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                Document document = new Document(pdf, PageSize.A4.rotate(), false);

                final String REGULAR = fontPath + "arial.ttf";
                PdfFont font = PdfFontFactory.createFont(REGULAR);

                // Creating a table object
                float[] pointColumnWidths = {50F, 100F, 200F, 150F};
                //Table table2 = new Table(pointColumnWidths).useAllAvailableWidth();
                Table table2 = new Table(UnitValue.createPercentArray(13)).useAllAvailableWidth();

                Border b0 = new SolidBorder(ColorConstants.BLACK, 1);
                Border b1 = new DoubleBorder(ColorConstants.BLACK, 1);
                Table table22 = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
                //table2.setBorder(b0);

                Style style = new Style();
                style.setBorder(new SolidBorder(1));
                style.setFont(font).setFontColor(ColorConstants.BLACK);
                style.setBackgroundColor(ColorConstants.YELLOW);

                Style style2 = new Style();
                style2.setBorder(new SolidBorder(1));
                style2.setFont(font).setFontColor(ColorConstants.BLACK);

                Style style3 = new Style();
                style3.setBorder(new SolidBorder(1));
                style3.setFont(font).setFontColor(ColorConstants.BLACK).setBold();

                Style styleNO = new Style();
                styleNO.setBorder(new SolidBorder(1));
                styleNO.setFont(font);
                styleNO.setBackgroundColor(ColorConstants.YELLOW);

                Style styleNOC = new Style();
                styleNOC.setBorder(new SolidBorder(1));
                styleNOC.setFont(font);
                styleNOC.setBackgroundColor(ColorConstants.LIGHT_GRAY);

                Style styleYES = new Style();
                styleYES.setBorder(new SolidBorder(1));
                styleYES.setFont(font);
                styleYES.setBackgroundColor(ColorConstants.GREEN);
                table2.addHeaderCell(createCell("Gymnázium Paměti národa, s. r. o. - Výsledky přijímacího řízení 1. kolo", 13, 1, style3));
                table2.addHeaderCell(createCell("Pořadí", 1, 2, style));
                table2.addHeaderCell(createCell("ID kód uchazeče", 1, 2, style));
                table2.addHeaderCell(createCell("Jednotná zkouška (CERMAT)", 4, 1, style));
                table2.addHeaderCell(createCell("Školní přijímací zkouška", 2, 1, style));
                table2.addHeaderCell(createCell("Vysvědčení", 1, 2, style));
                table2.addHeaderCell(createCell("Součet bodú", 2, 1, style));
                table2.addHeaderCell(createCell("Výsledek", 1, 2, style));
                table2.addHeaderCell(createCell("", 1, 2, style));

                table2.addHeaderCell(createCell("MAT", 1, 1, style));
                table2.addHeaderCell(createCell("ČJ/OČJ", 1, 1, style));
                table2.addHeaderCell(createCell("Součet", 1, 1, style));
                table2.addHeaderCell(createCell("Splnil", 1, 1, style));
                table2.addHeaderCell(createCell("Součet", 1, 1, style));
                table2.addHeaderCell(createCell("Splnil", 1, 1, style));
                table2.addHeaderCell(createCell("Celkem", 1, 1, style));
                table2.addHeaderCell(createCell("Vážený", 1, 1, style));


                /*Cell cell1 = new Cell().add(new Paragraph("Pořadí")
                        .setFont(font)
                        .setFontColor(ColorConstants.BLACK));
                cell1.setBackgroundColor(ColorConstants.GREEN);
                cell1.setBorder(Border.NO_BORDER);
                cell1.setBorderBottom(b1);
                cell1.setTextAlignment(TextAlignment.CENTER);
                table2.addCell(cell1);

                Cell cell2 = new Cell().add(new Paragraph("ID uchazeče")
                        .setFont(font)
                        .setFontColor(ColorConstants.BLACK));
                cell2.setBackgroundColor(ColorConstants.WHITE);
                cell2.setBorder(Border.NO_BORDER);
                cell2.setBorderBottom(b1);
                cell2.setTextAlignment(TextAlignment.LEFT);
                table2.addCell(cell2);

                Cell cell3 = new Cell().add(new Paragraph("MAT")
                        .setFont(font)
                        .setFontColor(ColorConstants.BLACK));
                cell3.setBackgroundColor(ColorConstants.WHITE);
                cell3.setBorder(Border.NO_BORDER);
                cell3.setBorderBottom(b1);
                cell3.setTextAlignment(TextAlignment.CENTER);
                table2.addCell(cell3);

                Cell cell4 = new Cell().add(new Paragraph("ČJ/OČJ")
                        .setFont(font)
                        .setFontColor(ColorConstants.BLACK));
                cell4.setBackgroundColor(ColorConstants.WHITE);
                cell4.setBorder(Border.NO_BORDER);
                cell4.setBorderBottom(b1);
                cell4.setTextAlignment(TextAlignment.CENTER);
                table2.addCell(cell4);

                Cell cell5 = new Cell().add(new Paragraph("Součet CERMAT")
                        .setFont(font)
                        .setFontColor(ColorConstants.BLACK));
                cell5.setBackgroundColor(ColorConstants.WHITE);
                cell5.setBorder(Border.NO_BORDER);
                cell5.setBorderBottom(b1);
                cell5.setTextAlignment(TextAlignment.CENTER);
                table2.addCell(cell5);

                Cell cell6 = new Cell().add(new Paragraph("Kritérium Cermat")
                        .setFont(font)
                        .setFontColor(ColorConstants.BLACK));
                cell6.setBackgroundColor(ColorConstants.WHITE);
                cell6.setBorder(Border.NO_BORDER);
                cell6.setBorderBottom(b1);
                cell6.setTextAlignment(TextAlignment.CENTER);
                table2.addCell(cell6);*/

                /*this.studentName = studentName;
                this.registerNumber = registerNumber;
                this.jpzMat = jpzMat;
                this.jpzCj = jpzCj;
                this.cermatCondition = cermatCondition;
                this.motivation = motivation;
                this.interview = interview;
                this.creative = creative;
                this.offSchool = offSchool;
                this.gpnCondition = gpnCondition;
                this.reportCard = reportCard;
                this.sum = sum;
                this.normalizeSum = normalizeSum;
                this.admitted = admitted;*/


                /*int i = 0;
                for (StudentResult value : request) {
                    i++;

                    Cell cell11 = new Cell().add(new Paragraph(String.valueOf(i))
                            .setFont(font)
                            .setFontColor(ColorConstants.BLACK));
                    cell11.setBackgroundColor(ColorConstants.WHITE);
                    cell11.setBorder(Border.NO_BORDER);
                    cell11.setTextAlignment(TextAlignment.CENTER);
                    table2.addCell(cell11);

                    Cell cell12 = new Cell().add(new Paragraph(value.getRegisterNumber())
                            .setFont(font)
                            .setFontColor(ColorConstants.BLACK));
                    cell12.setBackgroundColor(ColorConstants.WHITE);
                    cell12.setBorder(Border.NO_BORDER);
                    cell12.setTextAlignment(TextAlignment.LEFT);
                    table2.addCell(cell12);

                    Cell cell13 = new Cell().add(new Paragraph(String.valueOf(value.getSum()))
                            .setFont(font)
                            .setFontColor(ColorConstants.BLACK));
                    cell13.setBackgroundColor(ColorConstants.WHITE);
                    cell13.setBorder(Border.NO_BORDER);
                    cell13.setTextAlignment(TextAlignment.CENTER);
                    table2.addCell(cell13);

                    Cell cell14 = new Cell().add(new Paragraph(value.getAdmitted() ? "Ano" : "Ne")
                            .setFont(font)
                            .setFontColor(ColorConstants.BLACK));
                    cell14.setBackgroundColor(ColorConstants.WHITE);
                    cell14.setBorder(Border.NO_BORDER);
                    cell14.setTextAlignment(TextAlignment.CENTER);
                    table2.addCell(cell14);
                }*/

                int i = 0;
                List<StudentResult> requestSorted = request.stream().sorted(Comparator.comparingInt(StudentResult::getOrder)).collect(Collectors.toList());

                for (StudentResult value : requestSorted) {
                    i++;

                    table2.addCell(createCell(String.valueOf(value.getOrder()), 1, 1, style2));
                    table2.addCell(createCell(value.getRegisterNumber(), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getJpzMat()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getJpzCj()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf((value.getJpzMat() + value.getJpzCj())), 1, 1, style2));
                    table2.addCell(createCell(value.getCermatCondition() ? "Ano" : "Ne", 1, 1, value.getCermatCondition() ? styleYES : styleNOC));
                    table2.addCell(createCell(String.valueOf((value.getMotivation() + value.getInterview() + value.getCreative() + value.getOffSchool())), 1, 1, style2));
                    table2.addCell(createCell(value.getGpnCondition() ? "Ano" : "Ne", 1, 1, value.getGpnCondition() ? styleYES : styleNOC));
                    table2.addCell(createCell(String.valueOf(value.getReportCard()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getSum()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getNormalizeSum()), 1, 1, style2));
                    table2.addCell(createCell(value.getAdmitted() == 1 ? "Přijat" : value.getAdmitted() == 2 ? "Nepřijat (K)" : "Nepřijat (P)", 1, 1, value.getAdmitted() == 1 ? styleYES : value.getAdmitted() == 2 ? styleNO : styleNOC));
                    //table2.addCell(createCell(value.getAdmitted() == 1 ? "Přijat" : "Nepřijat", 1, 1, value.getAdmitted() == 1 ? styleYES : styleNO));
                    table2.addCell(createCell("", 1, 1, style2));
                }

                document.add(table2);

                //strankovani do paticky
                int numberOfPages = pdf.getNumberOfPages();
                for (int j = 1; j <= numberOfPages; j++) {
                    // Write aligned text to the specified by parameters point
                    //document.showTextAligned(new Paragraph(String.format("strana %s z %s   tisk: %s", j, numberOfPages, formatter.format(prgDateTime))).setFontSize(8), 35, 25, j, TextAlignment.LEFT, VerticalAlignment.TOP, 0);
                    document.showTextAligned(new Paragraph(String.format("Vysvětlivky: Nepřijat (K) - kapacitní důvody; Nepřijat (P) - nesplněné podmínky; tisk: %s  strana %s z %s   ", formatter.format(prgDateTime), j, numberOfPages)).setFontSize(10).setFont(font), 800, 25, j, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
                }

                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    /* verze pro rok 2024 */
    public String getPdfResultsBoard2024(List<StudentResult> request) {

        String fileName = "user-export-agenda.pdf";
        final String pdfName = "__" + Calendar.getInstance().getTimeInMillis() + ".pdf";

        try {


            if (request != null) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                //String dateTimeString = formatter.format(now);

                /*TimeZone zone = TimeZone.getTimeZone("Europe/Prague");
                DateFormat dtFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Calendar cal = Calendar.getInstance(zone);
                Date date = cal.getTime();
                String strFormat = dtFormat.format(date);

                System.out.println("LocalDateTime : " + dateTimeString);*/

                ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
                String dateTimeString2 = formatter.format(zonedDateTime);

                System.out.println("LocalDateTime : " + dateTimeString2);
                //UTC+8
                ZonedDateTime prgDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Prague"));
                System.out.println("Depart : " + formatter.format(prgDateTime));

                final String pdfFullName = repoPath + pdfName;
                PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFullName));
                Document document = new Document(pdf, PageSize.A4.rotate(), false);

                final String REGULAR = fontPath + "arial.ttf";
                PdfFont font = PdfFontFactory.createFont(REGULAR);

                // Creating a table object
                float[] pointColumnWidths = {50F, 100F, 200F, 150F};
                //Table table2 = new Table(pointColumnWidths).useAllAvailableWidth();
                Table table2 = new Table(UnitValue.createPercentArray(12)).useAllAvailableWidth();

                Border b0 = new SolidBorder(ColorConstants.BLACK, 1);
                Border b1 = new DoubleBorder(ColorConstants.BLACK, 1);
                Table table22 = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
                //table2.setBorder(b0);

                Style style = new Style();
                style.setBorder(new SolidBorder(1));
                style.setFont(font).setFontColor(ColorConstants.BLACK);
                style.setBackgroundColor(ColorConstants.YELLOW);

                Style style2 = new Style();
                style2.setBorder(new SolidBorder(1));
                style2.setFont(font).setFontColor(ColorConstants.BLACK);

                Style style3 = new Style();
                style3.setBorder(new SolidBorder(1));
                style3.setFont(font).setFontColor(ColorConstants.BLACK).setBold();

                Style styleNO = new Style();
                styleNO.setBorder(new SolidBorder(1));
                styleNO.setFont(font);
                styleNO.setBackgroundColor(ColorConstants.YELLOW);

                Style styleNOC = new Style();
                styleNOC.setBorder(new SolidBorder(1));
                styleNOC.setFont(font);
                styleNOC.setBackgroundColor(ColorConstants.LIGHT_GRAY);

                Style styleYES = new Style();
                styleYES.setBorder(new SolidBorder(1));
                styleYES.setFont(font);
                styleYES.setBackgroundColor(ColorConstants.GREEN);
                table2.addHeaderCell(createCell("Gymnázium Paměti národa, s. r. o. - Výsledky přijímacího řízení 1. kolo", 12, 1, style3));
                table2.addHeaderCell(createCell("Pořadí", 1, 2, style));
                table2.addHeaderCell(createCell("ID kód uchazeče", 1, 2, style));
                table2.addHeaderCell(createCell("Jednotná zkouška (CERMAT)", 4, 1, style));
                table2.addHeaderCell(createCell("Školní přijímací zkouška", 2, 1, style));
                //table2.addHeaderCell(createCell("Vysvědčení", 1, 2, style));
                table2.addHeaderCell(createCell("Součet bodú", 2, 1, style));
                table2.addHeaderCell(createCell("Výsledek", 1, 2, style));
                table2.addHeaderCell(createCell("", 1, 2, style));

                table2.addHeaderCell(createCell("MAT", 1, 1, style));
                table2.addHeaderCell(createCell("ČJ/OČJ", 1, 1, style));
                table2.addHeaderCell(createCell("Součet", 1, 1, style));
                table2.addHeaderCell(createCell("Splnil", 1, 1, style));
                table2.addHeaderCell(createCell("Součet", 1, 1, style));
                table2.addHeaderCell(createCell("Splnil", 1, 1, style));
                table2.addHeaderCell(createCell("Celkem", 1, 1, style));
                table2.addHeaderCell(createCell("Vážený", 1, 1, style));

                int i = 0;
                List<StudentResult> requestSorted = request.stream().sorted(Comparator.comparingInt(StudentResult::getOrder)).collect(Collectors.toList());

                for (StudentResult value : requestSorted) {
                    i++;

                    table2.addCell(createCell(String.valueOf(value.getOrder()), 1, 1, style2));
                    table2.addCell(createCell(value.getRegisterNumber(), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getJpzMat()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getJpzCj()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf((value.getJpzMat() + value.getJpzCj())), 1, 1, style2));
                    table2.addCell(createCell(value.getCermatCondition() ? "Ano" : "Ne", 1, 1, value.getCermatCondition() ? styleYES : styleNOC));
                    table2.addCell(createCell(String.valueOf((value.getMotivation() + value.getInterview() + value.getCreative() + value.getOffSchool())), 1, 1, style2));
                    table2.addCell(createCell(value.getGpnCondition() ? "Ano" : "Ne", 1, 1, value.getGpnCondition() ? styleYES : styleNOC));
                    //table2.addCell(createCell(String.valueOf(value.getReportCard()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getSum()), 1, 1, style2));
                    table2.addCell(createCell(String.valueOf(value.getNormalizeSum()), 1, 1, style2));
                    table2.addCell(createCell(value.getAdmitted() == 1 ? "Přijat" : value.getAdmitted() == 2 ? "Nepřijat (K)" : "Nepřijat (P)", 1, 1, value.getAdmitted() == 1 ? styleYES : value.getAdmitted() == 2 ? styleNO : styleNOC));
                    //table2.addCell(createCell(value.getAdmitted() == 1 ? "Přijat" : "Nepřijat", 1, 1, value.getAdmitted() == 1 ? styleYES : styleNO));
                    table2.addCell(createCell("", 1, 1, style2));
                }

                document.add(table2);

                //strankovani do paticky
                int numberOfPages = pdf.getNumberOfPages();
                for (int j = 1; j <= numberOfPages; j++) {
                    // Write aligned text to the specified by parameters point
                    //document.showTextAligned(new Paragraph(String.format("strana %s z %s   tisk: %s", j, numberOfPages, formatter.format(prgDateTime))).setFontSize(8), 35, 25, j, TextAlignment.LEFT, VerticalAlignment.TOP, 0);
                    document.showTextAligned(new Paragraph(String.format("Vysvětlivky: Nepřijat (K) - kapacitní důvody; Nepřijat (P) - nesplněné podmínky; tisk: %s  strana %s z %s   ", formatter.format(prgDateTime), j, numberOfPages)).setFontSize(10).setFont(font), 800, 25, j, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
                }

                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfName;
    }

    private static Cell createCell(String content, int colspan, int rowspan, Style style) {
        Paragraph paragraph = new Paragraph(content)
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER);

        Cell cell = new Cell(rowspan, colspan).add(paragraph);
        cell.addStyle(style);

        return cell;
    }

    public void writeResult(List<StudentResult> request) throws IOException {
        String resultFileName = "results.txt";

        if (request != null) {
            List<StudentResult> requestSorted = request.stream().sorted(Comparator.comparingInt(StudentResult::getOrder)).collect(Collectors.toList());
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(requestSorted);


            final Path resultFilePath = Paths.get(repoPath + resultFileName);
            Files.writeString(resultFilePath, json);

        }
    }

    public void writePublicDate(PublicDate publicDate) throws IOException {
        String resultFileName = "publicDate.txt";

        if (publicDate != null) {
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(publicDate);


            final Path resultFilePath = Paths.get(repoPath + resultFileName);
            Files.writeString(resultFilePath, json);

        }
    }

    public List<StudentResult> readResult() throws IOException {
        List<StudentResult> returnList;
        String resultFileName = "results.txt";

        ObjectMapper mapper = new ObjectMapper();


        final Path resultFilePath = Paths.get(repoPath + resultFileName);
        String json = Files.readAllLines(resultFilePath).get(0);
        returnList = mapper.readValue(json, new TypeReference<List<StudentResult>>() {
        });
        //do vysledkov nechceme zahrnut mena studentov
        returnList.forEach(studentResult -> studentResult.setStudentName(null));

        return returnList;


    }

    public Resource loadEmployeesWithClassPathResource(String filename) {
        //return new  ClassPathResource("file:///app/generate/"+filename);
        return new PathResource("file:///Users/roberthuljak/Downloads/stitky-adobe-crack.pdf");
    }

    private String getStampValue(AddressStamp value) {
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n\t\t");
        if (null != value.getTitle1() && !value.getTitle1().isBlank()) {
            sb.append(value.getTitle1() + " ");
        }
        if (null != value.getFirstName() && !value.getFirstName().isBlank()) {
            sb.append(value.getFirstName() + " ");
        }
        if (null != value.getLastName() && !value.getLastName().isBlank()) {
            sb.append(value.getLastName());
        }
        if (null != value.getTitle2() && !value.getTitle2().isBlank()) {
            sb.append(" " + value.getTitle2());
        }
        sb.append("\n\t\t");

        if (null != value.getStreet() && !value.getStreet().isBlank()) {
            sb.append(value.getStreet() + " ");
        }
        if (null != value.getNumber1() && !value.getNumber1().isBlank()) {
            sb.append(value.getNumber1());
        }
        if (null != value.getNumber1() && null != value.getNumber2() && !value.getNumber1().isBlank() && !value.getNumber2().isBlank()) {
            sb.append("/");
        }
        if (null != value.getNumber2() && !value.getNumber2().isBlank()) {
            sb.append(value.getNumber2());
        }
        //sb.append("\n\t");
        sb.append("\n\t\t");
        if (null != value.getZip() && !value.getZip().isBlank()) {
            if (value.getZip().trim().length() == 5) {
                sb.append(value.getZip().substring(0, 3) + " " + value.getZip().substring(3) + " ");
            } else {
                sb.append(value.getZip() + " ");
            }
        }
        if (null != value.getCity() && !value.getCity().isBlank()) {
            sb.append(value.getCity());
        }
        if (null != value.getCity() && null != value.getCityPart() && !value.getCityPart().equals(value.getCity())) {
            sb.append(" - " + value.getCityPart());
        }

        return sb.toString();
    }

    private String getStampValue(FolderStamp value) {
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n\t\t");
        if (null != value.getFirstName() && !value.getFirstName().isBlank()) {
            sb.append(value.getFirstName() + " ");
        }
        if (null != value.getLastName() && !value.getLastName().isBlank()) {
            sb.append(value.getLastName());
        }
        sb.append("\n\t\t");

        if (null != value.getBirthDate() && !value.getBirthDate().isBlank()) {
            sb.append(value.getBirthDate());
        }
        sb.append("\n\t\t");

        if (null != value.getRegNumber() && !value.getRegNumber().isBlank()) {
            sb.append(value.getRegNumber());
        }

        return sb.toString();
    }

    private static String getAddressConfirmationValue(StudentConfirmationResult.Address value) {
        StringBuffer sb = new StringBuffer();
        sb.append("");
        if (null != value.getParentTitle1() && !value.getParentTitle1().isBlank()) {
            sb.append(value.getParentTitle1() + " ");
        }
        if (null != value.getParentName() && !value.getParentName().isBlank()) {
            sb.append(value.getParentName() + " ");
        }
        if (null != value.getParentLastName() && !value.getParentLastName().isBlank()) {
            sb.append(value.getParentLastName());
        }
        if (null != value.getParentTitle2() && !value.getParentTitle2().isBlank()) {
            sb.append(" " + value.getParentTitle2());
        }
        sb.append("\n");

        if (null != value.getStreet() && !value.getStreet().isBlank()) {
            sb.append(value.getStreet() + " ");
        }
        if (null != value.getStreetNumber1() && !value.getStreetNumber1().isBlank()) {
            sb.append(value.getStreetNumber1());
        }
        if (null != value.getStreetNumber1() && null != value.getStreetNumber2() && !value.getStreetNumber1().isBlank() && !value.getStreetNumber2().isBlank()) {
            sb.append("/");
        }
        if (null != value.getStreetNumber2() && !value.getStreetNumber2().isBlank()) {
            sb.append(value.getStreetNumber2());
        }
        //sb.append("\n\t");
        sb.append("\n");
        if (null != value.getZipCode() && !value.getZipCode().isBlank()) {
            if (value.getZipCode().trim().length() == 5) {
                sb.append(value.getZipCode().substring(0, 3) + " " + value.getZipCode().substring(3) + " ");
            } else {
                sb.append(value.getZipCode() + " ");
            }
        }
        if (null != value.getCity() && !value.getCity().isBlank()) {
            sb.append(value.getCity());
        }
        if (null != value.getCity() && null != value.getCityPart() && !value.getCityPart().equals(value.getCity())) {
            sb.append(" - " + value.getCityPart());
        }

        return sb.toString();
    }

    private String getStampValue(AssetsStamp value) {
        StringBuffer sb = new StringBuffer();
        //sb.append("\n\n\t\t");
        if (null != value.getId()&& !value.getId().isBlank()) {
            sb.append("\n\n\t\t");
            sb.append("Inv.č.: ");
            sb.append(value.getId());
        }
        if (null != value.getTitle() && !value.getTitle().isBlank()) {
            sb.append("\n\n\t\t");
            sb.append(value.getTitle());
        }
        return sb.toString();
    }

    private static void renameFields(PdfDocument pdf, String prefix) {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getAllFormFields();
        int index = 0;
        for (String name : fields.keySet()) {
            String bb = prefix + (++index);
            form.getField(name).setFieldName(bb).setValue("").setReadOnly(Boolean.TRUE);
        }
    }

    private static void renameFields2(PdfDocument pdf, String suffix) {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getAllFormFields();
        int index = 0;
        for (String name : fields.keySet()) {
            String bb = name + suffix + (++index);
            form.getField(name).setFieldName(bb).setValue("").setReadOnly(Boolean.TRUE);
        }
    }

    private static void renameFields4(String fieldPrefix, PdfDocument pdf) {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getAllFormFields();
        for (String name : fields.keySet()) {
            String bb = fieldPrefix + name;
            String origValue =form.getField(name).getValueAsString();
            form.getField(name).setFieldName(bb).setValue(origValue).setReadOnly(Boolean.TRUE);
        }
    }

    private static void renameFields3(PdfDocument pdf, String suffix) {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getAllFormFields();
        int index = 0;
        int fieldOnPageIndex = -1;
        for (String name : fields.keySet()) {
            fieldOnPageIndex++;
            String oName = name.split("_")[0];
            String bb = oName + suffix + index;
            form.getField(name).setFieldName(bb).setValue("").setReadOnly(Boolean.TRUE);
            /*if(oName.equals("name")) {
                index++;
            }*/
            if (fieldOnPageIndex == 11) {
                index++;
                fieldOnPageIndex = -1;
            }
        }
    }

    private static void renameFields33(PdfDocument pdf, String suffix, int resetIndex) {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getAllFormFields();
        int index = 0;
        int fieldOnPageIndex = -1;
        for (String name : fields.keySet()) {
            fieldOnPageIndex++;
            String oName = name.split("_")[0];
            String bb = oName + suffix + index;
            form.getField(name).setFieldName(bb).setValue("").setReadOnly(Boolean.TRUE);
            /*if(oName.equals("name")) {
                index++;
            }*/
            if (fieldOnPageIndex == resetIndex) {
                index++;
                fieldOnPageIndex = -1;
            }
        }
    }

    private static void getAllFieldNames(PdfDocument pdf) {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getAllFormFields();
        for (String name : fields.keySet()) {
            System.out.println("name je: " + name);
        }
    }

    private static void fillResultAgenda(PdfDocument pdf, PdfFont font, StudentResult result, int page) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        String fieldName1 = "record1_" + page;
        fillResultValue(form, font, fieldName1, String.valueOf(result.getJpzCj()));
        String fieldName2 = "record2_" + page;
        fillResultValue(form, font, fieldName2, String.valueOf(result.getJpzMat()));
        String fieldName3 = "record3_" + page;
        fillResultValue(form, font, fieldName3, String.valueOf(result.getMotivation()));
        String fieldName4 = "record4_" + page;
        fillResultValue(form, font, fieldName4, String.valueOf(result.getInterview()));
        String fieldName5 = "record5_" + page;
        fillResultValue(form, font, fieldName5, String.valueOf(result.getCreative()));
        String fieldName6 = "record6_" + page;
        fillResultValue(form, font, fieldName6, String.valueOf(result.getOffSchool()));
        String fieldName7 = "record7_" + page;
        fillResultValue(form, font, fieldName7, String.valueOf(result.getReportCard()));
        String fieldName8 = "record8_" + page;
        fillResultValue(form, font, fieldName8, String.valueOf(result.getSum()));
        String fieldName9 = "record9_" + page;
        fillResultValue(form, font, fieldName9, result.getAdmitted() == 1 ? "Přijat" : result.getAdmitted() == 2 ? "Nepřijat misto" : "Nepřijat");
        String fieldName10 = "date_" + page;
        fillResultValue(form, font, fieldName10, LocalDate.now().format(dateTimeFormatter), TextAlignment.RIGHT, 8f);
        String fieldName11 = "name_" + page;
        fillResultValue(form, font, fieldName11, result.getStudentName(), TextAlignment.LEFT, 24f);
        String fieldName12 = "proceedingNumber_" + page;
        fillResultValue(form, font, fieldName12, result.getRegisterNumber());
    }

    private static void fillResultConfirmation(PdfDocument pdf, PdfFont font, PdfFont fontBold, StudentConfirmationResult result, int docNumber) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        String fieldName1 = "registerNumberField_" + docNumber;
        fillResultValue(form, font, fieldName1, String.valueOf(result.getRegisterNumber()), TextAlignment.LEFT, 12f);
        String fieldName2 = "addressField_" + docNumber;
        fillResultValue(form, font, fieldName2, getAddressConfirmationValue(result.getAddress()), TextAlignment.LEFT, 12f);
        String fieldName3 = "printedDateField_" + docNumber;
        fillResultValue(form, font, fieldName3, String.valueOf(result.getPrintedDate()), TextAlignment.LEFT, 12f);
        String fieldName4 = "studentNameField1_" + docNumber;
        fillResultValue(form, fontBold, fieldName4, String.valueOf(result.getStudentName()), TextAlignment.CENTER, 20f);
        String fieldName5 = "dateBirthField_" + docNumber;
        fillResultValue(form, fontBold, fieldName5, "nar. " + String.valueOf(result.getDateBirth()), TextAlignment.CENTER, 12f);
        String fieldName6 = "sonDaughterField1_" + docNumber;
        fillResultValue(form, font, fieldName6, String.valueOf(result.getSex().equalsIgnoreCase("M") ? "Váš syn získal" : "Vaše dcera získala"), TextAlignment.CENTER, 12f);
        String fieldName7 = "normalizeSumField_" + docNumber;
        fillResultValue(form, font, fieldName7, String.valueOf(result.getNormalizeSum()), TextAlignment.CENTER, 12f);
        String fieldName8 = "jpzTotalField_" + docNumber;
        fillResultValue(form, font, fieldName8, String.valueOf(String.format("%.0f", (result.getJpzMat() + result.getJpzCj()))), TextAlignment.RIGHT, 12f);
        String fieldName9 = "jpzMatField_" + docNumber;
        fillResultValue(form, font, fieldName9, String.valueOf(String.format("%.0f", result.getJpzMat())), TextAlignment.CENTER, 12f);
        String fieldName10 = "jpzCjField_" + docNumber;
        fillResultValue(form, font, fieldName10, String.valueOf(String.format("%.0f", result.getJpzCj())), TextAlignment.CENTER, 12f);
        String fieldName11 = "schoolTotalField_" + docNumber;
        fillResultValue(form, font, fieldName11, String.valueOf(String.format("%.0f", (result.getMotivation() + result.getInterview() + result.getCreative() + result.getOffSchool()))), TextAlignment.LEFT, 12f);
        String fieldName12 = "reportCardField_" + docNumber;
        fillResultValue(form, font, fieldName12, String.valueOf(String.format("%.0f", result.getReportCard())), TextAlignment.CENTER, 12f);
        String fieldName13 = "fullFillField_" + docNumber;
        if(result.getCermatCondition() && result.getGpnCondition()) {
            fillResultValue(form, font, fieldName13, String.valueOf(result.getSex().equalsIgnoreCase("M") ? "splnil" : "splnila"), TextAlignment.CENTER, 12f);
        } else {
            fillResultValue(form, font, fieldName13, String.valueOf(result.getSex().equalsIgnoreCase("M") ? "nesplnil" : "nesplnila"), TextAlignment.CENTER, 12f);
        }
        String fieldName14 = "sonDaughterField2_" + docNumber;
        fillResultValue(form, fontBold, fieldName14, String.valueOf(result.getSex().equalsIgnoreCase("M") ? "Vaším synem" : "Vaší dcerou"), TextAlignment.CENTER, 12f);
        String fieldName15 = "sonDaughterField3_" + docNumber;
        fillResultValue(form, fontBold, fieldName15, String.valueOf(result.getSex().equalsIgnoreCase("M") ? "Vašeho syna" : "Vaší dcery "), TextAlignment.CENTER, 12f);
        String fieldName16 = "viewedField_" + docNumber;
        fillResultValue(form, font, fieldName16, String.valueOf(result.getViewed()), TextAlignment.LEFT, 12f);
        String fieldName17 = "studentNameField2_" + docNumber;
        fillResultValue(form, font, fieldName17, String.valueOf(result.getStudentName()), TextAlignment.LEFT, 12f);
        String fieldName18 = "orderField_" + docNumber;
        fillResultValue(form, font, fieldName18, String.valueOf(result.getOrder() + "."), TextAlignment.CENTER, 12f);
        String fieldName19 = "studentNameField3_" + docNumber;
        fillResultValue(form, font, fieldName19, String.valueOf(result.getStudentName()), TextAlignment.LEFT, 12f);
    }

    private static void fillResultValue(PdfAcroForm form, PdfFont font, String fieldName, String fieldValue) {
        if (form.getField(fieldName) != null) {
            form.getField(fieldName)
                    .setValue(fieldValue)
                    .setReadOnly(Boolean.TRUE)
                    .setJustification(TextAlignment.RIGHT)
                    .setFontAndSize(font, 18f)
            ;
        }
    }

    private static void fillResultValue(PdfAcroForm form, PdfFont font, String fieldName, String fieldValue, TextAlignment alignment, float fontSize) {
        if (form.getField(fieldName) != null) {
            form.getField(fieldName)
                    .setValue(fieldValue)
                    .setReadOnly(Boolean.TRUE)
                    .setJustification(alignment)
                    .setFontAndSize(font, fontSize)
            ;
        }
    }

    private static String resolveConfirmationFileName(int type) {

        if(type == 1) {
            return "rozhodnuti-o-prijeti-1.pdf";
        } else if(type == 2) {
            return "rozhodnuti-o-neprijeti-2.pdf";
        } else if(type == 3) {
            return "rozhodnuti-o-neprijeti-3.pdf";
        } else if(type == 4) {
            return "rozhodnuti-o-neprijeti-cizinci-3.pdf";
        }

        /*rozhodnuti-o-neprijeti-2.pdf
        rozhodnuti-o-neprijeti-3.pdf
        rozhodnuti-o-neprijeti-cizinci-3.pdf
        rozhodnuti-o-prijeti-1.pdf*/

        return "rozhodnuti-o-prijeti-1.pdf";
    }

}
