package com.amela.repository;

import com.amela.model.Feedback;
import com.amela.model.house.House;
import com.amela.model.house.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback,Long> {

}
