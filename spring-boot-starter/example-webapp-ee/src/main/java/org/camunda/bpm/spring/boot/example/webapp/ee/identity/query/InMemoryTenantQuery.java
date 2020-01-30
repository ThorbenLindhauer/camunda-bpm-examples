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
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.TenantQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.InMemoryReadableIdentityProvider;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryTenant;

public class InMemoryTenantQuery extends AbstractInMemoryQuery<TenantQuery, Tenant> implements TenantQuery {

  private List<Predicate<InMemoryTenant>> filters = new ArrayList<>();

  private String userId;

  @Override
  public TenantQuery asc() {
    return this;
  }

  @Override
  public TenantQuery desc() {
    return this;
  }

  @Override
  public List<Tenant> listPage(int firstResult, int maxResults) {
    Stream<InMemoryTenant> stream = InMemoryReadableIdentityProvider.TENANTS.stream();
    for (Predicate<InMemoryTenant> filter : filters) {
      stream = stream.filter(filter);
    }

    return stream.skip(firstResult).limit(maxResults).collect(Collectors.toList());
  }

  @Override
  public TenantQuery tenantId(String tenantId) {
    this.filters.add(t -> t.getId().equals(tenantId));
    return this;
  }

  @Override
  public TenantQuery tenantIdIn(String... ids) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public TenantQuery tenantName(String tenantName) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public TenantQuery tenantNameLike(String tenantNameLike) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public TenantQuery userMember(String userId) {
    this.filters.add(t -> t.getUserMembers().contains(userId));
    this.userId = userId;
    return this;
  }

  @Override
  public TenantQuery groupMember(String groupId) {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public TenantQuery includingGroupsOfUser(boolean includingGroups) {
    if (includingGroups) {
      this.filters.add(t -> {
        Set<String> groupIds = InMemoryReadableIdentityProvider.GROUPS.stream()
            .filter(g -> g.getMembers().contains(userId))
            .map(g -> g.getId())
            .collect(Collectors.toSet());

        for (String groupId : groupIds) {
          if (t.getGroupMembers().contains(groupId)) {
            return true;
          }
        }

        return false;
      });
    }

    return this;
  }

  @Override
  public TenantQuery orderByTenantId() {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public TenantQuery orderByTenantName() {
    throw new BadUserRequestException("Not supported");
  }

}
