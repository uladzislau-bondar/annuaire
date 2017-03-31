package service;


import dao.AttachmentDao;
import dao.ContactDao;
import dao.PhoneDao;
import db.TransactionHandler;
import dto.AttachmentFrontDto;
import dto.ContactDatabaseDto;
import dto.ContactFrontDto;
import entities.Attachment;
import entities.Contact;
import entities.Phone;
import properties.UploadPropertyService;
import util.DtoUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;


public class ContactService {
    private final static String UPLOAD_PATH = UploadPropertyService.getInstance().getPath();
    private final static String PROFILE_PIC_NAME = "profilePic";

    public ContactDatabaseDto get(Long id){
        final ContactDatabaseDto dto = new ContactDatabaseDto();
        TransactionHandler.run(connection -> {
            dto.setContact(getContact(connection, id));
            dto.setPhones(getPhones(connection, id));
            dto.setAttachments(getAttachments(connection, id));
        });

        return dto;
    }

    public void save(ContactFrontDto dto){
        TransactionHandler.run(connection -> {
            Long id = saveContact(connection, dto.getContact());
            savePhoto(connection, dto.getPhoto(), id);
            savePhones(connection, dto, id);
            saveAttachments(connection, dto, id);
        });
    }

    public void update(ContactFrontDto dto, Long id){
        TransactionHandler.run(connection -> {
            dto.getContact().setId(id);
            updateContact(connection, dto.getContact());
            savePhoto(connection, dto.getPhoto(), id);
            savePhones(connection, dto, id);
            saveAttachments(connection, dto, id);
        });
    }

    public void delete(Long id){
        TransactionHandler.run(connection -> {
            deleteContact(connection, id);
        });
    }

    private Long saveContact(Connection connection, Contact contact){
        ContactDao dao = new ContactDao(connection);
        return dao.save(contact);
    }

    private void updateContact(Connection connection, Contact contact){
        ContactDao dao = new ContactDao(connection);
        dao.update(contact);
    }

    private void deleteContact(Connection connection, Long id){
        ContactDao dao = new ContactDao(connection);
        dao.delete(id);
    }

    private Contact getContact(Connection connection, Long id){
        ContactDao dao = new ContactDao(connection);
        return dao.getById(id);
    }

    private void savePhoto(Connection connection, Part photo, Long contactId){
        if (photo != null) {
            try{
                String photoPath = savePhotoToFile(photo, contactId);

                ContactDao dao = new ContactDao(connection);
                dao.updatePhotoPathById(photoPath, contactId);
            } catch (IOException e){
                // todo msg
            }
        }
    }

    private List<Phone> getPhones(Connection connection, Long contactId){
        PhoneDao dao = new PhoneDao(connection);
        return dao.getByContactId(contactId);
    }

    private List<Attachment> getAttachments(Connection connection, Long contactId){
        AttachmentDao dao = new AttachmentDao(connection);
        return dao.getByContactId(contactId);
    }

    private void savePhones(Connection connection, ContactFrontDto dto, Long contactId){
        PhoneDao dao = new PhoneDao(connection);
        for (Phone updatedPhone : dto.getUpdatedPhones()) {
            updatedPhone.setContactId(contactId);
            dao.update(updatedPhone);
        }
        for (Phone addedPhone : dto.getAddedPhones()) {
            addedPhone.setContactId(contactId);
            dao.save(addedPhone);
        }
        for (Long id : dto.getDeletedPhonesIds()) {
            dao.delete(id);
        }
    }

    private void saveAttachments(Connection connection, ContactFrontDto contact, Long contactId) {
        AttachmentDao dao = new AttachmentDao(connection);
        for (AttachmentFrontDto dto : contact.getUpdatedAttachments()) {
            Attachment attachment = DtoUtils.convertToAttachment(dto);
            attachment.setContactId(contactId);

            if (dto.getFile() != null){
                try{
                    String fileName = savePartToFile(dto.getName(), dto.getFile(), contactId);
                    attachment.setFileName(fileName);
                    dao.update(attachment);
                }
                catch (IOException e){
                    //todo msg
                }
            }
        }
        for (AttachmentFrontDto dto : contact.getAddedAttachments()) {
            Attachment attachment = DtoUtils.convertToAttachment(dto);
            attachment.setContactId(contactId);

            if (dto.getFile() != null){
                try{
                    String fileName = savePartToFile(dto.getName(), dto.getFile(), contactId);
                    attachment.setFileName(fileName);
                    dao.save(attachment);
                }
                catch (IOException e){
                    //todo msg
                }
            }
        }
        for (Long id : contact.getDeletedAttachmentsIds()) {
            dao.delete(id);
        }
    }

    private String savePhotoToFile(Part photo, Long contactId) throws IOException{
        String fileName = savePartToFile(PROFILE_PIC_NAME, photo, contactId);
        return fileName;
    }

    private String savePartToFile(String name, Part part, Long contactId) throws IOException{
        String path = UPLOAD_PATH + File.separator + "contact" + contactId;

        File fileSaveDir = new File(path);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String filePath = path + File.separator + name;
        part.write(filePath);

        return filePath;
    }
}
