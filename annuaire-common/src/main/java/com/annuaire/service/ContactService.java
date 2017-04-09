package com.annuaire.service;


import com.annuaire.dao.AttachmentDao;
import com.annuaire.dao.ContactDao;
import com.annuaire.dao.PhoneDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.*;
import com.annuaire.entities.Attachment;
import com.annuaire.entities.Contact;
import com.annuaire.entities.Phone;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;
import com.annuaire.properties.UploadPropertyService;
import com.annuaire.util.DtoUtils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ContactService {
    private final static String PROFILE_PIC_NAME = "profilePic";

    public ContactDatabaseDto get(Long id) throws ServiceException {
        final ContactDatabaseDto dto = new ContactDatabaseDto();

        try {
            TransactionHandler.run(connection -> {
                Contact contact = (getContact(connection, id));
                if (contact != null){
                    dto.setContact(contact);
                    dto.setPhones(getPhones(connection, id));
                    dto.setAttachments(getAttachments(connection, id));
                }
            });
        } catch (TransactionException e) {
            throw new ServiceException(e);
        }

        return dto;
    }

    public void save(ContactFrontDto dto) throws ServiceException, IOException {
        try {
            TransactionHandler.run(connection -> {
                Long id = saveContact(connection, dto.getContact());
                savePhoto(connection, dto.getPhoto(), id);
                savePhones(connection, dto, id);
                saveAttachments(connection, dto, id);
            });
        } catch (TransactionException e) {
            throw new ServiceException(e);
        }
    }

    public void update(ContactFrontDto dto, Long id) throws ServiceException, IOException {
        try {
            TransactionHandler.run(connection -> {
                dto.getContact().setId(id);
                if (dto.getContact().getPhotoPath() == null){
                    ContactDao dao = new ContactDao(connection);
                    String photoPath = dao.getPhotoPathById(id);
                    dto.getContact().setPhotoPath(photoPath);
                    savePhoto(connection, dto.getPhoto(), id);
                }
                updateContact(connection, dto.getContact());
                savePhones(connection, dto, id);
                saveAttachments(connection, dto, id);
            });
        } catch (TransactionException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(Long id) throws ServiceException{
        try{
            TransactionHandler.run(connection -> {
                deleteContact(connection, id);
            });
        } catch (TransactionException e){
            throw new ServiceException(e);
        }

    }

    private Long saveContact(Connection connection, Contact contact) throws SQLException{
        ContactDao dao = new ContactDao(connection);
        return dao.save(contact);
    }

    private void updateContact(Connection connection, Contact contact) throws SQLException{
        ContactDao dao = new ContactDao(connection);
        dao.update(contact);
    }

    private void deleteContact(Connection connection, Long id) throws SQLException{
        ContactDao dao = new ContactDao(connection);
        dao.delete(id);
    }

    private Contact getContact(Connection connection, Long id) throws SQLException{
        ContactDao dao = new ContactDao(connection);
        return dao.getById(id);
    }

    private void savePhoto(Connection connection, Part photo, Long contactId) throws SQLException, IOException{
        if (photo != null && photo.getSize() > 0) {
            String photoPath = savePhotoToFile(photo, contactId);

            ContactDao dao = new ContactDao(connection);
            dao.updatePhotoPathById(photoPath, contactId);
        }
    }

    private List<PhoneInfoDto> getPhones(Connection connection, Long contactId) throws SQLException{
        PhoneDao dao = new PhoneDao(connection);
        List<Phone> phones = dao.getByContactId(contactId);

        List<PhoneInfoDto> dtoList = new ArrayList<>();
        for (Phone phone : phones) {
            dtoList.add(DtoUtils.convertToDto(phone));
        }

        return dtoList;
    }

    private List<AttachmentInfoDto> getAttachments(Connection connection, Long contactId) throws SQLException{
        AttachmentDao dao = new AttachmentDao(connection);
        List<Attachment> attachments = dao.getByContactId(contactId);

        List<AttachmentInfoDto> dtoList = new ArrayList<>();
        for (Attachment attachment : attachments) {
            dtoList.add(DtoUtils.convertToDto(attachment));
        }

        return dtoList;
    }

    private void savePhones(Connection connection, ContactFrontDto dto, Long contactId) throws SQLException{
        PhoneDao dao = new PhoneDao(connection);
        saveAddedPhones(dao, dto.getAddedPhones(), contactId);
        updatePhones(dao, dto.getUpdatedPhones(), contactId);
        deletePhones(dao, dto.getDeletedPhonesIds());
    }

    private void saveAddedPhones(PhoneDao dao, List<Phone> phones, Long contactId) throws SQLException{
        for (Phone phone : phones) {
            phone.setContactId(contactId);
            dao.save(phone);
        }
    }

    private void updatePhones(PhoneDao dao, List<Phone> phones, Long contactId) throws SQLException{
        for (Phone phone : phones) {
            phone.setContactId(contactId);
            dao.update(phone);
        }
    }

    private void deletePhones(PhoneDao dao, List<Long> ids) throws SQLException{
        for (Long id : ids) {
            dao.delete(id);
        }
    }

    private void saveAttachments(Connection connection, ContactFrontDto contact, Long contactId) throws SQLException, IOException{
        AttachmentDao dao = new AttachmentDao(connection);
        saveAddedAttachments(dao, contact.getAddedAttachments(), contactId);
        updateAttachments(dao, contact.getUpdatedAttachments(), contactId);
        deleteAttachments(dao, contact.getDeletedAttachmentsIds());
    }

    private void saveAddedAttachments(AttachmentDao dao, List<AttachmentFrontDto> attachments, Long contactId) throws SQLException, IOException{
        for (AttachmentFrontDto dto : attachments) {
            Attachment attachment = DtoUtils.convertToAttachment(dto);
            attachment.setContactId(contactId);

            if (dto.getFile() != null) {
                Long id = dao.save(attachment);

                String fileName = savePartToFile(String.valueOf(id), dto.getFile(), contactId);
                dao.updateFilePathById(fileName, id);
            }
        }
    }

    private void updateAttachments(AttachmentDao dao, List<AttachmentFrontDto> attachments, Long contactId) throws SQLException, IOException{
        for (AttachmentFrontDto dto : attachments) {
            Attachment attachment = DtoUtils.convertToAttachment(dto);
            attachment.setContactId(contactId);

            if (dto.getFile() != null) {
                String fileName = savePartToFile(dto.getName(), dto.getFile(), contactId);
                attachment.setFileName(fileName);
                dao.update(attachment);
            }
        }
    }

    private void deleteAttachments(AttachmentDao dao, List<Long> ids) throws SQLException, IOException{
        for (Long id : ids) {
            String filePath = dao.getFilePathById(id);
            deleteFileByPath(filePath);

            dao.delete(id);
        }
    }

    private String savePhotoToFile(Part photo, Long contactId) throws IOException {
        String fileName = savePartToFile(PROFILE_PIC_NAME, photo, contactId);
        return fileName;
    }


    private String savePartToFile(String name, Part part, Long contactId) throws IOException {
        String uploadPath = UploadPropertyService.getInstance().getPath();
        String path = uploadPath + File.separator + "contact" + contactId;

        File fileSaveDir = new File(path);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String filePath = fileSaveDir.getAbsolutePath() + File.separator + name;
        part.write(filePath);

        return filePath;
    }

    private void deleteFileByPath(String filePath) throws IOException{
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }
    }
}
