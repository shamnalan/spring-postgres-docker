package org.jug.algeria.repository;

import org.jug.algeria.domain.AppList;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppList, Long> {
}
