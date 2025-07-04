/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package web.service.springdata.gremlin.domain;

import com.jetbrains.spring.data.gremlin.annotation.Edge;
import com.jetbrains.spring.data.gremlin.annotation.EdgeFrom;
import com.jetbrains.spring.data.gremlin.annotation.EdgeTo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;


@Data
@Edge
@AllArgsConstructor
@NoArgsConstructor
public class ServicesDataFlow {

    @Id
    private String id;

    @EdgeFrom
    private MicroService serviceFrom;

    @EdgeTo
    private MicroService serviceTo;

    private Map<String, Object> properties = new HashMap<>();
}

