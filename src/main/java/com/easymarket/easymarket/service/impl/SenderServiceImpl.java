package com.easymarket.easymarket.service.impl;

import com.easymarket.easymarket.entity.Sender;
import com.easymarket.easymarket.service.SenderService;
import org.springframework.stereotype.Service;

@Service
public class SenderServiceImpl implements SenderService {

    @Override
    public void testEmail(String Username) {
        StringBuffer sb = new StringBuffer();
        sb.append("Hello, " + Username + "!\n\n");
        sb.append("Date: 24.12.12 \n");
        sb.append("Total Price: 1211$ \n\n");
        sb.append("Cities: 2 \n");
        sb.append("Cargos: 8 \n");
        sb.append("Gracios!");
        String eml = "egorkamysh99@gmail.com";
        String text = sb.toString();
        Sender sender = new Sender();
        sender.send("Purchase information from EasyMarket©", text, eml);
    }
}

