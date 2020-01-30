/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.spring.boot.example.webapp.ee.identity.query;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.query.Query;

public abstract class AbstractInMemoryQuery<Q extends Query<Q, T>, T> implements Query<Q, T> {

  @Override
  public T singleResult() {
    List<T> result = list();
    if (result.size() == 0) {
      return null;
    }
    else if (result.size() == 1) {
      return result.get(0);
    }
    else {
      throw new ProcessEngineException("More than one result");
    }
  }

  @Override
  public List<T> list() {
    return listPage(0, Integer.MAX_VALUE);
  }

  @Override
  public long count() {
    return list().size();
  }
}
