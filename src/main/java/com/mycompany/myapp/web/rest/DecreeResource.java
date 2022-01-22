package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Decree;
import com.mycompany.myapp.repository.DecreeRepository;
import com.mycompany.myapp.service.utl.FileTools;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Decree}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DecreeResource {

    private final Logger log = LoggerFactory.getLogger(DecreeResource.class);

    private static final String ENTITY_NAME = "decree";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecreeRepository decreeRepository;

    public DecreeResource(DecreeRepository decreeRepository) {
        this.decreeRepository = decreeRepository;
    }

    /**
     * {@code POST  /decrees} : Create a new decree.
     *
     * @param decree the decree to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decree, or with status {@code 400 (Bad Request)} if the decree has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/decrees")
    public ResponseEntity<Decree> createDecree(@Valid @RequestBody Decree decree) throws URISyntaxException {
        log.debug("REST request to save Decree : {}", decree);
        if (decree.getId() != null) {
            throw new BadRequestAlertException("A new decree cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (decree.getImage() != null) {
            String filePath = FileTools.upload(decree.getImage(), decree.getImageContentType(), "decree");
            decree.setImage(null);
            decree.setImageContentType(decree.getImageContentType());
            decree.setImageUrl(filePath);
        }

        Decree result = decreeRepository.save(decree);
        return ResponseEntity
            .created(new URI("/api/decrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /decrees/:id} : Updates an existing decree.
     *
     * @param id the id of the decree to save.
     * @param decree the decree to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decree,
     * or with status {@code 400 (Bad Request)} if the decree is not valid,
     * or with status {@code 500 (Internal Server Error)} if the decree couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/decrees/{id}")
    public ResponseEntity<Decree> updateDecree(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Decree decree
    ) throws URISyntaxException {
        log.debug("REST request to update Decree : {}, {}", id, decree);
        if (decree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decree.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if (decree.getImage() != null) {
            String filePath = FileTools.upload(decree.getImage(), decree.getImageContentType(), "decree");
            decree.setImage(null);
            decree.setImageContentType(decree.getImageContentType());
            decree.setImageUrl(filePath);
        }

        Decree result = decreeRepository.save(decree);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, decree.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /decrees/:id} : Partial updates given fields of an existing decree, field will ignore if it is null
     *
     * @param id the id of the decree to save.
     * @param decree the decree to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decree,
     * or with status {@code 400 (Bad Request)} if the decree is not valid,
     * or with status {@code 404 (Not Found)} if the decree is not found,
     * or with status {@code 500 (Internal Server Error)} if the decree couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/decrees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Decree> partialUpdateDecree(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Decree decree
    ) throws URISyntaxException {
        log.debug("REST request to partial update Decree partially : {}, {}", id, decree);
        if (decree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decree.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if (decree.getImage() != null) {
            String filePath = FileTools.upload(decree.getImage(), decree.getImageContentType(), "decree");
            decree.setImage(null);
            decree.setImageContentType(decree.getImageContentType());
            decree.setImageUrl(filePath);
        }

        Optional<Decree> result = decreeRepository
            .findById(decree.getId())
            .map(existingDecree -> {
                if (decree.getDecreenum() != null) {
                    existingDecree.setDecreenum(decree.getDecreenum());
                }
                if (decree.getDecreeyear() != null) {
                    existingDecree.setDecreeyear(decree.getDecreeyear());
                }
                if (decree.getPurpose() != null) {
                    existingDecree.setPurpose(decree.getPurpose());
                }
                if (decree.getDectype() != null) {
                    existingDecree.setDectype(decree.getDectype());
                }
                if (decree.getDaynum() != null) {
                    existingDecree.setDaynum(decree.getDaynum());
                }
                if (decree.getCity() != null) {
                    existingDecree.setCity(decree.getCity());
                }
                if (decree.getCountrty() != null) {
                    existingDecree.setCountrty(decree.getCountrty());
                }
                if (decree.getStartDate() != null) {
                    existingDecree.setStartDate(decree.getStartDate());
                }
                if (decree.getEndDate() != null) {
                    existingDecree.setEndDate(decree.getEndDate());
                }
                if (decree.getArea() != null) {
                    existingDecree.setArea(decree.getArea());
                }
                if (decree.getCost() != null) {
                    existingDecree.setCost(decree.getCost());
                }
                if (decree.getDecreecost() != null) {
                    existingDecree.setDecreecost(decree.getDecreecost());
                }
                if (decree.getImageUrl() != null) {
                    existingDecree.setImageUrl(decree.getImageUrl());
                }
                if (decree.getImage() != null) {
                    existingDecree.setImage(decree.getImage());
                }
                if (decree.getImageContentType() != null) {
                    existingDecree.setImageContentType(decree.getImageContentType());
                }

                return existingDecree;
            })
            .map(decreeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, decree.getId().toString())
        );
    }

    /**
     * {@code GET  /decrees} : get all the decrees.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decrees in body.
     */
    @GetMapping("/decrees")
    public ResponseEntity<List<Decree>> getAllDecrees(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Decrees");
        Page<Decree> page;
        if (eagerload) {
            page = decreeRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = decreeRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /decrees/:id} : get the "id" decree.
     *
     * @param id the id of the decree to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decree, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decrees/{id}")
    public ResponseEntity<Decree> getDecree(@PathVariable Long id) {
        log.debug("REST request to get Decree : {}", id);
        Optional<Decree> decree = decreeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(decree);
    }

    /**
     * {@code DELETE  /decrees/:id} : delete the "id" decree.
     *
     * @param id the id of the decree to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/decrees/{id}")
    public ResponseEntity<Void> deleteDecree(@PathVariable Long id) {
        log.debug("REST request to delete Decree : {}", id);
        decreeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping(value = "/public/decrees/count-days/xlsx", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> countDaysAsXSLX() {
        List<Object[]> data = decreeRepository.findAllDayCount();
        String[] columns = { "dayCount", "employee name" };
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Companies");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        for (Object[] object : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(object[0].toString());
            row.createCell(1).setCellValue(object[1].toString());
        }
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new Date() + ".xlsx");
        header.setContentLength(bytes.length);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bytes), header);
    }

    @GetMapping(value = "/public/decrees/count-decree/xlsx", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> countDecreeAsXSLX() {
        List<Object[]> data = decreeRepository.findAllDecreecount();
        String[] columns = { "decreeCount", "employee name" };
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Companies");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        for (Object[] object : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(object[0].toString());
            row.createCell(1).setCellValue(object[1].toString());
        }
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new Date() + ".xlsx");
        header.setContentLength(bytes.length);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bytes), header);
    }

    @GetMapping(value = "/public/decrees/xlsx", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> getAllDecreesAsXSLX() {
        List<Object[]> data = decreeRepository.findAllreport();
        String[] columns = { "decreenum", "decreeyear", "countrty", "dectype", "daynum", "employee name" };
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Companies");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        for (Object[] object : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(object[0].toString());
            row.createCell(1).setCellValue(object[1].toString());
            row.createCell(2).setCellValue(object[2].toString());
            row.createCell(3).setCellValue(object[3].toString());
            row.createCell(4).setCellValue(object[4].toString());
            row.createCell(5).setCellValue(object[5].toString());
        }
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new Date() + ".xlsx");
        header.setContentLength(bytes.length);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bytes), header);
    }
}
