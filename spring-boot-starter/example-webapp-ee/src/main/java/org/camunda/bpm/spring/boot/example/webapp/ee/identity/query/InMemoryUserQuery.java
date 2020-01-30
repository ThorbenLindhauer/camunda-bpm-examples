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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.InMemoryReadableIdentityProvider;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryUser;

public class InMemoryUserQuery extends AbstractInMemoryQuery<UserQuery, User> implements UserQuery {

  private List<Predicate<InMemoryUser>> filters = new ArrayList<>();

  @Override
  public UserQuery asc() {
    return this;
  }

  @Override
  public UserQuery desc() {
    return this;
  }

  @Override
  public List<User> listPage(int firstResult, int maxResults) {
    Stream<InMemoryUser> stream = InMemoryReadableIdentityProvider.USERS.stream();
    for (Predicate<InMemoryUser> filter : filters) {
      stream = stream.filter(filter);
    }

    return stream.skip(firstResult).limit(maxResults).collect(Collectors.toList());
  }

  @Override
  public UserQuery userId(String id) {
    filters.add(u -> u.getId().equals(id));
    return this;
  }

  @Override
  public UserQuery userIdIn(String... ids) {

    Set<String> idSet = new HashSet<>();
    for (String id : ids) {
      idSet.add(id);
    }
    filters.add(u -> idSet.contains(u.getId()));

    return this;
  }

  @Override
  public UserQuery userFirstName(String firstName) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery userFirstNameLike(String firstNameLike) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery userLastName(String lastName) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery userLastNameLike(String lastNameLike) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery userEmail(String email) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery userEmailLike(String emailLike) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery memberOfGroup(String groupId) {
    this.filters.add(u -> u.getGroupMemberships().contains(groupId));
    return this;
  }

  @Override
  public UserQuery potentialStarter(String procDefId) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery memberOfTenant(String tenantId) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery orderByUserId() {

    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery orderByUserFirstName() {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery orderByUserLastName() {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public UserQuery orderByUserEmail() {
    throw new BadUserRequestException("Not supported");
  }

}
