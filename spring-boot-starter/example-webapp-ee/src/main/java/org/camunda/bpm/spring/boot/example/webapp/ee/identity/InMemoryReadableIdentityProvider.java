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
package org.camunda.bpm.spring.boot.example.webapp.ee.identity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.NativeUserQuery;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.TenantQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryGroup;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryTenant;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.model.InMemoryUser;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.query.InMemoryGroupQuery;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.query.InMemoryTenantQuery;
import org.camunda.bpm.spring.boot.example.webapp.ee.identity.query.InMemoryUserQuery;

public class InMemoryReadableIdentityProvider implements ReadOnlyIdentityProvider {

  public static final List<InMemoryUser> USERS = new CopyOnWriteArrayList<>();
  public static final List<InMemoryGroup> GROUPS = new CopyOnWriteArrayList<>();
  public static final List<InMemoryTenant> TENANTS = new CopyOnWriteArrayList<>();

  @Override
  public void flush() {
  }

  @Override
  public void close() {
  }

  @Override
  public User findUserById(String userId) {
    return createUserQuery().userId(userId).singleResult();
  }

  @Override
  public UserQuery createUserQuery() {
    return new InMemoryUserQuery();
  }

  @Override
  public UserQuery createUserQuery(CommandContext commandContext) {
    return new InMemoryUserQuery();
  }

  @Override
  public NativeUserQuery createNativeUserQuery() {
    throw new BadUserRequestException("Not supported");
  }

  @Override
  public boolean checkPassword(String userId, String password) {
    User user = findUserById(userId);
    return user.getPassword().equals(password);
  }

  @Override
  public Group findGroupById(String groupId) {
    return createGroupQuery().groupId(groupId).singleResult();
  }

  @Override
  public GroupQuery createGroupQuery() {
    return new InMemoryGroupQuery();
  }

  @Override
  public GroupQuery createGroupQuery(CommandContext commandContext) {
    return createGroupQuery();
  }

  @Override
  public Tenant findTenantById(String tenantId) {
    return createTenantQuery().tenantId(tenantId).singleResult();
  }

  @Override
  public TenantQuery createTenantQuery() {
    return new InMemoryTenantQuery();
  }

  @Override
  public TenantQuery createTenantQuery(CommandContext commandContext) {
    return createTenantQuery();
  }

}
