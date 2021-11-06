package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DecreeIssue;
import com.mycompany.myapp.repository.DecreeIssueRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DecreeIssue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DecreeIssueResource {

    private final Logger log = LoggerFactory.getLogger(DecreeIssueResource.class);

    private static final String ENTITY_NAME = "decreeIssue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecreeIssueRepository decreeIssueRepository;

    public DecreeIssueResource(DecreeIssueRepository decreeIssueRepository) {
        this.decreeIssueRepository = decreeIssueRepository;
    }

    /**
     * {@code POST  /decree-issues} : Create a new decreeIssue.
     *
     * @param decreeIssue the decreeIssue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decreeIssue, or with status {@code 400 (Bad Request)} if the decreeIssue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/decree-issues")
    public ResponseEntity<DecreeIssue> createDecreeIssue(@RequestBody DecreeIssue decreeIssue) throws URISyntaxException {
        log.debug("REST request to save DecreeIssue : {}", decreeIssue);
        if (decreeIssue.getId() != null) {
            throw new BadRequestAlertException("A new decreeIssue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DecreeIssue result = decreeIssueRepository.save(decreeIssue);
        return ResponseEntity
            .created(new URI("/api/decree-issues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /decree-issues/:id} : Updates an existing decreeIssue.
     *
     * @param id the id of the decreeIssue to save.
     * @param decreeIssue the decreeIssue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decreeIssue,
     * or with status {@code 400 (Bad Request)} if the decreeIssue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the decreeIssue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/decree-issues/{id}")
    public ResponseEntity<DecreeIssue> updateDecreeIssue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DecreeIssue decreeIssue
    ) throws URISyntaxException {
        log.debug("REST request to update DecreeIssue : {}, {}", id, decreeIssue);
        if (decreeIssue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decreeIssue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decreeIssueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DecreeIssue result = decreeIssueRepository.save(decreeIssue);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, decreeIssue.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /decree-issues/:id} : Partial updates given fields of an existing decreeIssue, field will ignore if it is null
     *
     * @param id the id of the decreeIssue to save.
     * @param decreeIssue the decreeIssue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decreeIssue,
     * or with status {@code 400 (Bad Request)} if the decreeIssue is not valid,
     * or with status {@code 404 (Not Found)} if the decreeIssue is not found,
     * or with status {@code 500 (Internal Server Error)} if the decreeIssue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/decree-issues/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DecreeIssue> partialUpdateDecreeIssue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DecreeIssue decreeIssue
    ) throws URISyntaxException {
        log.debug("REST request to partial update DecreeIssue partially : {}, {}", id, decreeIssue);
        if (decreeIssue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decreeIssue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decreeIssueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DecreeIssue> result = decreeIssueRepository
            .findById(decreeIssue.getId())
            .map(existingDecreeIssue -> {
                if (decreeIssue.getName() != null) {
                    existingDecreeIssue.setName(decreeIssue.getName());
                }

                return existingDecreeIssue;
            })
            .map(decreeIssueRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, decreeIssue.getId().toString())
        );
    }

    /**
     * {@code GET  /decree-issues} : get all the decreeIssues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decreeIssues in body.
     */
    @GetMapping("/decree-issues")
    public ResponseEntity<List<DecreeIssue>> getAllDecreeIssues(Pageable pageable) {
        log.debug("REST request to get a page of DecreeIssues");
        Page<DecreeIssue> page = decreeIssueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /decree-issues/:id} : get the "id" decreeIssue.
     *
     * @param id the id of the decreeIssue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decreeIssue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decree-issues/{id}")
    public ResponseEntity<DecreeIssue> getDecreeIssue(@PathVariable Long id) {
        log.debug("REST request to get DecreeIssue : {}", id);
        Optional<DecreeIssue> decreeIssue = decreeIssueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(decreeIssue);
    }

    /**
     * {@code DELETE  /decree-issues/:id} : delete the "id" decreeIssue.
     *
     * @param id the id of the decreeIssue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/decree-issues/{id}")
    public ResponseEntity<Void> deleteDecreeIssue(@PathVariable Long id) {
        log.debug("REST request to delete DecreeIssue : {}", id);
        decreeIssueRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
