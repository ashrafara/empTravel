package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Decree;
import com.mycompany.myapp.repository.DecreeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
                if (decree.getSponsor() != null) {
                    existingDecree.setSponsor(decree.getSponsor());
                }
                if (decree.getProponent() != null) {
                    existingDecree.setProponent(decree.getProponent());
                }
                if (decree.getStartDate() != null) {
                    existingDecree.setStartDate(decree.getStartDate());
                }
                if (decree.getEndDate() != null) {
                    existingDecree.setEndDate(decree.getEndDate());
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
}
