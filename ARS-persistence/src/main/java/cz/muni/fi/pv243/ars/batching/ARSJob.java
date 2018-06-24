package cz.muni.fi.pv243.ars.batching;

import cz.muni.fi.pv243.ars.persistence.model.Offer;
import cz.muni.fi.pv243.ars.persistence.model.Reservation;
import cz.muni.fi.pv243.ars.persistence.model.User;
import cz.muni.fi.pv243.ars.repository.OfferRepository;
import cz.muni.fi.pv243.ars.repository.ReservationRepository;
import cz.muni.fi.pv243.ars.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by mminatova on 6/24/18.
 */
public class ARSJob {

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private Logger log;

    public void generateReport() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<Offer> offers = offerRepository.findAll();
        List<User> users = userRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();

        for (Reservation reservation:
             reservations) {
            stringBuilder.append(reservation.toString());
        }

        for (Offer offer:
                offers) {
            stringBuilder.append(offer.toString());
        }

        for (User user:
                users) {
            stringBuilder.append(user.toString());
        }

        String report = stringBuilder.toString();

        //Omitted: Saving report logic

        log.info("Created report " + System.currentTimeMillis());
    }
}
