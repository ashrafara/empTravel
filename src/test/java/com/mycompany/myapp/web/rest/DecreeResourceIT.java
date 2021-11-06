package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Decree;
import com.mycompany.myapp.domain.enumeration.DecType;
import com.mycompany.myapp.repository.DecreeRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DecreeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DecreeResourceIT {

    private static final Integer DEFAULT_DECREENUM = 1;
    private static final Integer UPDATED_DECREENUM = 2;

    private static final Integer DEFAULT_DECREEYEAR = 1;
    private static final Integer UPDATED_DECREEYEAR = 2;

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final DecType DEFAULT_DECTYPE = DecType.TRAINING;
    private static final DecType UPDATED_DECTYPE = DecType.WORK;

    private static final Integer DEFAULT_DAYNUM = 1;
    private static final Integer UPDATED_DAYNUM = 2;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRTY = "BBBBBBBBBB";

    private static final String DEFAULT_SPONSOR = "AAAAAAAAAA";
    private static final String UPDATED_SPONSOR = "BBBBBBBBBB";

    private static final String DEFAULT_PROPONENT = "AAAAAAAAAA";
    private static final String UPDATED_PROPONENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/decrees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DecreeRepository decreeRepository;

    @Mock
    private DecreeRepository decreeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDecreeMockMvc;

    private Decree decree;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Decree createEntity(EntityManager em) {
        Decree decree = new Decree()
            .decreenum(DEFAULT_DECREENUM)
            .decreeyear(DEFAULT_DECREEYEAR)
            .purpose(DEFAULT_PURPOSE)
            .dectype(DEFAULT_DECTYPE)
            .daynum(DEFAULT_DAYNUM)
            .city(DEFAULT_CITY)
            .countrty(DEFAULT_COUNTRTY)
            .sponsor(DEFAULT_SPONSOR)
            .proponent(DEFAULT_PROPONENT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return decree;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Decree createUpdatedEntity(EntityManager em) {
        Decree decree = new Decree()
            .decreenum(UPDATED_DECREENUM)
            .decreeyear(UPDATED_DECREEYEAR)
            .purpose(UPDATED_PURPOSE)
            .dectype(UPDATED_DECTYPE)
            .daynum(UPDATED_DAYNUM)
            .city(UPDATED_CITY)
            .countrty(UPDATED_COUNTRTY)
            .sponsor(UPDATED_SPONSOR)
            .proponent(UPDATED_PROPONENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return decree;
    }

    @BeforeEach
    public void initTest() {
        decree = createEntity(em);
    }

    @Test
    @Transactional
    void createDecree() throws Exception {
        int databaseSizeBeforeCreate = decreeRepository.findAll().size();
        // Create the Decree
        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isCreated());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeCreate + 1);
        Decree testDecree = decreeList.get(decreeList.size() - 1);
        assertThat(testDecree.getDecreenum()).isEqualTo(DEFAULT_DECREENUM);
        assertThat(testDecree.getDecreeyear()).isEqualTo(DEFAULT_DECREEYEAR);
        assertThat(testDecree.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testDecree.getDectype()).isEqualTo(DEFAULT_DECTYPE);
        assertThat(testDecree.getDaynum()).isEqualTo(DEFAULT_DAYNUM);
        assertThat(testDecree.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDecree.getCountrty()).isEqualTo(DEFAULT_COUNTRTY);
        assertThat(testDecree.getSponsor()).isEqualTo(DEFAULT_SPONSOR);
        assertThat(testDecree.getProponent()).isEqualTo(DEFAULT_PROPONENT);
        assertThat(testDecree.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDecree.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDecree.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDecree.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createDecreeWithExistingId() throws Exception {
        // Create the Decree with an existing ID
        decree.setId(1L);

        int databaseSizeBeforeCreate = decreeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isBadRequest());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDecreenumIsRequired() throws Exception {
        int databaseSizeBeforeTest = decreeRepository.findAll().size();
        // set the field null
        decree.setDecreenum(null);

        // Create the Decree, which fails.

        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isBadRequest());

        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDecreeyearIsRequired() throws Exception {
        int databaseSizeBeforeTest = decreeRepository.findAll().size();
        // set the field null
        decree.setDecreeyear(null);

        // Create the Decree, which fails.

        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isBadRequest());

        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDaynumIsRequired() throws Exception {
        int databaseSizeBeforeTest = decreeRepository.findAll().size();
        // set the field null
        decree.setDaynum(null);

        // Create the Decree, which fails.

        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isBadRequest());

        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountrtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = decreeRepository.findAll().size();
        // set the field null
        decree.setCountrty(null);

        // Create the Decree, which fails.

        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isBadRequest());

        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSponsorIsRequired() throws Exception {
        int databaseSizeBeforeTest = decreeRepository.findAll().size();
        // set the field null
        decree.setSponsor(null);

        // Create the Decree, which fails.

        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isBadRequest());

        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProponentIsRequired() throws Exception {
        int databaseSizeBeforeTest = decreeRepository.findAll().size();
        // set the field null
        decree.setProponent(null);

        // Create the Decree, which fails.

        restDecreeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isBadRequest());

        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDecrees() throws Exception {
        // Initialize the database
        decreeRepository.saveAndFlush(decree);

        // Get all the decreeList
        restDecreeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(decree.getId().intValue())))
            .andExpect(jsonPath("$.[*].decreenum").value(hasItem(DEFAULT_DECREENUM)))
            .andExpect(jsonPath("$.[*].decreeyear").value(hasItem(DEFAULT_DECREEYEAR)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].dectype").value(hasItem(DEFAULT_DECTYPE.toString())))
            .andExpect(jsonPath("$.[*].daynum").value(hasItem(DEFAULT_DAYNUM)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].countrty").value(hasItem(DEFAULT_COUNTRTY)))
            .andExpect(jsonPath("$.[*].sponsor").value(hasItem(DEFAULT_SPONSOR)))
            .andExpect(jsonPath("$.[*].proponent").value(hasItem(DEFAULT_PROPONENT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDecreesWithEagerRelationshipsIsEnabled() throws Exception {
        when(decreeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDecreeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(decreeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDecreesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(decreeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDecreeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(decreeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDecree() throws Exception {
        // Initialize the database
        decreeRepository.saveAndFlush(decree);

        // Get the decree
        restDecreeMockMvc
            .perform(get(ENTITY_API_URL_ID, decree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(decree.getId().intValue()))
            .andExpect(jsonPath("$.decreenum").value(DEFAULT_DECREENUM))
            .andExpect(jsonPath("$.decreeyear").value(DEFAULT_DECREEYEAR))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.dectype").value(DEFAULT_DECTYPE.toString()))
            .andExpect(jsonPath("$.daynum").value(DEFAULT_DAYNUM))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.countrty").value(DEFAULT_COUNTRTY))
            .andExpect(jsonPath("$.sponsor").value(DEFAULT_SPONSOR))
            .andExpect(jsonPath("$.proponent").value(DEFAULT_PROPONENT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingDecree() throws Exception {
        // Get the decree
        restDecreeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDecree() throws Exception {
        // Initialize the database
        decreeRepository.saveAndFlush(decree);

        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();

        // Update the decree
        Decree updatedDecree = decreeRepository.findById(decree.getId()).get();
        // Disconnect from session so that the updates on updatedDecree are not directly saved in db
        em.detach(updatedDecree);
        updatedDecree
            .decreenum(UPDATED_DECREENUM)
            .decreeyear(UPDATED_DECREEYEAR)
            .purpose(UPDATED_PURPOSE)
            .dectype(UPDATED_DECTYPE)
            .daynum(UPDATED_DAYNUM)
            .city(UPDATED_CITY)
            .countrty(UPDATED_COUNTRTY)
            .sponsor(UPDATED_SPONSOR)
            .proponent(UPDATED_PROPONENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restDecreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDecree.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDecree))
            )
            .andExpect(status().isOk());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
        Decree testDecree = decreeList.get(decreeList.size() - 1);
        assertThat(testDecree.getDecreenum()).isEqualTo(UPDATED_DECREENUM);
        assertThat(testDecree.getDecreeyear()).isEqualTo(UPDATED_DECREEYEAR);
        assertThat(testDecree.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testDecree.getDectype()).isEqualTo(UPDATED_DECTYPE);
        assertThat(testDecree.getDaynum()).isEqualTo(UPDATED_DAYNUM);
        assertThat(testDecree.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDecree.getCountrty()).isEqualTo(UPDATED_COUNTRTY);
        assertThat(testDecree.getSponsor()).isEqualTo(UPDATED_SPONSOR);
        assertThat(testDecree.getProponent()).isEqualTo(UPDATED_PROPONENT);
        assertThat(testDecree.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDecree.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDecree.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDecree.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingDecree() throws Exception {
        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();
        decree.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDecreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, decree.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(decree))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDecree() throws Exception {
        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();
        decree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(decree))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDecree() throws Exception {
        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();
        decree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecreeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDecreeWithPatch() throws Exception {
        // Initialize the database
        decreeRepository.saveAndFlush(decree);

        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();

        // Update the decree using partial update
        Decree partialUpdatedDecree = new Decree();
        partialUpdatedDecree.setId(decree.getId());

        partialUpdatedDecree
            .decreenum(UPDATED_DECREENUM)
            .decreeyear(UPDATED_DECREEYEAR)
            .purpose(UPDATED_PURPOSE)
            .dectype(UPDATED_DECTYPE)
            .daynum(UPDATED_DAYNUM)
            .city(UPDATED_CITY)
            .countrty(UPDATED_COUNTRTY)
            .endDate(UPDATED_END_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restDecreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDecree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDecree))
            )
            .andExpect(status().isOk());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
        Decree testDecree = decreeList.get(decreeList.size() - 1);
        assertThat(testDecree.getDecreenum()).isEqualTo(UPDATED_DECREENUM);
        assertThat(testDecree.getDecreeyear()).isEqualTo(UPDATED_DECREEYEAR);
        assertThat(testDecree.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testDecree.getDectype()).isEqualTo(UPDATED_DECTYPE);
        assertThat(testDecree.getDaynum()).isEqualTo(UPDATED_DAYNUM);
        assertThat(testDecree.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDecree.getCountrty()).isEqualTo(UPDATED_COUNTRTY);
        assertThat(testDecree.getSponsor()).isEqualTo(DEFAULT_SPONSOR);
        assertThat(testDecree.getProponent()).isEqualTo(DEFAULT_PROPONENT);
        assertThat(testDecree.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDecree.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDecree.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDecree.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateDecreeWithPatch() throws Exception {
        // Initialize the database
        decreeRepository.saveAndFlush(decree);

        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();

        // Update the decree using partial update
        Decree partialUpdatedDecree = new Decree();
        partialUpdatedDecree.setId(decree.getId());

        partialUpdatedDecree
            .decreenum(UPDATED_DECREENUM)
            .decreeyear(UPDATED_DECREEYEAR)
            .purpose(UPDATED_PURPOSE)
            .dectype(UPDATED_DECTYPE)
            .daynum(UPDATED_DAYNUM)
            .city(UPDATED_CITY)
            .countrty(UPDATED_COUNTRTY)
            .sponsor(UPDATED_SPONSOR)
            .proponent(UPDATED_PROPONENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restDecreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDecree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDecree))
            )
            .andExpect(status().isOk());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
        Decree testDecree = decreeList.get(decreeList.size() - 1);
        assertThat(testDecree.getDecreenum()).isEqualTo(UPDATED_DECREENUM);
        assertThat(testDecree.getDecreeyear()).isEqualTo(UPDATED_DECREEYEAR);
        assertThat(testDecree.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testDecree.getDectype()).isEqualTo(UPDATED_DECTYPE);
        assertThat(testDecree.getDaynum()).isEqualTo(UPDATED_DAYNUM);
        assertThat(testDecree.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDecree.getCountrty()).isEqualTo(UPDATED_COUNTRTY);
        assertThat(testDecree.getSponsor()).isEqualTo(UPDATED_SPONSOR);
        assertThat(testDecree.getProponent()).isEqualTo(UPDATED_PROPONENT);
        assertThat(testDecree.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDecree.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDecree.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDecree.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingDecree() throws Exception {
        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();
        decree.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDecreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, decree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(decree))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDecree() throws Exception {
        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();
        decree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(decree))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDecree() throws Exception {
        int databaseSizeBeforeUpdate = decreeRepository.findAll().size();
        decree.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecreeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(decree)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Decree in the database
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDecree() throws Exception {
        // Initialize the database
        decreeRepository.saveAndFlush(decree);

        int databaseSizeBeforeDelete = decreeRepository.findAll().size();

        // Delete the decree
        restDecreeMockMvc
            .perform(delete(ENTITY_API_URL_ID, decree.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Decree> decreeList = decreeRepository.findAll();
        assertThat(decreeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
