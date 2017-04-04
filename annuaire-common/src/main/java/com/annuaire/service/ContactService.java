package com.annuaire.service;


import com.annuaire.dao.AttachmentDao;
import com.annuaire.dao.ContactDao;
import com.annuaire.dao.PhoneDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.*;
import com.annuaire.entities.Attachment;
import com.annuaire.entities.Contact;
import com.annuaire.entities.Phone;
import com.annuaire.properties.UploadPropertyService;
import com.annuaire.util.DtoUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class ContactService {
    private final static String UPLOAD_PATH = UploadPropertyService.getInstance().getPath();
    private final static String PROFILE_PIC_NAME = "profilePic";

    public ContactDatabaseDto get(Long id) {
        final ContactDatabaseDto dto = new ContactDatabaseDto();
        TransactionHandler.run(connection -> {
            dto.setContact(getContact(connection, id));
            dto.setPhones(getPhones(connection, id));
            dto.setAttachments(getAttachments(connection, id));
        });

        return dto;
    }

    public void save(ContactFrontDto dto) {
        TransactionHandler.run(connection -> {
            Long id = saveContact(connection, dto.getContact());
            savePhoto(connection, dto.getPhoto(), id);
            savePhones(connection, dto, id);
            saveAttachments(connection, dto, id);
        });
    }

    public void update(ContactFrontDto dto, Long id) {
        TransactionHandler.run(connection -> {
            dto.getContact().setId(id);
            updateContact(connection, dto.getContact());
            savePhoto(connection, dto.getPhoto(), id);
            savePhones(connection, dto, id);
            saveAttachments(connection, dto, id);
        });
    }

    public void delete(Long id) {
        TransactionHandler.run(connection -> {
            deleteContact(connection, id);
        });
    }

    private Long saveContact(Connection connection, Contact contact) {
        ContactDao dao = new ContactDao(connection);
        return dao.save(contact);
    }

    private void updateContact(Connection connection, Contact contact) {
        ContactDao dao = new ContactDao(connection);
        dao.update(contact);
    }

    private void deleteContact(Connection connection, Long id) {
        ContactDao dao = new ContactDao(connection);
        dao.delete(id);
    }

    private Contact getContact(Connection connection, Long id) {
        ContactDao dao = new ContactDao(connection);
        return dao.getById(id);
    }

    private void savePhoto(Connection connection, Part photo, Long contactId) {
        if (photo != null && photo.getSize() > 0) {
            try {
                String photoPath = savePhotoToFile(photo, contactId);

                ContactDao dao = new ContactDao(connection);
                dao.updatePhotoPathById(photoPath, contactId);
            } catch (IOException e) {
                // todo msg
            }
        }
    }

    private List<PhoneInfoDto> getPhones(Connection connection, Long contactId) {
        PhoneDao dao = new PhoneDao(connection);
        List<Phone> phones = dao.getByContactId(contactId);

        List<PhoneInfoDto> dtoList = new ArrayList<>();
        for (Phone phone : phones) {
            dtoList.add(DtoUtils.convertToDto(phone));
        }

        return dtoList;
    }

    private List<AttachmentInfoDto> getAttachments(Connection connection, Long contactId) {
        AttachmentDao dao = new AttachmentDao(connection);
        List<Attachment> attachments = dao.getByContactId(contactId);

        List<AttachmentInfoDto> dtoList = new ArrayList<>();
        for (Attachment attachment : attachments) {
            dtoList.add(DtoUtils.convertToDto(attachment));
        }

        return dtoList;
    }

    private void savePhones(Connection connection, ContactFrontDto dto, Long contactId) {
        PhoneDao dao = new PhoneDao(connection);
        saveAddedPhones(dao, dto.getAddedPhones(), contactId);
        updatePhones(dao, dto.getUpdatedPhones(), contactId);
        deletePhones(dao, dto.getDeletedPhonesIds());
    }

    private void saveAddedPhones(PhoneDao dao, List<Phone> phones, Long contactId) {
        for (Phone phone : phones) {
            phone.setContactId(contactId);
            dao.save(phone);
        }
    }

    private void updatePhones(PhoneDao dao, List<Phone> phones, Long contactId) {
        for (Phone phone : phones) {
            phone.setContactId(contactId);
            dao.update(phone);
        }
    }

    private void deletePhones(PhoneDao dao, List<Long> ids) {
        for (Long id : ids) {
            dao.delete(id);
        }
    }

    private void saveAttachments(Connection connection, ContactFrontDto contact, Long contactId) {
        AttachmentDao dao = new AttachmentDao(connection);
        saveAddedAttachments(dao, contact.getAddedAttachments(), contactId);
        updateAttachments(dao, contact.getUpdatedAttachments(), contactId);
        deleteAttachments(dao, contact.getDeletedAttachmentsIds());
    }

    private void saveAddedAttachments(AttachmentDao dao, List<AttachmentFrontDto> attachments, Long contactId) {
        for (AttachmentFrontDto dto : attachments) {
            Attachment attachment = DtoUtils.convertToAttachment(dto);
            attachment.setContactId(contactId);

            if (dto.getFile() != null) {
                try {
                    String fileName = savePartToFile(dto.getName(), dto.getFile(), contactId);
                    attachment.setFileName(fileName);
                    dao.save(attachment);
                } catch (IOException e) {
                    //todo msg
                }
            }
        }
    }

    private void updateAttachments(AttachmentDao dao, List<AttachmentFrontDto> attachments, Long contactId) {
        for (AttachmentFrontDto dto : attachments) {
            Attachment attachment = DtoUtils.convertToAttachment(dto);
            attachment.setContactId(contactId);

            if (dto.getFile() != null) {
                try {
                    String fileName = savePartToFile(dto.getName(), dto.getFile(), contactId);
                    attachment.setFileName(fileName);
                    dao.update(attachment);
                } catch (IOException e) {
                    //todo msg
                }
            }
        }
    }

    private void deleteAttachments(AttachmentDao dao, List<Long> ids) {
        for (Long id : ids) {
            dao.delete(id);
        }
    }

    private String savePhotoToFile(Part photo, Long contactId) throws IOException {
        String fileName = savePartToFile(PROFILE_PIC_NAME, photo, contactId);
        return fileName;
    }


    // todo maybe save attachments by name
    private String savePartToFile(String name, Part part, Long contactId) throws IOException {
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
