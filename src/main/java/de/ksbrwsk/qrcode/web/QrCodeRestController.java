package de.ksbrwsk.qrcode.web;

import de.ksbrwsk.qrcode.config.ApplicationProperties;
import de.ksbrwsk.qrcode.model.*;
import de.ksbrwsk.qrcode.service.PdfCreator;
import de.ksbrwsk.qrcode.service.QrCodeEncoder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
public class QrCodeRestController {

    private final static String PAGE_INDEX = "index";
    private final static String PAGE_RESULT = "result";
    private final static String PAGE_QR_CODE_URL = "qr-code-url";
    private final static String PAGE_QR_CODE_PHONE = "qr-code-phone";
    private final static String PAGE_QR_CODE_FACETIME = "qr-code-facetime";
    private final static String PAGE_QR_CODE_VCARD = "qr-code-vcard";
    private final static String PAGE_QR_CODE_EMAIL = "qr-code-email";
    private final static String PAGE_QR_CODE_SMS = "qr-code-sms";
    private final static String PAGE_QR_CODE_EVENT = "qr-code-event";
    @Value("${app.my.pdf.repo}")
    private String repoPath;

    @Value("${app.my.hostname}")
    private String hostname;

    private final static String QR_CODE = "image";
    private final static String TEXT_TO_BE_ENCODED = "text";
    private final static String SUCCESS_MESSAGE = "successMessage";
    private final static String ERROR_MESSAGE = "errorMessage";

    private final ApplicationProperties applicationProperties;
    private final QrCodeEncoder qrCodeEncoder;
    private final de.ksbrwsk.qrcode.service.PdfCreator pdfCreator;

    public QrCodeRestController(ApplicationProperties applicationProperties, QrCodeEncoder qrCodeEncoder, PdfCreator pdfCreator) {
        this.applicationProperties = applicationProperties;
        this.qrCodeEncoder = qrCodeEncoder;
        this.pdfCreator = pdfCreator;
    }


