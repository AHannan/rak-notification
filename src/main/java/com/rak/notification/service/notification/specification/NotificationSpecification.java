package com.rak.notification.service.notification.specification;

import com.rak.notification.dao.model.notification.Notification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NotificationSpecification {

    public static Specification<Notification> filterByUserId(String userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<Notification> buildSpecification(String userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(filterByUserId(userId).toPredicate(root, query, criteriaBuilder));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
