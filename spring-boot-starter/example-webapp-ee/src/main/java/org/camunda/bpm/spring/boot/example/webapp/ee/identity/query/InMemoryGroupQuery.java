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
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.InMemoryReadableIdentityProvider;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryGroup;

public class InMemoryGroupQuery extends AbstractInMemoryQuery<GroupQuery, Group> implements GroupQuery {

  private List<Predicate<InMemoryGroup>> filters = new ArrayList<>();

  @Override
  public GroupQuery asc() {
    return this;
  }

  @Override
  public GroupQuery desc() {
    return this;
  }

  @Override
  public List<Group> listPage(int firstResult, int maxResults) {
    Stream<InMemoryGroup> stream = InMemoryReadableIdentityProvider.GROUPS.stream();
    for (Predicate<InMemoryGroup> filter : filters) {
      stream = stream.filter(filter);
    }

    return stream.skip(firstResult).limit(maxResults).collect(Collectors.toList());
  }

  @Override
  public GroupQuery groupId(String groupId) {
    this.filters.add(g -> g.getId().equals(groupId));
    return this;
  }

  @Override
  public GroupQuery groupIdIn(String... ids) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery groupName(String groupName) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery groupNameLike(String groupNameLike) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery groupType(String groupType) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery groupMember(String groupMemberUserId) {
    this.filters.add(g -> g.getMembers().contains(groupMemberUserId));
    return this;
  }

  @Override
  public GroupQuery potentialStarter(String procDefId) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery memberOfTenant(String tenantId) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery orderByGroupId() {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery orderByGroupName() {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public GroupQuery orderByGroupType() {
    throw new BadUserRequestException("Not supported");
  }

}
