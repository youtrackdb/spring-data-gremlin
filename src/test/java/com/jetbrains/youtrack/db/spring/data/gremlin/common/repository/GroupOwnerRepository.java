/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.common.repository;

import com.jetbrains.youtrack.db.spring.data.gremlin.common.domain.GroupOwner;
import com.jetbrains.youtrack.db.spring.data.gremlin.repository.GremlinRepository;

public interface GroupOwnerRepository extends GremlinRepository<GroupOwner, String> {
}
