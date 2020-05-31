package com.easymarket.easymarket.service.impl;

import com.easymarket.easymarket.entity.Cargo;
import com.easymarket.easymarket.entity.CargoCondition;
import com.easymarket.easymarket.entity.Sender;
import com.easymarket.easymarket.entity.User;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.repository.CargoRepository;
import com.easymarket.easymarket.service.CargoService;
import com.easymarket.easymarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {
    private CargoRepository cargoRepository;
    private UserServiceImpl userService;

    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Override
    public Page<Cargo> getAll(Pageable pageable) {
        return cargoRepository.findAll(pageable);
    }

    @Override
    public Page<Cargo> getAllByUserId(Long id, Pageable pageable) {
        return cargoRepository.findAllByUserId(id, pageable);
    }

    @Override
    public Page<Cargo> getAllFreeCargo(Pageable pageable) {
        return cargoRepository.findAllByCargoCondition(CargoCondition.FREE, pageable);
    }

    @Override
    public Cargo getById(Long id) throws ResourceNotFoundException {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo doesn't exist with id " + id));
    }

    @Override
    public List<Cargo> updateCondition(List<Cargo> cargo, CargoCondition cargoCondition) {
        List<Cargo> saved = new ArrayList<>();
        for (Cargo temp : cargo) {
            temp.setCargoCondition(cargoCondition);
            saved.add(cargoRepository.save(temp));
        }
        System.out.println(saved);
        return saved;
    }

    @Override
    public void update(Long id, Cargo cargo) throws ResourceNotFoundException {
        cargoRepository.save(cargo);
    }

    @Override
    public void save(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    @Override
    public void delete(Cargo cargo) {
        cargoRepository.delete(cargo);
    }

    @Transactional
    @Override
    public void sendConfirmToEmail(Long cargoId, double sum) throws ResourceNotFoundException {
        BigDecimal newSum = BigDecimal.valueOf(sum);
        String hash = Base64.getUrlEncoder().encodeToString((cargoId.toString() + ";" + (newSum.toString())).getBytes());
        String url = "http://localhost:4200/" + cargoId + "/confirm/" + hash;
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append( "<body>\n" );
        html.append("<h2>Добрый день, ").append(getById(cargoId).getUser().getName()).append("!</h2>\n");
        html.append("<p>Это очень важное письмо пришло, чтобы вы подтвердили оплату доставки.</p>\n");
        html.append("<p>Нажмите на эту ссылку:<a href=\"").append(url).append("\">EasyMarket</a>\n</p>\n");
        html.append( "</body>\n" );
        html.append( "</html>" );
        Sender sender = new Sender();
        String subject = "Подтверждение оплаты";
        sender.send(subject, html.toString(), getById(cargoId).getUser().getEmail());
    }



    @Override
    public void updatePaid(Long id, boolean isPaid) throws ResourceNotFoundException {
        Cargo oldCargo = cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo doesn't exist with id " + id));
        oldCargo.setIsPaid(isPaid);
        cargoRepository.save(oldCargo);
    }

    @Override
    public void confirm(String hashConfirm) throws Exception {
        String[] strArr;
        String delimeter = ";";
        byte[] decodedBytes = Base64.getUrlDecoder().decode(hashConfirm);
        String hash = new String(decodedBytes);
        strArr = hash.split(delimeter);
        double sum = Double.valueOf(strArr[1]);
        long cargoId = Long.valueOf(strArr[0]);
        if (!getById(cargoId).getIsPaid().booleanValue()) {
            String EmailUser = getById(cargoId).getTrip().getUser().getEmail();
            StringBuilder html = new StringBuilder();
            html.append("<html>\n");
            html.append( "<body>\n" );
            html.append("<h2>Добрый день, ").append(getById(cargoId).getTrip().getUser().getName()).append("!</h2>\n");
            html.append("<p>Это очень важное письмо пришло, чтобы уведомить вас. Вы получили " + sum +"$ :) </p>\n");
            html.append( "</body>\n" );
            html.append( "</html>" );
            Sender sender = new Sender();
            String subject = "Вы получили оплату";
            sender.send(subject, html.toString(), EmailUser);

            getById(cargoId).setIsPaid(true);
            cargoRepository.save(getById(cargoId));

        } else throw new Exception("Oh no, we have a problem! This link has already been used!");
    }

}
