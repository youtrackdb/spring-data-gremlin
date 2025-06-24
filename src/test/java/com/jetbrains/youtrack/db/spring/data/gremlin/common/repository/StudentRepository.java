/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common.repository;

import com.jetbrains.youtrack.db.spring.data.gremlin.common.domain.Student;
import com.jetbrains.youtrack.db.spring.data.gremlin.repository.GremlinRepository;

import java.util.List;

public interface StudentRepository extends GremlinRepository<Student, Long> {

    List<Student> findByName(String name);
}
