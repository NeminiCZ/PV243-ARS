package cz.muni.fi.pv243;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import cz.muni.fi.pv243.ars.persistence.enumeration.UserRole;
import cz.muni.fi.pv243.ars.persistence.model.Address;
import cz.muni.fi.pv243.ars.persistence.model.Offer;
import cz.muni.fi.pv243.ars.persistence.model.Reservation;
import cz.muni.fi.pv243.ars.persistence.model.User;
import cz.muni.fi.pv243.ars.repository.AddressRepository;
import cz.muni.fi.pv243.ars.repository.OfferRepository;
import cz.muni.fi.pv243.ars.repository.ReservationRepository;
import cz.muni.fi.pv243.ars.repository.UserRepository;
import cz.muni.fi.pv243.ars.utils.Resources;
import cz.muni.fi.pv243.utils.EntityFactoryPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by mminatova on 6/6/18.
 */
@RunWith(Arquillian.class)
public class OfferRepositoryTest {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Inject
    private Logger log;

    @Inject
    private UserTransaction tx;

    @Inject
    private UserRepository userRepository;

    @Inject
    private OfferRepository offerRepository;

    private EntityFactoryPersistence ef = new EntityFactoryPersistence();

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addClasses(Resources.class, EntityFactoryPersistence.class)
                .addPackages(true, "cz.muni.fi.pv243.ars.repository", "cz.muni.fi.pv243.ars.persistence")
                .addPackage(UserRole.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    private Address address;


    @Before
    public void init() throws SystemException, NotSupportedException {
        tx.begin();

        address = ef.createAddress("Praha", "Czech Republic", "Dlouha");
        Address address1 = ef.createAddress("Bratislava", "Slovakia", "Bajkalska");
        Address address2 = ef.createAddress("Brno", "Czech Republic", "Sumavska");

        Offer offer1 = ef.createOffer(address1);
        Offer offer2 = ef.createOffer(address2);

        offerRepository.create(offer1);
        offerRepository.create(offer2);
    }

    @After
    public void tearDown() throws SystemException {
        tx.rollback();
    }

    @Test
    public void createOfferTest() {
        log.info("create offer test");
        Offer expected = ef.createOffer(address);

        offerRepository.create(expected);

        Offer actual = offerRepository.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateOfferTest() {
        log.info("update offer test");
        Offer expected = ef.createOffer(address);
        expected.setAnimalFriendly(true);
        offerRepository.create(expected);
        assertEquals(offerRepository.findById(expected.getId()), expected);

        expected.setAnimalFriendly(false);
        offerRepository.update(expected);

        Offer actual = offerRepository.findById(expected.getId());
        assertEquals(expected, actual);
        assertEquals(expected.getAnimalFriendly(), actual.getAnimalFriendly());
    }

    @Test
    public void deleteOfferTest() {
        log.info("delete offer test");
        Offer toDelete = ef.createOffer(address);
        offerRepository.create(toDelete);

        int offerCount = offerRepository.findAll().size();
        offerRepository.delete(toDelete);

        assertEquals(userRepository.findById(toDelete.getId()), null);
        assertEquals(offerCount - 1, offerRepository.findAll().size());
    }

    @Test
    public void findByIdTest() {
        log.info("find by id offer test");
        Offer expected = ef.createOffer(address);
        offerRepository.create(expected);

        Offer actual = offerRepository.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() {
        log.info("find all offer test");
        assertEquals(offerRepository.findAll().size(), 2);

        Offer offer = ef.createOffer(address);
        offerRepository.create(offer);

        assertEquals(offerRepository.findAll().size(), 3);
    }


    @Test
    public void findAllForUserTest() {
        log.info("find all offers for user test");
        User user = ef.createUser("testUser");
        userRepository.create(user);
        List<Offer> allForUser = offerRepository.findAllForUser(user);
        assertEquals(allForUser.size(), 0);

        Offer offer = ef.createOffer(address);
        offer.setUser(user);
        offerRepository.create(offer);

        assertEquals(offerRepository.findAllForUser(user).size(), 1);
    }



}
