package com.example.libreria_app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libreria_app.model.Mensajeria;

@Repository
public interface MensajeriaRepository extends JpaRepository<Mensajeria, Long> {
    List<Mensajeria> findBySenderIdOrRecipientIdOrderByTimestampAsc(Long senderId, Long recipientId);
    List<Mensajeria> findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampAsc(Long sender1, Long recipient1, Long sender2, Long recipient2);
}
