package com.amela.service.feedback;

import com.amela.model.Feedback;
import com.amela.model.house.House;
import com.amela.model.house.Image;
import com.amela.service.IGeneralService;

public interface IFeedbackService extends IGeneralService<Feedback> {
    Iterable<Feedback> findAllByHouse(House house);
}
