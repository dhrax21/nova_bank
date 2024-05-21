package com.novabank.Novabank.Service;

import com.novabank.Novabank.DTO.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}
