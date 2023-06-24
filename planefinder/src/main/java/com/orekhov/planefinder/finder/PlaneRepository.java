package com.orekhov.planefinder.finder;

import org.springframework.data.repository.CrudRepository;

public interface PlaneRepository extends CrudRepository<Aircraft, Long> {
}

