/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.jetbrains.youtrack.db.spring.data.gremlin.repository.support;

import com.jetbrains.youtrack.db.spring.data.gremlin.common.TestConstants;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.domain.Person;
import com.jetbrains.youtrack.db.spring.data.gremlin.common.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class GremlinRepositoryFactoryBeanUnitTest {

    @Autowired
    private ApplicationContext context;

    private GremlinRepositoryFactoryBean factoryBean;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        this.factoryBean = new GremlinRepositoryFactoryBean(PersonRepository.class);
    }

    @Test
    public void testGetFactoryInstance() {
        final Person person = new Person(TestConstants.VERTEX_PERSON_ID, TestConstants.VERTEX_PERSON_NAME);
        final RepositoryFactorySupport factorySupport = this.factoryBean.getFactoryInstance(this.context);

        Assert.assertNotNull(factorySupport);
        Assert.assertEquals(factorySupport.getEntityInformation(Person.class).getIdType(), String.class);
        Assert.assertEquals(factorySupport.getEntityInformation(Person.class).getId(person), person.getId());
    }
}