    @PostMapping("/api/process/url")
    public QrCodeProcessingResult processRhuUrl(@Valid @RequestBody QrCodeUrl qrCodeUrl,
                                                BindingResult bindingResult) {
        //addCommonModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            log.info("generate QR Code for RHU Url {}", qrCodeUrl.getUrlToBeEncoded());
            QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
            //this.addResultModelAttributes(model, result);
            //pdfCreator.getPdfStickSheet(null, null);
            return result;
        }
        throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/process/stamps")
    public String processRhuStamps(@RequestBody List<@Valid Stamp> request,
                                   BindingResult bindingResult) {
        //addCommonModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            log.info("generate StampListl {}", request.size());
            //QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
            //this.addResultModelAttributes(model, result);
            ;

            return "/api/files/" + pdfCreator.getPdfStickSheet(new ArrayList<AddressStamp>(), null);
        }
        throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
    }

    /**
     * Pro vyplneni sablony tisku zakladajiciho do Agendy
     */
    @PostMapping("/api/results/agenda")
    public ResponseEntity<AddressStampReponse> processResultsAgenda(@RequestBody List<@Valid StudentResult> request,
                                                                    BindingResult bindingResult) {
        return ResponseEntity.ok()
                .body(new AddressStampReponse(hostname + "/api/files/" + pdfCreator.getPdfResultsAgenda(request)));
    }

    /**
     * Pro vyplneni sablony tisku rozhodnuti pro uchazece
     */
    @PostMapping("/api/results/confirmation")
    public ResponseEntity<AddressStampReponse> processResultsConfirmation(@RequestBody List<@Valid StudentConfirmationResult> request,
                                                                          BindingResult bindingResult) {
        return ResponseEntity.ok()
                .body(new AddressStampReponse(hostname + "/api/files/" + pdfCreator.getPdfResultsConfirmation(request)));
    }

    /**
     * Pro vyplneni generovani tisku vysledku na nastenku
     */
    @PostMapping("/api/results/board")
    public ResponseEntity<AddressStampReponse> processResultsBoard(@RequestBody List<@Valid StudentResult> request,
                                                                   BindingResult bindingResult) {
        return ResponseEntity.ok()
                .body(new AddressStampReponse(hostname + "/api/files/" + pdfCreator.getPdfResultsBoard(request)));
    }

    /**
     * Pro generovani adresnich stitku
     */
    @PostMapping("/api/address/stamps")

    public ResponseEntity<AddressStampReponse> addressStamps(@Valid @RequestBody List<@Valid AddressStamp> request, @RequestParam Integer startIndex) {
        //addCommonModelAttributes(model);

        log.info("generate AddressStampList {}", request.size());
        //QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
        //this.addResultModelAttributes(model, result);
        ;
        //System.out.println("city: "+request.get(0).getCity());

        return ResponseEntity.ok()
                .body(new AddressStampReponse(hostname + "/api/files/" + pdfCreator.getPdfStickSheet(request, startIndex)));


    }

    /**
     * Pro generovani majetkovych stitku
     */
    @PostMapping("/api/assets/stamps")

    public ResponseEntity<AddressStampReponse> assetsStamps(@Valid @RequestBody List<@Valid AssetsStamp> request, @RequestParam Integer startIndex) {
        //addCommonModelAttributes(model);

        log.info("generate AssetsStampList {}", request.size());
        //QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
        //this.addResultModelAttributes(model, result);
        ;
        //System.out.println("city: "+request.get(0).getCity());

        return ResponseEntity.ok()
                .body(new AddressStampReponse(hostname + "/api/files/" + pdfCreator.getPdfStickAssetsSheet(request, startIndex)));


    }

    /**
     * Pro generovani ePA csv pro postu
     */
    @PostMapping("/api/address/epa")

    public ResponseEntity<AddressStampReponse> addressStamps(@Valid @RequestBody List<@Valid AddressStamp> request) {
        //addCommonModelAttributes(model);

        log.info("generate ePA {}", request.size());
        //QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
        //this.addResultModelAttributes(model, result);
        ;
        //System.out.println("city: "+request.get(0).getCity());

        return ResponseEntity.ok()
                .body(new AddressStampReponse(hostname + "/api/files/txt/" + pdfCreator.getEPA(request)));


    }

    /**
     * Pro nastaveni casu od kdy maji byt vysledky pres API dostupne pres GET
     */
    @PostMapping("/api/results")

    public ResponseEntity uploadPublicDate(@Valid @RequestBody PublicDate publicDate) {
        //addCommonModelAttributes(model);

        log.info("uploadPublicDate {}", publicDate);
        //QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
        //this.addResultModelAttributes(model, result);
        ;
        //System.out.println("city: "+request.get(0).getCity());

        try {
            pdfCreator.writePublicDate(publicDate);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Pro ulozeni vysledku na filesystem
     */
    @PostMapping("/api/RXQgcXVpYSBvZmZpY2lhIHNlZCB2b2x1cHRhcyBhcGVyaWFtIGF1dCBxdWFlcmF0IG5lbW8gaWQgbW9sbGl0aWEgb2RpdCBzZWQgb2ZmaWNpYSBpbmNpZHVudCBhdXQgbW9kaSBub3N0cnVtIGF1dCBkb2xvcmVzIGl1c3RvLg/results")

    public ResponseEntity uploadResults(@Valid @RequestBody List<@Valid StudentResult> request) {
        //addCommonModelAttributes(model);

        log.info("uploadResults {}", request.size());
        //QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
        //this.addResultModelAttributes(model, result);
        ;
        //System.out.println("city: "+request.get(0).getCity());

        try {
            pdfCreator.writeResult(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Pro nacitani vysledku z filesystemu (napr. pro vebove stranky)
     */
    @GetMapping("/api/results")

    public ResponseEntity<List<@Valid StudentResult>> getResults() {
        //addCommonModelAttributes(model);

        log.info("getResults");
        //QrCodeProcessingResult result = this.qrCodeEncoder.generateQrCodeUrl(qrCodeUrl);
        //this.addResultModelAttributes(model, result);
        ;
        //System.out.println("city: "+request.get(0).getCity());

        try {

            return ResponseEntity.ok().body(pdfCreator.readResult());
        } catch (IOException e) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }

    /*@GetMapping("/api/files/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {


        Resource file = pdfCreator.loadEmployeesWithClassPathResource(filename);
        if (file.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        }
        throw new ErrorResponseException(HttpStatus.NOT_FOUND);

    }*/

    /**
     * Pro nacitani vygenerovaneho PDF souboru
     */
    @GetMapping("/api/files/{filename}")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@PathVariable String filename) {

        String path = repoPath + filename;
        File file = new File(path);
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            ResponseEntity<Object>
                    responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                    MediaType.parseMediaType("application/pdf")).body(resource);

            return responseEntity;
        } catch (FileNotFoundException e) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Pro nacitani vygenerovaneho TXT souboru
     */
    @GetMapping("/api/files/txt/{filename}")
    @ResponseBody
    public ResponseEntity<Object> serveTxtFile(@PathVariable String filename) {

        String path = repoPath + filename;
        File file = new File(path);
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            ResponseEntity<Object>
                    responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                    MediaType.parseMediaType("text/csv")).body(resource);

            return responseEntity;
        } catch (FileNotFoundException e) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }

    private void addCommonModelAttributes(@NotNull Model model) {
        model.addAttribute("titleMessage", this.applicationProperties.getTitle());
        model.addAttribute("appInfo", this.applicationProperties.getAppInfo());
    }

    private void addResultModelAttributes(@NotNull Model model, @NotNull QrCodeProcessingResult result) {
        model.addAttribute(QR_CODE, result.getImage());
        model.addAttribute(TEXT_TO_BE_ENCODED, result.getEncodedText());
        if (result.isSuccessfull()) {
            model.addAttribute(SUCCESS_MESSAGE, result.getSuccessMessage());
        } else {
            model.addAttribute(ERROR_MESSAGE, result.getErrorMessage());
        }
    }
}