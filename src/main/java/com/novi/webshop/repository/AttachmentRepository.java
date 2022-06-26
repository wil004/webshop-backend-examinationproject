package com.novi.webshop.repository;

import com.novi.webshop.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, String > {
}
