package tcc;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tcc.flight.FlightReservationDoc;
import tcc.hotel.HotelReservationDoc;

/**
 * Simple non-transactional client. Can be used to populate the booking services
 * with some requests.
 */
public class TestClient {
	public static void main(String[] args) {
		try {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(TestServer.BASE_URI);

			GregorianCalendar tomorrow = new GregorianCalendar();
			tomorrow.setTime(new Date());
			tomorrow.add(GregorianCalendar.DAY_OF_YEAR, 1);

			// book flight

			WebTarget webTargetFlight = target.path("flight");

			FlightReservationDoc docFlight = new FlightReservationDoc();
			docFlight.setName("Christian");
			docFlight.setFrom("Karlsruhe");
			docFlight.setTo("Berlin");
			docFlight.setAirline("airberlin");
			docFlight.setDate(tomorrow.getTimeInMillis());

			Response responseFlight = webTargetFlight.request().accept(MediaType.APPLICATION_XML)
					.post(Entity.xml(docFlight));

			if (responseFlight.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : " + responseFlight.getStatus());
			}

			FlightReservationDoc outputFlight = responseFlight.readEntity(FlightReservationDoc.class);
			System.out.println("Output from Server: " + outputFlight);

			// book hotel

			WebTarget webTargetHotel = target.path("hotel");

			HotelReservationDoc docHotel = new HotelReservationDoc();
			docHotel.setName("Christian");
			docHotel.setHotel("Interconti");
			docHotel.setDate(tomorrow.getTimeInMillis());

			Response responseHotel = webTargetHotel.request().accept(MediaType.APPLICATION_XML)
					.post(Entity.xml(docHotel));

			if (responseHotel.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : " + responseHotel.getStatus());
			}

			HotelReservationDoc outputHotel = responseHotel.readEntity(HotelReservationDoc.class);
			System.out.println("Output from Server: " + outputHotel);

			if (responseHotel.getStatus() != 200 || responseFlight.getStatus() != 200) {
				// cancel flight booking
				try {
					WebTarget cancelFlightTarget = client.target(outputFlight.getUrl());
					Response deleteFlightResponse = cancelFlightTarget.request().accept(MediaType.TEXT_PLAIN).delete();
					System.out.println("Cancel flight response: " + deleteFlightResponse);
				} catch (Exception e) {
					System.out.println("Cancel flight request failed" + e.getMessage());
				}

				// cancel hotel booking
				try {
					WebTarget deleteHotelTarget = client.target(outputHotel.getUrl());
					Response deleteHotelResponse = deleteHotelTarget.request().accept(MediaType.TEXT_PLAIN).delete();
					System.out.println("Cancel hotel response: " + deleteHotelResponse);
				} catch (Exception e) {
					System.out.println("Cancel hotel request failed" + e.getMessage());
				}

				System.out.println("Canceled preliminary bookings");
				return;
			}

			// confirm flight booking
			WebTarget confirmFlightTarget = client.target(outputFlight.getUrl());
			Response confirmFlightResponse = confirmFlightTarget.request().accept(MediaType.TEXT_PLAIN)
					.put(Entity.json(""));
			System.out.println("Confirm flight response: " + confirmFlightResponse);

			// confirm hotel booking
			WebTarget confirmHotelTarget = client.target(outputHotel.getUrl());
			Response confirmHotelResponse = confirmHotelTarget.request().accept(MediaType.TEXT_PLAIN)
					.put(Entity.json(""));
			System.out.println("Confirm flight response: " + confirmHotelResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
