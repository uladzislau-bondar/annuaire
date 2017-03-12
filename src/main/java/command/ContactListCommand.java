package command;


import db.Connector;
import dto.ContactDto;
import entities.Address;
import entities.Contact;
import entities.ContactBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DtoUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactCommand.class);

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
        forward("index");
    }

    @Override
    public void process() throws ServletException, IOException {
//        Address address1 = new Address();
//        address1.setAddress("Kolasa 28, 612a");
//        Address address2 = new Address();
//        address2.setAddress("Hmelnockogo 94, 117");
//
//        ContactBuilder builder1 = new ContactBuilder()
//                .firstName("Ulad")
//                .lastName("Bondar")
//                .id(123L)
//                .dateOfBirth(new Date())
//                .placeOfWork("BSUIR")
//                .address(address1);
//        Contact contact1 = builder1.build();
//        ContactBuilder builder2 = new ContactBuilder()
//                .firstName("John")
//                .lastName("Smiths")
//                .dateOfBirth(new Date(123456))
//                .placeOfWork("American Aerlines")
//                .address(address2);
//        Contact contact2 = builder2.build();

        Connection connection = Connector.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
//            c.id, c.firstName, c.lastName, c.dateOfBirth, c.placeOfWork, c.addressId" +
//            "a.id, a.country, a.city, a.address, a.zip

            String query = "select * from contacts c " +
                    "left outer join addresses a on c.addressId=a.id limit 1";

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            if(rs != null && rs.next()){
                Address address = new Address();
                address.setCountry(rs.getString("a.country"));
                address.setCity(rs.getString("a.city"));
                address.setAddress(rs.getString("a.address"));
                address.setZip(rs.getInt("a.zip"));

                ContactBuilder builder = new ContactBuilder();
                builder.id(rs.getLong("c.id"))
                        .firstName(rs.getString("c.firstName"))
                        .lastName(rs.getString("c.lastName"))
                        .dateOfBirth(rs.getDate("c.dateOfBirth"))
                        .address(address)
                        .placeOfWork(rs.getString("c.placeOfWork"));
                Contact contact = builder.build();
                ContactDto dto = DtoUtils.convertToDto(contact);

                List<ContactDto> contactList = new ArrayList<>();
                contactList.add(dto);

                request.setAttribute("contactList", contactList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Database connection problem");
            throw new ServletException("DB Connection problem.");
        }finally{
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                logger.error("SQLException in closing PreparedStatement or ResultSet");;
            }

        }
    }

    @Override
    public void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }
}
